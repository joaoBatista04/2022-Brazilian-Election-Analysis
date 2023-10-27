package domain;

import java.util.ArrayList;
import java.util.List;

public class Partido {
    
    //=========================================== ATTRIBUTES ===========================================
    private int numeroPartido;
    private String siglaPartido;
    private int numeroFederacao;
    private int votosNaLegenda;
    private int posicao;
    private List<Candidato> candidatosDoPartido;

    /**
     * @param numeroPartido
     * @param siglaPartido
     * @param numeroFederacao
     */
    public Partido(int numeroPartido, String siglaPartido, int numeroFederacao){
        this.numeroPartido = numeroPartido;
        this.siglaPartido = siglaPartido;
        this.numeroFederacao = numeroFederacao;
        this.candidatosDoPartido = new ArrayList<>();
    }

    //=========================================== SETTERS ===========================================
    /**
     * @param votosNaLegenda
     */
    public void setVotosNaLegenda(int votosNaLegenda){
        this.votosNaLegenda += votosNaLegenda;
    }

    /**
     * @param posicao
     */
    public void setPosicao(int posicao){
        this.posicao = posicao;
    }

    /**
     * @param candidato
     */
    public void setCandidatosDoPartido(Candidato candidato){
        this.candidatosDoPartido.add(candidato);
    }

    //=========================================== GETTERS ===========================================
}
