package sistemasupermercado.Model;
 
public class Venda {
    // Atributos
    String cliente;
    String valor;
    String data;
    String quantidadeDeProdutos;

    // Construtor
    public Venda(String cliente, String valor, String data, String quantidadeDeProdutos) {
        this.cliente = cliente;
        this.valor = valor;
        this.data = data;
        this.quantidadeDeProdutos = quantidadeDeProdutos;
    }

    // Getters and Setters
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getQuantidadeDeProdutos() {
        return quantidadeDeProdutos;
    }

    public void setQuantidadeDeProdutos(String quantidadeDeProdutos) {
        this.quantidadeDeProdutos = quantidadeDeProdutos;
    }

}
