package by.pms.parsing.prooffice;

import by.pms.parsing.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProofficeParseGenerator {
    private List<Components> components = new ArrayList<>();

    public List<Components> getComponents() {
        return components;
    }

    public void parse() {
        List<ProofficeParseThread> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(new ProofficeParseThread(i));
        }
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 20, 100,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8), Executors.defaultThreadFactory());
        for (var s : list) {
            pool.execute(s);
        }
        pool.shutdown();
        while (pool.getActiveCount() != 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Interrupted exception in ProofficeParseGenerator.java");
            }
        }
        for (var s : list) {
            components.addAll(s.getComponents());
        }
    }
}
