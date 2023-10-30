package util.comparators;

import java.util.Comparator;

import domain.Partido;

public class PartidoComparadorPorCandidato implements Comparator<Partido>{
    @Override
    public int compare(Partido p1, Partido p2){
        //Por contagem dos votos
        if(p1.candidatoMaisVotadoPartido().getQuantidadeVotos() > p2.candidatoMaisVotadoPartido().getQuantidadeVotos()){
            return -1;
        }

        if(p1.candidatoMaisVotadoPartido().getQuantidadeVotos() < p2.candidatoMaisVotadoPartido().getQuantidadeVotos()){
            return 1;
        }

        //Pela idade do candidatos mais votado
        if(p1.candidatoMaisVotadoPartido().getDataDeNascimento().compareTo(p2.candidatoMaisVotadoPartido().getDataDeNascimento()) < 0){
            return -1;
        }

        if(p1.candidatoMaisVotadoPartido().getDataDeNascimento().compareTo(p2.candidatoMaisVotadoPartido().getDataDeNascimento()) > 0){
            return 1;
        }

        //Por numero de votacao
        if(p1.candidatoMaisVotadoPartido().getPartidoCandidato().getNumeroPartido() < p2.candidatoMaisVotadoPartido().getPartidoCandidato().getNumeroPartido()){
            return -1;
        }

        if(p1.candidatoMaisVotadoPartido().getPartidoCandidato().getNumeroPartido() < p2.candidatoMaisVotadoPartido().getPartidoCandidato().getNumeroPartido()){
            return 1;
        }

       return 0;
    }
}
