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

import java.awt.Color;
import java.awt.GridLayout;

import sistemasupermercado.Gerenciamento.Connection.ClientesDAO;
import sistemasupermercado.Gerenciamento.Connection.EstoqueDAO;
import sistemasupermercado.Gerenciamento.Model.Cliente;
import sistemasupermercado.Gerenciamento.Model.Estoque;

public class Caixa extends JFrame {
    // Atributos
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton verificaCPF, cadastrarNovoCliente, fazerCompra, adicionaProduto;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel;
    private JLabel clienteVIP;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos;
    private List<Cliente> clientes;
    private List<Estoque> listaDeCompra = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;
    private boolean isClienteVIP;
    private int contProduto = 1;
    private int quantidadeTotal = 0;
    private int valorTotal = 0;

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
        clienteVIP = new JLabel("Cliente VIP");
        verificaCPF = new JButton("Verificar CPF");
        cadastrarNovoCliente = new JButton("Cadastrar novo cliente");
        adicionaProduto = new JButton("Adicionar Produto");
        fazerCompra = new JButton("Fazer compra");

        clienteVIP.setForeground(new Color(65, 166, 18));
        // Adicionando o mainPanel ao JFrame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        cpfPanel.setLayout(new GridLayout(1, 3, 5, 4));
        cpfPanel.add(inputCPF);
        cpfPanel.add(verificaCPF);
        cpfPanel.add(cadastrarNovoCliente);
        mainPanel.add(cpfPanel);

        produtoPanel.setLayout(new GridLayout(1, 2, 4, 5));
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
        atualizaQuantidadeEValorTotal();
        mainPanel.add(totalPanel);

        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(fazerCompra);
        mainPanel.add(buttonPanel);

        // Tratamento de eventos
        verificaCPF.addActionListener(e -> {
            isClienteVIP = validaCpf(inputCPF.getText());
            System.out.println(isClienteVIP);
            if (isClienteVIP == true) {
                cpfPanel.add(clienteVIP);
            }

            inputCPF.setText("");
        });

        adicionaProduto.addActionListener(e -> {
            if (!inputProduto.getText().isEmpty()) {
                buscarProduto(Integer.parseInt(inputProduto.getText()));

                inputProduto.setText("");
            }
        });
    }

    public boolean validaCpf(String cpf) {
        clientes = new ClientesDAO().listarTodos();
        for (Cliente cliente : clientes) {
            if (cpf.trim().equals(cliente.getCpf())) {
                return true;
            }
        }
        return false;
    }

    public void buscarProduto(int id) {
        produtos = new EstoqueDAO().listarTodos();
        for (Estoque produto : produtos) {
            if (produto.getId() == id) {
                int res = JOptionPane.showConfirmDialog(null, "A quantidade do produto é maior que 1?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    try {
                        contProduto = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade do produto:"));
                    } catch (Exception err) {
                        System.out.println(err);
                        contProduto = 1;
                    }
                }
                tableModel.addRow(new Object[] {
                        produto.getNomeDoProduto(), contProduto, produto.getPreco()
                });
                Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(), contProduto, Integer.parseInt(produto.getPreco()));
            }
        }
        atualizaQuantidadeEValorTotal();
    }

    public void atualizaQuantidadeEValorTotal(){
        for (int i = 0; i < table.getRowCount(); i++) {
            quantidadeTotal += (int) table.getValueAt(i, 1);
        }
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));

        for (int i = 0; i < table.getRowCount(); i++) {
            valorTotal += (int) table.getValueAt(i, 2);
        }
        valorFinal.setText(String.valueOf(valorTotal));
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
