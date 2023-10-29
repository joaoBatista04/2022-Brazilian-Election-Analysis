package util.exceptions;

public class ExceptionProcessamentoArquivosCandidatos extends Exception{
    @Override
    public String getMessage(){
        return "Falha ao processar o arquivo de candidatos.";
    }
}
