package thelancers01.project.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Record {
    @Id
    @GeneratedValue
    private int id;
    private int nextId = 1;
    private String name;
    @OneToMany(mappedBy = "record")
    private List<DataPoint> dataPoints= new ArrayList<>();

    public Record(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<DataPoint> getDataPointsList() {
        return dataPoints;
    }

    public void setDataPoints(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }
        public Record (String name, List<DataPoint> dataPoints){
        this.dataPoints = dataPoints;
        this.name = name;
    }

    public Record (String name, DataPoint dataPoint){
        dataPoints.add(dataPoint);
        this.name = name;
    }
    public Record(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(name, record.name) && Objects.equals(dataPoints, record.dataPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dataPoints);
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + name + '\'' +
                ", dataPoints=" + dataPoints +
                '}';
    }
}
