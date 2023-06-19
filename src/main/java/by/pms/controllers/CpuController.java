package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.CpuEntity;
import by.pms.repository.CpuRepository;
import by.pms.repository.ProofficeComponentsRepository;
import by.pms.repository.ZeonComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class CpuController {
    private Map<Long, Float> allCostsCpus = new HashMap<>();
    private List<CpuEntity> listCpuWithPrises = new ArrayList<>();
    @Autowired
    private CpuRepository cpuRepository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;

    public List<CpuEntity> updateAllCostsCpu() {
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                listCpuWithPrises.add(cpuRepository.findByNameLikeIgnoreCase(s.getName()).get());
                float ccost = 0;
                float zcost = 0;
                try {
                    zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                } catch (Exception ignore) {
                }
                try {
                    ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                            proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                } catch (Exception ignore) {
                }
                allCostsCpus.put(s.getId(), Math.min(zcost, ccost));
            }
        }
        return listCpuWithPrises;
    }

    @GetMapping("/findCpu")
    public String information_sequence(Model model) {
        if (allCostsCpus.isEmpty()) {
            listCpuWithPrises = updateAllCostsCpu();
        }
        if (!allCostsCpus.isEmpty()) {
            model.addAttribute("prices", allCostsCpus);
        }
        model.addAttribute("ipce", listCpuWithPrises);
        return "allCpu";
    }

    @GetMapping("/cpuInfo/{id}")
    public String getInfoCpu(@PathVariable Long id, Model model) {
        Optional<CpuEntity> cpu = cpuRepository.findById(id);
        model.addAttribute("cpu", cpu);
        try {
            Optional<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCase(cpu.get().getName());
            model.addAttribute("costzeon", costResult);
        } catch (Exception e) {
        }
        try {
            Optional<ProofficeComponentsEntity> costProofcice = proofficeRepository.findByNameLikeIgnoreCase(cpu.get().getName());
            model.addAttribute("costpro", costProofcice);
        } catch (Exception e) {
        }
        return "cpuInfo";
    }
}
