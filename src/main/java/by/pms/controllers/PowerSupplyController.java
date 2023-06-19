package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.PowerSupplyEntity;
import by.pms.repository.PowerSupplyRepository;
import by.pms.repository.ProofficeComponentsRepository;
import by.pms.repository.ZeonComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Controller
public class PowerSupplyController {
    @Autowired
    private PowerSupplyRepository repository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    private List<PowerSupplyEntity> prices = new ArrayList<>();
    private Map<Long, Float> allCosts = new HashMap<>();

    public List<PowerSupplyEntity> updateAllCosts() {
        Iterable<PowerSupplyEntity> ipce = repository.findAll();
        for (var s : ipce)
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                prices.add(repository.findByNameLikeIgnoreCase(s.getName()).get());
                float zcost = 0;
                try {
                    zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                } catch (Exception ignore) {
                }
                float ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                        proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                allCosts.put(s.getId(), Math.min(zcost, ccost));
            }
        return prices;
    }

    @GetMapping("/allPower")
    public String allPower(Model model) {
        if (allCosts.isEmpty()) {
            prices = updateAllCosts();
        }
        if (!allCosts.isEmpty()) {
            model.addAttribute("prices", allCosts);
        }
        model.addAttribute("ipce", prices);
        return "allPower";
    }

    @GetMapping("/powerInfo/{id}")
    public String powerInfo(@PathVariable Long id, Model model) {
        Optional<PowerSupplyEntity> power = repository.findById(id);
        try {
            Optional<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCase(power.get().getName());
            model.addAttribute("costzeon", costResult);
        } catch (Exception ignore) {
        }
        try {
            Optional<ProofficeComponentsEntity> costProofcice = proofficeRepository.findByNameLikeIgnoreCase(power.get().getName());
            model.addAttribute("costpro", costProofcice);
        } catch (Exception ignore) {
        }
        model.addAttribute("power", power);
        return "powerInfo";
    }
}
