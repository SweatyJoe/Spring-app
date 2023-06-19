package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.VideoCardEntity;
import by.pms.repository.ProofficeComponentsRepository;
import by.pms.repository.VideoCardRepository;
import by.pms.repository.ZeonComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class GpuController {
    private List<VideoCardEntity> listGpuWithPrices = new ArrayList<>();
    @Autowired
    private VideoCardRepository videoCardRepository;
    @Autowired
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    private Map<Long, Float> allCostsGpu = new HashMap<>();

    public List<VideoCardEntity> updateAllCostsGpu() {
        Iterable<VideoCardEntity> ipce = videoCardRepository.findAll();
        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                listGpuWithPrices.add(videoCardRepository.findByNameLikeIgnoreCase(s.getName()).get());
                float zcost = 0;
                try {
                    zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                } catch (Exception ignore) {
                }
                float ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                        proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                allCostsGpu.put(s.getId(), Math.min(zcost, ccost));
            }
        }
        return listGpuWithPrices;
    }

    @GetMapping("/allGpu")
    public String allGpu(Model model) {
        if (allCostsGpu.isEmpty()) {
            listGpuWithPrices = updateAllCostsGpu();
        }
        if (!allCostsGpu.isEmpty()) {
            model.addAttribute("prices", allCostsGpu);
        }
        model.addAttribute("ipce", listGpuWithPrices);
        return "allGpu";
    }

    @GetMapping("/gpuInfo/{id}")
    public String getInfoGpu(@PathVariable Long id, Model model) {
        Optional<VideoCardEntity> gpu = videoCardRepository.findById(id);
        try {
            Optional<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCase(gpu.get().getName());
            model.addAttribute("costzeon", costResult);
        } catch (Exception e) {
        }
        try {
            Optional<ProofficeComponentsEntity> costProofcice = proofficeRepository.findByNameLikeIgnoreCase(gpu.get().getName());
            model.addAttribute("costpro", costProofcice);
        } catch (Exception e) {
        }
        model.addAttribute("gpu", gpu);
        return "gpuInfo";
    }
}
