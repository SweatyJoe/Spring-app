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
    private String name;
    @NotNull
    private String appointment;
    private int power;
    @NotNull
    private String powerRange;
    private double height;
    private double width;
    private double depth;

    private String standard;
    private int v12Lines;
    private double v12AmperageMax;
    private double v12CombinePower;
    private String PFC;
    private String certificate;
    private String fanSize;
    private boolean light;
    //раъёмы:
    private boolean moduleConnection;
    private String motherPower;
    private String cpu4pin;
    private String cpu8pin;
    private String fdd4pin;
    private String ide4pin;
    private String sata;
    private String pci6pin;
    private String pci8pin;
    private String pci16pin;
    private boolean usbPower;

    public PowerSupplyEntity(@NotNull String name, @NotNull String appointment, int power, @NotNull String powerRange, double height,
                             double width, double depth, String standard, int v12Lines, double v12AmperageMax,
                             double v12CombinePower, String PFC, String certificate, String fanSize,
                             boolean light, boolean moduleConnection, String motherPower, String cpu4pin,
                             String cpu8pin, String fdd4pin, String ide4pin, String sata, String pci6pin,
                             String pci8pin, String pci16pin, boolean usbPower) {
        this.name = name;
        this.appointment = appointment;
        this.power = power;
        this.powerRange = powerRange;
        this.height = height;
        this.width = width;
        this.depth = depth;
        this.standard = standard;
        this.v12Lines = v12Lines;
        this.v12AmperageMax = v12AmperageMax;
        this.v12CombinePower = v12CombinePower;
        this.PFC = PFC;
        this.certificate = certificate;
        this.fanSize = fanSize;
        this.light = light;
        this.moduleConnection = moduleConnection;
        this.motherPower = motherPower;
        this.cpu4pin = cpu4pin;
        this.cpu8pin = cpu8pin;
        this.fdd4pin = fdd4pin;
        this.ide4pin = ide4pin;
        this.sata = sata;
        this.pci6pin = pci6pin;
        this.pci8pin = pci8pin;
        this.pci16pin = pci16pin;
        this.usbPower = usbPower;
    }
}
