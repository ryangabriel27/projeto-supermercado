package sistemasupermercado.Gerenciamento.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import sistemasupermercado.Gerenciamento.Model.Estoque;

public class FinalizaCompra extends JFrame {

    private JPanel mainP, topPanel, midPanel, buttonPanel;
    private JButton comprar;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane jScrollPane;

    public FinalizaCompra(List<Estoque> compras, double valorFinal, Object formaDePagamento, String cpfCliente,
            boolean isClienteVIP) {

        super("Resumo da compra");

        // Criando um painel principal
        mainP = new JPanel();
        mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));// Definindo o layout do painel principal
        this.add(mainP); // Adicionando o painel Principal ao frame

        JLabel clienteVIP = new JLabel(""); // Cria uma JLabel para informar se é VIP ou não
        if (isClienteVIP) {
            clienteVIP = new JLabel("Cliente VIP"); // Se sim ganha um valor
            clienteVIP.setForeground(new Color(0, 173, 26));
        }

        midPanel = new JPanel(new FlowLayout()); // MidPanel apenas para centralizar o texto
        midPanel.add(new JLabel("Resumo da compra:"));
        mainP.add(midPanel); // Adicionando o midPanel ao painel principal

        tableModel = new DefaultTableModel(new Object[][] {},
                new String[] { "Produto", "Quantidade", "Valor p/Unidade" });
        table = new JTable(tableModel); // Criando uma tabela que exibirá todos os produtos, com suas devidas
                                        // quantidades e devidos valores, comprados pelo cliente
        preencheTabela(compras); // Preenche a tabela com os itens
        jScrollPane = new JScrollPane();
        mainP.add(jScrollPane); // Adiciona o jScrollPane com a tabela ao painel Principal
        jScrollPane.setViewportView(table);

        topPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // TopPanel com informações com forma de pagamento, cliente
                                                            // e
                                                            // total da compra
        topPanel.add(new JLabel("Forma de pagamento: " + String.valueOf(formaDePagamento)));
        topPanel.add(new JLabel("TOTAL: R$ " + String.valueOf(valorFinal)));
        topPanel.add(new JLabel("Cliente (CPF):" + cpfCliente + " "));
        topPanel.add(clienteVIP);
        mainP.add(topPanel); // Adicionando o topPanel ao painel principal

        buttonPanel = new JPanel(new GridLayout(1, 1)); // buttonPanel com o botão para fechar essa janela
        comprar = new JButton("Fechar");
        buttonPanel.add(comprar);
        mainP.add(buttonPanel);

        comprar.addActionListener(e -> {
            this.dispose(); // Fecha a janela, ao clicar no botão Fechar
        });
    }

    public void preencheTabela(List<Estoque> compras) { // Preenche a tabela com a listaDeCompra
        for (Estoque compra : compras) { // Percorre toda a listaDeCompra do cliente e vai adicionando os itens a tabela
            tableModel.addRow(new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(),
                    "R$ " + compra.getPrecoCompra() });
        }
    }

    public void run() {
        this.pack();
        this.setVisible(true);
    }
}
