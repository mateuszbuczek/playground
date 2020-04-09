import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\workspace");

        ArrayList<Integer> data = new ArrayList<>();
        data.add(35);
        data.add(213);
        data.add(9);

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]").set("spark.driver.host", "127.0.0.1");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = sparkContext.parallelize(data);
        JavaRDD<Double> map = rdd.map(Math::sqrt);

        JavaRDD<Tuple2<Integer, Double>> sqrtRdd = rdd.map(value -> new Tuple2<>(value, Math.sqrt(value)));

        sparkContext.close();
    }
}
