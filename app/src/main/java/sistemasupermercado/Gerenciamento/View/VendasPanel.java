package sistemasupermercado.Gerenciamento.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sistemasupermercado.Gerenciamento.Connection.VendasDAO;
import sistemasupermercado.Gerenciamento.Control.VendasControl;
import sistemasupermercado.Gerenciamento.Model.Venda;

public class VendasPanel extends JPanel {

    private JPanel buttonPanel;
    private JButton realizaVenda, cancelaVenda;
    private JTextField inputProduto, inputQuantidade, inputTotal, inputCliente, inputData;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Venda> vendas = new ArrayList<>();
    private int linhaSelecionada = -1;
    private JScrollPane jSPane;

    public VendasPanel() {
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        realizaVenda = new JButton("Realizar Venda");
        cancelaVenda = new JButton("Cancelar Venda");
        inputProduto = new JTextField(10);
        inputQuantidade = new JTextField(10);
        inputTotal = new JTextField(10);
        inputCliente = new JTextField(10);
        inputData = new JTextField(10);

        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Registro de Vendas"));
        add(title);

        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "id", "Cliente (CPF)", "Total", "Quantidade", "Data" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new VendasDAO().criaTabela();
        atualizarTabela();
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[] { venda.getId(), venda.getCliente(), venda.getValor(),
                    venda.getQuantidadeDeProdutos(), venda.getData() });
        }
    }
}
