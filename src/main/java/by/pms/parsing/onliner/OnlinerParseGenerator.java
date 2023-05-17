package by.pms.parsing.onliner;

import by.pms.repository.CpuRepository;
import by.pms.repository.DramRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Класс отвечает за создание потоков (пока только 1)
 * и запуск заполнителя бд для цпу
 */
public class OnlinerParseGenerator {
    private static String elems;

    /*TODO
     * threadPool - need some rework;
     * mb TP is shit...
     * add more thread for parsing;*/
    public OnlinerParseGenerator(CpuRepository cpuRepository, DramRepository dramRepository) {
        List<OnlinerParseThread> threadList = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            threadList.add(new OnlinerParseThread(i));
        }
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 800, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8), threadFactory);
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
        }
    }

    public static String getElems() {
        return elems;
    }

}
