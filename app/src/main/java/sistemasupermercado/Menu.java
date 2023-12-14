package sistemasupermercado;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import sistemasupermercado.Gerenciamento.View.MainFrame;

public class Menu extends JFrame {
    public Menu() {
        super("Mercado Alegria");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        JPanel mainP = new JPanel(); // Criando um painel Principal
        mainP.setLayout(new BoxLayout(mainP, BoxLayout.Y_AXIS)); // Definindo o layout do painel principal
        add(mainP); // Adicionando o painel principal ao frame

        JPanel title = new JPanel(); // painel Title, nele contém o titulo do menu centralizado
        JLabel titulo = new JLabel("Bem-Vindo!!!");
        Font fonteTamanhoNovo = new Font("Sans-Serif", Font.BOLD, 24); // Config da fonte e tamanho da mesma
        titulo.setFont(fonteTamanhoNovo); // Colocando a config no JLabel
        title.add(titulo); // Adicionando o JLabel ao painel title
        mainP.add(title); // Adicionando ao painel Principal

        JPanel logo = new JPanel(); // Painel com a logo, centralizada
        JLabel img = new JLabel();
        ImageIcon iconMenu = new ImageIcon(getClass().getResource("./assets/logoMercado.png"));
        img.setIcon(iconMenu);
        logo.add(img); // Adicionando uma imagem ao menu do app
        mainP.add(logo); // Adicionando o painel logo ao painel Principal

        JPanel buttons = new JPanel(new GridLayout(1, 2, 10, 0)); // Painel buttons com os 2 botões que redirecionam
                                                                  // para o gerenciamento ou para o caixa
        JButton gerenciamento = new JButton("Gerenciamento");
        JButton caixa = new JButton("Caixa");
        buttons.add(gerenciamento);
        buttons.add(caixa);
        mainP.add(buttons); // Adicionando o painel buttons ao painel principal

        /* ===============================
         * Estilização:
         */
        gerenciamento.setBackground(new Color(186, 95, 4));
        gerenciamento.setForeground(Color.WHITE);
        caixa.setBackground(new Color(23, 71, 2));
        caixa.setForeground(Color.WHITE);
        /* =============================== */
        gerenciamento.addActionListener(e -> { // Ao clicar no botão gerenciamento
            new MainFrame().run(); // Executa o mainFrame
            this.dispose(); // Fecha o menu
        });

        caixa.addActionListener(e -> {
            new Caixa().run(); // Executa o caixa
            this.dispose(); // Fecha o menu
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null,
                        "Deseja realmente sair?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                }
            } // Questiona o usuário se realmente ele deseja fechar a aplicação
        });
    }

    public void run() {
        pack();
        setVisible(true);
    }
}
