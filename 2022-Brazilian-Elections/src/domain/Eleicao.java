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
        if(this.tipoEleicao == TipoEleicao.ESTADUAL){
            return 7;
        } else if(this.tipoEleicao == TipoEleicao.FEDERAL){
            return 6;
        } else{
            return 7;
        }
    }

    public LocalDate getDataEleicao() {
        return dataEleicao;
    }

    //=========================================== SETTERS ===========================================
    public Partido addPartido(int numeroPartido, String siglaPartido, int numeroFederacao){
        Partido p = new Partido(numeroPartido, siglaPartido, numeroFederacao);

        this.partidos.put(numeroPartido, p);

        return p;
    }

    public void addCandidato(int numeroCandidato, String nomeCandidatoUrna, String nomeTipoDestinoVotos, LocalDate dataNascimento, boolean situacao, int codigoGenero, Partido partido){
        Candidato c = new Candidato(numeroCandidato, nomeCandidatoUrna, dataNascimento, situacao, codigoGenero, nomeTipoDestinoVotos, partido);

        this.candidatos.put(numeroCandidato, c);

        partido.setCandidatosDoPartido(c);
    }

    public void addLegendaPartidoCandidatos(int numeroCandidato, Partido partido){
        this.legendasPartidosCandidatos.put(numeroCandidato, partido);
    }

    public void setVotosNominais(int votosNominais){
        this.votosNominais += votosNominais;
    }

    public void setVotosLegenda(int votosLegenda){
        this.votosLegenda += votosLegenda;
    }

    //=========================================== SPECIAL GETTERS CANDIDATOS ===========================================
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

    public List<Candidato> getTodosOsCandidatos(){
        var c = new ArrayList<Candidato>(candidatos.values());

        c.sort(null);

        for (int i = 0; i < c.size(); i++) {
            c.get(i).setPosicaoGeral(i + 1);
        }

        return c;
    }

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
    public List<Partido> getPartidosList(){
        var p = new ArrayList<Partido>(this.partidos.values());

        p.sort(null);

        for(int i = 0; i < p.size(); i++){
            p.get(i).setPosicao(i + 1);
        }

        return p;
    }

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

    public int quantidadeHomensEleitos(){
        int total = 0;

        for(Candidato c : getCandidatosEleitos()){
            if(c.getCodigoGenero() == 2){
                total++;
            }
        }

        return total;
    }

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
