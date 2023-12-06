package sistemasupermercado.Control;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sistemasupermercado.Connection.VendasDAO;
import sistemasupermercado.Model.Venda;

public class VendasControl {
    private List<Venda> vendas;
    private DefaultTableModel tableModel;
    private JTable table;

    public VendasControl(List<Venda> vendas, DefaultTableModel tableModel, JTable table) {
        this.vendas = vendas;
        this.tableModel = tableModel;
        this.table = table;
    }

    public void atualizarTabela() {
        tableModel.setRowCount(0);
        vendas = new VendasDAO().listarTodos();
        for (Venda venda : vendas) {
            tableModel.addRow(new Object[] { venda.getCliente(), venda.getQuantidadeDeProdutos(), venda.getValor(),
                    venda.getData() });
        }
    }

    public void realizarVenda(String cliente, String quantidadeDeProdutos, String valor, String data) {
        try {
            Venda venda = new Venda(cliente.trim().toUpperCase(), valor.trim(), data.trim(), quantidadeDeProdutos.trim());

            vendas.add(venda);
            new VendasDAO().cadastrar(cliente.trim().toUpperCase(), quantidadeDeProdutos.trim(), valor.trim(),
                    data.trim());
            atualizarTabela();
            JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!");
        } catch (Exception err) {
            System.out.println(err.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Verifique se os dados escritos estão corretos e tente novamente!", "ERRO!",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void cancelarVenda(String cliente) {
        new VendasDAO().apagar(cliente);
        atualizarTabela();
        JOptionPane.showMessageDialog(table, "Venda cancelada!", null, JOptionPane.ERROR_MESSAGE);
    }
}
