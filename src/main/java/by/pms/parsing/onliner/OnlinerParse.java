package by.pms.parsing.onliner;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnlinerParse {
    private static String elems;

    /*TODO
     *  threadPool - need some rework;
     *  add more thread for parsing;*/
    public OnlinerParse() {
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
        //this.sendRequest();
        OnlinerParseThread opt = new OnlinerParseThread();
        //threadPool.submit(opt);
        Thread th = new Thread(opt);
        try {

            th.join();
            th.start();
        } catch (InterruptedException e) {
            System.out.println("\n\nError on Start Thread\n\n");
            e.printStackTrace();
        }
        Map<String, String> onlinerCpuElements = opt.getCPUElements();
        try {
            for (var s : onlinerCpuElements.keySet()) {
                new OnlinerCPUEntityPlaceholder(s, onlinerCpuElements.get(s));
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
