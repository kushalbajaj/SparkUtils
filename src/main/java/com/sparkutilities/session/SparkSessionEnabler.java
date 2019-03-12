package com.sparkutilities.session;

import org.apache.spark.sql.SparkSession;

public class SparkSessionEnabler {


    public static SparkSession getSparkSession() {
        return SparkSession
                .builder()
                .appName("Spark Structured Streaming Example")
                .master("local[1]")
                .getOrCreate();
    }

}
