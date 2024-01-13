package thelancers01.project.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class DataPoint {



    @Id
    @GeneratedValue
    private int id;
    private int nextId = 1;
    private String date;
    private String value;
    @ManyToOne
    @JoinColumn(name = "record_id")
    private Record record;

    public Record getRecords() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public DataPoint(String date, String value, Record record) {
        this.date = date;
        this.value = value;
        this.record = record;
        this.id = nextId;
        nextId++;
    }

    public DataPoint(String date, String value) {
            this.date = date;
            this.value = value;
            this.id = nextId;
            nextId++;
        }
        public DataPoint(Record record){
        this.record = record;
        }

        public DataPoint(){}
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }
    public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataPoint dataPoint = (DataPoint) o;
        return id == dataPoint.id;
    }
}



