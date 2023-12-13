package sistemasupermercado.Gerenciamento.View;

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

    private JPanel mainP, topPanel, bottomPanel, buttonPanel;
    private JButton comprar;
    private DefaultTableModel tableModel;
    private JTable table;
    private JScrollPane jScrollPane;

    public FinalizaCompra(List<Estoque> compras, double valorFinal) {

        mainP = new JPanel();
        mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS));
        this.add(mainP);

        topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.add(new JLabel("Escolha a forma de pagamento:"));

        JComboBox formasDePagamento = new JComboBox<String>();
        formasDePagamento.addItem("1 - Cartão de Crédito ou Débito");
        formasDePagamento.addItem("2 - Dinheiro");
        formasDePagamento.addItem("3 - PIX");
        topPanel.add(formasDePagamento);
        mainP.add(topPanel);

        mainP.add(new JLabel("Resumo da compra:"));

        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Produto", "Quantidade" , "Valor p/Unidade" });
        table = new JTable(tableModel);
        preencheTabela(compras);
        jScrollPane = new JScrollPane();
        mainP.add(jScrollPane);
        jScrollPane.setViewportView(table);

        bottomPanel = new JPanel(new GridLayout(1, 2));
        bottomPanel.add(new JLabel("TOTAL: "));
        bottomPanel.add(new JLabel("R$ "+String.valueOf(valorFinal)));
        mainP.add(bottomPanel);

        buttonPanel = new JPanel(new GridLayout(1,1));
        comprar = new JButton("Fechar");
        buttonPanel.add(comprar);
        mainP.add(buttonPanel);

        comprar.addActionListener(e ->{
            this.dispose();
        });
    }

    public void preencheTabela(List<Estoque> compras){
        for (Estoque compra : compras) {
            tableModel.addRow(new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra() ,  "R$ "+compra.getPrecoCompra() });
        }
    }

    public void run(){
        this.pack();
        this.setDefaultCloseOperation(2);
        this.setVisible(true);
    }
}
