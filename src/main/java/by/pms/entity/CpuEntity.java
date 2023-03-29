package by.pms.entity;

import jakarta.persistence.*;
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
    private String name;
    private String socket;
    private int cors;
    private int threads;
    private int tdp;
    private boolean box;
    private boolean integratedGraph;
    private double freq;
    private double maxFreq;

    public CpuEntity(String name, String socket, int cors, int threads, int tdp, boolean box, boolean integratedGraph, double freq, double maxFreq) {
        this.name = name;
        this.socket = socket;
        this.cors = cors;
        this.threads = threads;
        this.tdp = tdp;
        this.box = box;
        this.integratedGraph = integratedGraph;
        this.freq = freq;
        this.maxFreq = maxFreq;
    }
}
