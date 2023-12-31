package sistemasupermercado.Gerenciamento.View;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sistemasupermercado.Gerenciamento.Connection.ClientesDAO;
import sistemasupermercado.Gerenciamento.Control.ClientesControl;
import sistemasupermercado.Gerenciamento.Model.Cliente;

public class ClientesPanel extends JPanel {

    // Componentes
    private JPanel buttonPanel;
    private JButton cadastraCliente, editaCliente, apagaCliente;
    private JTextField inputCpf, inputNome, inputIdade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Cliente> clientes = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public ClientesPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel

        // --------------------------*
        // Componentes
        cadastraCliente = new JButton("Cadastrar");
        apagaCliente = new JButton("Excluir");
        editaCliente = new JButton("Editar");
        inputCpf = new JTextField(7);
        inputNome = new JTextField(4);
        inputIdade = new JTextField(12);

        // --------------------------*
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Cadastro de Clientes VIP"));
        add(title);
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(3,2,2,2)); // InputPanel
        inputPanel.add(new JLabel("Digite o cpf do cliente:"));
        inputPanel.add(inputCpf);
        inputPanel.add(new JLabel("Digite o nome completo do cliente:"));
        inputPanel.add(inputNome);
        inputPanel.add(new JLabel("Digite a idade do cliente:"));
        inputPanel.add(inputIdade);
        add(inputPanel);

        // --------------------------*
        buttonPanel = new JPanel(); // Painel de botões
        buttonPanel.add(cadastraCliente);
        buttonPanel.add(editaCliente);
        buttonPanel.add(apagaCliente);
        add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        // --------------------------*
        jSPane = new JScrollPane(); // Criando um scrollPane
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "CPF", "Nome Completo", "Idade" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        // Cria o banco de dados caso não tenha sido criado
        new ClientesDAO().criaTabela();
        // Atualiza a tabela
        atualizarTabela();

        // --------------------------*
        // Estilização:
        apagaCliente.setBackground(new Color(168, 3, 3));
        apagaCliente.setForeground(new Color(255, 255, 255));
        cadastraCliente.setBackground(new Color(46, 128, 32));
        cadastraCliente.setForeground(new Color(255, 255, 255));
        editaCliente.setBackground(new Color(109, 110, 109));
        editaCliente.setForeground(new Color(255, 255, 255));
        // --------------------------*
        // Tratamento de eventos :

        table.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputCpf.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputNome.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputIdade.setText((String) table.getValueAt(linhaSelecionada, 2));
                }

            }

        });

        ClientesControl control = new ClientesControl(clientes, tableModel, table); // Objeto da classe carrosControl

        // Cadastrar um cliente:
        cadastraCliente.addActionListener(e -> {
            if (!inputCpf.getText().isEmpty() && !inputNome.getText().isEmpty()
                    && !inputIdade.getText().isEmpty()) {

                control.cadastrarCliente(inputCpf.getText(), inputNome.getText(), inputIdade.getText());
                // Limpa os campos de entrada após a operação de cadastro
                inputCpf.setText("");
                inputNome.setText("");
                inputIdade.setText("");
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um cliente!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }

        });
        // --------------------------*

        // Editar um cliente:
        editaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja atualizar as informações deste cliente?",
                        "Editar", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {

                    // Chama o método "atualizar" do objeto operacoes com os valores dos campos de
                    // entrada
                    control.atualizar(inputCpf.getText(), inputNome.getText(), inputIdade.getText());

                    // Limpa os campos de entrada após a operação de atualização
                }
            }
        });
        // --------------------------*

        // Apagar um cliente:
        apagaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja excluir este cliente?",
                        "Excluir", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    // Chama o método "apagar" do objeto operacoes com o valor do campo de entrada
                    // "cpf"
                    control.apagar(inputCpf.getText());
                    // Limpa os campos de entrada após a operação de exclusão
                    inputCpf.setText("");
                    inputNome.setText("");
                    inputIdade.setText("");
                }

            }
        });

    }

    public void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        clientes = new ClientesDAO().listarTodos();
        // Obtém os clientes atualizados do banco de dados
        for (Cliente cliente : clientes) {
            // Adiciona os dados de cada cliente como uma nova linha na tabela Swing
            tableModel.addRow(new Object[] { cliente.getCpf(), cliente.getNome(), cliente.getIdade() });
        }

    }
}
