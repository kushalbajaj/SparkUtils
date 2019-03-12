package com.sparkutilities.stream.structuredstream;

import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import com.sparkutilities.pojo.Sensor;

import java.util.*;

public class StructuredSample {

    public static void main(String[] args) {

        Map<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", "StringDeserializer.class");
        kafkaParams.put("value.deserializer", "StringDeserializer.class");


        Set<String> topics =  new HashSet<>(Arrays.asList("target"));

        SparkSession spark = SparkSession
                .builder()
                .appName("Spark Structured Streaming Example")
                .master("local[1]")
                .getOrCreate();

        Dataset<Row> load = spark.readStream()
                .format("kafka")
                .option("key.deserializer", "StringDeserializer.class")
                .option("value.deserializer", "StringDeserializer.class")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "test")
                .load();

        Dataset<Row> rowDataset = load.selectExpr("CAST(value AS STRING)");
        Encoder<Sensor> personEncoder = Encoders.bean(Sensor.class);
        StreamingQuery query = rowDataset.writeStream()
                .format("console")
                .start();



        //load.printSchema();


        Dataset<Row> kafkaDataset = load.selectExpr("CAST(value AS STRING)");


      try {
            //ssc.awaitTermination();
            spark.streams().awaitAnyTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        }


    }



}

