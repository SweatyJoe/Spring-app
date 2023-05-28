package by.pms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Entity
@Getter
@NoArgsConstructor
@PackagePrivate
public class ProofficeComponentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String url;
    Float cost;
    public ProofficeComponentsEntity(String name, String url, Float cost) {
        this.name = name;
        this.url = url;
        this.cost = cost;
    }
}
