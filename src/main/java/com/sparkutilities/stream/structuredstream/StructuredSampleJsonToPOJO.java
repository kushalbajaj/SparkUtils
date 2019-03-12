package com.sparkutilities.stream.structuredstream;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sparkutilities.pojo.Sensor;

import java.util.*;

public class StructuredSampleJsonToPOJO {

    public static void main(String[] args) {

        Map<String, String> kafkaParams = new HashMap<>();
        setKafkaParams(kafkaParams);


        Set<String> topics =  new HashSet<>(Arrays.asList("target"));

        SparkSession spark = getSparkSession();

        Dataset<Row> load = spark.readStream()
                .format("kafka")
                .option("key.deserializer", "StringDeserializer.class")
                .option("value.deserializer", "StringDeserializer.class")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "target")
                .load();

        Dataset<Row> rowDataset = load.selectExpr("CAST(value AS STRING)");
/*        StreamingQuery query = rowDataset.writeStream()
                .format("console")
                .start();*/

        //load.printSchema();
        Encoder<Sensor> sensorEncoder = Encoders.bean(Sensor.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Dataset<Sensor> map = rowDataset.map(new MapFunction<Row, Sensor>() {

            @Override
            public Sensor call(Row row) throws Exception {
                String s = row.toString().replaceFirst(";","");
                System.out.println(s);
                JSONArray jsonArray = new JSONArray(s);
               JSONObject jsonElement = jsonArray.getJSONObject(0);
                Sensor sensor = objectMapper.readValue(jsonElement.toString(), Sensor.class);
                System.out.println(sensor);

                return sensor;
            }
        }, sensorEncoder);

        StreamingQuery query = map.writeStream()
                .format("console")
                .start();

        //  Dataset<Row> kafkaDataset = load.selectExpr("CAST(value AS STRING)");

      try {
            //ssc.awaitTermination();
            spark.streams().awaitAnyTermination();
        } catch (StreamingQueryException e) {
            e.printStackTrace();
        }


    }

    private static void setKafkaParams(Map<String, String> kafkaParams) {
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", "StringDeserializer.class");
        kafkaParams.put("value.deserializer", "StringDeserializer.class");
    }

    private static SparkSession getSparkSession() {
        return SparkSession
                .builder()
                .appName("Spark Structured Streaming Example")
                .master("local[1]")
                .getOrCreate();
    }


}

