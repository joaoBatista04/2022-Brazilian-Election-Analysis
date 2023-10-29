package util.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.nio.charset.Charset;
import java.time.LocalDate;

import domain.Eleicao;
import domain.Partido;

import java.io.IOException;

import util.exceptions.ExceptionProcessamentoArquivoVotacao;
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

                if(isValidCandidato(currentData[13], currentData[68], eleicao.getTipoEleicao())){
                    updateCandidato(eleicao, currentData, partido);
                    continue;
                }

                if(eleicao.getTipoEleicao() == Integer.parseInt(currentData[13]) && currentData[67].compareTo("Válido (legenda)") == 0){
                    updateCandidatosInvalidos(eleicao, currentData, partido);
                }
            }
        } catch(IOException e) {
            System.out.println("Falha ao ler linha do arquivo de candidatos (.csv).");
            throw new ExceptionProcessamentoArquivosCandidatos();
        } catch(NumberFormatException e){
            System.out.println("Falha ao transformar string em tipo numerico.");
            throw new ExceptionProcessamentoArquivosCandidatos();
        }
    }

    public static void processarArquivoVotacao(BufferedReader bufferVotacao, Eleicao eleicao) throws ExceptionProcessamentoArquivoVotacao{
        String currentLine;
        String[] currentData;

        try {
            bufferVotacao.readLine();
            while((currentLine = bufferVotacao.readLine()) != null){
                currentData = inputFormatter(currentLine);

                if(isVotoValido(currentData[17], eleicao.getTipoEleicao(), currentData[19])){
                    processarVotosCandidatoValido(eleicao, currentData);
                    processarVotosCandidatoInvalido(eleicao, currentData);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler uma linha do arquivo de votos");
            throw new ExceptionProcessamentoArquivoVotacao();
        } catch(NumberFormatException e){
            System.out.println("As informacoes de votos nao podem ser transformadas para um tipo numerico.");
            throw new ExceptionProcessamentoArquivoVotacao();
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
            System.out.println("As infromacoes de numero do Partido e da Federacao nao podem ser transformadas em um tipo numerico.");
            throw e;
        }

        if(eleicao.getPartidos().containsKey(numeroPartido)){
            return eleicao.getPartidos().get(numeroPartido);
        }

        return eleicao.addPartido(numeroPartido, siglaPartido, numeroFederacao);
    }

    private static boolean isValidCandidato(String codigoCargo, String codigoDetalhesSituacaoCandidato, int tipoEleicao) throws NumberFormatException{
        int codigoCargoCandidato;
        int codigoDetalhesSituacao;

        try {
            codigoCargoCandidato = Integer.parseInt(codigoCargo);
            codigoDetalhesSituacao = Integer.parseInt(codigoDetalhesSituacaoCandidato);
        } catch (Exception e) {
            System.out.println("As informacoes de cargo e situacao do candidato nao podem ser transformadas em um tipo numerico.");
            throw e;
        }

        if(codigoCargoCandidato == tipoEleicao && (codigoDetalhesSituacao == 2 || codigoDetalhesSituacao == 16)){
            return true;
        }

        return false;
    }

    private static void updateCandidato(Eleicao eleicao, String[] currentData, Partido partido) throws NumberFormatException{
        int dia;
        int mes;
        int ano;
        int numeroCandidato;
        int codigoGenero;
        String[] data = currentData[42].split("/");
        String nomeCandidatoUrna = currentData[18];
        String nomeTipoDestinoVotos = currentData[67];
        LocalDate dataNascimento;
        boolean situacao;

        try {
            dia = Integer.parseInt(data[0]);
            mes = Integer.parseInt(data[1]);
            ano = Integer.parseInt(data[2]);

            dataNascimento = LocalDate.of(ano, mes, dia);

            situacao = isCandidatoEleito(currentData[56]);
            numeroCandidato = Integer.parseInt(currentData[16]);
            codigoGenero = Integer.parseInt(currentData[45]);

            eleicao.addCandidato(numeroCandidato, nomeCandidatoUrna, nomeTipoDestinoVotos, dataNascimento, situacao, codigoGenero, partido);
        } catch (Exception e) {
            System.out.println("As informacoes do candidato nao podem ser transformadas em um tipo numerico.");
            throw e;
        }
    }

    private static boolean isCandidatoEleito(String situacao) throws NumberFormatException{
        int situation;

        try {
            situation = Integer.parseInt(situacao);
        } catch (Exception e) {
            System.out.println("A string de situacao do candidato nao pode ser transformada em um tipo numerico.");
            throw e;
        }

        if(situation == 2 || situation == 3){
            return true;
        }

        return false;
    }

    private static void updateCandidatosInvalidos(Eleicao eleicao, String[] currentData, Partido partido) throws NumberFormatException{
        int numeroCandidato;

        try {
            numeroCandidato = Integer.parseInt(currentData[16]);
        } catch (Exception e) {
            System.out.println("A informacao do numero do candidato nao pode ser transformada em um tipo numerico.");
            throw e;
        }

        eleicao.addLegendaPartidoCandidatos(numeroCandidato, partido);
    }

    private static boolean isVotoValido(String codigoCargo, int tipoEleicao, String numeroVotavel) throws NumberFormatException{
        int cdC, nrV;

        try {
            cdC = Integer.parseInt(codigoCargo);
            nrV = Integer.parseInt(numeroVotavel);
        } catch (Exception e) {
            System.out.println("As informacoes do codigo do cargo e do numero votavel nao podem ser transformadas em um tipo numerico.");
            throw e;
        }

        if (cdC == tipoEleicao && nrV != 95 && nrV != 96 && nrV != 97 && nrV != 98){
            return true;
        }

        return false;
    }

    private static void processarVotosCandidatoValido(Eleicao eleicao, String[] currentData) throws NumberFormatException{
        int nrVotavel, qntdVotos;

        try {
            nrVotavel = Integer.parseInt(currentData[19]);
            qntdVotos = Integer.parseInt(currentData[21]);
        } catch (Exception e) {
            System.out.println("As informacoes de numero votavel e quantidade de votos nao podem ser transformadas em um tipo numerico.");
            throw e;
        }

        if(eleicao.getCandidatos().containsKey(nrVotavel)){
            eleicao.getCandidatos().get(nrVotavel).setQuantidadeVotos(qntdVotos);
            eleicao.setVotosNominais(qntdVotos);
            return;
        }

        if(eleicao.getPartidos().containsKey(nrVotavel)){
            eleicao.getPartidos().get(nrVotavel).setVotosNaLegenda(qntdVotos);
            eleicao.setVotosLegenda(qntdVotos);
        }
    }

    private static void processarVotosCandidatoInvalido(Eleicao eleicao, String[] currentData) throws NumberFormatException{
        int nrVotavel, qntdVotos;

        try {
            nrVotavel = Integer.parseInt(currentData[19]);
            qntdVotos = Integer.parseInt(currentData[21]);
        } catch (Exception e) {
            System.out.println("As informacoes de numero votavel e quantidade de votos nao podem ser transformadas em um tipo numerico.");
            throw e;
        }

        if(eleicao.getLegendasPartidosCandidatos().containsKey(nrVotavel)){
            eleicao.getLegendasPartidosCandidatos().get(nrVotavel).setVotosNaLegenda(qntdVotos);
            eleicao.setVotosLegenda(qntdVotos);
        }
    }
}