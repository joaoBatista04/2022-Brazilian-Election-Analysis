package util.exceptions;

public class ExceptionArquivoCandidatos extends Exception{
    @Override
    public String getMessage(){
        return "Falha ao abrir ou encontrar o arquivo de candidatos (.csv)";
    }
}
