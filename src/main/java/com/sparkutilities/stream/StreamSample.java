package com.sparkutilities.stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.StreamingContext;
import org.apache.spark.streaming.dstream.DStream;

public class StreamSample {


    static JavaSparkContext context;



    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();

        sparkConf.setAppName("Hello Spark");
        sparkConf.setMaster("local[1]");
        StreamingContext ssc = new StreamingContext(sparkConf, new Duration(10000));
        DStream<String> stringDStream = ssc.textFileStream(args[0]); //"file:///C:\Users\kushalbajaj\Desktop\a\"

        stringDStream.print();

        ssc.start();
        ssc.awaitTermination();
        context.close();

    }
}

