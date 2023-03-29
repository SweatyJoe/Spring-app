package by.pms.controllers;

import by.pms.entity.ImarketParseComponents;
import by.pms.parsing.onliner.OnlinerParse;
import by.pms.repository.ImarketParseComponentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private ImarketParseComponentsRepository imarketRepo;


    @GetMapping("/")
    public String begin(Model model) {
        //new PreparseSet(imarketRepo);
        new OnlinerParse();
        Iterable<ImarketParseComponents> ipce = imarketRepo.findAll();
        model.addAttribute("ipce", ipce);
        return "home";
    }
}
