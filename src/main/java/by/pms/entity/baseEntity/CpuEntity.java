package by.pms.entity.baseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CpuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String name;
    @NotNull
    private String socket;
    private int cors;
    private int threads;
    private int tdp;
    private boolean box;
    private String integratedGraph;
    private double freq;
    private double maxFreq;
    private String memory;
    @NotNull
    private String url;

    public CpuEntity(@NotNull String name, @NotNull String socket, int cors, int threads, int tdp, boolean box,
                     String integratedGraph, double freq, double maxFreq, String memory, String url) {
        this.name = name;
        this.socket = socket;
        this.cors = cors;
        this.threads = threads;
        this.tdp = tdp;
        this.box = box;
        this.integratedGraph = integratedGraph;
        this.freq = freq;
        this.maxFreq = maxFreq;
        this.memory = memory;
        this.url = url;
    }
}
