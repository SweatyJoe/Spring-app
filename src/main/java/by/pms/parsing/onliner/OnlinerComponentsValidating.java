package by.pms.parsing.onliner;

import by.pms.parsing.onliner.placeholders.*;
import by.pms.repository.*;

public class OnlinerComponentsValidating implements Runnable {
    private final CpuRepository cpuRepository;
    private final DramRepository dramRepository;
    private final PowerSupplyRepository supplyRepository;
    private final SsdRepository ssdRepository;
    private final VideoCardRepository videoCardRepository;
    private String item;
    private String url;

    public OnlinerComponentsValidating(String item, String url, CpuRepository cpuRepository, DramRepository dramRepository,
                                       PowerSupplyRepository supplyRepository, SsdRepository ssdRepository,
                                       VideoCardRepository videoCardRepository) {
        this.item = item;
        this.url = url;
        this.cpuRepository = cpuRepository;
        this.dramRepository = dramRepository;
        this.supplyRepository = supplyRepository;
        this.ssdRepository = ssdRepository;
        this.videoCardRepository = videoCardRepository;
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
    }
}
