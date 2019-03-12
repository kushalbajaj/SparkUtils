/*
import kafka.serializer.StringDecoder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import scala.Tuple2;

import java.util.*;

public class BinaryStreamSample {

    public static void main(String[] args) {




       SparkConf sparkConf = new SparkConf();

        sparkConf.setAppName("Hello Spark");
        sparkConf.setMaster("local[1]");
        sparkConf.set("spark.streaming.minRememberDuration", String.valueOf(Integer.MAX_VALUE));
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(10000));

*/
/*
       int numThreads = Integer.parseInt(args[1]);
        Map<String, Integer> topicMap = new HashMap<>();
        String[] topics = "target".split(",");
        for (String topic: topics) {
            topicMap.put(topic, numThreads);
        }
        JavaPairReceiverInputDStream<String, String> stream = KafkaUtils.createStream(ssc, "localhost:2181", "spark-group", topicMap);
        HashMap<String, String> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", BROKERS);
        KafkaUtils.create(ssc,)*//*


        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", "lol-2");
        kafkaParams.put("auto.offset.reset", "latest");
        kafkaParams.put("enable.auto.commit", false);

        Collection<String> topics =  Arrays.asList("target");

        final JavaInputDStream<ConsumerRecord<String, String>> directStream =
                KafkaUtils.createDirectStream(
                        ssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.Subscribe(topics, kafkaParams)
                );



        //JavaPairInputDStream<String, String> directStream = KafkaUtils.createDirectStream(ssc, String.class, String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);
         directStream.print();
        JavaPairDStream<String, String> stringStringJavaPairDStream = directStream.mapToPair(record -> new Tuple2<>(record.key(), record.value()));
        stringStringJavaPairDStream.print();

        // rowDataset.show();
        //rowDataset.printSchema();

      try {
            ssc.awaitTermination();
            //spark.streams().awaitAnyTermination();
        }  catch (InterruptedException e) {
          e.printStackTrace();
      }
        */
/*context.close();*//*


    }

*/
/*    private static JavaSparkContext getJavaSparkContext() {
        if(context==null) {
            SparkConf sparkConf = new SparkConf();

            sparkConf.setAppName("Hello Spark");
            sparkConf.setMaster("local");
            sparkConf.set("spark.cassandra.connection.host", "localhost");
            return new JavaSparkContext(sparkConf);
        }
        else {
            return context;
        }
    }*//*


}

*/
