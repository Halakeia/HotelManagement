package hotel.bao.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import hotel.bao.entities.ItemNotaFiscal;

public class NotaFiscal {
    private String numero;
    private LocalDateTime dataEmissao;
    private Usuario cliente;
    private List<ItemNotaFiscal> itens = new ArrayList<>();
    private double valorTotal;
    
    // Getters e Setters

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public List<ItemNotaFiscal> getItens() {
        return itens;
    }

    public void setItens(List<ItemNotaFiscal> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
