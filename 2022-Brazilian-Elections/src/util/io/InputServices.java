package util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.nio.charset.Charset;

import domain.Eleicao;
import domain.Partido;

import util.exceptions.ExceptionProcessamentoArquivosCandidatos;

public class InputServices {
    public static BufferedReader createBufferedReader(String path) throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(path), Charset.forName("ISO-8859-1")));
            return br;
        } catch (IOException e) {
            throw e;
        }
    }

    public static void processarArquivoCandidatos(BufferedReader bufferCandidatos, Eleicao eleicao) throws ExceptionProcessamentoArquivosCandidatos{
        try {
            String currentLine;
            String[] currentData;
            Partido partido;
            
            bufferCandidatos.readLine();
            while((currentLine = bufferCandidatos.readLine()) != null){
                currentData = inputFormatter(currentLine);

                partido = updatePartidos(eleicao, currentData);
            }
        } catch(IOException e) {
            System.out.println("Falha ao ler linha do arquivo de candidatos (.csv).");
            throw new ExceptionProcessamentoArquivosCandidatos();
        } catch(NumberFormatException e){
            System.out.println("Falha ao transformar string em tipo numérico.");
            throw new ExceptionProcessamentoArquivosCandidatos();
        }
    }

    private static String[] inputFormatter(String currentLine){
        String[] currentData = currentLine.split(";");
        
        for(var i = 0; i < currentData.length; i++){
            currentData[i] = currentData[i].replaceAll("\"", "");
        }

        return currentData;
    }

    private static Partido updatePartidos(Eleicao eleicao, String[] currentData){
        int numeroPartido;
        int numeroFederacao;
        String siglaPartido = currentData[28];

        try {
            numeroPartido = Integer.parseInt(currentData[27]);
            numeroFederacao = Integer.parseInt(currentData[30]);
        } catch (Exception e) {
            System.out.println("As informações de número do Partido e da Federação não podem ser transformadas em um tipo numérico.");
            throw e;
        }

        if(eleicao.getPartidos().containsKey(numeroPartido)){
            return eleicao.getPartidos().get(numeroPartido);
        }

        return eleicao.addPartido(numeroPartido, siglaPartido, numeroFederacao);
    }
}
