package by.pms.parsing.zeon;

import by.pms.parsing.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZeonParseGenerator {
    private List<Components> components = new ArrayList<>();

    public List<Components> getComponents() {
        return components;
    }

    public void parse() {
        List<ZeonParseThread> listTmpParse = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            listTmpParse.add(new ZeonParseThread(i));
        }
        ThreadPoolExecutor pool = new ThreadPoolExecutor(8, 20, 100,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(8), Executors.defaultThreadFactory());
        for(var s : listTmpParse){
            pool.execute(s);
        }
        pool.shutdown();
        while(pool.getActiveCount() != 0){
            try{
                Thread.sleep(200);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        for(var s : listTmpParse){
            components.addAll(s.getElements());
        }
        /*ExecutorService executorService = Executors.newFixedThreadPool(8);
        Future future = null;
        for (var s : listTmpParse) {
            future = executorService.submit(s);
        }
        try {
            if (future != null) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        executorService.shutdown();*/
    }
}
