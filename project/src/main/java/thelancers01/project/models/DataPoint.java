//package thelancers01.project.models;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//
//import java.util.Objects;
//
//@Entity
//public class DataPoint {
//
//
//    @Id
//    @GeneratedValue
//    private int id;
//    private int nextId = 1;
//
//
//    private String date;
//        private int value;
//
//        public DataPoint(String date, int value) {
//            this.date = date;
//            this.value = value;
//            this.id = nextId;
//            nextId++;
//        }
//
//    public int getId() {
//        return id;
//    }
//
//
//    public String getDate() {
//            return date;
//        }
//
//        public void setDate(String date) {
//            this.date = date;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        public void setValue(int value) {
//            this.value = value;
//        }
//
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        DataPoint dataPoint = (DataPoint) o;
//        return id == dataPoint.id;
//    }
//}
//
//
//
