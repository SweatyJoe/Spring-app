package by.pms.controllers;

import by.pms.entity.baseEntity.MotherboardEntity;
import by.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/get")
public class RequestController {
    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private DramRepository dramRepository;
    @Autowired
    private PowerSupplyRepository supplyRepository;
    @Autowired
    private SsdRepository ssdRepository;
    @Autowired
    private VideoCardRepository videoCardRepository;
    @Autowired
    private MotherboardRepository motherboardRepository;
    @Autowired
    private CoolingRepository coolingRepository;
    @Autowired
    private HddRepository hddRepository;

    @GetMapping("/gpu")
    public ResponseEntity<String> gpuName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(videoCardRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/cpu")
    public ResponseEntity<String> cpuName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cpuRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/mb")
    public ResponseEntity<String> motherboardName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(motherboardRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/ssd")
    public ResponseEntity<String> ssdName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ssdRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/dram")
    public ResponseEntity<String> dramName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dramRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/supply")
    public ResponseEntity<String> supplyName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(supplyRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/cooling")
    public ResponseEntity<String> coolingName(@RequestParam(value = "id", required = false) String id) {
        if (id == null) {
            return new ResponseEntity<>("Не верный id", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(coolingRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
    }

    @GetMapping("/mbItem")
    public ResponseEntity<MotherboardEntity> mbEntity(@RequestParam(value = "id") String id) {
        try {
            return new ResponseEntity<>(motherboardRepository.findById(Long.parseLong(id)).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/power")
    public ResponseEntity<String> powerName(@RequestParam(value = "id", required = false) String id) {
        try {
            return new ResponseEntity<>(supplyRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/hdd")
    public ResponseEntity<String> hddName(@RequestParam(value = "id", required = false) String id) {
        try {
            return new ResponseEntity<>(hddRepository.findById(Long.parseLong(id)).get().getName(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
