package util.exceptions;

public class ExceptionDataEleicao extends Exception{
    @Override
    public String getMessage(){
        return "Falha ao gerar data de eleição (argumento de data não pode ser transformada em um tipo numérico.)";
    }
}
