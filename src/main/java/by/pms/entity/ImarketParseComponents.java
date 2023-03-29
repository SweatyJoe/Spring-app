package by.pms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ImarketParseComponents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    private String url;
    private float cost;

    public ImarketParseComponents(String name, String url, float cost) {
        this.name = name;
        this.url = url;
        this.cost = cost;
    }
}
