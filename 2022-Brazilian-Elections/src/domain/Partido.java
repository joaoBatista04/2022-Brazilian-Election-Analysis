package domain;

import java.util.ArrayList;
import java.util.List;

public class Partido implements Comparable<Partido>{
    
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
    public String getSiglaDoPartido(){
        return this.siglaPartido;
    }

    public int getFederacaoPartido(){
        return this.numeroFederacao;
    }

    public int getPosicao(){
        return this.posicao;
    }

    public int getNumeroPartido(){
        return this.numeroPartido;
    }

    public int getVotosNaLegenda(){
        return this.votosNaLegenda;
    }

    //=========================================== SPECIAL GETTERS ===========================================
    public int getVotosNominais() {
        int total = 0;

        for (Candidato c : this.candidatosDoPartido) {
        total += c.getQuantidadeVotos();
        }

        return total;
    }

    public int getVotosTotais(){
        return this.votosNaLegenda + getVotosNominais();
    }

    public int getQuantidadeDeEleitosPartido(){
        int total = 0;

        for(Candidato c : this.candidatosDoPartido){
            if(c.getCodigoSituacaoTotalTurno()){
                total++;
            }
        }

        return total;
    }

    public List<Candidato> getCandidatosList(){
        var candidatos = new ArrayList<>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos;
    }

    public Candidato candidatoMaisVotadoPartido(){
        var candidatos = new ArrayList<Candidato>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos.get(0);
    }

    public Candidato candidatoMenosVotadoPartido(){
        var candidatos = new ArrayList<Candidato>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos.get(candidatos.size() - 1);
    }

    //=========================================== COMPARABLES AND OVERRIDES ===========================================
    @Override
    public int compareTo(Partido arg0) {
        int ownTotal = this.votosNaLegenda + getVotosNominais();
        int otherTotal = arg0.votosNaLegenda + arg0.getVotosNominais();

        if (ownTotal > otherTotal)
        return -1;
        if (ownTotal < otherTotal)
        return 1;
        if (this.numeroPartido < arg0.numeroPartido)
        return -1;
        if (this.numeroPartido > arg0.numeroPartido)
        return 1;
        return 0;
  }
}
