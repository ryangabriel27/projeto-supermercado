package sistemasupermercado.Connection;

import java.util.List;

import sistemasupermercado.Model.Estoque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EstoqueDAO {
    // Códigos para o banco de dados
    // codigos para o banco de dados
    // atributo
    private Connection connection;
    private List<Estoque> produtos;

    // construtor
    public EstoqueDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    // métodos
    // criar Tabela
    public void criaTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS estoque_mercado (nome VARCHAR(255) PRIMARY KEY, preco VARCHAR(255), quantidade VARCHAR(4))";
        try (Statement stmt = this.connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabela criada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar a tabela: " + e.getMessage(), e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    // Listar todos os valores cadastrados
    public List<Estoque> listarTodos() {
        PreparedStatement stmt = null;
        // Declaração do objeto PreparedStatement para executar a consulta
        ResultSet rs = null;
        // Declaração do objeto ResultSet para armazenar os resultados da consulta
        produtos = new ArrayList<>();
        // Cria uma lista para armazenar os carros recuperados do banco de dados
        try {
            stmt = connection.prepareStatement("SELECT * FROM estoque_mercado");
            // Prepara a consulta SQL para selecionar todos os registros da tabela
            rs = stmt.executeQuery();
            // Executa a consulta e armazena os resultados no ResultSet
            while (rs.next()) {
                // Para cada registro no ResultSet, cria um objeto Carros com os valores do
                // registro

                Estoque produto = new Estoque(
                        rs.getString("nome"),
                        rs.getString("preco"),
                        rs.getString("quantidade"));
                produtos.add(produto); // Adiciona o objeto Clientes à lista de carros
            }
        } catch (SQLException ex) {
            System.out.println(ex); // Em caso de erro durante a consulta, imprime o erro
        } finally {
            ConnectionFactory.closeConnection(connection, stmt, rs);

            // Fecha a conexão, o PreparedStatement e o ResultSet
        }
        return produtos; // Retorna a lista de clientes recuperados do banco de dados
    }

    public void apagarTabela() {
        String sql = "DROP TABLE estoque_mercado";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela apagada com sucesso.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao apagar tabela.", e);
        } finally {
            ConnectionFactory.closeConnection(this.connection);
        }
    }

    public void cadastrar(String nome, String preco, String quantidade) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para cadastrar na tabela
        String sql = "INSERT INTO estoque_mercado(nome, preco, quantidade) VALUES (?, ?, ?)";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome.toUpperCase().trim());
            stmt.setString(2, preco.toUpperCase().trim());
            stmt.setString(3, quantidade.trim());
            stmt.executeUpdate();
            System.out.println("Dados inseridos com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Atualizar dados no banco
    public void atualizar(String nome, String preco, String quantidade) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para atualizar dados pela placa
        String sql = "UPDATE estoque_mercado SET preco = ?, quantidade = ? WHERE nome = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, preco.toUpperCase().trim());
            stmt.setString(2, quantidade.trim());
            // placa é chave primaria não pode ser alterada.
            stmt.setString(3, nome);
            stmt.executeUpdate();
            System.out.println("Dados atualizados com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }

    // Apagar dados do banco
    public void apagar(String nome) {
        PreparedStatement stmt = null;
        // Define a instrução SQL parametrizada para apagar dados pela placa
        String sql = "DELETE FROM estoque_mercado WHERE nome = ?";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.executeUpdate(); // Executa a instrução SQL
            System.out.println("Dado apagado com sucesso");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar dados no banco de dados.", e);
        } finally {
            ConnectionFactory.closeConnection(connection, stmt);
        }
    }
}
