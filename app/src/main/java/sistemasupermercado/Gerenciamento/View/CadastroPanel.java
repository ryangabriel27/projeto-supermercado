package sistemasupermercado.Gerenciamento.View;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sistemasupermercado.Gerenciamento.Connection.ClientesDAO;
import sistemasupermercado.Gerenciamento.Control.ClientesControl;
import sistemasupermercado.Gerenciamento.Model.Cliente;

public class CadastroPanel extends JPanel {
    // Componentes
    private JPanel buttonPanel;
    private JButton cadastraCliente;
    private JTextField inputCpf, inputNome, inputIdade;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Cliente> clientes = new ArrayList<>();

    public CadastroPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Definindo layout do CarrosPanel

        // --------------------------*
        // Componentes
        cadastraCliente = new JButton("Cadastrar");
        inputCpf = new JTextField(7);
        inputNome = new JTextField(4);
        inputIdade = new JTextField(12);

        // --------------------------*
        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Cadastro de Clientes VIP"));
        add(title);
        // Adicionar os componentes:
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 2, 2)); // InputPanel
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
        add(buttonPanel);// Adicionando o painel De botões a Tela Principal

        // --------------------------
        // Cadastrar um cliente:
        cadastraCliente.addActionListener(e -> {
            if (!inputCpf.getText().isEmpty() && !inputNome.getText().isEmpty()
                    && !inputIdade.getText().isEmpty()) {
                        new ClientesDAO().cadastrar(inputCpf.getText(), inputNome.getText(), inputIdade.getText());
                
                        JOptionPane.showMessageDialog(null, "Cliente cadastrado!");
                    
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha os campos corretamente para cadastrar um cliente!!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        // --------------------------*

    }
}
