package thelancers01.project.models.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import thelancers01.project.models.DataPoint;

@Repository
public interface DataPointRepository extends CrudRepository<DataPoint, Integer> {

//@Autowired
//private RecordRepository recordRepository;
//
//
//    @GetMapping("index")
//    public String seeAllRecords(Model model){
//        model.addAttribute("records", recordRepository.findAll());
//        return "records/index";
//    }

//@GetMapping("view")
//    public String displayRecords(Model model){
//    List<DataPoint> dataPoints = fetchDataPoints();
//
//    model.addAttribute("dataPoints", dataPoints);
//    return "records/view";
//}



//    private List<DataPoint> fetchDataPoints() {
//        List<DataPoint> dataPoints = new ArrayList<>();
//        dataPoints.add(new DataPoint("January 10th", 60));
//        dataPoints.add(new DataPoint("January 12th", 66));
//        dataPoints.add(new DataPoint("January 18th", 54));
//        dataPoints.add(new DataPoint("January 24th", 70));
//        dataPoints.add(new DataPoint("February 4th", 75));
//        Record bicepCurlMax = new Record(dataPoints);
//
//
//        return bicepCurlMax.getDataPointsList();
//    }


}
