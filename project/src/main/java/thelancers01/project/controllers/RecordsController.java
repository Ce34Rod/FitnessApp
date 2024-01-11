//package thelancers01.project.controllers;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import thelancers01.project.models.DataPoint;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("records")
//public class RecordsController {
//
//
//@GetMapping("view")
//    public String displayRecords(Model model){
//    List<DataPoint> dataPoints = fetchDataPoints();
//    model.addAttribute("dataPoints", dataPoints);
//    return "records/view";
//}
//
//    private List<DataPoint> fetchDataPoints() {
//        List<DataPoint> dataPoints = new ArrayList<>();
//        dataPoints.add(new DataPoint("January 10th", 60));
//        dataPoints.add(new DataPoint("January 12th", 66));
//        dataPoints.add(new DataPoint("January 18th", 54));
//        dataPoints.add(new DataPoint("January 24th", 70));
//        dataPoints.add(new DataPoint("February 4th", 75));
//
//
//        return dataPoints;
//    }
//
//
//
//}
