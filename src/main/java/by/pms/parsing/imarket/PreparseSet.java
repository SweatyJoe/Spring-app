package by.pms.parsing.imarket;

import by.pms.entity.ImarketParseComponents;
import by.pms.repository.ImarketParseComponentsRepository;

import java.util.ArrayList;
import java.util.List;

public class PreparseSet {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    private final List<ImarketParseComponents> components = new ArrayList<>();
    private final ImarketParseComponentsRepository repository;

    public PreparseSet(ImarketParseComponentsRepository repository) {
        this.repository = repository;
        startParsingImarket();
        updateDB();
    }

    /**
     * Method for start parse imarket.by.
     * initialize list components
     */
    private void startParsingImarket() {
        ImarketParse iParse = new ImarketParse();
        components.addAll(iParse.getTotalElements());
    }

    /**
     * Method for saving data to db and search for unique name
     * and if we find same name - compare costs
     */
    private void updateDB() {
        try {
            for (var s : components) {
                if (repository.findByNameIgnoreCase(s.getName()).isEmpty()) {
                    repository.save(s);
                } else {
                    if (repository.findByNameIgnoreCase(s.getName()).get().getCost() > s.getCost()) {
                        repository.findByNameIgnoreCase(s.getName()).get().setCost(s.getCost());
                    }
                }
            }
            components.clear();
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Save exception: " + e.getMessage() + ANSI_RESET);
        }
    }
}
