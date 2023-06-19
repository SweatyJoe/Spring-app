package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.DramEntity;
import by.pms.repository.DramRepository;
import by.pms.repository.ProofficeComponentsRepository;
import by.pms.repository.ZeonComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class DDRController {
    @Autowired
    private DramRepository repository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    private List<DramEntity> listWithPrices = new ArrayList<>();
    private Map<Long, Float> allCosts = new HashMap<>();

    public List<DramEntity> updateAllCosts() {
        Iterable<DramEntity> ipce = repository.findAll();
        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                listWithPrices.add(repository.findByNameLikeIgnoreCase(s.getName()).get());
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

    @GetMapping("/allDDR")
    public String allMB(Model model) {
        if (allCosts.isEmpty()) {
            listWithPrices = updateAllCosts();
        }
        if (!allCosts.isEmpty()) {
            model.addAttribute("prices", allCosts);
        }
        model.addAttribute("ipce", listWithPrices);
        return "allDdr";
    }

    @GetMapping("/ddrInfo/{id}")
    public String getInfo(@PathVariable Long id, Model model) {
        Optional<DramEntity> mb = repository.findById(id);
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
        model.addAttribute("ddr", mb);
        return "ddrInfo";
    }
}
