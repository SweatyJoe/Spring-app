package by.pms.parsing.onliner;

import by.pms.repository.CpuRepository;

import java.util.Map;

/**
 * Класс отвечает за создание потоков (по только 1)
 * */
public class OnlinerParseGenerator {
    private static String elems;

    /*TODO
     * threadPool - need some rework;
     * mb TP is shit...
     * add more thread for parsing;*/
    public OnlinerParseGenerator(CpuRepository repository) {
        //ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        //this.sendRequest();
        OnlinerParseThread opt = new OnlinerParseThread();
        Thread th = new Thread(opt);
        th.setName("parsing-thread-only");
        if (th.isAlive()) return;
        try {
            th.join();
            th.start();
        } catch (InterruptedException e) {
            System.out.println("\n\nError on Start Thread\n\n");
            e.printStackTrace();
        }
        try {
            while (th.isAlive()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Map<String, String> onlinerCpuElements = opt.getCPUElements();
        try {
            for (var s : onlinerCpuElements.keySet()) {
                System.out.println("S: [" + s + "]  [" + onlinerCpuElements.get(s) + "]");
                new OnlinerCPUEntityPlaceholder(s, onlinerCpuElements.get(s), repository);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("THE END");
    }

    public static String getElems() {
        return elems;
    }

}
