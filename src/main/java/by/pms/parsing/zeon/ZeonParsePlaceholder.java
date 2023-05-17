package by.pms.parsing.zeon;

import by.pms.parsing.Components;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *  сделать класс, ну а вообще надо:
 *  поразмыслить над entity для этого всего;
 *  подумать над объеденением онлайнера и зеона,
 *  что б была общая база цен.
 */
public class ZeonParsePlaceholder extends ZeonParseGenerator {
    private List<Components> ZeonComponents = new ArrayList<>();

    public ZeonParsePlaceholder() {
        ZeonParseGenerator e = new ZeonParsePlaceholder();
        e.parse();
        ZeonComponents = e.getComponents();
    }

    private void doing() {

    }
}
