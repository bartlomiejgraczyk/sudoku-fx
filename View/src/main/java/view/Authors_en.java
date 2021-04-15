package view;

import java.util.ListResourceBundle;

public class Authors_en extends ListResourceBundle {

    private final Object[][] contents = {
            {"1", "Jakub Pietras"},
            {"2", "Bartłomiej Graczyk"}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}