package estgf.ipp.pt.pokemongo.gui;

import estgf.ipp.pt.pokemongo.application.Network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Inputs {

    public static final String lerPontoOrigem() {
        final String INSERIR_PONTO_ORIGEM_MSG = "Insira o Ponto de Origem: ";
        String pontoOrigem = new String();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print(INSERIR_PONTO_ORIGEM_MSG);
                pontoOrigem = bufferedReader.readLine();
                while (pontoOrigem.isEmpty() == true) {
                    System.err.print("\n" + Util.MAU_INPUT + "\n");
                    Util.primaEnterparaContinuar();
                    Util.limparEcra();
                    System.out.print(INSERIR_PONTO_ORIGEM_MSG);
                    pontoOrigem = bufferedReader.readLine();
                }
            } catch (NumberFormatException | IOException exception) {
                System.err.print("\n" + Util.MAU_INPUT + "\n");
                Util.primaEnterparaContinuar();
                Util.limparEcra();
            }
        } while (pontoOrigem.isEmpty() == true);
        return pontoOrigem;
    }

    public static final String lerPontoDestino() {
        final String INSERIR_PONTO_DESTINO_MSG = "Insira o Ponto de Destino: ";
        String pontoDestino = new String();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print(INSERIR_PONTO_DESTINO_MSG);
                pontoDestino = bufferedReader.readLine();
                while (pontoDestino.isEmpty() == true) {
                    System.err.print("\n" + Util.MAU_INPUT + "\n");
                    Util.primaEnterparaContinuar();
                    Util.limparEcra();
                    System.out.print(INSERIR_PONTO_DESTINO_MSG);
                    pontoDestino = bufferedReader.readLine();
                }
            } catch (NumberFormatException | IOException exception) {
                System.err.print("\n" + Util.MAU_INPUT + "\n");
                Util.primaEnterparaContinuar();
                Util.limparEcra();
            }
        } while (pontoDestino.isEmpty() == true);
        return pontoDestino;
    }

    public static final short lerOpcaoTransporte() {
        final String INSERIR_OPCAO_TRANSPORTE_MSG = "Introduza o Opcao Transporte (" + Network.PRIVATE_TRANSPORT + ":Privado) (" + Network.PUBLIC_TRANSPORT + ":Público) (" + Network.WALK_TRANSPORT + ":A Pe): ";
        short opcaoTransporte = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print(INSERIR_OPCAO_TRANSPORTE_MSG);
                opcaoTransporte = Short.parseShort(bufferedReader.readLine());
            } catch (NumberFormatException | IOException exception) {
                System.err.print("\n" + Util.MAU_INPUT + "\n");
                Util.primaEnterparaContinuar();
                Util.limparEcra();
            }
        } while (opcaoTransporte < Network.PRIVATE_TRANSPORT || opcaoTransporte > Network.WALK_TRANSPORT);
        return opcaoTransporte;
    }

    public static final short lerNumeroPokestops() {
        final String INSERIR_NUMERO_POKESTOPS_MSG = "Insira o Numero Minimo de Pokestops: ";
        short numeroPokestops = 0;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                System.out.print(INSERIR_NUMERO_POKESTOPS_MSG);
                numeroPokestops = Short.parseShort(bufferedReader.readLine());
            } catch (NumberFormatException | IOException exception) {
                System.err.print("\n" + Util.MAU_INPUT + "\n");
                Util.primaEnterparaContinuar();
                Util.limparEcra();
            }
        } while (numeroPokestops < 0);
        return numeroPokestops;
    }
}
