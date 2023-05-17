package by.pms.parsing.zeon;

import by.pms.parsing.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        ExecutorService executorService = Executors.newFixedThreadPool(8);
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
        executorService.shutdown();
    }
}
