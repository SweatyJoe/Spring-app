package by.pms.parsing.zeon;

import by.pms.entity.ZeonComponentsEntity;
import by.pms.parsing.Components;
import by.pms.repository.ZeonComponentsRepository;

import java.util.List;

/**
 * TODO something
 */
public class ZeonParsePlaceholder {
    public ZeonParsePlaceholder(ZeonComponentsRepository repository) {
        ZeonParseGenerator e = new ZeonParseGenerator();
        e.parse();
        componentsToEntity(e.getComponents(), repository);
    }

    private void componentsToEntity(List<Components> components, ZeonComponentsRepository repository) {
        for (var c : components) {
            if(repository.findByNameLikeIgnoreCase(c.getName()).isEmpty()){
                repository.save(new ZeonComponentsEntity(c.getName(), c.getUrl(), c.getCost()));
            }
        }
    }
}
