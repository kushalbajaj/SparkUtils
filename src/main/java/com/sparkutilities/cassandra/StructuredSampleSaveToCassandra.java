package com.sparkutilities.cassandra;

import com.sparkutilities.session.SparkSessionEnabler;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.*;

public class StructuredSampleSaveToCassandra {

    public static void main(String[] args) {

        Map<String, String> kafkaParams = new HashMap<>();
        setKafkaParams(kafkaParams);


        Set<String> topics =  new HashSet<>(Arrays.asList("target"));

        SparkSession spark = SparkSessionEnabler.getSparkSession();

        Dataset<Row> load = spark.readStream()
                .format("kafka")
                .option("key.deserializer", "StringDeserializer.class")
                .option("value.deserializer", "StringDeserializer.class")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "target")
                .load();

        Dataset<Row> rowDataset = load.selectExpr("CAST(value AS STRING)");
      /*
      Sample Transformations

      Encoder<Sensor> sensorEncoder = Encoders.bean(Sensor.class);
        Dataset<Sensor> sensorDataset=rowDataset.map((MapFunction<Row,Sensor>)row->
            Util.getMappedObjectFromJson(row.toString(), Sensor.class),sensorEncoder);

        sensorDataset.foreach((ForeachFunction<Sensor>)  sensor ->sensor.setValue(sensor.getValue()+1));*/

rowDataset.writeStream().foreach(new CassandraWriter("localhost",9042,"sample_keyspace")).start();

      try {
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




}
