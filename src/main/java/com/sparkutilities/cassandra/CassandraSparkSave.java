package com.sparkutilities.cassandra;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.CassandraRow;
import com.datastax.spark.connector.japi.rdd.CassandraTableScanJavaRDD;
import com.sparkutilities.pojo.Student;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;

public class CassandraSparkSave {


    static JavaSparkContext context;

    public void saveToCassandra(Student o)
    {
        context = getJavaSparkContext();
        ArrayList<Student> objects = new ArrayList<>();
        objects.add(o);

        JavaRDD<Student> objectJavaRDD = context.parallelize(objects);

        JavaPairRDD javaPairRDD;
        CassandraJavaUtil.javaFunctions(objectJavaRDD).writerBuilder("sample_keyspace", "student", CassandraJavaUtil.mapToRow(Student.class)).saveToCassandra();

    }
    public void saveStudent(Student s)
    {
          saveToCassandra(s);
    }
    public static void main(String[] args) {
        CassandraSparkSave sample=new CassandraSparkSave();

        sample.saveStudent(new Student(11111,"anubhav","ggn",4209211,4209));

        JavaSparkContext context = getJavaSparkContext();
        CassandraTableScanJavaRDD<CassandraRow> select = CassandraJavaUtil.javaFunctions(context).cassandraTable("sample_keyspace", "student").select("student_name");
        System.out.print(select.collect());


        //...

        context.close();

    }

    private static JavaSparkContext getJavaSparkContext() {
        if(context==null) {
            SparkConf sparkConf = new SparkConf();

            sparkConf.setAppName("Hello Spark");
            sparkConf.setMaster("spark://<host>:7077");
            sparkConf.set("spark.cassandra.connection.host", "<host>");
            sparkConf.set("spark.cassandra.connection.port", "<port>");
            return new JavaSparkContext(sparkConf);
        }
        else {
            return context;
        }
    }

}

