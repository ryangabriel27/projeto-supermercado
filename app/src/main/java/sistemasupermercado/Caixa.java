package sistemasupermercado;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;

import sistemasupermercado.Gerenciamento.Model.Cliente;
import sistemasupermercado.Gerenciamento.Model.Estoque;


public class Caixa extends JFrame {
    // Atributos
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton verificaCPF, cadastrarNovoCliente, fazerCompra, adicionaProduto;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    // Construtor
    public Caixa() {
        // Inicializando componentes
        jSPane = new JScrollPane();
        mainPanel = new JPanel();
        totalPanel = new JPanel();
        cpfPanel = new JPanel();
        produtoPanel = new JPanel();
        buttonPanel = new JPanel();
        inputCPF = new JTextField(20);
        inputProduto = new JTextField(20);
        valorFinal = new JTextField();
        quantidadeDeItens = new JTextField();
        verificaCPF = new JButton("Verificar CPF");
        cadastrarNovoCliente = new JButton("Cadastrar novo cliente");
        adicionaProduto = new JButton("Adicionar Produto");
        fazerCompra = new JButton("Fazer compra");

        // Adicionando o mainPanel ao JFrame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        cpfPanel.setLayout(new GridLayout(1,3,5,4));
        cpfPanel.add(inputCPF);
        cpfPanel.add(verificaCPF);
        cpfPanel.add(cadastrarNovoCliente);
        mainPanel.add(cpfPanel);

        produtoPanel.setLayout(new GridLayout(1,2,4,5));
        produtoPanel.add(inputProduto);
        produtoPanel.add(adicionaProduto);
        mainPanel.add(produtoPanel);

        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produto", "Quantidade", "Valor" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);
        mainPanel.add(jSPane);

        totalPanel.setLayout(new GridLayout(1, 3));
        totalPanel.add(new JLabel("Total:"));
        totalPanel.add(valorFinal);
        totalPanel.add(quantidadeDeItens);
        valorFinal.setEditable(false);
        quantidadeDeItens.setEditable(false);
        mainPanel.add(totalPanel);

        buttonPanel.setLayout(new GridLayout(1,1));
        buttonPanel.add(fazerCompra);
        mainPanel.add(buttonPanel);
    }


    public void run() {
        pack();
        setVisible(true);
    }
}
