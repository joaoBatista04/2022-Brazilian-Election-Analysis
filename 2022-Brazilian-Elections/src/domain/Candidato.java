package domain;

import java.time.LocalDate;

import util.enums.Genero;

public class Candidato implements Comparable<Candidato>{
    
    //=========================================== ATTRIBUTES ===========================================
    private int numeroCandidato;
    private String nomeCandidatoUrna;
    private LocalDate dataDeNascimento;
    private boolean codigoSituacaoTotalTurno;
    private Genero genero;
    private String nomeTipoDestinacaoVotos;
    private int quantidadeVotos;
    private int eleitoPosicao;
    private int posicaoGeral;
    private Partido partidoCandidato;

    /** 
     * @param codigoCargo
     * @param codigoSituacaoCandidatoTot
     * @param numeroCandidato
     * @param nomeCandidatoUrna
     * @param dataDeNascimento
     * @param codigoSituacaoTotalTurno
     * @param codigoGenero
     * @param nomeTipoDestinacaoVotos
     * @param partidoCandidato
     */
    public Candidato(int numeroCandidato, String nomeCandidatoUrna, LocalDate dataDeNascimento, boolean codigoSituacaoTotalTurno, int codigoGenero, String nomeTipoDestinacaoVotos, Partido partidoCandidato){
        this.numeroCandidato = numeroCandidato;
        this.nomeCandidatoUrna = nomeCandidatoUrna;
        this.dataDeNascimento = dataDeNascimento;
        this.codigoSituacaoTotalTurno = codigoSituacaoTotalTurno;
        this.nomeTipoDestinacaoVotos = nomeTipoDestinacaoVotos;
        this.partidoCandidato = partidoCandidato;
        this.quantidadeVotos = 0;

        if(codigoGenero == 2){
            this.genero = Genero.MASCULINO;
        } else if(codigoGenero == 4){
            this.genero = Genero.FEMININO;
        } else{
            this.genero = Genero.FEMININO;
        }
    }

    //=========================================== SETTERS ===========================================
    /**
     * @param quantidadeVotos
     */
    public void setQuantidadeVotos(int quantidadeVotos){
        this.quantidadeVotos += quantidadeVotos;
    }

    /**
     * @param eleitoPosicao
     */
    public void setEleitoPosicao(int eleitoPosicao){
        this.eleitoPosicao = eleitoPosicao;
    }

    /**
     * @param posicaoGeral
     */
    public void setPosicaoGeral(int posicaoGeral){
        this.posicaoGeral = posicaoGeral;
    }

    //=========================================== GETTERS ===========================================
    /**
     * @return (int) numeroCandidato
     */
    public int getNumeroCandidato(){
        return this.numeroCandidato;
    }

    /**
     * @return (String) Nome do Candidato na Urna
     */
    public String getNomeCandidatoUrna(){
        return this.nomeCandidatoUrna;
    }

    /**
     * @return (LocalDate) Data de Nascimento do Candidato
     */
    public LocalDate getDataDeNascimento(){
        return this.dataDeNascimento;
    }

    /**
     * @return (boolean) CodigoSituacaoTotalCandidato
     */
    public boolean getCodigoSituacaoTotalTurno(){
        return this.codigoSituacaoTotalTurno;
    }

    /**
     * @return (int) Codigo de Genero
     */
    public int getCodigoGenero(){
        if(this.genero == Genero.MASCULINO){
            return 2;
        }

        if(this.genero == Genero.FEMININO){
            return 4;
        }

        return 4;
    }

    /**
     * @return (String) NomeTipoDestinacaoVotos
     */
    public String getNomeTipoDestinacaoVotos() {
        return this.nomeTipoDestinacaoVotos;
    }

    /**
     * @return (int) Quantidade de Votos do Candidato
     */
    public int getQuantidadeVotos() {
        return this.quantidadeVotos;
    }

    /**
     * @return (int) Posicao do eleito
     */
    public int getEleitoPosicao() {
        return this.eleitoPosicao;
    }

    /**
     * @return (int) Posicao geral do eleito
     */
    public int getPosicaoGeral() {
        return this.posicaoGeral;
    }

    /**
     * @return (Partido) Partido do Candidato
     */
    public Partido getPartidoCandidato() {
        return this.partidoCandidato;
    }

    //=========================================== COMPARABLES AND OVERRIDES ===========================================
    @Override
    public int compareTo(Candidato o) {
        if (this.quantidadeVotos < o.getQuantidadeVotos())
            return 1;
        if (this.quantidadeVotos > o.getQuantidadeVotos())
            return -1;
        if (this.dataDeNascimento.compareTo(o.getDataDeNascimento()) < 0)
            return -1;
        if (this.dataDeNascimento.compareTo(o.getDataDeNascimento()) > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return this.nomeCandidatoUrna + " (" + this.partidoCandidato.getSiglaDoPartido() + ", " + this.quantidadeVotos + " votos)";
    }
}
