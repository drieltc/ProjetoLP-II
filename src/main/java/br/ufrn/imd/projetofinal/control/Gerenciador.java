package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Turma;
import br.ufrn.imd.projetofinal.model.Aluno;

import java.io.*;
import java.util.ArrayList;

public class Gerenciador {
    public static ArrayList<Turma> carregarTurmas(){
        ClassLoader classLoader = Gerenciador.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("Turmas.txt");

        ArrayList<Turma> turmas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String linha;
            Turma turmaAtual = null;

            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                // Se a linha não contém ";", é o nome de uma turma
                if (!linha.contains(";") && !linha.isEmpty()) {
                    turmaAtual = new Turma();
                    turmaAtual.setNome(linha);
                    turmaAtual.setAlunos(new ArrayList<>());
                    turmas.add(turmaAtual);
                }
                // Caso contrário, é um aluno
                else if (turmaAtual != null && linha.contains(";")) {
                    String[] partes = linha.split(";");
                    String nomeAluno = partes[0].trim();
                    String obsAluno = partes[4].trim();
                    Aluno aluno = new Aluno(nomeAluno);

                    // Adiciona notas
                    for (int i = 1; i < partes.length -1; i++) {
                        aluno.addNota(Float.parseFloat(partes[i].trim()));
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

    public static void salvarTurmas(){

    }
}
