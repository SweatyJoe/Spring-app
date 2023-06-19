package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.CoolingEntity;
import by.pms.repository.CoolingRepository;
import by.pms.repository.ProofficeComponentsRepository;
import by.pms.repository.ZeonComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class CoolingController {
    @Autowired
    private CoolingRepository coolingRepository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    private List<CoolingEntity> listWithPrices = new ArrayList<>();
    private Map<Long, Float> allCosts = new HashMap<>();

    public List<CoolingEntity> updateAllCosts() {
        Iterable<CoolingEntity> ipce = coolingRepository.findAll();
        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                listWithPrices.add(coolingRepository.findByNameLikeIgnoreCase(s.getName()).get());
                float zcost = 0;
                try {
                    zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                } catch (Exception ignore) {
                }
                float ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                        proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                allCosts.put(s.getId(), Math.min(zcost, ccost));
            }
        }
        return listWithPrices;
    }

    @GetMapping("/allCooling")
    public String allMB(Model model) {
        if (allCosts.isEmpty()) {
            listWithPrices = updateAllCosts();
        }
        if (!allCosts.isEmpty()) {
            model.addAttribute("prices", allCosts);
        }
        model.addAttribute("ipce", listWithPrices);
        return "allCooling";
    }

    @GetMapping("/coolingInfo/{id}")
    public String getInfo(@PathVariable Long id, Model model) {
        Optional<CoolingEntity> mb = coolingRepository.findById(id);
        try {
            Optional<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCase(mb.get().getName());
            model.addAttribute("costzeon", costResult);
        } catch (Exception ignore) {
        }
        try {
            Optional<ProofficeComponentsEntity> costProofcice = proofficeRepository.findByNameLikeIgnoreCase(mb.get().getName());
            model.addAttribute("costpro", costProofcice);
        } catch (Exception ignore) {
        }
        model.addAttribute("cool", mb);
        return "coolingInfo";
    }
}
