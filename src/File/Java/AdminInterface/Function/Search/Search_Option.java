package File.Java.AdminInterface.Function.Search;

import javax.swing.Icon;

public class Search_Option {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Search_Option(String name, Icon icon) {
        this.name = name;
        this.icon = icon;
    }


    private String name;
    private Icon icon;
}
