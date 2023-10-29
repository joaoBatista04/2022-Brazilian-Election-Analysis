package util.io;

import java.text.NumberFormat;
import java.util.Locale;

import domain.Candidato;
import domain.Eleicao;

import util.exceptions.ExceptionGeracaoDeRelatorios;

public class OutputServices {
    public static void geracaoDeRelatorios(Eleicao eleicao) throws ExceptionGeracaoDeRelatorios{
        Locale localeBr = Locale.forLanguageTag("pt-br");
        NumberFormat nf = NumberFormat.getInstance(localeBr);
        NumberFormat nfDec = NumberFormat.getInstance(localeBr);

        nfDec.setMinimumFractionDigits(2);
        nfDec.setMaximumFractionDigits(2);

        try {
            numeroDeVagasEleicao(eleicao, nf);
            candidatosEleitos(eleicao, nf);
            candidatosMaisVotados(eleicao, nf);
            candidatosPrejudicados(eleicao, nf);
            candidatosBeneficiados(eleicao, nf);
        } catch (Exception e) {
            throw new ExceptionGeracaoDeRelatorios();
        }
    }

    private static void numeroDeVagasEleicao(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        System.out.println("Número de vagas: " + nf.format(eleicao.getNumeroDeVagasDaEleicao()));
        System.out.println();
    }

    private static void candidatosEleitos(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        String tipoEleicao = "";
        String vot;

        if(eleicao.getTipoEleicao() == 7){
            tipoEleicao = "estaduais";
        } else if(eleicao.getTipoEleicao() == 6){
            tipoEleicao = "federais";
        }

        System.out.printf("Deputados %s eleitos:\n", tipoEleicao);

        for(Candidato c : eleicao.getCandidatosEleitos()){
            System.out.printf("%s - ", nf.format(c.getEleitoPosicao()));

            if (c.getPartidoCandidato().getFederacaoPartido() != -1){
                System.out.print("*");
            }

            vot = "voto";
            vot = filtroSingularPlural(c.getQuantidadeVotos(), vot);

            System.out.printf("%s (%s, %s %s)\n", c.getNomeCandidatoUrna(), c.getPartidoCandidato().getSiglaDoPartido(), nf.format(c.getQuantidadeVotos()), vot);
        }

        System.out.printf("\n");
    }

    private static void candidatosMaisVotados(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        String vot;

        for (Candidato c : eleicao.getCandidatosMaisVotados()) {
            System.out.printf("%s - ", nf.format(c.getPosicaoGeral()));
            if (c.getPartidoCandidato().getFederacaoPartido() != -1)
                System.out.print("*");

            vot = "voto";
            vot = filtroSingularPlural(c.getQuantidadeVotos(), vot);
            System.out.printf("%s (%s, %s %s)\n", c.getNomeCandidatoUrna(), c.getPartidoCandidato().getSiglaDoPartido(), nf.format(c.getQuantidadeVotos()), vot);
        }
        System.out.print("\n");
    }

    private static void candidatosPrejudicados(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:");
        System.out.println("(com sua posição no ranking de mais votados)");

        String vot;

        for (Candidato c : eleicao.getCandidatosEleitosSeEleicaoMajoritaria()) {
            System.out.printf("%s - ", nf.format(c.getPosicaoGeral()));
            if (c.getPartidoCandidato().getFederacaoPartido() != -1)
                System.out.print("*");

            vot = "voto";
            vot = filtroSingularPlural(c.getQuantidadeVotos(), vot);
            System.out.printf("%s (%s, %s %s)\n", c.getNomeCandidatoUrna(), c.getPartidoCandidato().getSiglaDoPartido(), nf.format(c.getQuantidadeVotos()), vot);
        }
        System.out.print("\n");
    }

    private static void candidatosBeneficiados(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:");
        System.out.println("(com sua posição no ranking de mais votados)");

        String vot;

        for (Candidato c : eleicao.getCandidatosEleitosProporcional()) {
            System.out.printf("%s - ", nf.format(c.getPosicaoGeral()));
            if (c.getPartidoCandidato().getFederacaoPartido() != -1)
                System.out.print("*");

            vot = "voto";
            vot = filtroSingularPlural(c.getQuantidadeVotos(), vot);
            System.out.printf("%s (%s, %s %s)\n", c.getNomeCandidatoUrna(), c.getPartidoCandidato().getSiglaDoPartido(), nf.format(c.getQuantidadeVotos()), vot);
        }
        System.out.print("\n");
    }

    private static String filtroSingularPlural(int value, String out) {
        if (value > 1) {
            if (out.compareTo("nominal") == 0) {
                return "nominais";
            }
            return out.concat("s");
        }
        return out;
    }
}
