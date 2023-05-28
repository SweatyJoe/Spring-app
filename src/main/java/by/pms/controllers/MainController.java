package by.pms.controllers;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.CpuEntity;
import by.pms.entity.baseEntity.VideoCardEntity;
import by.pms.parsing.onliner.OnlinerParseGenerator;
import by.pms.parsing.prooffice.ProofficeParsePlaceholder;
import by.pms.parsing.zeon.ZeonParsePlaceholder;
import by.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {
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
    private ZeonComponentsRepository zeonRepository;
    @Autowired
    private ProofficeComponentsRepository proofficeRepository;
    @Autowired
    private MotherboardRepository motherboardRepository;

    /*TODO
     *  do something with paging;
     */
    /*@GetMapping("/")
    public String defaultMapping(Model model, @RequestParam(defaultValue = "0") int pageNo){
        Pageable pageable = (Pageable) PageRequest.of(pageNo, 30);
        List<CpuEntity> list = cpuRepository.findCpuEntitiesById(1L, pageable);
        return "home";
    }*/

    @GetMapping("/")
    public String compare(Model model) {
        ZeonParsePlaceholder zeonPlh = new ZeonParsePlaceholder(zeonRepository);
        ProofficeParsePlaceholder proofficePlh = new ProofficeParsePlaceholder(proofficeRepository);
        return "pack";
    }

    @GetMapping("/updateAllEntity")
    public String begin(Model model) {
        new OnlinerParseGenerator(cpuRepository, dramRepository, supplyRepository,
                ssdRepository, videoCardRepository, motherboardRepository);
        Iterable<CpuEntity> cpu = cpuRepository.findAll();
        model.addAttribute("cpu", cpu);
        return "home";
    }

    @RequestMapping("/list")
    public String list(@RequestParam(value = "cpu", required = false) Long cpu,
                       @RequestParam(value = "gpu", required = false) Long gpu,
                       Model model) {
        Optional<CpuEntity> resultCpu = Optional.empty();
        Optional<VideoCardEntity> resultGpu = Optional.empty();
        try {
            resultCpu = cpuRepository.findById(cpu);
        } catch (Exception e) {
        }
        try {
            resultGpu = videoCardRepository.findById(gpu);
        } catch (Exception e) {
        }
        if (resultCpu.isPresent()) {
            model.addAttribute("cpu", resultCpu);
        }
        if (resultGpu.isPresent()) {
            model.addAttribute("gpu", resultGpu);
        }
        return "pack";
    }

    /*TODO
     *  long load*/
    @GetMapping("/findCpu")
    public String information_sequence(Model model) {
        List<CpuEntity> list = new ArrayList<>();
        Map<Long, Float> allCosts = new HashMap<>();
        Iterable<CpuEntity> ipce = cpuRepository.findAll();

        for (var s : ipce) {
            if (zeonRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ||
                    proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent()) {
                list.add(cpuRepository.findByNameLikeIgnoreCase(s.getName()).get());
                float zcost = zeonRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost();
                float ccost = proofficeRepository.findByNameLikeIgnoreCase(s.getName()).isPresent() ?
                        proofficeRepository.findByNameLikeIgnoreCase(s.getName()).get().getCost() : Long.MAX_VALUE;
                allCosts.put(s.getId(), Math.min(zcost, ccost));
            }
        }
        if (!allCosts.isEmpty()) {
            /*Map<CpuEntity, Float> collectionMap = new HashMap<>();
            for (var s : list) {
                collectionMap.put(s, allCosts.get(s.getId()));
            }*/
            model.addAttribute("prices", allCosts);
        }
        model.addAttribute("ipce", list);
        return "information_sequence";
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

    @GetMapping("/gpuInfo/{id}")
    public String getInfoGpu(@PathVariable Long id, Model model) {
        Optional<VideoCardEntity> gpu = videoCardRepository.findById(id);
        model.addAttribute("gpu", gpu);
        return "gpuInfo";
    }
}
