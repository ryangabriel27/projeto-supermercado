package sistemasupermercado;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;

import sistemasupermercado.Gerenciamento.Connection.ClientesDAO;
import sistemasupermercado.Gerenciamento.Connection.EstoqueDAO;
import sistemasupermercado.Gerenciamento.Connection.VendasDAO;
import sistemasupermercado.Gerenciamento.Model.Cliente;
import sistemasupermercado.Gerenciamento.Model.Estoque;
import sistemasupermercado.Gerenciamento.View.CadastroPanel;
import sistemasupermercado.Gerenciamento.View.ClientesPanel;
import sistemasupermercado.Gerenciamento.View.FinalizaCompra;

public class Caixa extends JFrame {
    // Atributos
    private JTextField inputCPF, valorFinal, quantidadeDeItens, inputProduto;
    private JButton verificaCPF, cadastrarNovoCliente, fazerCompra, adicionaProduto, clienteVIP;
    private JPanel mainPanel, cpfPanel, buttonPanel, produtoPanel, totalPanel;
    private DefaultTableModel tableModel;
    private JTable table;
    private List<Estoque> produtos;
    private List<Cliente> clientes;
    private List<Estoque> listaDeCompra = new ArrayList<>();
    private JScrollPane jSPane;
    private boolean isClienteVIP;
    private boolean produtoNaoEncontrado = true;
    private int contProduto = 1;
    private int quantidadeTotal = 0;
    private double valorTotal = 0, descontoVip = 0;

    // ===========================================
    // Construtor
    public Caixa() {
        super("Caixa supermercado");
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
        clienteVIP = new JButton("Cliente VIP");
        verificaCPF = new JButton("Verificar CPF");
        cadastrarNovoCliente = new JButton("Cadastrar novo cliente");
        adicionaProduto = new JButton("Adicionar Produto");
        fazerCompra = new JButton("Fazer compra");
        // ============================================

        clienteVIP.setBackground(new Color(65, 166, 18));
        clienteVIP.setForeground(new Color(252, 252, 252));

        // Adicionando o mainPanel ao JFrame
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel);

        JPanel title = new JPanel(new FlowLayout());
        title.add(new JLabel("Caixa"));
        mainPanel.add(title);

        cpfPanel.setLayout(new GridLayout(1, 5, 5, 4));
        cpfPanel.add(new JLabel("Insira o cpf do cliente:"));
        cpfPanel.add(inputCPF);
        cpfPanel.add(verificaCPF);
        cpfPanel.add(cadastrarNovoCliente);
        mainPanel.add(cpfPanel);

        produtoPanel.setLayout(new GridLayout(1, 3, 4, 5));
        produtoPanel.add(new JLabel("Insira o id do produto:"));
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
        totalPanel.add(quantidadeDeItens);
        totalPanel.add(valorFinal);
        valorFinal.setEditable(false);
        quantidadeDeItens.setEditable(false);
        atualizaQuantidadeEValorTotal();
        mainPanel.add(totalPanel);

        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(fazerCompra);
        mainPanel.add(buttonPanel);

        // ====================================
        // Tratamento de eventos:
        verificaCPF.addActionListener(e -> { // Ao clicar no botão verificaCPF
            if (!inputCPF.getText().isEmpty() && inputCPF.getText().matches(("[0-9]+"))) { // Se o inputCPF não estiver
                                                                                           // vazio e não for igual ao
                                                                                           // placeholder
                isClienteVIP = validaCpf(inputCPF.getText()); // Verifica se cpf digitade é de um cliente é VIP ou não
                if (isClienteVIP == true) { // Se o cliente for VIP, é adicionado um botão verde escrito Cliente VIP
                                            // apenas para ter um marcador visual de que o cliente é vip
                    JOptionPane.showMessageDialog(null, "Cliente VIP!"); // Notifica o usuário
                    JOptionPane.showMessageDialog(null,
                            "Cliente VIP recebe um desconto de 20% do valor total da compra!");
                    cpfPanel.add(clienteVIP);
                }
                atualizaQuantidadeEValorTotal(); // Atualiza o valor, pois como o cliente é VIP tem um desconto de 20%
                                                 // do valor final
            }
        });

        adicionaProduto.addActionListener(e -> { // Ao clicar no botão adicionaProduto

            if (!inputProduto.getText().isEmpty() && inputProduto.getText().matches("[0-9]+")) { // Verifica se o
                                                                                                 // inputProduto está
                                                                                                 // vazio e se há apenas
                                                                                                 // números no input
                buscarProduto(Integer.parseInt(inputProduto.getText())); // Procura o id digitado pelo usuário no banco
                                                                         // de dados e caso encontre o adiciona a lista
                inputProduto.setText(""); // Limpa o campo

            } else { // Caso tenha algum erro de escrita
                JOptionPane.showMessageDialog(null, "Preencha os campos corretamente!", "Mercado",
                        JOptionPane.WARNING_MESSAGE);
            }

        });

        cadastrarNovoCliente.addActionListener(e -> { // Ao clicar no botão cadastrar cliente
            cadastraNovoCliente(); // Cria uma janela de cadastro para o cliente
        });

        fazerCompra.addActionListener(new ActionListener() { // Ao clicar no botão fazerCompra

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!listaDeCompra.isEmpty() && valorTotal != 0) { // Verifica se a listaDeCompra está vazia e se o
                                                                   // valor é igual a 0
                    finalizaCompra(); // Se não, abre uma janela com o resumo da compra e registra a venda
                }
            }
        });
        // WindowListener:
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int res = JOptionPane.showConfirmDialog(null,
                        "Ao fechar, você será redirecionado ao menu. Deseja realmente sair?",
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (res == JOptionPane.YES_OPTION) {
                    setDefaultCloseOperation(2);
                    new Menu().run();
                }
            } // Questiona o usuário se realmente ele deseja fechar a aplicação
        });
    }

    // ====================================================
    // Métodos:

    public boolean validaCpf(String cpf) { // Método para verificar se o cliente é VIP
        clientes = new ClientesDAO().listarTodos(); // Preenche a lista com os itens do banco de dados
        for (Cliente cliente : clientes) { // Percorre toda a lista procurando um cpf que se iguale ao digitado pelo
                                           // usuário
            if (cpf.trim().equals(cliente.getCpf())) {
                return true; // Caso tenha um cpf cadastrado no banco de dados igual ao digitado pelo usuário
                             // retorna 'true'
            }
        }
        return false; // Caso não encontre retorna 'false'
    }

    public void buscarProduto(int id) { // Método para procurar um produto no banco de dados e o adicionar a
                                        // listaDeCompras
        contProduto = 1; // Quantidade mínima para adicionar
        produtos = new EstoqueDAO().listarTodos(); // Preenche a lista com os itens cadastrados no estoque
        for (Estoque produto : produtos) { // Percorre toda a lista procurando um id que se iguale ao digitado pelo
                                           // usuário

            if (produto.getId() == id) { // Caso encontre um que se iguale

                produtoNaoEncontrado = false; // A boolean produtoNaoEncontrado se torna false

                int res = JOptionPane.showConfirmDialog(null, "A quantidade do produto é maior que 1?", // Pergunta ao
                                                                                                        // usuário se a
                                                                                                        // quantidade
                                                                                                        // comprada é
                                                                                                        // maior que 1
                        "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

                if (res == JOptionPane.YES_OPTION) { // Caso a quantidade seja maior que 1 então
                    try {
                        contProduto = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade do produto:")); // Pede
                                                                                                                        // para
                                                                                                                        // o
                                                                                                                        // usuário
                                                                                                                        // digitar
                                                                                                                        // a
                                                                                                                        // nova
                                                                                                                        // quantidade
                                                                                                                        // de
                                                                                                                        // produtos
                    } catch (Exception err) {
                        System.out.println(err);
                        contProduto = 1; // Caso o usuário aperte em cancelar o valor é 1
                    }
                }

                if (contProduto <= Integer.parseInt(produto.getQuantidade())) { // Verifica no estoque se há a
                                                                                // quantidade desejada do produto
                    // Caso tenha a quantidade necessária
                    int novaQuantidade = Integer.parseInt(produto.getQuantidade()) - contProduto; // Atualiza a
                                                                                                  // quantidade no
                                                                                                  // produto cadastrado
                                                                                                  // no banco de dados,
                                                                                                  // retirando a
                                                                                                  // quantidade desejada
                                                                                                  // da quantidade
                                                                                                  // disponível
                    new EstoqueDAO().atualizarQuantidade(produto.getId(),
                            String.valueOf(novaQuantidade)); // Atualiza a quantidade no banco de dados

                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida", getTitle(), JOptionPane.ERROR_MESSAGE); // Caso
                                                                                                                       // a
                                                                                                                       // quantidade
                                                                                                                       // seja
                                                                                                                       // inferior
                                                                                                                       // a
                                                                                                                       // desejada,
                                                                                                                       // notifica
                                                                                                                       // o
                                                                                                                       // usuário
                    break;
                }

                tableModel.addRow(new Object[] { // Adiciona uma nova linha na tabela com o nome, quantidade desejada e
                                                 // preço do produto que foi buscado
                        produto.getNomeDoProduto(), contProduto, produto.getPreco()
                });

                Estoque produtoComprado = new Estoque(produto.getNomeDoProduto(), // Esse produto que foi encontrado se
                                                                                  // torna um objeto da Classe Estoque e
                                                                                  // posteriormente é adicionado a List
                                                                                  // listaDeCompras , que preenche a
                                                                                  // tabela.
                        Double.parseDouble(produto.getPreco()),
                        contProduto);
                listaDeCompra.add(produtoComprado); // Adicionando o objeto a lista
            }
        }

        if (produtoNaoEncontrado) { // Caso o id digitado for incorreto, ou não tiver nenhum produto no estoque com
                                    // este id, notifica o usuário
            JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Mercado", JOptionPane.ERROR_MESSAGE);
        }
        atualizaQuantidadeEValorTotal(); // Atualiza a quantidade total e o valor total
    }

    public void atualizaTabela() { // Método para atualizar a tabela
        tableModel.setRowCount(0); // Limpa todas as linhas existentes na tabela
        // Obtém os carros atualizados do banco de dados
        for (Estoque compra : listaDeCompra) {
            // Adiciona os dados de cada produto como uma nova linha na tabela Swing
            tableModel.addRow(
                    new Object[] { compra.getNomeDoProduto(), compra.getQuantidadeCompra(), compra.getPrecoCompra() });
        }
    }

    public void cadastraNovoCliente() { // Método para cadastrar um novo cliente VIP, diretamente do caixa.
        int res = JOptionPane.showConfirmDialog(null, "Iniciar cadastro do cliente", // Questiona o usuário se deseja
                                                                                     // iniciar o cadastro
                "Mercado", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) { // Se sim, então abre o CadastroPanel, uma janela para o cadastro
            new CadastroPanel().run();
        }
    }

    public void atualizaQuantidadeEValorTotal() { // Método para atualizar a quantidade total e o valor total
        isClienteVIP = validaCpf(inputCPF.getText()); // Confirma novamente se o cliente é VIP ou não
        valorTotal = 0; // Define o valorTotal como 0
        for (Estoque compra : listaDeCompra) { // Percorre todos os itens que preenchem a listaDeCompra e vai se
                                               // atribuindo o valor de cada produto multiplicado pela quantidade
                                               // comprada
            double soma = compra.getQuantidadeCompra() * compra.getPrecoCompra();
            valorTotal += soma;
        }

        /* xxxxxx Desconto VIP - 20% xxxxx */
        if (isClienteVIP == true) { // Se o cliente for VIP é aplicado um desconto de 20% no valorfinal
            valorTotal -= (0.2 * valorTotal);
        }
        /* xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx */

        valorFinal.setText("R$ " + String.valueOf(valorTotal)); // É atribuido a caixa de texto o valorTotal da compra

        quantidadeTotal = 0; // O mesmo processo do valorTotal acontece com a quantidade total, mas ao invés
                             // de atribuir o preço, atribui apenas a quantidade
        for (Estoque compra : listaDeCompra) {
            quantidadeTotal += compra.getQuantidadeCompra();
        }
        quantidadeDeItens.setText(String.valueOf(quantidadeTotal));// É atribuido a caixa de texto a quantidadeTotal de
                                                                   // itens da compra
    }

    public void resetaTabela() { // Limpa a tabela, esse método é usado após concluir uma venda
        listaDeCompra.clear(); // É removido todos os itens da listaDeCompra
        atualizaTabela(); // Atualiza a tabela e como não há itens na lista a tabela fica vazia
        atualizaQuantidadeEValorTotal(); // Atualiza a quantidade e valor total e como não há itens, logo ambos são
                                         // iguais a 0
    }

    public void finalizaCompra() { // Método para finalizar a compra
        if (!inputCPF.getText().isEmpty()) { // Verifica se o campo CPF não está vazio
            Object[] opcoes = { "Cartão de crédito/débito", "Dinheiro", "PIX" }; // Array de formasDePagamento
            Object res = JOptionPane.showInputDialog(null, "Escolha uma forma de pagamento", "Selecao de itens", // JOptionPane
                                                                                                                 // com
                                                                                                                 // uma
                                                                                                                 // caixa
                                                                                                                 // de
                                                                                                                 // seleção
                                                                                                                 // para
                                                                                                                 // o
                                                                                                                 // usuário
                                                                                                                 // escolher
                                                                                                                 // a
                                                                                                                 // forma
                                                                                                                 // de
                                                                                                                 // pagamento
                                                                                                                 // da
                                                                                                                 // compra
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, "");

            new FinalizaCompra(listaDeCompra, valorTotal, res, inputCPF.getText(), isClienteVIP).run(); // Mostra uma
                                                                                                        // janela com um
                                                                                                        // resumo da
                                                                                                        // compra,
            // mostrando a forma de pagamento, total da compra e
            // itens comprados

            Date d = new Date();

            new VendasDAO().cadastrar(inputCPF.getText(), valorFinal.getText().replaceAll("R$", "").trim(), // Registra
                                                                                                            // a
                                                                                                            // venda em
                                                                                                            // um
                                                                                                            // banco de
                                                                                                            // dados
                    String.valueOf(d), String.valueOf(quantidadeTotal));
            JOptionPane.showMessageDialog(null, "Venda realizada!", getTitle(), // Notifica o usuário de que a venda foi
                                                                                // realizada!
                    JOptionPane.INFORMATION_MESSAGE);
            inputCPF.setText(""); // Limpa o campo CPF
            resetaTabela(); // Limpa a tabela
        } else {
            JOptionPane.showMessageDialog(null, "Preencha o campo CPF para finalizar a compra", "Mercado Alegria",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void run() { // Método para iniciar a aplicação
        pack();
        setVisible(true);
    }
}
