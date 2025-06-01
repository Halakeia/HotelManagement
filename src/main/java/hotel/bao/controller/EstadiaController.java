package hotel.bao.controller;

import jakarta.validation.Valid;
import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.service.EstadiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/estadia")
public class EstadiaController {

    @Autowired
    private EstadiaService estadiaService;

    @PostMapping
    public ResponseEntity<EstadiaDTO> save(@Valid @RequestBody EstadiaDTO dto) {
        dto = estadiaService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<EstadiaDTO> update(@Valid @RequestBody EstadiaDTO dto,
                                             @PathVariable Long id) {
        dto = estadiaService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EstadiaDTO> findById(@PathVariable long id) {
        EstadiaDTO dto = estadiaService.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    @GetMapping
    public ResponseEntity<Page<EstadiaDTO>> findAll(Pageable pageable) {
        Page<EstadiaDTO> list = estadiaService.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        estadiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
