import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\workspace");

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]").set("spark.driver.host", "127.0.0.1");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        reduceByKeyExample(sparkContext);

        sparkContext.close();
    }

    private static void reduceByKeyExample(JavaSparkContext sparkContext) {
        ArrayList<String> data = new ArrayList<>();
        data.add("WARN: 4 September 0405");
        data.add("ERROR: 2 March 2021");
        data.add("WARN: 4 March 2021");

        sparkContext.parallelize(data)
                .mapToPair(value -> new Tuple2<>(value.split(":")[0], 1L))
                .reduceByKey(Long::sum)
                .foreach(tuple -> System.out.println(tuple._1 + " has " + tuple._2));
    }
}
