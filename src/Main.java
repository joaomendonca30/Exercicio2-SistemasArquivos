

import controller.SteamController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SteamController controller = new SteamController();

        String path = "C:\\TEMP";
        String nome = "SteamCharts.csv";


        controller.exibirJogosPopulares(2023, "Outubro", 1000, path, nome);

        controller.gerarArquivoFiltrado(2023, "Outubro", path, nome);


    }
}