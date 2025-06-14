package br.com.alura.screensound.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    private Carreira carreira;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas = new ArrayList<>();

    public Artista(){}

    public Artista(String nome, String carreira) {
        this.nome = nome;
        this.carreira = Carreira.valueOf(carreira.toUpperCase());
    }

    public String getNome() {
        return nome;
    }

    public Carreira getCarreira() {
        return carreira;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCarreira(Carreira carreira) {
        this.carreira = carreira;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "Artista: " + nome + " | " +
                "Carreira: " + carreira + " | " +
                "Musicas:" + musicas;
    }
}
