package by.pms.entity.baseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Getter
@Entity
@NoArgsConstructor
@PackagePrivate
public class MotherboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    String name;
    @NotNull
    String manufacturer;
    @NotNull
    String socket;
    @NotNull
    String chipset;
    @NotNull
    String formFactor;
    boolean light;
    @NotNull
    String memoryType;
    int countMemorySlots;
    int maxMemory;
    String memoryMode;
    int maxMemoryFreq;
    //слоты расширения
    String pciVersion;
    String pciMode;
    int pci16Total;
    int pci1620;
    int pci1;
    int pci120;
    int pci4;
    int pci420;
    int pci8;
    int pci820;
    int pci;
    int pciX;
    String dopPciInfo;
    //интерфейсы накопителей
    int m2;
    String m2Info;
    int sata30;
    int sata20;
    String raid;
    //сеть
    boolean wifiSlot;
    String wifi;
    double bluetooth;
    String ethernet;
    //audio
    boolean graphicSupport;
    String sound;
    double soundMode;
    //разёмы
    int usb20;
    int usbGen1TypeA;
    int usbGen2TypeA;
    int usbGen1TypeC;
    int usbGen2TypeC;
    int usbGen2x2;
    int thunderbolt3;
    int thunderbolt4;
    int sPDIF;
    int jack35;
    int com;
    int lpt;
    int ps2;
    int displayPort;
    int miniDisplayPort;
    int vga;
    int dvi;
    int hdmi;
    int lssConnectors;
    int caseFanConnectors;
    int argbConnectors;
    int rgbConnectors;

    int length;
    int width;

    public MotherboardEntity(@NotNull String name, @NotNull String manufacturer, @NotNull String socket,
                             @NotNull String chipset, @NotNull String formFactor, boolean light,
                             @NotNull String memoryType, int countMemorySlots, int maxMemory,
                             String memoryMode, int maxMemoryFreq, String pciVersion, String pciMode,
                             int pci16Total, int pci1620, int pci1, int pci120, int pci4, int pci420,
                             int pci8, int pci820, int pci, int pciX, String dopPciInfo, int m2,
                             String m2Info, int sata30, int sata20, String raid, boolean wifiSlot,
                             String wifi, double bluetooth, String ethernet, boolean graphicSupport,
                             String sound, double soundMode, int usb20, int usbGen1TypeA,
                             int usbGen2TypeA, int usbGen1TypeC, int usbGen2TypeC, int usbGen2x2,
                             int thunderbolt3, int thunderbolt4, int sPDIF, int jack35, int com,
                             int lpt, int ps2, int displayPort, int miniDisplayPort, int vga, int dvi,
                             int hdmi, int lssConnectors, int caseFanConnectors, int argbConnectors,
                             int rgbConnectors, int length, int width) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.socket = socket;
        this.chipset = chipset;
        this.formFactor = formFactor;
        this.light = light;
        this.memoryType = memoryType;
        this.countMemorySlots = countMemorySlots;
        this.maxMemory = maxMemory;
        this.memoryMode = memoryMode;
        this.maxMemoryFreq = maxMemoryFreq;
        this.pciVersion = pciVersion;
        this.pciMode = pciMode;
        this.pci16Total = pci16Total;
        this.pci1620 = pci1620;
        this.pci1 = pci1;
        this.pci120 = pci120;
        this.pci4 = pci4;
        this.pci420 = pci420;
        this.pci8 = pci8;
        this.pci820 = pci820;
        this.pci = pci;
        this.pciX = pciX;
        this.dopPciInfo = dopPciInfo;
        this.m2 = m2;
        this.m2Info = m2Info;
        this.sata30 = sata30;
        this.sata20 = sata20;
        this.raid = raid;
        this.wifiSlot = wifiSlot;
        this.wifi = wifi;
        this.bluetooth = bluetooth;
        this.ethernet = ethernet;
        this.graphicSupport = graphicSupport;
        this.sound = sound;
        this.soundMode = soundMode;
        this.usb20 = usb20;
        this.usbGen1TypeA = usbGen1TypeA;
        this.usbGen2TypeA = usbGen2TypeA;
        this.usbGen1TypeC = usbGen1TypeC;
        this.usbGen2TypeC = usbGen2TypeC;
        this.usbGen2x2 = usbGen2x2;
        this.thunderbolt3 = thunderbolt3;
        this.thunderbolt4 = thunderbolt4;
        this.sPDIF = sPDIF;
        this.jack35 = jack35;
        this.com = com;
        this.lpt = lpt;
        this.ps2 = ps2;
        this.displayPort = displayPort;
        this.miniDisplayPort = miniDisplayPort;
        this.vga = vga;
        this.dvi = dvi;
        this.hdmi = hdmi;
        this.lssConnectors = lssConnectors;
        this.caseFanConnectors = caseFanConnectors;
        this.argbConnectors = argbConnectors;
        this.rgbConnectors = rgbConnectors;
        this.length = length;
        this.width = width;
    }
}
