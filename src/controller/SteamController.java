package controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SteamController {

    public void exibirJogosPopulares(int ano, String mes, int mediaMinima, String path, String nome) throws IOException {

        File arq = new File(path, nome);
        FileInputStream fluxo = new FileInputStream(arq);
        InputStreamReader leitor = new InputStreamReader(fluxo);
        BufferedReader br = new BufferedReader(leitor);

        br.readLine();

        String linha = br.readLine();

        while (linha != null) {

            String[] parts = linha.split(",");
            System.out.println(parts[1]);
            int year = Integer.parseInt(parts[1].trim());
            String month = parts[2].trim();
            String avgPlayersStr = parts[4].trim();

            System.out.println("year -> " + year);
            System.out.println("month -> " + month);
            System.out.println("avgPlayersStr -> " + avgPlayersStr);

            if (!avgPlayersStr.equals("NA")) {
                Double avgPlayers = Double.parseDouble(avgPlayersStr);

                if (year == ano && month.equals(mes) && avgPlayers >= mediaMinima) {
                    System.out.printf("(%s | %d)\n", parts[0].trim(), avgPlayers);
                }
            }
            br.close();
            leitor.close();
            fluxo.close();
        }
    }

    public void gerarArquivoFiltrado(int ano, String mes, String path, String nome) throws IOException {
        File arq = new File(path, nome);

        if (!arq.exists()) {
            System.out.println("O arquivo não foi encontrado.");
            return;
        }

        List<String> linhasFiltradas = new ArrayList<>();

        try (FileInputStream fluxo = new FileInputStream(arq);
             InputStreamReader leitor = new InputStreamReader(fluxo);
             BufferedReader br = new BufferedReader(leitor)) {

            String linha = br.readLine();

            while ((linha = br.readLine()) != null) {
                String[] parts = linha.split(",");
                int year = Integer.parseInt(parts[1].trim());
                String month = parts[2].trim();

                if (year == ano && month.equals(mes)) {
                    linhasFiltradas.add(parts[0].trim() + ";" + parts[4].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
        }
        try (FileWriter writer = new FileWriter(arq.getParent() + File.separator + "jogos_filtrados.csv");
             BufferedWriter bw = new BufferedWriter(writer)) {

            for (String linha : linhasFiltradas) {
                bw.write(linha);
                bw.newLine();
            }

            System.out.println("Arquivo filtrado gerado com sucesso.");
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao escrever no arquivo filtrado: " + e.getMessage());
        }
    }
}
