package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Eleicao {
    
    //=========================================== ATTRIBUTES ===========================================
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    private Map<Integer, Partido> partidos = new HashMap<>();
    private Map<Integer, Partido> legendasPartidosCandidatos = new HashMap<>();

    private int votosNominais;
    private int votosLegenda;

    private int tipoEleicao;
    private LocalDate dataEleicao;

    public Eleicao(int tipoEleicao, LocalDate dataEleicao){
        this.tipoEleicao = tipoEleicao;
        this.dataEleicao = dataEleicao;
    }

    //=========================================== GETTERS ===========================================
    public Map<Integer, Candidato> getCandidatos() {
        return new HashMap<>(this.candidatos);
    }

    public Map<Integer, Partido> getPartidos() {
        return new HashMap<>(this.partidos);
    }

    public Map<Integer, Partido> getLegendasPartidosCandidatos() {
        return new HashMap<>(this.legendasPartidosCandidatos);
    }

    public int getVotosNominais() {
        return votosNominais;
    }

    public int getVotosLegenda() {
        return votosLegenda;
    }

    public int getTipoEleicao() {
        return tipoEleicao;
    }

    public LocalDate getDataEleicao() {
        return dataEleicao;
    }
}
