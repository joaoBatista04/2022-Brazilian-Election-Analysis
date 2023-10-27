package domain;

import java.time.LocalDate;

public class Candidato {
    
    //=========================================== ATTRIBUTES ===========================================
    private int codigoCargo;
    private int codigoSituacaoCandidatoTot;
    private int numeroCandidato;
    private String nomeCandidatoUrna;
    private LocalDate dataDeNascimento;
    private boolean codigoSituacaoTotalTurno;
    private int codigoGenero;
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
    public Candidato(int codigoCargo, int codigoSituacaoCandidatoTot, int numeroCandidato, String nomeCandidatoUrna, LocalDate dataDeNascimento, boolean codigoSituacaoTotalTurno, int codigoGenero, String nomeTipoDestinacaoVotos, Partido partidoCandidato){
        this.codigoCargo = codigoCargo;
        this.codigoSituacaoCandidatoTot = codigoSituacaoCandidatoTot;
        this.numeroCandidato = numeroCandidato;
        this.nomeCandidatoUrna = nomeCandidatoUrna;
        this.dataDeNascimento = dataDeNascimento;
        this.codigoSituacaoTotalTurno = codigoSituacaoTotalTurno;
        this.codigoGenero = codigoGenero;
        this.nomeTipoDestinacaoVotos = nomeTipoDestinacaoVotos;
        this.partidoCandidato = partidoCandidato;
        this.quantidadeVotos = 0;
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
     * @return (int) codigoCargo
     */
    public int getCodigoCargo(){
        return this.codigoCargo;
    }

    /**
     * @return (int) codigoSituacaoCandidatoTot
     */
    public int codigoSituacaoCandidatoTot(){
        return this.codigoSituacaoCandidatoTot;
    }

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
        return this.codigoGenero;
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
}
