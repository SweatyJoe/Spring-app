package by.pms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PowerSupplyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String appointment;
    private int power;
    @NotNull
    private String powerRange;
    private int height;
    private int width;
    private int depth;

    private String standard;
    private int v12Lines;
    private double v12AmperageMax;
    private double v12CombinePower;
    private String PFC;
    private String certificate;
    private String fanSize;
    private boolean light;

    //раъёмы:
    
}
