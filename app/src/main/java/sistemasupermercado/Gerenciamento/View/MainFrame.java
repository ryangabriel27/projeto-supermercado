package sistemasupermercado.Gerenciamento.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javafx.stage.WindowEvent;
import sistemasupermercado.Menu;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Sistema supermercado");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        JPanel mainPanel = new JPanel();
        add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JPanel logo = new JPanel();
        JLabel img = new JLabel();
        ImageIcon iconMenu = new ImageIcon(getClass().getResource("../../assets/logo2.png"));
        img.setIcon(iconMenu);
        logo.add(img, FlowLayout.LEFT); // Adicionando uma imagem ao menu do app
        mainPanel.add(logo); // Adicionando o painel logo ao painel Principal

        // ---------------------*
        // Aplicativo principal:
        JTabbedPane abas = new JTabbedPane();
        abas.add("Estoque", new EstoquePanel()); // Adiciona o painel de estoque ao TabbedPane
        abas.add("Clientes", new ClientesPanel()); // Adiciona o painel de cliente ao TabbedPane
        abas.add("Vendas", new VendasPanel()); // Adiciona o painel de vendas ao TabbedPane
        mainPanel.add(abas);

        JButton sair = new JButton("Voltar para o menu");
        mainPanel.add(sair);
        // ---------------------*
        /*
         * Estilização:
         */
        abas.setBackground(new Color(186, 95, 4));
        abas.setForeground(Color.WHITE);
        // ---------------------*
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null, "Ao sair, você será redirecionado ao menu. Deseja realmente sair?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                    new Menu().run();
                }
            } // Questiona o usuário se realmente ele deseja fechar a aplicação

        });

        sair.addActionListener(e ->{
            new Menu().run();
            this.dispose();
        });
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
