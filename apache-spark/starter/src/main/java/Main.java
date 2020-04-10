import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;
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
        ArrayList<String> data = new ArrayList<>();
        data.add("WARN: 4 September 0405");
        data.add("ERROR: 2 March 2021");
        data.add("WARN: 4 March 2021");

        sparkContext.parallelize(data)
                .flatMap(value -> Arrays.asList(value.split(" ")).iterator())
                .filter(word -> word.contains("0"))
                .foreach(value -> System.out.println(value));
    }
}
