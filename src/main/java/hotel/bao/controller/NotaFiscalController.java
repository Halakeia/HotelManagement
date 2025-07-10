package hotel.bao.controller;

import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.service.NotaFiscalService;
import hotel.bao.service.exceptions.NotaFiscalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nota-fiscal/cliente")
public class NotaFiscalController {
    
    @Autowired
    private NotaFiscalService notaFiscalService;
    
    @GetMapping("/{clienteId}")
    public ResponseEntity<String> imprimirNotaFiscal(@PathVariable Long clienteId) {
        try {
            String notaFiscalFormatada = notaFiscalService.imprimirNotaFiscal(clienteId);
            return ResponseEntity.ok(notaFiscalFormatada);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/maior-valor/{id}")
    public ResponseEntity<String> buscarEstadiaMaiorValor(@PathVariable Long id) {
        try {
            String result = notaFiscalService.buscarEstadiaMaiorValor(id);
            return ResponseEntity.ok(result);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/menor-valor/{id}")
    public ResponseEntity<String> buscarEstadiaMenorValor(@PathVariable Long id) {
        try {
            String result = notaFiscalService.buscarEstadiaMenorValor(id);
            return ResponseEntity.ok(result);
        } catch (NotaFiscalException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}