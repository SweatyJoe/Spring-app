package by.pms.controllers;

import by.pms.entity.ZeonComponentsEntity;
import by.pms.entity.baseEntity.CpuEntity;
import by.pms.entity.baseEntity.VideoCardEntity;
import by.pms.parsing.onliner.OnlinerParseGenerator;
import by.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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
        /*ZeonParsePlaceholder zeonParse = new ZeonParsePlaceholder(zeonRepository);
        try {
            for (var s : zeonRepository.findAll()) {
                if (cpuRepository.findByNameLikeIgnoreCase(s.getName()).isEmpty()) {
                    if (dramRepository.findByNameLikeIgnoreCase(s.getName()).isEmpty()) {
                        if (supplyRepository.findByNameLikeIgnoreCase(s.getName()).isEmpty()) {
                            if (ssdRepository.findByNameLikeIgnoreCase(s.getName()).isEmpty()) {
                                if (videoCardRepository.findByNameLikeIgnoreCase(s.getName()).isEmpty()) {
                                    System.out.println("Cant find [" + s.getName() + "]");
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return "pack";
    }

    @GetMapping("/update")
    public String begin(Model model) {
        new OnlinerParseGenerator(cpuRepository, dramRepository, supplyRepository, ssdRepository, videoCardRepository);
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        model.addAttribute("ipce", ipce);
        return "pack";
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
        if (!resultCpu.isEmpty()) {
            model.addAttribute("cpu", resultCpu);
        }
        if (!resultGpu.isEmpty()) {
            model.addAttribute("gpu", resultGpu);
        }
        return "pack";
    }

    @GetMapping("/findCpu")
    public String information_sequence(Model model) {
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        model.addAttribute("ipce", ipce);
        return "information_sequence";
    }

    @GetMapping("/cpuInfo/{id}")
    public String getInfoCpu(@PathVariable Long id, Model model) {
        Optional<CpuEntity> cpu = cpuRepository.findById(id);
        model.addAttribute("cpu", cpu);
        try {
            List<ZeonComponentsEntity> costResult = zeonRepository.findByNameLikeIgnoreCaseOrderByCostAsc(cpu.get().getName());
            model.addAttribute("cost", costResult);
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
