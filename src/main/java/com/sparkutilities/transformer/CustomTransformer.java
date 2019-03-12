package com.sparkutilities.transformer;

import com.streamsets.pipeline.api.Record;
import com.streamsets.pipeline.spark.api.SparkTransformer;
import com.streamsets.pipeline.spark.api.TransformResult;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import scala.Tuple2;

import java.io.Serializable;
import java.util.List;

public class CustomTransformer extends SparkTransformer implements Serializable {
    private transient JavaSparkContext javaSparkContext;

    @Override
    public void init(JavaSparkContext javaSparkContext, List<String> params) {
        this.javaSparkContext = javaSparkContext;
    }

    @Override
    public TransformResult transform(JavaRDD<Record> records) {
        // Create an empty errors JavaPairRDD
        SparkConf conf = javaSparkContext.getConf();
        conf.set("spark.executor.instances", "10");
        conf.setMaster("yarn");
        javaSparkContext.close();
        javaSparkContext=new JavaSparkContext(conf);

      /*  SQLContext sq = new SQLContext(javaSparkContext);
        Dataset<Row> dataFrame = sq.createDataFrame(records, String.class);

        Encoder<Sensor> sensorEncoder = Encoders.bean(Sensor.class);
        //Util.getMappedObjectFromJson(row.toString(), Sensor.class);
        Dataset<Sensor> sensorDataset = dataFrame.map(new MapFunction<Row, Sensor>() {
            @Override
            public Sensor call(Row row) throws Exception {
                Sensor sensor = Util.getMappedObjectFromJson(row.toString(), Sensor.class);
                sensor.setSensorId(UUIDs.random());
                System.out.println(sensor);
                return sensor;
            }
        }, sensorEncoder);
*/

        JavaRDD<Tuple2<Record, String>> emptyRDD = javaSparkContext.emptyRDD();
        JavaPairRDD<Record, String> errors = JavaPairRDD.fromJavaRDD(emptyRDD);


        // Apply a map to the incoming records
        JavaRDD<Record> result = records.map(new Function<Record, Record>() {
            @Override
            public Record call(Record record) throws Exception {
                // Just return the incoming record
                System.out.println(record);
                return record;
            }
        });
        return new TransformResult(result, errors);
    }
}