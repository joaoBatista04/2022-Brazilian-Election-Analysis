package util.io;

import java.text.NumberFormat;
import java.util.Locale;

import domain.Candidato;
import domain.Eleicao;
import domain.Partido;
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
            votacaoPartidosCandidatosEleitos(eleicao, nf);
            primeiroEUltimoCandidatoDosPartidos(eleicao, nf);
            eleitosPorIdade(eleicao, nf, nfDec);
            eleitosPorGenero(eleicao, nf, nfDec);
            totalVotos(eleicao, nf, nfDec);
        } catch (Exception e) {
            throw new ExceptionGeracaoDeRelatorios();
        }
    }

    private static void numeroDeVagasEleicao(Eleicao eleicao, NumberFormat nf) throws NumberFormatException{
        System.out.println("Número de vagas: " + nf.format(eleicao.getNumeroDeVagasDaEleicao()));
        System.out.print("\n");
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

        System.out.print("\n");
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

    private static void votacaoPartidosCandidatosEleitos(Eleicao eleicao, NumberFormat nf){
        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        String vot, nom, cand, ele;

        for(Partido p : eleicao.getPartidosList()){
            vot = "voto";
            nom = "nominal";
            cand = "candidato";
            ele = "eleito";

            vot = filtroSingularPlural(p.getVotosTotais(), vot);
            nom = filtroSingularPlural(p.getVotosNominais(), nom);
            cand = filtroSingularPlural(p.getQuantidadeDeEleitosPartido(), cand);
            ele = filtroSingularPlural(p.getQuantidadeDeEleitosPartido(), ele);

            System.out.printf("%s - %s - %s, %s %s (%s %s e %s de legenda), %s %s %s\n", nf.format(p.getPosicao()), p.getSiglaDoPartido(), nf.format(p.getNumeroPartido()), nf.format(p.getVotosTotais()), vot, nf.format(p.getVotosNominais()), nom, nf.format(p.getVotosNaLegenda()), nf.format(p.getQuantidadeDeEleitosPartido()), cand, ele);
        }

        System.out.print("\n");
    }

    private static void primeiroEUltimoCandidatoDosPartidos(Eleicao eleicao, NumberFormat nf){
        System.out.println("Primeiro e último colocados de cada partido:");

        String maisVot, menosVot;

        for(Partido p : eleicao.getPartidosOrdenadosPorCandidatos()){
            maisVot = "voto";
            menosVot = "voto";
            maisVot = filtroSingularPlural(p.candidatoMaisVotadoPartido().getQuantidadeVotos(), maisVot);
            menosVot = filtroSingularPlural(p.candidatoMenosVotadoPartido().getQuantidadeVotos(), menosVot);

            System.out.printf("%s - %s - %s, %s (%d, %s %s) / %s (%d, %s %s)\n", nf.format(p.getPosicao()), p.getSiglaDoPartido(), nf.format(p.getNumeroPartido()), p.candidatoMaisVotadoPartido().getNomeCandidatoUrna(), p.candidatoMaisVotadoPartido().getNumeroCandidato(), nf.format(p.candidatoMaisVotadoPartido().getQuantidadeVotos()), maisVot, p.candidatoMenosVotadoPartido().getNomeCandidatoUrna(), p.candidatoMenosVotadoPartido().getNumeroCandidato(), nf.format(p.candidatoMenosVotadoPartido().getQuantidadeVotos()), menosVot);
        }

        System.out.print("\n");
    }

    private static void eleitosPorIdade(Eleicao eleicao, NumberFormat nf, NumberFormat nfDec){
        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        int totalEleitos = eleicao.getNumeroDeVagasDaEleicao();

        int f1 = eleicao.quantidadeEleitosPorIdade(0, 30);
        int f2 = eleicao.quantidadeEleitosPorIdade(30, 40);
        int f3 = eleicao.quantidadeEleitosPorIdade(40, 50);
        int f4 = eleicao.quantidadeEleitosPorIdade(50, 60);
        int f5 = eleicao.quantidadeEleitosPorIdade(60, 120);

        float p1 = ((float) f1 / (float) totalEleitos) * 100;
        float p2 = ((float) f2 / (float) totalEleitos) * 100;
        float p3 = ((float) f3 / (float) totalEleitos) * 100;
        float p4 = ((float) f4 / (float) totalEleitos) * 100;
        float p5 = ((float) f5 / (float) totalEleitos) * 100;

        System.out.printf("      Idade < 30: %s (%s%%)\n", nf.format(f1), nfDec.format(p1));
        System.out.printf("30 <= Idade < 40: %s (%s%%)\n", nf.format(f2), nfDec.format(p2));
        System.out.printf("40 <= Idade < 50: %s (%s%%)\n", nf.format(f3), nfDec.format(p3));
        System.out.printf("50 <= Idade < 60: %s (%s%%)\n", nf.format(f4), nfDec.format(p4));
        System.out.printf("60 <= Idade     : %s (%s%%)\n\n", nf.format(f5), nfDec.format(p5));
    }

    private static void eleitosPorGenero(Eleicao eleicao, NumberFormat nf, NumberFormat nfDec){
        int totalEleitos = eleicao.getNumeroDeVagasDaEleicao();
        System.out.println("Eleitos, por gênero:");

        int homens = eleicao.quantidadeHomensEleitos();
        int mulheres = eleicao.quantidadeMulheresEleitas();

        float pHomens = ((float) homens / (float) totalEleitos) * 100;
        float pMulheres = ((float) mulheres / (float) totalEleitos) * 100;

        System.out.printf("Feminino:  %s (%s%%)\n", nf.format(mulheres), nfDec.format(pMulheres));
        System.out.printf("Masculino: %s (%s%%)\n\n", nf.format(homens), nfDec.format(pHomens));
    }

    private static void totalVotos(Eleicao eleicao, NumberFormat nf, NumberFormat nfDec){
        int votosValidos = eleicao.getVotosLegenda() + eleicao.getVotosNominais();
        int votosNominais = eleicao.getVotosNominais();
        int votosLegendas = eleicao.getVotosLegenda();

        float pVotosNominais = ((float) votosNominais / (float) votosValidos) * 100;
        float pVotosLegendas = ((float) votosLegendas / (float) votosValidos) * 100; 
    
        System.out.printf("Total de votos válidos:    %s\n", nf.format(votosValidos));
        System.out.printf("Total de votos nominais:   %s (%s%%)\n", nf.format(votosNominais), nfDec.format(pVotosNominais));
        System.out.printf("Total de votos de legenda: %s (%s%%)\n", nf.format(votosLegendas), nfDec.format(pVotosLegendas));
    }
}
