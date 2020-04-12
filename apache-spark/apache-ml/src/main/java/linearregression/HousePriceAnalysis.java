package linearregression;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class HousePriceAnalysis {

    public static void main(String[] args) {
        SparkSession session = getSession();

        Dataset<Row> csvData = session.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("src/main/resources/kc_house_data.csv");

        Dataset<Row> labelAndFeatures = new VectorAssembler()
                .setInputCols(new String[]{"bedrooms", "bathrooms", "sqft_living", "sqft_lot", "floors", "grade"})
                .setOutputCol("features")
                .transform(csvData)
                .select("price", "features")
                .withColumnRenamed("price", "label");

        Dataset<Row>[] trainingAndTestData = labelAndFeatures.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = trainingAndTestData[0];
        Dataset<Row> testingData = trainingAndTestData[1];

        LinearRegressionModel model = new LinearRegression()
                .fit(trainingData);

        model.transform(testingData).show();

        System.out.println("The training data r2 value is " + model.summary().r2() + " and the RMSE is " + model.summary().rootMeanSquaredError());
        System.out.println("The testing data r2 value is " + model.evaluate(testingData).r2() + " and the RMSE is " + model.evaluate(testingData).rootMeanSquaredError());
    }

    private static SparkSession getSession() {
        System.setProperty("hadoop.home.dir", "C:\\workspace");

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        return SparkSession.builder()
                .appName("House price analysis")
                .config("spark.sql.warehouse.dir", "file:///c:/tmp/")
                .master("local[*]")
                .config("spark.driver.host", "127.0.0.1")
                .getOrCreate();
    }
}
