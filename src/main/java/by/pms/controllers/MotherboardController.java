package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.MotherboardEntity;
import by.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MotherboardController {
    @Autowired
    private MotherboardRepository motherboardRepository;
    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private DramRepository dramRepository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    private List<MotherboardEntity> listMBWithPrices = new ArrayList<>();
    private Map<Long, Float> allCostsMB = new HashMap<>();

    public List<MotherboardEntity> updateAllCostsMB() {
        Iterable<MotherboardEntity> ipce = motherboardRepository.findAll();
        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                listMBWithPrices.add(motherboardRepository.findByNameLikeIgnoreCase(s.getName()).get());
                float zcost = 0;
                try {
                    zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                } catch (Exception ignore) {
                }
                float ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                        proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                allCostsMB.put(s.getId(), Math.min(zcost, ccost));
            }
        }
        return listMBWithPrices;
    }

    @GetMapping("/allMB")
    public String allMB(Model model) {
        if (allCostsMB.isEmpty()) {
            listMBWithPrices = updateAllCostsMB();
        }
        if (!allCostsMB.isEmpty()) {
            model.addAttribute("prices", allCostsMB);
        }
        model.addAttribute("ipce", listMBWithPrices);
        return "allMotherboard";
    }

    @GetMapping("/mbInfo/{id}")
    public String getInfoGpu(@PathVariable Long id, Model model) {
        Optional<MotherboardEntity> mb = motherboardRepository.findById(id);
        try {
            Optional<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCase(mb.get().getName());
            model.addAttribute("costzeon", costResult);
        } catch (Exception e) {
        }
        try {
            Optional<ProofficeComponentsEntity> costProofcice = proofficeRepository.findByNameLikeIgnoreCase(mb.get().getName());
            model.addAttribute("costpro", costProofcice);
        } catch (Exception e) {
        }
        model.addAttribute("gpu", mb);
        return "mbInfo";
    }

    @GetMapping("/mbfilter")
    public ResponseEntity<List<MotherboardEntity>> getAll(@RequestParam(value = "cpu", required = false) String cpu,
                                                          @RequestParam(value = "ddr", required = false) String ddr) {
        List<MotherboardEntity> listSoc = new ArrayList<>();
        List<MotherboardEntity> listMem = new ArrayList<>();
        try {
            String socket = "";
            String memory = "";
            try {
                socket = cpuRepository.findById(Long.parseLong(cpu)).get().getSocket();
            } catch (Exception ignore) {
            }
            try {
                memory = dramRepository.findById(Long.parseLong(ddr)).get().getType();
            } catch (Exception ignore) {
            }
            if (socket != "") {
                try {
                    listSoc.addAll(motherboardRepository.findBySocketLikeIgnoreCase(socket));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (memory != "") {
                try {
                    if (memory.equals("DDR4 DIMM")) {
                        listMem.addAll(motherboardRepository.findByMemoryTypeLikeIgnoreCase(memory.replace(" DIMM", "")));
                    }
                    else listMem.addAll(motherboardRepository.findByMemoryTypeLikeIgnoreCase(memory));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else return new ResponseEntity<>(listSoc, HttpStatus.OK);
            if (socket == "") {
                System.out.println(listMem.size());
                return new ResponseEntity<>(listMem, HttpStatus.OK);
            }
            if (!listSoc.isEmpty() && !listMem.isEmpty()) {
                if(listMem.size() < listSoc.size()){
                    for (var item : listSoc) {
                        if (!listMem.contains(item)) {
                            listMem.remove(item);
                        }
                    }
                } else{
                    for (var item : listMem) {
                        if (!listSoc.contains(item)) {
                            listSoc.remove(item);
                        }
                    }
                }
            }
            System.out.println(listMem.size());
            return new ResponseEntity<>(listMem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
