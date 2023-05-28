package by.pms.entity.baseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class SsdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    private double capacity;
    @NotNull
    private String form;
    @NotNull
    private String connector;
    private String chipType;
    private String controller;
    private int m2size;
    private String resource;
    private String encryption;
    private int sequentialRead;
    private int sequentialWrite;
    private int randomRead;
    private int randomWrite;
    private double powerRW;
    private double powerE;
    private int tbf;
    private double thickness;
    private boolean cooling;
    private boolean light;

    public SsdEntity(@NotNull String name, double capacity, @NotNull String form, @NotNull String connector,
                     String chipType, String controller, int m2size, String resource,
                     String encryption, int sequentialRead, int sequentialWrite, int randomRead,
                     int randomWrite, double powerRW, double powerE, int tbf, double thickness,
                     boolean cooling, boolean light) {
        this.name = name;
        this.capacity = capacity;
        this.form = form;
        this.connector = connector;
        this.chipType = chipType;
        this.controller = controller;
        this.m2size = m2size;
        this.resource = resource;
        this.encryption = encryption;
        this.sequentialRead = sequentialRead;
        this.sequentialWrite = sequentialWrite;
        this.randomRead = randomRead;
        this.randomWrite = randomWrite;
        this.powerRW = powerRW;
        this.powerE = powerE;
        this.tbf = tbf;
        this.thickness = thickness;
        this.cooling = cooling;
        this.light = light;
    }
}
