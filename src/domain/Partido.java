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
    /**
     * 
     * @return String Sigla do Partido
     */
    public String getSiglaDoPartido(){
        return this.siglaPartido;
    }

    /**
     * 
     * @return int Numero da Federacao do Partido
     */
    public int getFederacaoPartido(){
        return this.numeroFederacao;
    }

    /**
     * 
     * @return int Posicao do Partido na Eleicao
     */
    public int getPosicao(){
        return this.posicao;
    }

    /**
     * 
     * @return int Numero do Partido
     */
    public int getNumeroPartido(){
        return this.numeroPartido;
    }

    /**
     * 
     * @return int Quantidade de Votos Recebidos pelo Partido
     */
    public int getVotosNaLegenda(){
        return this.votosNaLegenda;
    }

    //=========================================== SPECIAL GETTERS ===========================================
    /**
     * 
     * @return int Quantidade de Votos Nominais obtidos pelo Partido
     */
    public int getVotosNominais() {
        int total = 0;

        for (Candidato c : this.candidatosDoPartido) {
        total += c.getQuantidadeVotos();
        }

        return total;
    }

    /**
     * 
     * @return int Quantidade de Votos Totais recebidos pelo Partido
     */
    public int getVotosTotais(){
        return this.votosNaLegenda + getVotosNominais();
    }

    /**
     * 
     * @return int Quantidade de Candidatos Eleitos do Partido
     */
    public int getQuantidadeDeEleitosPartido(){
        int total = 0;

        for(Candidato c : this.candidatosDoPartido){
            if(c.getCodigoSituacaoTotalTurno()){
                total++;
            }
        }

        return total;
    }

    /**
     * 
     * @return List<Candidato> Lista de Candidatos do Partido
     */
    public List<Candidato> getCandidatosList(){
        var candidatos = new ArrayList<>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos;
    }

    /**
     * 
     * @return Candidato Mais Votado do Partido
     */
    public Candidato candidatoMaisVotadoPartido(){
        var candidatos = new ArrayList<Candidato>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos.get(0);
    }

    /**
     * 
     * @return Candidato Menos Votado do Partido
     */
    public Candidato candidatoMenosVotadoPartido(){
        var candidatos = new ArrayList<Candidato>(this.candidatosDoPartido);

        candidatos.sort(null);

        return candidatos.get(candidatos.size() - 1);
    }

    //=========================================== COMPARABLES AND OVERRIDES ===========================================
    @Override
    public int compareTo(Partido p0) {
        int ownTotal = this.votosNaLegenda + getVotosNominais();
        int otherTotal = p0.votosNaLegenda + p0.getVotosNominais();

        if (ownTotal > otherTotal)
        return -1;
        if (ownTotal < otherTotal)
        return 1;
        if (this.numeroPartido < p0.numeroPartido)
        return -1;
        if (this.numeroPartido > p0.numeroPartido)
        return 1;
        return 0;
  }
}
