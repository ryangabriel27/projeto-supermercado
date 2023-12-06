package sistemasupermercado.Gerenciamento.Model;

public class Estoque {
    // Atributos
    String nomeDoProduto;
    String preco;
    String quantidade;

    // Construtor
    public Estoque(String nomeDoProduto, String preco, String quantidade) {
        this.nomeDoProduto = nomeDoProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Getters and Setters
    public String getNomeDoProduto() {
        return nomeDoProduto;
    }

    public void setNomeDoProduto(String nomeDoProduto) {
        this.nomeDoProduto = nomeDoProduto;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

}
