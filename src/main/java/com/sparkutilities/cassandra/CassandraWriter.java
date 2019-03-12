package com.sparkutilities.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.sparkutilities.util.Util;
import org.apache.spark.sql.ForeachWriter;
import org.apache.spark.sql.Row;
import com.sparkutilities.pojo.Sensor;

public class CassandraWriter extends ForeachWriter<Row> {

    private Cluster cluster;
    private Session session;
    private String ip;
    private int port;
    private String keyspace;
    private Mapper<Sensor> mapper;

    public CassandraWriter(String ip,int port, String keyspace) {
        this.ip = ip;
        this.port = port;
        this.keyspace = keyspace;

    }

    @Override
    public boolean open(long partitionId, long version) {

        cluster = Cluster.builder().addContactPoints(ip).withPort(port).build();
        session = cluster.connect(keyspace);
        MappingManager manager = new MappingManager(session);
        mapper = manager.mapper(Sensor.class);
        return true;
    }

    @Override
    public void process(Row row) {
        Sensor sensor = getSensor(row);
        mapper.save(sensor);
    }

    private Sensor getSensor(Row row) {
        Sensor sensor = Util.getMappedObjectFromJson(row.toString(), Sensor.class);
        sensor.setSensorId(UUIDs.random());
        System.out.println(sensor);
        return sensor;
    }

    @Override
    public void close(Throwable errorOrNull) {
        session.close();

    }
}
