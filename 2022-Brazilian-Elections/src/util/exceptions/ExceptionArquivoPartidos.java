package util.exceptions;

public class ExceptionArquivoPartidos extends Exception{
    @Override
    public String getMessage(){
        return "Falha ao abrir ou encontrar arquivo de partidos (.csv)";
    }
}
