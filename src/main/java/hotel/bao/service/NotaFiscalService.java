package hotel.bao.service;

import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.dtos.UsuarioDTO;
import hotel.bao.entities.*;
import hotel.bao.service.exceptions.NotaFiscalException;
import hotel.bao.service.exceptions.ResourceNotFound;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class NotaFiscalService {
    
    @Autowired
    private EstadiaService estadiaService;
    
    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public String imprimirNotaFiscal(Long clienteId) {
        // Busca o cliente
        UsuarioDTO cliente = usuarioService.findById(clienteId);
        if (cliente == null) {
            throw new NotaFiscalException("Cliente não encontrado");
        }

        // Busca todas as estadias do cliente
        List<EstadiaDTO> estadias = estadiaService.findByClienteId(clienteId);
        if (estadias.isEmpty()) {
            throw new NotaFiscalException("Não há estadias registradas para este cliente");
        }

        // Gera a nota fiscal
        NotaFiscal notaFiscal = gerarNotaFiscal(cliente, estadias);
        return formatarNota(notaFiscal);
    }

    private NotaFiscal gerarNotaFiscal(UsuarioDTO clienteDTO, List<EstadiaDTO> estadias) {
        NotaFiscal nota = new NotaFiscal();
        nota.setNumero(gerarNumeroNotaFiscal());
        nota.setDataEmissao(LocalDateTime.now());
        
        // Dados do cliente
        Usuario cliente = new Usuario();
        cliente.setName(clienteDTO.getName());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCelular(clienteDTO.getCelular());
        cliente.setLogin(clienteDTO.getLogin());
        nota.setCliente(cliente);
        
        // Processa todas as estadias
        List<ItemNotaFiscal> todosItens = new ArrayList<>();
        for (EstadiaDTO estadiaDTO : estadias) {
            todosItens.addAll(montarItensNota(convertDtoToEntity(estadiaDTO)));
        }
        nota.setItens(todosItens);
        
        // Calcula o valor total de todas as estadias
        nota.setValorTotal(calcularValorTotal(todosItens));
        
        return nota;
    }

    private double calcularValorTotal(List<ItemNotaFiscal> itens) {
        return itens.stream()
            .mapToDouble(ItemNotaFiscal::getValorTotal)
            .sum();
    }

    private String formatarNota(NotaFiscal nota) {
        StringBuilder sb = new StringBuilder();
        sb.append("================================\n");
        sb.append("         NOTA FISCAL            \n");
        sb.append("================================\n");
        sb.append("Número: ").append(nota.getNumero()).append("\n");
        sb.append("Data: ").append(nota.getDataEmissao().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        )).append("\n\n");
        
        // Dados completos do cliente
        sb.append("CLIENTE\n");
        sb.append("Nome: ").append(nota.getCliente().getName()).append("\n");
        sb.append("Email: ").append(nota.getCliente().getEmail()).append("\n");
        sb.append("Celular: ").append(nota.getCliente().getCelular()).append("\n");
        sb.append("Login: ").append(nota.getCliente().getLogin()).append("\n\n");
        
        // Itens (todas as estadias)
        sb.append("ESTADIAS\n");
        for (ItemNotaFiscal item : nota.getItens()) {
            sb.append(item.getDescricao())
              .append(" - ")
              .append(item.getQuantidade())
              .append(" diária(s) x R$ ")
              .append(String.format("%.2f", item.getValorUnitario()))
              .append(" = R$ ")
              .append(String.format("%.2f", item.getValorTotal()))
              .append("\n");
        }
        
        sb.append("\nTOTAL: R$ ")
          .append(String.format("%.2f", nota.getValorTotal()))
          .append("\n");
        sb.append("================================\n");
        
        return sb.toString();
    }


    private Estadia convertDtoToEntity(EstadiaDTO estadiaDto) {
        Estadia estadia = new Estadia();

        Usuario cliente = new Usuario();
        cliente.setName(estadiaDto.getCliente().getName());
        cliente.setEmail(estadiaDto.getCliente().getEmail());
        cliente.setCelular(estadiaDto.getCliente().getCelular());

        Quarto quarto = new Quarto();
        quarto.setPreco(estadiaDto.getQuarto().getPreco());
        quarto.setDescricao(estadiaDto.getQuarto ().getDescricao ());

        estadia.setCliente(cliente);
        estadia.setQuarto(quarto);
        estadia.setDataEntrada(estadiaDto.getDataEntrada());
        estadia.setDataSaida(estadiaDto.getDataSaida());

        return estadia;
    }

    

    private Usuario montarDadosCliente(Usuario cliente) {
        Usuario dadosCliente = new Usuario();
        dadosCliente.setName(cliente.getName());
        dadosCliente.setEmail(cliente.getEmail());
        dadosCliente.setCelular(cliente.getCelular());
        return dadosCliente;
    }

    private String gerarNumeroNotaFiscal() {
        // Implementar lógica de geração do número da nota
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }
    
    private List<ItemNotaFiscal> montarItensNota(Estadia estadia) {
        List<ItemNotaFiscal> itens = new ArrayList<>();
        
        // Adicionar item do quarto
        if (estadia.getQuarto() != null && 
            !StringUtils.isBlank(estadia.getQuarto().getDescricao()) && 
            estadia.getQuarto().getPreco() > 0) {
            
            ItemNotaFiscal item = new ItemNotaFiscal();
            item.setDescricao(estadia.getQuarto().getDescricao());
            item.setValorUnitario(estadia.getQuarto().getPreco());
            
            // Calcular quantidade de diárias
            long dias = ChronoUnit.DAYS.between(
                estadia.getDataEntrada(),
                estadia.getDataSaida()
            );
            item.setQuantidade(dias);
            item.setValorTotal(item.getValorUnitario() * item.getQuantidade());
            
            itens.add(item);
        }
        
        return itens;
    }
    
    private double calcularValorTotal(Estadia estadia) {
        return montarItensNota(estadia).stream()
            .mapToDouble(ItemNotaFiscal::getValorTotal)
            .sum();
    }

    @Transactional(readOnly = true)
    public String buscarEstadiaMaiorValor(Long clienteId) {
        List<EstadiaDTO> estadias = estadiaService.findByClienteId(clienteId);
        if (estadias.isEmpty()) {
            throw new NotaFiscalException("Não há estadias registradas para este cliente");
        }

        EstadiaDTO estadia = estadias.stream()
                .max((e1, e2) -> {
                    double valor1 = calcularValorTotal(convertDtoToEntity(e1));
                    double valor2 = calcularValorTotal(convertDtoToEntity(e2));
                    return Double.compare(valor1, valor2);
                })
                .orElseThrow(() -> new NotaFiscalException("Erro ao calcular estadia de maior valor"));

        return String.format("Estadia maior valor: %s Valor: R$ %.2f",
                estadia.getQuarto().getDescricao(),
                calcularValorTotal(convertDtoToEntity(estadia)));
    }

    @Transactional(readOnly = true)
    public String buscarEstadiaMenorValor(Long clienteId) {
        List<EstadiaDTO> estadias = estadiaService.findByClienteId(clienteId);
        if (estadias.isEmpty()) {
            throw new NotaFiscalException("Não há estadias registradas para este cliente");
        }

        EstadiaDTO estadia = estadias.stream()
                .min((e1, e2) -> {
                    double valor1 = calcularValorTotal(convertDtoToEntity(e1));
                    double valor2 = calcularValorTotal(convertDtoToEntity(e2));
                    return Double.compare(valor1, valor2);
                })
                .orElseThrow(() -> new NotaFiscalException("Erro ao calcular estadia de menor valor"));

        return String.format("Estadia menor valor: %s Valor: R$ %.2f",
                estadia.getQuarto().getDescricao(),
                calcularValorTotal(convertDtoToEntity(estadia)));
    }

}