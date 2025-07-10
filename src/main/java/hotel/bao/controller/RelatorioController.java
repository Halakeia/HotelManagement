package hotel.bao.controller;

import hotel.bao.service.RelatorioService;
import hotel.bao.service.exceptions.NotaFiscalException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relatorio/cliente")
public class RelatorioController {
    
    @Autowired
    private RelatorioService notaFiscalService;

    @GetMapping("nota-fiscal/{clienteId}")
    @Operation(
            summary = "Imprimir nota fiscal",
            description = "Gera e retorna a nota fiscal formatada do cliente especificado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Nota fiscal gerada com sucesso",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "400", description = "Erro ao gerar a nota fiscal")
            }
    )
    public ResponseEntity<String> imprimirNotaFiscal(@PathVariable Long clienteId) {
        try {
            String notaFiscalFormatada = notaFiscalService.imprimirNotaFiscal(clienteId);
            return ResponseEntity.ok(notaFiscalFormatada);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/maior-valor/{id}")
    @Operation(
            summary = "Buscar estadia de maior valor",
            description = "Retorna a estadia com o maior valor associada ao cliente com o ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estadia encontrada com sucesso",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "400", description = "Erro ao buscar estadia")
            }
    )
    public ResponseEntity<String> buscarEstadiaMaiorValor(@PathVariable Long id) {
        try {
            String result = notaFiscalService.buscarEstadiaMaiorValor(id);
            return ResponseEntity.ok(result);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/menor-valor/{id}")
    @Operation(
            summary = "Buscar estadia de menor valor",
            description = "Retorna a estadia com o menor valor associada ao cliente com o ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estadia encontrada com sucesso",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "400", description = "Erro ao buscar estadia")
            }
    )
    public ResponseEntity<String> buscarEstadiaMenorValor(@PathVariable Long id) {
        try {
            String result = notaFiscalService.buscarEstadiaMenorValor(id);
            return ResponseEntity.ok(result);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/maior-aluguel/{id}")
    @Operation(
            summary = "Buscar maior valor de aluguel",
            description = "Retorna o maior valor de aluguel pago pelo cliente com o ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Aluguel encontrado com sucesso",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "400", description = "Erro ao buscar aluguel")
            }
    )
    public ResponseEntity<String> buscarMaiorAluguel(@PathVariable Long id){
        try{
            String result = notaFiscalService.buscarAluguelMaiorValor(id);
            return ResponseEntity.ok(result);
        }catch (NotaFiscalException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}