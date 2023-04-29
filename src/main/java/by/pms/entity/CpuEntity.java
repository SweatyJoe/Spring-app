package by.pms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
    @NotNull
    private int cors;
    @NotNull
    private int threads;
    @NotNull
    private int tdp;
    private boolean box;
    @NotNull
    private String integratedGraph;
    @NotNull
    private double freq;
    @NotNull
    private double maxFreq;
    @NotNull
    private String memory;
    @NotNull
    private String url;

    public CpuEntity(String name, String socket, int cors, int threads, int tdp, boolean box,
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
