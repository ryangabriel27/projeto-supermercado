package sistemasupermercado.View;

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
import sistemasupermercado.Connection.VendasDAO;
import sistemasupermercado.Control.VendasControl;
import sistemasupermercado.Model.Venda;

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

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 2, 4));
        inputPanel.add(new JLabel("Produto Vendido:"));
        inputPanel.add(inputProduto);
        inputPanel.add(new JLabel("Quantidade Vendida:"));
        inputPanel.add(inputQuantidade);
        inputPanel.add(new JLabel("Total da Venda:"));
        inputPanel.add(inputTotal);
        inputPanel.add(new JLabel("Cliente:"));
        inputPanel.add(inputCliente);
        inputPanel.add(new JLabel("Data da Venda (DD/MM/YYYY):"));
        inputPanel.add(inputData);
        add(inputPanel);

        buttonPanel = new JPanel();
        buttonPanel.add(realizaVenda);
        buttonPanel.add(cancelaVenda);
        add(buttonPanel);

        jSPane = new JScrollPane();
        add(jSPane);
        tableModel = new DefaultTableModel(new Object[][] {}, new String[] { "Produto", "Quantidade", "Total" });
        table = new JTable(tableModel);
        jSPane.setViewportView(table);

        new VendasDAO().criaTabela();
        atualizarTabela();

        realizaVenda.setBackground(new Color(46, 128, 32));
        realizaVenda.setForeground(new Color(255, 255, 255));
        cancelaVenda.setBackground(new Color(168, 3, 3));
        cancelaVenda.setForeground(new Color(255, 255, 255));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                linhaSelecionada = table.rowAtPoint(e.getPoint());
                if (linhaSelecionada != -1) {
                    inputProduto.setText((String) table.getValueAt(linhaSelecionada, 0));
                    inputQuantidade.setText((String) table.getValueAt(linhaSelecionada, 1));
                    inputTotal.setText((String) table.getValueAt(linhaSelecionada, 2));
                }
            }
        });

        VendasControl control = new VendasControl(vendas, tableModel, table);

        realizaVenda.addActionListener(e -> {
            if (!inputProduto.getText().isEmpty() && !inputQuantidade.getText().isEmpty()
                    && !inputTotal.getText().isEmpty() && !inputCliente.getText().isEmpty()
                    && !inputData.getText().isEmpty()) {

                control.realizarVenda(inputProduto.getText(), inputQuantidade.getText(),
                        inputTotal.getText(), inputCliente.getText(), inputData.getText());

                inputProduto.setText("");
                inputQuantidade.setText("");
                inputTotal.setText("");
                inputCliente.setText("");
                inputData.setText("");
            } else {
                JOptionPane.showMessageDialog(inputPanel,
                        "Preencha todos os campos corretamente para realizar uma venda!", null,
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        cancelaVenda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Deseja cancelar esta venda?", "Cancelar Venda",
                        JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    control.apagar(inputProduto.getText());
                    inputProduto.setText("");
                    inputQuantidade.setText("");
                    inputTotal.setText("");
                    inputCliente.setText("");
                    inputData.setText("");
                }
            }
        });
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodas();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[] { venda.getProduto(), venda.getQuantidade(), venda.getTotal() });
        }
    }
}
