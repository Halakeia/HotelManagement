package hotel.bao.dtos;

import hotel.bao.entities.Cliente;
import hotel.bao.entities.Estadia;
import hotel.bao.entities.Quarto;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EstadiaDTO {
    private int id;
    @NotNull(message = "O cliente é obrigatório")
    private Cliente cliente;

    @NotNull(message = "O quarto é obrigatório")
    private Quarto quarto;

    @NotNull(message = "A data de entrada é obrigatória")
    @FutureOrPresent(message = "A data de entrada deve ser hoje ou uma data futura")
    private LocalDate dataEntrada;

    @NotNull(message = "A data de saída é obrigatória")
    @FutureOrPresent(message = "A data de saída deve ser hoje ou uma data futura")
    private LocalDate dataSaida;

    public EstadiaDTO(int id, Cliente cliente, Quarto quarto, LocalDate dataEntrada, LocalDate dataSaida) {
        this.id = id;
        this.cliente = cliente;
        this.quarto = quarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public EstadiaDTO() {
    }

    public EstadiaDTO(Estadia entity) {
        this.id = entity.getId();
        this.cliente = entity.getCliente();
        this.quarto = entity.getQuarto();
        this.dataEntrada = entity.getDataEntrada();
        this.dataSaida = entity.getDataSaida();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDate dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

}
