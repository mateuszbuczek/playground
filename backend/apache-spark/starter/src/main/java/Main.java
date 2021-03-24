import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "C:\\workspace");

        Logger.getLogger("org.apache").setLevel(Level.WARN);

        SparkConf conf = new SparkConf().setAppName("startingSpark").setMaster("local[*]").set("spark.driver.host", "127.0.0.1");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        flatMapExample(sparkContext);

        sparkContext.close();
    }

    private static void flatMapExample(JavaSparkContext sparkContext) {

        JavaRDD<String> rdd = sparkContext.textFile("src/main/resources/input-spring.txt");

        rdd
                .map(text -> text.replaceAll("[^a-zA-Z\\s]", "").toLowerCase())
                .flatMap(value -> Arrays.asList(value.split(" ")).iterator())
                .filter(text -> text.trim().length() > 0)
                .filter(Util::isNotBoring)
                .mapToPair(word -> new Tuple2<>(word, 1L))
                .reduceByKey(Long::sum)
                .map(value -> new Tuple2<>(value._2, value._1))
                .sortBy(value -> value._1, false, 1)
                .take(10)
                .forEach(System.out::println);
    }
}
