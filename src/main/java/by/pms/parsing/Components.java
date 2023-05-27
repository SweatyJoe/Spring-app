package by.pms.parsing;

import lombok.Getter;

@Getter
public class Components {
    private String name;
    private String url;
    private float cost;

    public Components(String name, String url, float cost) {
        this.name = name;
        this.url = url;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Components{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", cost=" + cost +
                '}';
    }
}
