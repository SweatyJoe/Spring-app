package by.pms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class DramEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String kit;
    @NotNull
    private int totalCapacity;
    private int capacity;
    @NotNull
    private String type;
    @NotNull
    private boolean ecc;
    @NotNull
    private int frequency;
    @NotNull
    private String pcIndex;
    @NotNull
    private double latency;
    private String timing;
    private double voltage;
    private double xmp;
    private boolean cooling;
    private boolean lowProfileModule;
    private String light;

    public DramEntity(String name, String kit, int totalCapacity, int capacity, String type,
                      boolean ecc, int frequency, String pcIndex, double latency, String timing,
                      double voltage, double xmp, boolean cooling, boolean lowProfileModule, String light) {
        this.name = name;
        this.kit = kit;
        this.totalCapacity = totalCapacity;
        this.capacity = capacity;
        this.type = type;
        this.ecc = ecc;
        this.frequency = frequency;
        this.pcIndex = pcIndex;
        this.latency = latency;
        this.timing = timing;
        this.voltage = voltage;
        this.xmp = xmp;
        this.cooling = cooling;
        this.lowProfileModule = lowProfileModule;
        this.light = light;
    }
}
