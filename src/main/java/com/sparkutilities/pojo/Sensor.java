package com.sparkutilities.pojo;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.UUID;

@Table(name="sensor")
public class Sensor {
    @PartitionKey
    UUID sensorId;
    String sensorName;
    String packageInfo ;
    String flight;
    String value ;

    public UUID getSensorId() {
        return sensorId;
    }

    public void setSensorId(UUID sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId=" + sensorId +
                ", sensorName='" + sensorName + '\'' +
                ", packageInfo='" + packageInfo + '\'' +
                ", flight='" + flight + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
