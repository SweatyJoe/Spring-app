package by.pms.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Entity
@Data
@NoArgsConstructor
@PackagePrivate
public class VideoCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    String name;
    @NotNull
    String type; //interface
    @NotNull
    String manufacturer;
    String graphicProcessor;
    boolean overclock;
    boolean beamTracing;
    boolean lhr;
    boolean external;
    int baseFreq;
    int maxFreq;
    int countStreamProcessors;
    int countRTCors;
    int videoMemory;
    String memoryType;
    int memoryFreq;
    double memoryBandwidth;
    int memoryBus;
    String directX;
    boolean sli;
    String connectors;
    int energyConsumption;
    int recommendedPower;
    String colling;
    double thicknessCooling;
    int fansCount;
    double length;
    double height;
    double thickness;
    boolean lowProfile;
    String subFunctions;
    //interfaces
    int vga;
    int dvi;
    int hdmi;
    @Nullable
    String hdmiVersion;
    int miniHdmi;
    int displayPort;
    @Nullable
    String displayPortVersion;
    int miniDisplayPort;
    int VESAStereo;
    int usbTypeC;

    public VideoCardEntity(@NotNull String name, @NotNull String type, @NotNull String manufacturer, String graphicProcessor,
                           boolean overclock, boolean beamTracing, boolean lhr, boolean external,
                           int baseFreq, int maxFreq, int countStreamProcessors, int countRTCors,
                           int videoMemory, String memoryType, int memoryFreq, double memoryBandwidth,
                           int memoryBus, String directX, boolean sli, String connectors,
                           int energyConsumption, int recommendedPower, String colling,
                           double thicknessCooling, int fansCount, double length, double height, double thickness,
                           boolean lowProfile, String subFunctions, int vga, int dvi, int hdmi,
                           @Nullable String hdmiVersion, int miniHdmi, int displayPort,
                           @Nullable String displayPortVersion, int miniDisplayPort, int VESAStereo,
                           int usbTypeC) {
        this.name = name;
        this.type = type;
        this.manufacturer = manufacturer;
        this.graphicProcessor = graphicProcessor;
        this.overclock = overclock;
        this.beamTracing = beamTracing;
        this.lhr = lhr;
        this.external = external;
        this.baseFreq = baseFreq;
        this.maxFreq = maxFreq;
        this.countStreamProcessors = countStreamProcessors;
        this.countRTCors = countRTCors;
        this.videoMemory = videoMemory;
        this.memoryType = memoryType;
        this.memoryFreq = memoryFreq;
        this.memoryBandwidth = memoryBandwidth;
        this.memoryBus = memoryBus;
        this.directX = directX;
        this.sli = sli;
        this.connectors = connectors;
        this.energyConsumption = energyConsumption;
        this.recommendedPower = recommendedPower;
        this.colling = colling;
        this.thicknessCooling = thicknessCooling;
        this.length = length;
        this.height = height;
        this.thickness = thickness;
        this.lowProfile = lowProfile;
        this.subFunctions = subFunctions;
        this.vga = vga;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.hdmiVersion = hdmiVersion;
        this.miniHdmi = miniHdmi;
        this.displayPort = displayPort;
        this.displayPortVersion = displayPortVersion;
        this.miniDisplayPort = miniDisplayPort;
        this.VESAStereo = VESAStereo;
        this.usbTypeC = usbTypeC;
        this.fansCount = fansCount;
    }
}
