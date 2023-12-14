package sistemasupermercado.Gerenciamento.View;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sistemasupermercado.Gerenciamento.Connection.ClientesDAO;
import sistemasupermercado.Gerenciamento.Control.ClientesControl;
import sistemasupermercado.Gerenciamento.Model.Cliente;

public class CadastroPanel extends JFrame {
    // Componentes
    private JPanel buttonPanel, mainP;
    private JButton cadastraCliente;
    private JTextField inputCpf, inputNome, inputIdade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Cliente> clientes = new ArrayList<>();

    public CadastroPanel() {
        mainP = new JPanel();
        mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel

        // --------------------------*
        // Componentes
        cadastraCliente = new JButton("Cadastrar");
        inputCpf = new JTextField(7);
        inputNome = new JTextField(4);
        inputIdade = new JTextField(12);

        // --------------------------*
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Cadastro de Clientes VIP"));
        mainP.add(title);
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 2, 2)); // InputPanel
        inputPanel.add(new JLabel("Digite o cpf do cliente:"));
        inputPanel.add(inputCpf);
        inputPanel.add(new JLabel("Digite o nome completo do cliente:"));
        inputPanel.add(inputNome);
        inputPanel.add(new JLabel("Digite a idade do cliente:"));
        inputPanel.add(inputIdade);
        mainP.add(inputPanel);

        // --------------------------*
        buttonPanel = new JPanel(); // Painel de botões
        buttonPanel.add(cadastraCliente);
        mainP.add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        add(mainP);
        // --------------------------
        // Cadastrar um cliente:
        cadastraCliente.addActionListener(e -> {
            if (!inputCpf.getText().isEmpty() && !inputNome.getText().isEmpty()
                    && !inputIdade.getText().isEmpty()) { // Verifica se os campos não estiverem vazios

                if (validaCpf(inputCpf.getText().trim()) && validaIdade(inputIdade.getText().trim())) { // Valida o cpf
                                                                                                        // e a idade, se
                                                                                                        // forem
                                                                                                        // validados
                                                                                                        // cadastra o
                                                                                                        // cliente no
                                                                                                        // banco de
                                                                                                        // dados
                    new ClientesDAO().cadastrar(inputCpf.getText(), inputNome.getText(), inputIdade.getText());

                    JOptionPane.showMessageDialog(null, "Cliente cadastrado!");
                    this.dispose(); // Fecha a janela
                } else {
                    JOptionPane.showMessageDialog(inputPanel,
                            "Preencha os campos corretamente para cadastrar um cliente!!", null,
                            JOptionPane.WARNING_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um cliente!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        // --------------------------*

    }

    public boolean validaCpf(String cpf) { // Verifica o texto digitado no inputCpf (apenas dígitos e tamanho igual a 11
        // 'ex: 12345678910')
        if (cpf.matches("[0-9]+") && cpf.length() == 11) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validaIdade(String idade) { // Verifica o texto digitado no inputIdade (apenas dígitos e número maior
        // que 0)
        if (idade.matches("[0-9]+") && Integer.parseInt(idade) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
