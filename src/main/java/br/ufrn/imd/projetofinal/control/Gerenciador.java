package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Turma;
import br.ufrn.imd.projetofinal.model.Aluno;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe responsável por gerenciar a leitura e escrita de dados das turmas em um arquivo.
 */
public class Gerenciador {

    /**
     * Caminho do arquivo onde as informações das turmas são armazenadas.
     */
    private static final String ARQUIVO_TURMAS = "src/main/resources/Turmas.txt";

    /**
     * Carrega as turmas do arquivo de texto.
     *
     * @return uma lista de objetos {@link Turma} contendo os dados carregados do arquivo.
     */
    public static ArrayList<Turma> carregarTurmas() {
        ArrayList<Turma> turmas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_TURMAS))) {
            String linha;
            Turma turmaAtual = null;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (!linha.contains(";") && !linha.isEmpty()) {
                    String[] partes = linha.split("]");
                    turmaAtual = new Turma();
                    turmaAtual.setNome(partes[0].trim());
                    turmaAtual.setObs(partes.length > 1 ? partes[1].trim() : "");
                    turmas.add(turmaAtual);
                } else if (turmaAtual != null && linha.contains(";")) {
                    String[] partes = linha.split(";");
                    String nomeAluno = partes[0].trim();
                    String obsAluno = partes[4].trim();

                    Aluno aluno = new Aluno(nomeAluno);
                    for (int i = 1; i < partes.length - 1; i++) {
                        aluno.setNota(Float.parseFloat(partes[i].trim()), i - 1);
                    }
                    aluno.setObs(obsAluno);
                    turmaAtual.addAluno(aluno);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro ao converter nota: " + e.getMessage());
        }
        return turmas;
    }

    /**
     * Salva as turmas em um arquivo de texto.
     *
     * @param turmas uma lista de objetos {@link Turma} que serão salvos no arquivo.
     */
    public static void salvarTurmas(ArrayList<Turma> turmas) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_TURMAS))) {
            for (Turma turma : turmas) {
                // Escreve o nome da turma (com ";" substituído por "|,")
                writer.write(turma.getNome().replace(";", "|,") + "]" + turma.getObs().replace(";", "|,") + "\n");

                // Escreve os alunos da turma
                for (Aluno aluno : turma.getAlunos()) {
                    StringBuilder linha = new StringBuilder();
                    linha.append(aluno.getNome().replace(";", "|,")).append(";") // Substitui ";" por "|," no nome do aluno
                            .append(aluno.getNotas()[0]).append(";")
                            .append(aluno.getNotas()[1]).append(";")
                            .append(aluno.getNotas()[2]).append(";")
                            .append(aluno.getObs().replace(";", "|,")); // Substitui ";" por "|," na observação do aluno

                    writer.write(linha.toString() + "\n");
                }
            }
            System.out.println("Turmas salvas com sucesso no arquivo!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao salvar as turmas no arquivo.");
        }
    }
}
