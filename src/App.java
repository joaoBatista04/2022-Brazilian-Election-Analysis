import java.io.BufferedReader;
import java.time.LocalDate;

import util.io.InputServices;
import util.io.OutputServices;

import domain.Eleicao;

import util.exceptions.ExceptionArquivoCandidatos;
import util.exceptions.ExceptionArquivoPartidos;
import util.exceptions.ExceptionDataEleicao;

public class App {
    public static void main(String[] args) throws Exception{
        //Definicao do tipo da eleicao
        int tipo;
        if(args[0].compareTo("--estadual") == 0){
            tipo = 7;
        } else if(args[0].compareTo("--federal") == 0){
            tipo = 6;
        } else{
            throw new Exception("Argumento inválido para o tipo de eleição!");
        }

        //Criacao dos BufferedReaders para leitura dos arquivos .csv
        BufferedReader bufferCandidatos, bufferVotacao;
        try{
            bufferCandidatos = InputServices.createBufferedReader(args[1]);
        } catch(Exception e){
            throw new ExceptionArquivoCandidatos();
        }
        try{
            bufferVotacao = InputServices.createBufferedReader(args[2]);
        } catch(Exception e){
            throw new ExceptionArquivoPartidos();
        }

        //Definicao da data da eleicao
        String[] data = args[3].split("/");
        int dia, mes, ano;
        try{
            dia = Integer.parseInt(data[0]);
            mes = Integer.parseInt(data[1]);
            ano = Integer.parseInt(data[2]);
        } catch(Exception e){
            throw new ExceptionDataEleicao();
        }

        //Criacao da eleicao
        Eleicao eleicao = new Eleicao(tipo, LocalDate.of(ano, mes, dia));

        try {
            //Leitura dos arquivos .csv e atualizacao das informacoes da eleicao
            InputServices.processarArquivoCandidatos(bufferCandidatos, eleicao);
            InputServices.processarArquivoVotacao(bufferVotacao, eleicao);

            //Geracao dos relatorios da eleicao baseado nos dados lidos
            OutputServices.geracaoDeRelatorios(eleicao);
        } catch (Exception e) {
            throw e;
        }
    }
}
