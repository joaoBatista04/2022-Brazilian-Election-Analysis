package domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.comparators.PartidoComparadorPorCandidato;

import util.enums.TipoEleicao;

public class Eleicao {
    
    //=========================================== ATTRIBUTES ===========================================
    private Map<Integer, Candidato> candidatos = new HashMap<>();
    private Map<Integer, Partido> partidos = new HashMap<>();
    private Map<Integer, Partido> legendasPartidosCandidatos = new HashMap<>();

    private int votosNominais;
    private int votosLegenda;

    private TipoEleicao tipoEleicao;
    private LocalDate dataEleicao;

    /**
     * 
     * @param tipoEleicao
     * @param dataEleicao
     */
    public Eleicao(int tipoEleicao, LocalDate dataEleicao){
        if(tipoEleicao == 7){
            this.tipoEleicao = TipoEleicao.ESTADUAL;
        }

        if(tipoEleicao == 6){
            this.tipoEleicao = TipoEleicao.FEDERAL;
        }

        this.dataEleicao = dataEleicao;

        this.votosNominais = 0;
        this.votosLegenda = 0;
    }

    //=========================================== GETTERS ===========================================
    /**
     * 
     * @return Map<Integer, Candidato> Candidatos da Eleicao
     */
    public Map<Integer, Candidato> getCandidatos() {
        return new HashMap<>(this.candidatos);
    }

    /**
     * 
     * @return Map<Integer, Partido> Partidos da Eleicao
     */
    public Map<Integer, Partido> getPartidos() {
        return new HashMap<>(this.partidos);
    }

    /**
     * 
     * @return Mpa<Integer, Partido> Partidos dos Candidatos da Eleicao
     */
    public Map<Integer, Partido> getLegendasPartidosCandidatos() {
        return new HashMap<>(this.legendasPartidosCandidatos);
    }

    /**
     * 
     * @return int Quantidade de Votos Nominais
     */
    public int getVotosNominais() {
        return votosNominais;
    }

    /**
     * 
     * @return int Quantidade de Votos em Partidos
     */
    public int getVotosLegenda() {
        return votosLegenda;
    }

    /**
     * 
     * @return int Tipo da Eleicao (ESTADUAL = 7, FEDERAL = 6)
     */
    public int getTipoEleicao() {
        if(this.tipoEleicao == TipoEleicao.ESTADUAL){
            return 7;
        } else if(this.tipoEleicao == TipoEleicao.FEDERAL){
            return 6;
        } else{
            return 7;
        }
    }

    /**
     * 
     * @return LocalDate Data da Eleicao
     */
    public LocalDate getDataEleicao() {
        return dataEleicao;
    }

    //=========================================== SETTERS ===========================================
    /**
     * 
     * @param numeroPartido
     * @param siglaPartido
     * @param numeroFederacao
     * @return Partido Adiciona Partido na Eleicao
     */
    public Partido addPartido(int numeroPartido, String siglaPartido, int numeroFederacao){
        Partido p = new Partido(numeroPartido, siglaPartido, numeroFederacao);

        this.partidos.put(numeroPartido, p);

        return p;
    }

    /**
     * 
     * @param numeroCandidato
     * @param nomeCandidatoUrna
     * @param nomeTipoDestinoVotos
     * @param dataNascimento
     * @param situacao
     * @param codigoGenero
     * @param partido
     */
    public void addCandidato(int numeroCandidato, String nomeCandidatoUrna, String nomeTipoDestinoVotos, LocalDate dataNascimento, boolean situacao, int codigoGenero, Partido partido){
        Candidato c = new Candidato(numeroCandidato, nomeCandidatoUrna, dataNascimento, situacao, codigoGenero, nomeTipoDestinoVotos, partido);

        this.candidatos.put(numeroCandidato, c);

        partido.setCandidatosDoPartido(c);
    }

    /**
     * 
     * @param numeroCandidato
     * @param partido
     */
    public void addLegendaPartidoCandidatos(int numeroCandidato, Partido partido){
        this.legendasPartidosCandidatos.put(numeroCandidato, partido);
    }

    /**
     * 
     * @param votosNominais
     */
    public void setVotosNominais(int votosNominais){
        this.votosNominais += votosNominais;
    }

    /**
     * 
     * @param votosLegenda
     */
    public void setVotosLegenda(int votosLegenda){
        this.votosLegenda += votosLegenda;
    }

    //=========================================== SPECIAL GETTERS CANDIDATOS ===========================================
    /**
     * 
     * @return int Numero de Vagas da Eleicao
     */
    public int getNumeroDeVagasDaEleicao(){
        int resultado = 0;
        var c = new ArrayList<Candidato>(candidatos.values());

        for (Candidato cand : c) {
            if (cand.getCodigoSituacaoTotalTurno()){
                resultado++;
            }
        }
        
        return resultado;
    }

    /**
     * 
     * @return List<Candidato> Candidatos Eleitos
     */
    public List<Candidato> getCandidatosEleitos(){
        var candidatosEleitos = new ArrayList<Candidato>();

        for(Candidato cand : candidatos.values()){
            if(cand.getCodigoSituacaoTotalTurno()){
                candidatosEleitos.add(cand);
            }
        }

        candidatosEleitos.sort(null);

        for(int i = 0; i < candidatosEleitos.size(); i++){
            candidatosEleitos.get(i).setEleitoPosicao(i + 1);
        }
        
        return candidatosEleitos;
    }

    /**
     * 
     * @return List<Candidato> Todos os Candidatos da Eleicao
     */
    public List<Candidato> getTodosOsCandidatos(){
        var c = new ArrayList<Candidato>(candidatos.values());

        c.sort(null);

        for (int i = 0; i < c.size(); i++) {
            c.get(i).setPosicaoGeral(i + 1);
        }

        return c;
    }

    /**
     * 
     * @return List<Candidato> Candidatos Mais Votados da Eleicao
     */
    public List<Candidato> getCandidatosMaisVotados(){
        int quantidadeVagas = getNumeroDeVagasDaEleicao();
        var todosOsCandidatos = getTodosOsCandidatos();
        var candidatosMaisVotados = new ArrayList<Candidato>();

        for(int i = 0; i < quantidadeVagas; i++){
            candidatosMaisVotados.add(todosOsCandidatos.get(i));
        }

        candidatosMaisVotados.sort(null);

        return candidatosMaisVotados;
    }

    /**
     * 
     * @return List<Candidato> Candidatos Eleitos se a Eleicao fosse Majoritaria
     */
    public List<Candidato> getCandidatosEleitosSeEleicaoMajoritaria(){
        var eleicaoMajoritaria = new ArrayList<Candidato>();
        var eleitos = getCandidatosEleitos();

        for(Candidato c : getCandidatosMaisVotados()){
            if(!eleitos.contains(c)){
                eleicaoMajoritaria.add(c);
            }
        }

        eleicaoMajoritaria.sort(null);

        return eleicaoMajoritaria;
    }

    /**
     * 
     * @return List<Candidato> Candidatos Eleitos somente pelo Sistema Proporcional de Eleicao
     */
    public List<Candidato> getCandidatosEleitosProporcional(){
        var eleitosProporcional = new ArrayList<Candidato>();
        var maisVotados = getCandidatosMaisVotados();

        for(Candidato c : getCandidatosEleitos()){
            if(!maisVotados.contains(c)){
                eleitosProporcional.add(c);
            }
        }

        eleitosProporcional.sort(null);

        return eleitosProporcional;
    }

    //=========================================== SPECIAL GETTERS PARTIDOS ===========================================
    /**
     * 
     * @return List<Partido> Lista de Partidos da Eleicao
     */
    public List<Partido> getPartidosList(){
        var p = new ArrayList<Partido>(this.partidos.values());

        p.sort(null);

        for(int i = 0; i < p.size(); i++){
            p.get(i).setPosicao(i + 1);
        }

        return p;
    }

    /**
     * 
     * @return List<Partido> Lista de Partidos Ordenados por Candidatos
     */
    public List<Partido> getPartidosOrdenadosPorCandidatos(){
        var listaPartidos = new ArrayList<Partido>();

        for(Partido p : getPartidosList()){
            if(p.getVotosTotais() > 0 && p.getCandidatosList().size() != 0){
                listaPartidos.add(p);
            }
        }

        PartidoComparadorPorCandidato comparador = new PartidoComparadorPorCandidato();

        listaPartidos.sort(comparador);

        for(int i = 0; i < listaPartidos.size(); i++){
            listaPartidos.get(i).setPosicao(i + 1);
        }

        return listaPartidos;
    }

    /**
     * 
     * @param comeco
     * @param fim
     * @return int Quantidade de Candidatos Eleitos por Faixa Etaria
     */
    public int quantidadeEleitosPorIdade(int comeco, int fim){
        int total = 0;
        long diff = 0;

        for(Candidato c : getCandidatosEleitos()){
            diff = c.getDataDeNascimento().until(this.dataEleicao, ChronoUnit.YEARS);

            if(diff >= comeco && diff < fim){
                total++;
            }
        }

        return total;
    }

    /**
     * 
     * @return int Quantidade de Homens Eleitos
     */
    public int quantidadeHomensEleitos(){
        int total = 0;

        for(Candidato c : getCandidatosEleitos()){
            if(c.getCodigoGenero() == 2){
                total++;
            }
        }

        return total;
    }

    /**
     * 
     * @return int Quantidade de Mulheres Eleitas
     */
    public int quantidadeMulheresEleitas(){
        int total = 0;

        for(Candidato c : getCandidatosEleitos()){
            if(c.getCodigoGenero() == 4){
                total++;
            }
        }

        return total;
    }
}
