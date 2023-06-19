package by.pms.controllers;

import by.pms.parsing.onliner.OnlinerCostsRecorder;
import by.pms.parsing.onliner.OnlinerParseGenerator;
import by.pms.parsing.prooffice.ProofficeParsePlaceholder;
import by.pms.parsing.zeon.ZeonParsePlaceholder;
import by.pms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
    @Autowired
    private CoolingRepository coolingRepository;
    @Autowired
    private HddRepository hddRepository;
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
        return "pack";
    }

    @GetMapping("/updateCosts")
    public String updateCosts() {
        ProofficeParsePlaceholder proofficePlh = new ProofficeParsePlaceholder(proofficeRepository);
        ZeonParsePlaceholder zeonPlh = new ZeonParsePlaceholder(zeonRepository);
        return "redirect:/";
    }

    @GetMapping("/updateAllEntity")
    public String begin(Model model) {
        new OnlinerParseGenerator(cpuRepository, dramRepository, supplyRepository,
                ssdRepository, videoCardRepository, motherboardRepository, coolingRepository, hddRepository);
        //Iterable<CpuEntity> cpu = cpuRepository.findAll();
        //model.addAttribute("cpu", cpu);
        return "redirect:/";
    }

    @GetMapping("/test")
    public void test() {
        new OnlinerCostsRecorder(cpuRepository);
    }
}
