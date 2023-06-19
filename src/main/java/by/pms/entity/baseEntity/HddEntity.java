package by.pms.entity.baseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Entity
@Getter
@PackagePrivate
@NoArgsConstructor
public class HddEntity {
    @Id
    Long id;
    String name;
    int capacity;
    String formFactor;
    String interfaceType;
    int speed;
    int buffer;
    boolean security;
    String sectorSize;
    double noiseLevelRW;
    double noiseLevelWait;
    int percussionInWork;
    int percussionInStay;
    double powerRW;
    double powerWait;
    double height;

    public HddEntity(String name, int capacity, String formFactor, String interfaceType, int speed, int buffer,
                     boolean security, String sectorSize, double noiseLevelRW, double noiseLevelWait,
                     int percussionInWork, int percussionInStay, double powerRW, double powerWait, double height) {
        this.name = name;
        this.capacity = capacity;
        this.formFactor = formFactor;
        this.interfaceType = interfaceType;
        this.speed = speed;
        this.buffer = buffer;
        this.security = security;
        this.sectorSize = sectorSize;
        this.noiseLevelRW = noiseLevelRW;
        this.noiseLevelWait = noiseLevelWait;
        this.percussionInWork = percussionInWork;
        this.percussionInStay = percussionInStay;
        this.powerRW = powerRW;
        this.powerWait = powerWait;
        this.height = height;
    }
}
