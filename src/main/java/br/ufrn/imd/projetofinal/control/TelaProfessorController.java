package br.ufrn.imd.projetofinal.control;

import br.ufrn.imd.projetofinal.dao.Turma;
import br.ufrn.imd.projetofinal.model.Aluno;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.IOException;

/**
 * Controlador da interface do professor.
 * Permite adição/remoção de alunos e turmas, exportar dados da turma, formar duplas
 */
public class TelaProfessorController implements Initializable {

    @FXML
    private ListView<String> listaTurmas;

    @FXML
    private TableView<Aluno> tabela;

    @FXML private TableColumn<Aluno, String> NomeCol;
    @FXML private TableColumn<Aluno, Float> n1Col;
    @FXML private TableColumn<Aluno, Float> n2Col;
    @FXML private TableColumn<Aluno, Float> n3Col;
    @FXML private TableColumn<Aluno, String> obsCol;
    @FXML private TextArea observacaoTurma;

    private ArrayList<Turma> turmas;

    /**
     * Inicializa a interface gráfica do controlador, configurando os componentes da tabela,
     * eventos de edição, e o comportamento das observações das turmas.
     * Este método é chamado automaticamente pelo JavaFX ao carregar o arquivo FXML.
     *
     * @param location O local usado para resolver caminhos relativos para o objeto raiz, ou {@code null}.
     * @param resources Os recursos usados para localizar o objeto raiz, ou {@code null}.
     */
    @Override public void initialize(URL location, ResourceBundle resources) {
        carregarTurmas();

        // Configuração inicial da tabela como editável
        tabela.setEditable(true);

        // Configuração da coluna de nomes
        NomeCol.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        NomeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        NomeCol.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setNome(event.getNewValue());
            Gerenciador.salvarTurmas(turmas);
        });

        // Configuração das colunas de notas
        n1Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[0].asObject());
        n1Col.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        n1Col.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setNota(event.getNewValue(), 0);
            Gerenciador.salvarTurmas(turmas);
        });

        n2Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[1].asObject());
        n2Col.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        n2Col.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setNota(event.getNewValue(), 1);
            Gerenciador.salvarTurmas(turmas);
        });

        n3Col.setCellValueFactory(cellData -> cellData.getValue().notasProperty()[2].asObject());
        n3Col.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        n3Col.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setNota(event.getNewValue(), 2);
            Gerenciador.salvarTurmas(turmas);
        });

        // Configuração da coluna de observações
        obsCol.setCellValueFactory(cellData -> cellData.getValue().obsProperty());
        obsCol.setCellFactory(TextFieldTableCell.forTableColumn());
        obsCol.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setObs(event.getNewValue());
            Gerenciador.salvarTurmas(turmas);
        });

        // Controle para identificar alterações programáticas
        final BooleanProperty isProgrammaticChange = new SimpleBooleanProperty(false);

        // Atualizar a tabela ao selecionar uma turma na lista
        listaTurmas.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int indiceSelecionado = newValue.intValue();
            if (indiceSelecionado >= 0) {
                Turma turmaSelecionada = turmas.get(indiceSelecionado);
                tabela.getItems().clear();
                tabela.getItems().addAll(turmaSelecionada.getAlunos());

                // Marcar que a mudança no texto é programática
                isProgrammaticChange.set(true);
                observacaoTurma.setText(turmaSelecionada.getObs());
                isProgrammaticChange.set(false);
            }
        });

        // Salvar automaticamente alterações na observação da turma
        observacaoTurma.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isProgrammaticChange.get()) {
                int indiceSelecionado = listaTurmas.getSelectionModel().getSelectedIndex();
                if (indiceSelecionado >= 0) {
                    Turma turmaSelecionada = turmas.get(indiceSelecionado);
                    turmaSelecionada.setObs(newValue);
                    Gerenciador.salvarTurmas(turmas);
                }
            }
        });
    }

    /**
     * Carrega as turmas salvas do armazenamento e atualiza a interface gráfica.
     * Este método utiliza o Gerenciador para obter a lista de turmas e, em seguida,
     * preenche o componente de lista de turmas na interface.
     *
     * Comportamento:
     * <ul>
     *   <li>Lê as turmas do armazenamento usando o método {@link Gerenciador#carregarTurmas()}.</li>
     *   <li>Limpa os itens previamente exibidos no componente {@code listaTurmas}.</li>
     *   <li>Adiciona os nomes das turmas carregadas ao componente {@code listaTurmas}.</li>
     * </ul>
     */
    @FXML public void carregarTurmas() {
        turmas = Gerenciador.carregarTurmas();

        // Limpa a lista antes de carregar novas turmas
        listaTurmas.getItems().clear();

        // Preenche a lista com os nomes das turmas carregadas
        for (Turma turma : turmas) {
            listaTurmas.getItems().add(turma.getNome());
        }
    }

    /**
     * Salva as turmas atuais no armazenamento.
     *
     * Este método utiliza o Gerenciador para persistir a lista de turmas no armazenamento.
     * Ele é chamado quando há necessidade de garantir que todas as alterações feitas nas turmas
     * sejam salvas permanentemente.
     *
     * Comportamento:
     * <ul>
     *   <li>Invoca o método {@link Gerenciador#salvarTurmas(ArrayList)} para salvar a lista de turmas.</li>
     *   <li>Garante a persistência das alterações realizadas na interface ou em operações internas.</li>
     * </ul>
     */
    @FXML public void salvarTurmas() {
        Gerenciador.salvarTurmas(turmas);
    }

    /**
     * Exibe os detalhes da turma selecionada na interface.
     *
     * Este método é acionado por um evento de clique na lista de turmas. Ele recupera
     * a turma selecionada, limpa os dados anteriormente exibidos na tabela e popula
     * a tabela com os alunos da turma selecionada.
     *
     * Comportamento:
     * <ul>
     *   <li>Obtém o índice da turma selecionada na lista de turmas.</li>
     *   <li>Verifica se há uma seleção válida (índice maior ou igual a 0).</li>
     *   <li>Limpa os dados previamente exibidos na tabela.</li>
     *   <li>Adiciona os alunos da turma selecionada à tabela para exibição.</li>
     * </ul>
     *
     * @param event o evento de clique do mouse que aciona este método
     */
    @FXML public void mostrarDetalhesTurma(MouseEvent event) {
        int indiceSelecionado = listaTurmas.getSelectionModel().getSelectedIndex();

        if (indiceSelecionado >= 0) {
            Turma turmaSelecionada = turmas.get(indiceSelecionado);

            tabela.getItems().clear(); // Limpa os dados anteriores na tabela

            tabela.getItems().addAll(turmaSelecionada.getAlunos()); // Adiciona alunos na tabela
        }
    }

    /**
     * Realiza o logout do sistema e redireciona o usuário para a tela de login.
     *
     * Este método é acionado por um evento de clique em um botão ou outro elemento
     * que desencadeie uma ação. Ele carrega a interface da tela de login e redefine
     * a cena do stage atual para exibir essa tela. Caso ocorra um erro durante o
     * carregamento, a stack trace será exibida no console.
     *
     * Fluxo de execução:
     * <ul>
     *   <li>Carrega o arquivo FXML correspondente à tela de login.</li>
     *   <li>Obtém o stage atual a partir do evento disparado.</li>
     *   <li>Define a nova cena com a interface da tela de login.</li>
     *   <li>Exibe a tela de login.</li>
     * </ul>
     *
     * Tratamento de erros:
     * <ul>
     *   <li>Se ocorrer uma IOException ao carregar o arquivo FXML, a exceção será
     *       capturada e sua stack trace será exibida no console.</li>
     * </ul>
     *
     * @param event o evento de ação que aciona este método
     */
    @FXML protected void LogOut(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/TelaLogin.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(new Scene(root));
            stage.setTitle("Tela de Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adiciona um novo aluno à turma selecionada na interface.
     *
     * Este método é acionado quando o usuário clica no botão de adicionar aluno.
     * Ele realiza as seguintes operações:
     * <ul>
     *   <li>Verifica se uma turma foi selecionada na lista de turmas.</li>
     *   <li>Cria um novo aluno com valores padrão para nome, notas e observação.</li>
     *   <li>Adiciona o novo aluno à turma selecionada.</li>
     *   <li>Atualiza a tabela de alunos para exibir o novo aluno.</li>
     *   <li>Salva as alterações no armazenamento persistente.</li>
     *   <li>Foca na nova linha da tabela para permitir a edição imediata do nome do aluno.</li>
     * </ul>
     *
     * Caso nenhuma turma esteja selecionada, uma mensagem de erro é exibida ao usuário.
     *
     * Fluxo de execução:
     * <ol>
     *   <li>Obtém o índice da turma selecionada.</li>
     *   <li>Se válido, cria um aluno com valores padrão e o adiciona à turma correspondente.</li>
     *   <li>Atualiza a tabela de alunos e persiste as alterações.</li>
     *   <li>Se nenhuma turma for selecionada, exibe um alerta de erro.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *   <li>Se nenhuma turma for selecionada, exibe uma mensagem de erro em uma caixa de diálogo.</li>
     * </ul>
     *
     * Valores padrão do novo aluno:
     * <ul>
     *   <li>Nome: string vazia.</li>
     *   <li>Notas: 0.0 para as três posições.</li>
     *   <li>Observação: "Nenhuma Observação".</li>
     * </ul>
     *
     */
    @FXML protected void addAluno() {
        int indiceSelecionado = listaTurmas.getSelectionModel().getSelectedIndex();
        if (indiceSelecionado >= 0) {
            Turma turmaSelecionada = turmas.get(indiceSelecionado);

            // Criar um novo aluno com valores padrão
            Aluno novoAluno = new Aluno("");
            novoAluno.setNota(0.0f, 0);
            novoAluno.setNota(0.0f, 1);
            novoAluno.setNota(0.0f, 2);
            novoAluno.setObs("Nenhuma Observação");

            // Adicionar o aluno na turma
            turmaSelecionada.addAluno(novoAluno);

            // Atualizar a tabela
            tabela.getItems().add(novoAluno);

            // Salvar as alterações
            Gerenciador.salvarTurmas(turmas);

            // Focar na nova linha para edição
            tabela.edit(tabela.getItems().size() - 1, NomeCol);
        } else {
            // Exibir mensagem de erro se nenhuma turma estiver selecionada
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Nenhuma turma selecionada");
            alerta.setContentText("Por favor, selecione uma turma antes de adicionar um aluno.");
            alerta.showAndWait();
        }
    }

    /**
     * Remove o aluno selecionado da turma atualmente selecionada.
     *
     * Este método é acionado quando o usuário clica no botão de remover aluno.
     * Ele realiza as seguintes operações:
     * <ul>
     *   <li>Verifica se há um aluno selecionado na tabela.</li>
     *   <li>Verifica se há uma turma selecionada na lista de turmas.</li>
     *   <li>Remove o aluno da turma selecionada.</li>
     *   <li>Atualiza a tabela de alunos para refletir a remoção.</li>
     *   <li>Salva as alterações no armazenamento persistente.</li>
     * </ul>
     *
     * Caso nenhum aluno ou turma esteja selecionado, uma mensagem de erro é exibida ao usuário.
     *
     * Fluxo de execução:
     * <ol>
     *   <li>Obtém o aluno selecionado na tabela.</li>
     *   <li>Se válido, obtém a turma selecionada na lista de turmas.</li>
     *   <li>Remove o aluno da turma correspondente e atualiza a tabela.</li>
     *   <li>Persiste as alterações no armazenamento.</li>
     *   <li>Se nenhuma turma ou aluno for selecionado, exibe um alerta de erro.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *   <li>Exibe uma mensagem de erro se nenhum aluno for selecionado na tabela.</li>
     *   <li>Exibe uma mensagem de erro se nenhuma turma for selecionada na lista.</li>
     * </ul>
     */
    @FXML public void removeAluno() {
        // Obter o aluno selecionado na tabela
        Aluno alunoSelecionado = tabela.getSelectionModel().getSelectedItem();
        if (alunoSelecionado != null) {
            // Obter a turma atualmente selecionada
            int indiceTurmaSelecionada = listaTurmas.getSelectionModel().getSelectedIndex();
            if (indiceTurmaSelecionada >= 0) {
                Turma turmaSelecionada = turmas.get(indiceTurmaSelecionada);

                // Remover o aluno da turma
                turmaSelecionada.removeAluno(alunoSelecionado);

                // Atualizar a tabela
                tabela.getItems().remove(alunoSelecionado);

                // Salvar as alterações
                Gerenciador.salvarTurmas(turmas);
            } else {
                // Exibir mensagem de erro se nenhuma turma estiver selecionada
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Nenhuma turma selecionada");
                alerta.setContentText("Por favor, selecione uma turma antes de remover um aluno.");
                alerta.showAndWait();
            }
        } else {
            // Exibir mensagem de erro se nenhum aluno estiver selecionado
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Nenhum aluno selecionado");
            alerta.setContentText("Por favor, selecione um aluno na tabela antes de removê-lo.");
            alerta.showAndWait();
        }
    }

    /**
     * Exporta os dados da turma selecionada para um arquivo CSV.
     *
     * Este método permite que o usuário selecione uma turma da lista de turmas
     * e exporte os dados dos alunos dessa turma em formato CSV, incluindo:
     * <ul>
     *     <li>Nome do aluno</li>
     *     <li>Notas 1, 2 e 3</li>
     *     <li>Observação</li>
     * </ul>
     *
     * Fluxo de execução:
     * <ol>
     *     <li>Obtém a turma selecionada na lista de turmas.</li>
     *     <li>Abre um seletor de arquivos para que o usuário escolha onde salvar o arquivo CSV.</li>
     *     <li>Se um arquivo for selecionado, escreve os dados dos alunos no arquivo em formato CSV.</li>
     *     <li>Exibe uma mensagem de sucesso após a exportação.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *     <li>Exibe uma mensagem de erro se nenhuma turma for selecionada.</li>
     *     <li>Exibe uma mensagem de erro se ocorrer uma falha ao salvar o arquivo.</li>
     * </ul>
     *
     * @throws IOException Caso ocorra um erro ao escrever no arquivo.
     * @see FileChooser
     * @see BufferedWriter
     */
    @FXML private void exportarTurma() {
        // Obter a turma selecionada
        int indiceTurmaSelecionada = listaTurmas.getSelectionModel().getSelectedIndex();
        if (indiceTurmaSelecionada >= 0) {
            Turma turmaSelecionada = turmas.get(indiceTurmaSelecionada);

            // Abrir um seletor de arquivos para salvar o arquivo
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Exportar Turma");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo CSV", "*.csv"));
            File arquivo = fileChooser.showSaveDialog(listaTurmas.getScene().getWindow());

            if (arquivo != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                    // Escrever cabeçalho
                    writer.write("Nome,Nota1,Nota2,Nota3,Observação");
                    writer.newLine();

                    // Escrever dados dos alunos
                    for (Aluno aluno : turmaSelecionada.getAlunos()) {
                        writer.write(String.format(Locale.US,
                                "\"%s\",%.2f,%.2f,%.2f,\"%s\"",
                                aluno.getNome(),
                                aluno.getNotas()[0],
                                aluno.getNotas()[1],
                                aluno.getNotas()[2],
                                aluno.getObs()
                        ));
                        writer.newLine();
                    }

                    // Confirmar exportação
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Exportação bem-sucedida");
                    alerta.setHeaderText("Dados da turma exportados com sucesso!");
                    alerta.setContentText("Os dados foram salvos em: " + arquivo.getAbsolutePath());
                    alerta.showAndWait();

                } catch (IOException e) {
                    // Tratar erros de escrita
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Erro");
                    alerta.setHeaderText("Erro ao exportar os dados");
                    alerta.setContentText("Ocorreu um problema ao salvar o arquivo.");
                    alerta.showAndWait();
                }
            }
        } else {
            // Mensagem de erro se nenhuma turma estiver selecionada
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Nenhuma turma selecionada");
            alerta.setContentText("Por favor, selecione uma turma antes de exportar.");
            alerta.showAndWait();
        }
    }

    /**
     * Gera e exibe as duplas de alunos da turma selecionada.
     *
     * Este método permite ao usuário selecionar uma turma da lista e gerar duplas
     * de alunos para atividades. As duplas geradas são exibidas em uma nova janela.
     *
     * Fluxo de execução:
     * <ol>
     *     <li>Obtém a turma atualmente selecionada na lista de turmas.</li>
     *     <li>Chama o método {@code formarDuplas()} da turma para gerar as duplas.</li>
     *     <li>Carrega o layout da janela de exibição de duplas a partir do arquivo FXML.</li>
     *     <li>Configura o controlador da nova janela e passa as duplas geradas para exibição.</li>
     *     <li>Exibe a nova janela com as duplas.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *     <li>Exibe um alerta caso nenhuma turma esteja selecionada.</li>
     *     <li>Captura e imprime qualquer exceção lançada durante o carregamento da janela.</li>
     * </ul>
     *
     * Requisitos:
     * <ul>
     *     <li>O método {@code formarDuplas()} deve estar implementado na classe {@code Turma}.</li>
     *     <li>O arquivo FXML da nova janela deve estar localizado em {@code /br/ufrn/imd/projetofinal/TelaDuplas.fxml}.</li>
     *     <li>A classe {@code TelaDuplasController} deve ser configurada para manipular a exibição das duplas.</li>
     * </ul>
     *
     * @see Turma#formarDuplas()
     * @see TelaDuplasController
     */
    @FXML private void gerarDuplas() {
        // Obter a turma selecionada
        int indiceTurmaSelecionada = listaTurmas.getSelectionModel().getSelectedIndex();
        if (indiceTurmaSelecionada >= 0) {
            Turma turmaSelecionada = turmas.get(indiceTurmaSelecionada);

            // Gerar duplas
            List<List<Aluno>> duplas = turmaSelecionada.formarDuplas();

            try {
                // Carregar o layout da nova janela
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/TelaDuplas.fxml"));
                Parent root = loader.load();

                // Configurar o controlador
                TelaDuplasController controller = loader.getController();
                Stage stage = new Stage();
                controller.setStage(stage);
                controller.carregarDuplas(duplas);

                // Configurar a janela
                stage.setTitle("Duplas Formadas");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Alerta caso nenhuma turma esteja selecionada
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Nenhuma turma selecionada");
            alerta.setContentText("Por favor, selecione uma turma antes de gerar as duplas.");
            alerta.showAndWait();
        }
    }

    /**
     * Abre uma nova janela para criar uma turma e adiciona a nova turma à lista de turmas.
     *
     * Este método permite ao usuário criar uma nova turma a partir de uma interface dedicada.
     * A nova turma é adicionada à lista de turmas e salva no sistema após sua criação.
     *
     * Fluxo de execução:
     * <ol>
     *     <li>Carrega o layout da janela de criação de turma a partir do arquivo FXML.</li>
     *     <li>Configura o controlador da nova janela e exibe-a como uma janela modal.</li>
     *     <li>Após o fechamento da janela, verifica se uma nova turma foi criada.</li>
     *     <li>Adiciona a nova turma à lista de turmas e à exibição da lista na interface.</li>
     *     <li>Salva as turmas atualizadas utilizando o método {@code Gerenciador.salvarTurmas()}.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *     <li>Exibe o rastreamento de pilha no console caso ocorra uma exceção ao carregar o layout ou manipular a janela.</li>
     * </ul>
     *
     * Requisitos:
     * <ul>
     *     <li>O arquivo FXML da janela deve estar localizado em {@code /br/ufrn/imd/projetofinal/TelaCriarTurma.fxml}.</li>
     *     <li>A classe {@code TelaCriarTurmaController} deve ser configurada para manipular a criação da turma.</li>
     *     <li>O método {@code getNovaTurma()} da classe {@code TelaCriarTurmaController} deve retornar a turma criada.</li>
     * </ul>
     *
     * @see TelaCriarTurmaController#getNovaTurma()
     * @see Gerenciador#salvarTurmas(ArrayList)
     */
    @FXML private void addTurma() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/projetofinal/TelaCriarTurma.fxml"));
            Parent root = loader.load();

            TelaCriarTurmaController controller = loader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);

            stage.setTitle("Criar Nova Turma");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Recupera a nova turma criada
            Turma novaTurma = controller.getNovaTurma();
            if (novaTurma != null) {
                turmas.add(novaTurma);
                listaTurmas.getItems().add(novaTurma.getNome());
                Gerenciador.salvarTurmas(turmas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a turma selecionada da lista de turmas, após confirmação do usuário.
     *
     * Este método permite ao usuário excluir uma turma da lista de turmas gerenciada pelo sistema.
     * A exclusão é realizada apenas após a confirmação do usuário, e as alterações são persistidas.
     *
     * Fluxo de execução:
     * <ol>
     *     <li>Obtém o índice da turma selecionada na lista de turmas.</li>
     *     <li>Se uma turma estiver selecionada, exibe uma caixa de diálogo de confirmação.</li>
     *     <li>Se o usuário confirmar, remove a turma da lista de turmas e da exibição na interface.</li>
     *     <li>Limpa a tabela de alunos e quaisquer observações associadas à turma removida.</li>
     *     <li>Salva as alterações utilizando o método {@code Gerenciador.salvarTurmas()}.</li>
     *     <li>Exibe uma mensagem de sucesso.</li>
     * </ol>
     *
     * Tratamento de erros:
     * <ul>
     *     <li>Exibe uma mensagem de erro se nenhuma turma estiver selecionada.</li>
     * </ul>
     *
     * Requisitos:
     * <ul>
     *     <li>A lista de turmas deve ser gerenciada por uma instância de {@code ObservableList}.</li>
     *     <li>Os elementos da interface gráfica, como {@code listaTurmas}, {@code tabela} e {@code observacaoTurma},
     *         devem estar devidamente inicializados.</li>
     *     <li>O método {@code Gerenciador.salvarTurmas()} deve estar implementado para persistir os dados.</li>
     * </ul>
     *
     * @see Gerenciador#salvarTurmas(ArrayList)
     * @see Alert
     */
    @FXML private void removeTurma() {
        // Obter o índice da turma selecionada
        int indiceSelecionado = listaTurmas.getSelectionModel().getSelectedIndex();

        if (indiceSelecionado >= 0) {
            // Confirmar a remoção com o usuário
            Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacao.setTitle("Confirmação de Remoção");
            confirmacao.setHeaderText("Você está prestes a remover a turma \"" + indiceSelecionado + "\".");
            confirmacao.setContentText("Deseja continuar?");

            // Aguardar resposta do usuário
            confirmacao.showAndWait().ifPresent(resposta -> {
                if (resposta == ButtonType.OK) {
                    // Remover a turma da lista
                    turmas.remove(indiceSelecionado);

                    // Atualizar a ListView
                    listaTurmas.getItems().remove(indiceSelecionado);

                    // Limpar a tabela e observações associadas à turma
                    tabela.getItems().clear();
                    observacaoTurma.clear();

                    // Salvar as mudanças
                    Gerenciador.salvarTurmas(turmas);

                    // Mensagem de sucesso
                    Alert sucesso = new Alert(Alert.AlertType.INFORMATION);
                    sucesso.setTitle("Remoção Concluída");
                    sucesso.setHeaderText("A turma foi removida com sucesso.");
                    sucesso.showAndWait();
                }
            });
        } else {
            // Mensagem de erro se nenhuma turma estiver selecionada
            Alert erro = new Alert(Alert.AlertType.ERROR);
            erro.setTitle("Erro");
            erro.setHeaderText("Nenhuma turma selecionada");
            erro.setContentText("Por favor, selecione uma turma antes de tentar removê-la.");
            erro.showAndWait();
        }
    }

}