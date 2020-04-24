package linearregression;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.ml.evaluation.RegressionEvaluator;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.param.ParamMap;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.tuning.ParamGridBuilder;
import org.apache.spark.ml.tuning.TrainValidationSplit;
import org.apache.spark.ml.tuning.TrainValidationSplitModel;
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

        Dataset<Row>[] dataSplits = labelAndFeatures.randomSplit(new double[]{0.8, 0.2});
        Dataset<Row> trainingData = dataSplits[0];
        Dataset<Row> holdoutData = dataSplits[1];

        LinearRegression linearRegression = new LinearRegression();

        ParamMap[] paramMaps = new ParamGridBuilder()
                .addGrid(linearRegression.regParam(), new double[] {0.01, 0.1, 0.5})
                .addGrid(linearRegression.elasticNetParam(), new double[] {0, 0.5, 1})
                .build();

        TrainValidationSplit trainValidationSplit = new TrainValidationSplit()
                .setEstimator(linearRegression)
                .setEvaluator(new RegressionEvaluator().setMetricName("r2"))
                .setEstimatorParamMaps(paramMaps)
                .setTrainRatio(0.8);

        TrainValidationSplitModel model = trainValidationSplit.fit(trainingData);
        LinearRegressionModel lrModel = (LinearRegressionModel) model.bestModel();

        lrModel.transform(holdoutData).show();

        System.out.println("The training data r2 value is " + lrModel.summary().r2() + " and the RMSE is " + lrModel.summary().rootMeanSquaredError());
        System.out.println("The testing data r2 value is " + lrModel.evaluate(holdoutData).r2() + " and the RMSE is " + lrModel.evaluate(holdoutData).rootMeanSquaredError());
        System.out.println("best model regParam " + lrModel.getRegParam() + " elasticNetParam " + lrModel.getElasticNetParam());
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
