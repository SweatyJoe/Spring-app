package by.pms.controllers;

import by.pms.entity.CpuEntity;
import by.pms.repository.CpuRepository;
import by.pms.repository.ImarketParseComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private ImarketParseComponentsRepository imarketRepo;
    @Autowired
    private CpuRepository cpuRepository;


    @GetMapping("/")
    public String begin(Model model) {
        //new OnlinerParseGenerator(cpuRepository);
        Iterable<CpuEntity> ipce = cpuRepository.findAll();
        model.addAttribute("ipce", ipce);
        return "home";
    }

    @PostMapping("/info/{name}")
    public String infos(Model model, String name) {
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
}
