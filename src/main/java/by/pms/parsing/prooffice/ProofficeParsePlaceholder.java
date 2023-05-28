package by.pms.parsing.prooffice;

import by.pms.entity.ProofficeComponentsEntity;
import by.pms.repository.ProofficeComponentsRepository;

public class ProofficeParsePlaceholder {
    public ProofficeParsePlaceholder(ProofficeComponentsRepository repository) {
        storeData(repository);
    }

    private void storeData(ProofficeComponentsRepository repository) {
        ProofficeParseGenerator generator = new ProofficeParseGenerator();
        generator.parse();
        for(var s : generator.getComponents()){
            try{
                if(repository.findByNameLikeIgnoreCase(s.getName()).isEmpty()){
                    repository.save(new ProofficeComponentsEntity(s.getName(), s.getUrl(), s.getCost()));
                }
            } catch (Exception e){
                System.out.println("Cant compare " + s.getName());
            }
        }
    }
}
