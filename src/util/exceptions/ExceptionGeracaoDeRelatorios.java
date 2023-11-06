package util.exceptions;

public class ExceptionGeracaoDeRelatorios extends Exception{
    @Override
    public String getMessage(){
        return "Falha na geracao de relatorios de saida.";
    }
}
