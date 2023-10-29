import java.io.BufferedReader;
import java.time.LocalDate;

import domain.Eleicao;
import util.exceptions.ExceptionArquivoCandidatos;
import util.exceptions.ExceptionArquivoPartidos;
import util.exceptions.ExceptionDataEleicao;
import util.io.InputServices;

public class App {
    public static void main(String[] args) throws Exception{
        int tipo;
        if(args[0].compareTo("--estadual") == 0){
            tipo = 6;
        } else if(args[0].compareTo("--federal") == 0){
            tipo = 7;
        } else{
            throw new Exception("Argumento inválido para o tipo de eleição!");
        }

        BufferedReader bufferCandidatos, bufferPartidos;
        try{
            bufferCandidatos = InputServices.createBufferedReader(args[1]);
        } catch(Exception e){
            throw new ExceptionArquivoCandidatos();
        }
        try{
            bufferPartidos = InputServices.createBufferedReader(args[2]);
        } catch(Exception e){
            throw new ExceptionArquivoPartidos();
        }

        String[] data = args[3].split("/");
        int dia, mes, ano;
        try{
            dia = Integer.parseInt(data[0]);
            mes = Integer.parseInt(data[1]);
            ano = Integer.parseInt(data[2]);
        } catch(Exception e){
            throw new ExceptionDataEleicao();
        }

        Eleicao eleicao = new Eleicao(tipo, LocalDate.of(ano, mes, dia));

        try {
            InputServices.processarArquivoCandidatos(bufferCandidatos, eleicao);
        } catch (Exception e) {
            throw e;
        }
    }
}
