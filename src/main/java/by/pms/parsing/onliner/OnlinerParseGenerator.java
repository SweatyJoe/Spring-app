package by.pms.parsing.onliner;

import by.pms.parsing.onliner.placeholders.OnlinerCPUEntityPlaceholder;
import by.pms.parsing.onliner.placeholders.OnlinerDramEntityPlaceholder;
import by.pms.parsing.onliner.placeholders.OnlinerPowerSupplyPlaceholder;
import by.pms.parsing.onliner.placeholders.OnlinerSsdPlaceholder;
import by.pms.repository.CpuRepository;
import by.pms.repository.DramRepository;
import by.pms.repository.PowerSupplyRepository;
import by.pms.repository.SsdRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Класс отвечает за создание потоков
 * и запуск заполнителя бд характеристик комопнентов
 */
public class OnlinerParseGenerator {
    private static String elems;

    public OnlinerParseGenerator(CpuRepository cpuRepository, DramRepository dramRepository,
                                 PowerSupplyRepository supplyRepository, SsdRepository ssdRepository) {
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (var item : threadList.get(0).getCPUElements().keySet()) {
            if (item.contains("Процессор ")) {
                new OnlinerCPUEntityPlaceholder(item, threadList.get(0).getCPUElements().get(item), cpuRepository);
            }
            if (item.contains("Оперативная память ")) {
                new OnlinerDramEntityPlaceholder(item, threadList.get(0).getCPUElements().get(item), dramRepository);
            }
            if (item.contains("Блок питания ")) {
                new OnlinerPowerSupplyPlaceholder(item, threadList.get(0).getCPUElements().get(item), supplyRepository);
            }
            if (item.contains("SSD ")) {
                new OnlinerSsdPlaceholder(item, threadList.get(0).getCPUElements().get(item), ssdRepository);
            }
        }
    }

    public static String getElems() {
        return elems;
    }

}
