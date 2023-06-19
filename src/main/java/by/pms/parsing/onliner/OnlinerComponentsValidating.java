package by.pms.parsing.onliner;

import by.pms.parsing.onliner.placeholders.*;
import by.pms.repository.*;

public class OnlinerComponentsValidating implements Runnable {
    private final CpuRepository cpuRepository;
    private final DramRepository dramRepository;
    private final PowerSupplyRepository supplyRepository;
    private final SsdRepository ssdRepository;
    private final VideoCardRepository videoCardRepository;
    private final MotherboardRepository motherboardRepository;
    private final CoolingRepository coolingRepository;
    private final HddRepository hddRepository;
    private String item;
    private String url;

    public OnlinerComponentsValidating(String item, String url, CpuRepository cpuRepository, DramRepository dramRepository,
                                       PowerSupplyRepository supplyRepository, SsdRepository ssdRepository,
                                       VideoCardRepository videoCardRepository, MotherboardRepository motherboardRepository,
                                       CoolingRepository coolingRepository, HddRepository hddRepository) {
        this.item = item;
        this.url = url;
        this.cpuRepository = cpuRepository;
        this.dramRepository = dramRepository;
        this.supplyRepository = supplyRepository;
        this.ssdRepository = ssdRepository;
        this.videoCardRepository = videoCardRepository;
        this.motherboardRepository = motherboardRepository;
        this.coolingRepository = coolingRepository;
        this.hddRepository = hddRepository;
    }

    public String getItem() {
        return item;
    }

    @Override
    public void run() {
        if (item.contains("Процессор ")) {
            new OnlinerCPUEntityPlaceholder(item, url, cpuRepository);
        }
        if (item.contains("Оперативная память ")) {
            new OnlinerDramEntityPlaceholder(item, url, dramRepository);
        }
        if (item.contains("Блок питания ")) {
            new OnlinerPowerSupplyPlaceholder(item, url, supplyRepository);
        }
        if (item.contains("SSD ")) {
            new OnlinerSsdPlaceholder(item, url, ssdRepository);
        }
        if (item.contains("Видеокарта ")) {
            new OnlinerVideoCardPlaceholder(item, url, videoCardRepository);
        }
        if (item.contains("Материнская плата ")) {
            new OnlinerMotherboardPlaceholder(item, url, motherboardRepository);
        }
        if (item.contains("Кулер для процессора")) {
            new OnlinerCoolingEntityPlaceholder(item, url, coolingRepository);
        }
        if (item.contains("Жесткий диск")) {
            new OnlinerHddEntityPlaceholder(item, url, hddRepository);
        }
    }
}
