package thelancers01.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import thelancers01.project.models.DataPoint;
import thelancers01.project.models.Record;
import thelancers01.project.models.data.DataPointRepository;
import thelancers01.project.models.data.RecordRepository;

import java.util.Optional;

@Controller
@RequestMapping("records")
public class RecordsController {


@Autowired
private RecordRepository recordRepository;

@Autowired
private DataPointRepository dataPointRepository;


    @GetMapping("index")
    public String seeAllRecords(Model model){
        model.addAttribute("records", recordRepository.findAll());
        return "records/index";
    }

    @GetMapping("create")
    public String createRecord(Model model){
        model.addAttribute(new Record());
        model.addAttribute(new DataPoint());
        return "records/create";
    }

    @PostMapping("create")
    public String processCreateRecord(@ModelAttribute @Valid Record record, @ModelAttribute DataPoint dataPoint){

//        dataPointRepository.save(dataPoint);
//        Record record1 = new Record(record.getName(), dataPoint);
//        recordRepository.save(record1);
//        dataPoint.setRecord(record1);
        dataPointRepository.save(dataPoint);

        Record record1 = new Record(record.getName(), dataPoint);
        recordRepository.save(record1);

        // Set the Record in the DataPoint
        dataPoint.setRecord(record1);

        // Set the DataPoint in the Record
        record1.getDataPointsList().add(dataPoint);


        return "redirect:/records/index";
    }

@GetMapping("view/{recordId}")
    public String displayRecords(Model model, @PathVariable int recordId){
    //List<DataPoint> dataPoints = fetchDataPoints();

    Optional<Record> optRecord = recordRepository.findById(recordId);
    if(optRecord.isPresent()){
        Record record = (Record) optRecord.get();
        model.addAttribute("record", record);
        //model.addAttribute("dataPoints", dataPoints);
        Optional<DataPoint> optDataPoint = dataPointRepository.findById(recordId);
        if(optDataPoint.isPresent()) {
            DataPoint dataPoint = (DataPoint) optDataPoint.get();
            model.addAttribute("dataPoint", dataPoint);
        }
        return "records/view";

    } else {
        return "redirect:../";
    }

}


@GetMapping("createDataPoint/{recordId}")
public String createDataPoint(Model model, @PathVariable int recordId){
    model.addAttribute(new DataPoint());
    model.addAttribute("recordId", recordId);
    return "records/createDataPoint";
}



@PostMapping("createDataPoint")
public String processCreateDataPoint(@RequestParam int recordId, Model model, @ModelAttribute DataPoint dataPoint){
    Optional<Record> optionalRecord = recordRepository.findById(recordId);

    if (optionalRecord.isPresent()) {
        Record record = optionalRecord.get();

        dataPoint.setRecord(record);

        dataPointRepository.save(dataPoint);

        return "redirect:/records/view/" + recordId;
    } else {

        return "redirect:/error";
    }
}

    @GetMapping("deleteDataPoint/{recordId}")
    public String deleteDataPoint (@PathVariable int recordId, Model model) {
        model.addAttribute("dataPoints", dataPointRepository.findById(recordId));
        Optional<Record> optRecord = recordRepository.findById(recordId);
        model.addAttribute("recordId", recordId);
        if (optRecord.isPresent()) {
            Record record = (Record) optRecord.get();
            model.addAttribute("record", record);
            Optional<DataPoint> optDataPoint = dataPointRepository.findById(recordId);
            if (optDataPoint.isPresent()) {
                DataPoint dataPoint = (DataPoint) optDataPoint.get();
                model.addAttribute("dataPoint", dataPoint);
            }
            return "records/deleteDataPoint";

        } else {
            return "redirect:/records/index";

        }
    }


    @PostMapping("deleteDataPoint")
    public String postDeleteDataPoint(@RequestParam(required = false) int[] dataPointIds, @RequestParam int recordId){
        if(dataPointIds!=null){
            for(int id : dataPointIds){
                dataPointRepository.deleteById(id);
            }
        }
        return "redirect:/records/view/" + recordId;
    }




@GetMapping("delete")
    public String deleteRecords (Model model){
        model.addAttribute("records", recordRepository.findAll());
        return "records/delete";
}
@PostMapping("delete")
    public String postDeleteRecords(@RequestParam(required = false) int[] recordIds){
        if(recordIds!=null){
            for(int id : recordIds){
                recordRepository.deleteById(id);
            }
        }
        return "redirect:/records/index";
}

}
