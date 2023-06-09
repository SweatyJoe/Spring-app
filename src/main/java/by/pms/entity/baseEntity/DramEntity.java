package by.pms.entity.baseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    private double totalCapacity;
    private int capacity;
    @NotNull
    private String type;
    private boolean ecc;
    private int frequency;
    @NotNull
    private String pcIndex;
    private double latency;
    private String timing;
    private double voltage;
    private double xmp;
    private boolean cooling;
    private boolean lowProfileModule;
    private String light;

    public DramEntity(@NotNull String name, @NotNull String kit, double totalCapacity, int capacity, @NotNull String type,
                      boolean ecc, int frequency, @NotNull String pcIndex, double latency, String timing,
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
