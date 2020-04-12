package linearregression;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class GymCompetition {

    public static void main(String[] args) {
        SparkSession session = getSession();

        Dataset<Row> csvData = session.read()
                .option("inferSchema", true)
                .option("header", true)
                .csv("src/main/resources/GymCompetition.csv");

        csvData.show();

        Dataset<Row> labelAndFeatures = new VectorAssembler()
                .setInputCols(new String[]{"Age", "Height", "Weight"})
                .setOutputCol("features")
                .transform(csvData)
                .select("NoOfReps", "features")
                .withColumnRenamed("NoOfReps", "label");

        labelAndFeatures.show();

        LinearRegressionModel model = new LinearRegression()
                .fit(labelAndFeatures);

        System.out.println(String.format("Model intercept is '%s' and coefficients '%s'", model.intercept(), model.coefficients()));

        model.transform(labelAndFeatures).show();
    }

    private static SparkSession getSession() {
        System.setProperty("hadoop.home.dir", "C:\\workspace");

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        return SparkSession.builder()
                .appName("Gym competitors")
                .config("spark.sql.warehouse.dir", "file:///c:/tmp/")
                .master("local[*]")
                .config("spark.driver.host", "127.0.0.1")
                .getOrCreate();
    }
}
