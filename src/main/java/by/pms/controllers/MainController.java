package by.pms.controllers;

import by.pms.entity.CpuEntity;
import by.pms.parsing.onliner.OnlinerParseGenerator;
import by.pms.repository.CpuRepository;
import by.pms.repository.DramRepository;
import by.pms.repository.PowerSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private CpuRepository cpuRepository;
    private DramRepository dramRepository;
    private PowerSupplyRepository supplyRepository;


    @GetMapping("/")
    public String begin(Model model) {
        new OnlinerParseGenerator(cpuRepository, dramRepository, supplyRepository);
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        model.addAttribute("ipce", ipce);
        return "home";
    }

    @GetMapping("/info/{name}")
    public String infos(Model model, @PathVariable String name) {
        try {
            Optional<CpuEntity> result = cpuRepository.findByNameLikeIgnoreCase(name);
            model.addAttribute("result", result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Iterable<CpuEntity> ipce = cpuRepository.findAll();
            model.addAttribute("ipce", ipce);
        }
        return "home";
    }

    @GetMapping("/information")
    public String information_sequence(Model model) {
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        model.addAttribute("ipce", ipce);
        return "information_sequence";
    }
}
