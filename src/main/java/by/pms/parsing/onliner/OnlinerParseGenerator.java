package by.pms.parsing.onliner;

import by.pms.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Класс отвечает за создание потоков
 * и запуск заполнителя бд характеристик комопнентов
 */
public class OnlinerParseGenerator {
    private final CpuRepository cpuRepository;
    private final DramRepository dramRepository;
    private final PowerSupplyRepository supplyRepository;
    private final SsdRepository ssdRepository;
    private final VideoCardRepository videoCardRepository;
    private final MotherboardRepository motherboardRepository;
    private final CoolingRepository coolingRepository;
    private final HddRepository hddRepository;

    public OnlinerParseGenerator(CpuRepository cpuRepository, DramRepository dramRepository,
                                 PowerSupplyRepository supplyRepository, SsdRepository ssdRepository,
                                 VideoCardRepository videoCardRepository, MotherboardRepository motherboardRepository,
                                 CoolingRepository coolingRepository, HddRepository hddRepository) {
        this.cpuRepository = cpuRepository;
        this.dramRepository = dramRepository;
        this.supplyRepository = supplyRepository;
        this.ssdRepository = ssdRepository;
        this.videoCardRepository = videoCardRepository;
        this.motherboardRepository = motherboardRepository;
        this.coolingRepository = coolingRepository;
        this.hddRepository = hddRepository;
        startValidating();
    }

    private void startValidating() {
        List<OnlinerParseThread> threadList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            threadList.add(new OnlinerParseThread(i));
        }
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 800,
                100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8), threadFactory);
        for (var s : threadList) {
            pool.execute(s);
        }
        pool.shutdown();
        while (pool.getActiveCount() != 0) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ThreadPoolExecutor newPool = new ThreadPoolExecutor(7, 14,
                100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8),
                Executors.defaultThreadFactory());
        List<OnlinerComponentsValidating> validatingList = new ArrayList<>();
        for (var item : threadList.get(0).getCPUElements().keySet()) {
            validatingList.add(new OnlinerComponentsValidating(item, threadList.get(0).getCPUElements().get(item),
                    cpuRepository, dramRepository, supplyRepository, ssdRepository, videoCardRepository,
                    motherboardRepository, coolingRepository, hddRepository));
        }
        try {
            for (var list : validatingList) {
                try {
                    newPool.execute(list);
                } catch (Exception e) {
                    System.out.println("Thread must be rejected. ID [" + list.getItem() + "]");
                    continue;
                }
                Thread.sleep(400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        newPool.shutdown();
        while (newPool.getActiveCount() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
