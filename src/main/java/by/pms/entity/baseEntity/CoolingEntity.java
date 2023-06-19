package by.pms.entity.baseEntity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@Entity
@NoArgsConstructor
@Getter
@PackagePrivate
public class CoolingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotNull
    String name;
    @NotNull
    String coolingType;
    @NotNull
    String socket;
    String material;
    int fanDiameter;
    int fanNumber;
    int minSpeed;
    int maxSpeed;
    double airflow;
    boolean pwm;
    String connectorType;
    @Nullable
    String lightConnectorType;
    String light;
    double noise;
    int tdp;
    //pomp
    int pompMinSpeed;
    int pompMaxSpeed;
    boolean pompLight;

    double width;
    double depth;
    double height;

    public CoolingEntity(@NotNull String name, @NotNull String coolingType, @NotNull String socket, String material,
                         int fanDiameter, int fanNumber, int minSpeed, int maxSpeed, double airflow, boolean pwm,
                         String connectorType, @Nullable String lightConnectorType, String light, double noise,
                         int pompMinSpeed, int pompMaxSpeed, boolean pompLight, double width, double depth,
                         double height, int tdp) {
        this.name = name;
        this.coolingType = coolingType;
        this.socket = socket;
        this.material = material;
        this.fanDiameter = fanDiameter;
        this.fanNumber = fanNumber;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.airflow = airflow;
        this.pwm = pwm;
        this.connectorType = connectorType;
        this.lightConnectorType = lightConnectorType;
        this.light = light;
        this.noise = noise;
        this.pompMinSpeed = pompMinSpeed;
        this.pompMaxSpeed = pompMaxSpeed;
        this.pompLight = pompLight;
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.tdp = tdp;
    }
}
