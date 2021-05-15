package estgf.ipp.pt.pokemongo.gui;

import estgf.ipp.pt.pokemongo.application.Network;
import estgf.ipp.pt.pokemongo.application.Vertex;
import estgf.ipp.pt.pokemongo.application.Weight;
import estgf.ipp.pt.pokemongo.exceptions.NoSuchPathException;
import estgf.ipp.pt.pokemongo.exceptions.PokemonStopsNumberNotExists;
import estgf.ipp.pt.pokemongo.exceptions.VertexNotExists;
import estgf.ipp.pt.pokemongo.management.InputData;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import jxl.read.biff.BiffException;

public class NetworkDemo {

    private static void listarPontosInteresse(Network network) {
        for (int i = 0; i < network.size(); i++) {
            Vertex vertex = network.getVertices()[i];
            System.out.println("ID: " + vertex.getId() + ";Pokestop?: " + vertex.isPokestop());
        }
        System.out.println();
    }

    private static void listarLigacaoesExistentes(Network network) {
        boolean[][] adjMatrix = network.getAdjMatrix();
        Weight[][] wAdjMatrix = network.getwAdjMatrix();

        for (int i = 0; i < network.size(); i++) {
            for (int j = 0; j < network.size(); j++) {
                if (adjMatrix[i][j]) {
                    Weight weightPath = wAdjMatrix[i][j];
                    System.out.println("Vértice Origem: " + network.getVertices()[i].getId() + ";Vértice Destino: " + network.getVertices()[j].getId() + ";Distância: " + weightPath.getDistance() + ";Transporte Público: " + weightPath.isPublicTransport() + ";Transporte Privado: " + weightPath.isPrivateTransport());
                }
            }
        }
        System.out.println();
    }

    private static void calcularCaminhoMenosCusto(Network network) {
        String pontoOrigem = Inputs.lerPontoOrigem();
        String pontoDestino = Inputs.lerPontoDestino();
        short opcaoTransporte = Inputs.lerOpcaoTransporte();
        short pokestops = Inputs.lerNumeroPokestops();

        try {
            Vertex verticeOrigem = network.getVertexByID(pontoOrigem);
            Vertex verticeDestino = network.getVertexByID(pontoDestino);

            try {
                Iterator<Vertex> iteratorMinCostPath = network.iteratorMinCostPath(verticeOrigem, verticeDestino, opcaoTransporte, pokestops);
                System.out.print("\nCaminho: ");
                while (iteratorMinCostPath.hasNext()) {
                    System.out.print(iteratorMinCostPath.next().getId() + ";");
                }
            } catch (PokemonStopsNumberNotExists | NoSuchPathException exception) {
                System.out.println(exception.getLocalizedMessage());
            }
        } catch (VertexNotExists vne) {
            System.out.println(vne.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        int opcao = -1;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            Network network = InputData.loadData("../PokemonGO.xls");
            do {
                Util.limparEcra();
                do {
                    System.out.print(MenusInteracao.MENU_PRINCIPAL_MSG);
                    try {
                        opcao = Integer.parseInt(bufferedReader.readLine());
                    } catch (NumberFormatException exception) {
                    } finally {
                        if (opcao < MenusInteracao.OPCAO_SAIR || opcao > MenusInteracao.OPCAO_CALCULAR_CAMINHO_MENOS_CUSTO) {
                            System.err.println("\nIntroduz uma Opcao Valida!!!");
                            Util.primaEnterparaContinuar();
                        }
                        Util.limparEcra();
                    }
                } while (opcao < MenusInteracao.OPCAO_SAIR || opcao > MenusInteracao.OPCAO_CALCULAR_CAMINHO_MENOS_CUSTO);
                switch (opcao) {
                    case MenusInteracao.OPCAO_VER_PONTOS_INTERESSSE:
                        listarPontosInteresse(network);
                        break;
                    case MenusInteracao.OPCAO_VER_LIGACOES_EXISTENTES:
                        listarLigacaoesExistentes(network);
                        break;
                    case MenusInteracao.OPCAO_CALCULAR_CAMINHO_MENOS_CUSTO:
                        calcularCaminhoMenosCusto(network);
                        break;
                }
                Util.primaEnterparaContinuar();
            } while (opcao != MenusInteracao.OPCAO_SAIR);
        } catch (IOException | BiffException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
