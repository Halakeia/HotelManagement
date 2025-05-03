package hotel.bao.controllers;

import hotel.bao.dtos.QuartoDTO;
import hotel.bao.service.QuartoService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.data.domain.Pageable;

import java.net.URI;

@RestController
@RequestMapping("/quarto")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;

    @PostMapping
    public ResponseEntity<QuartoDTO>save(@RequestBody QuartoDTO dto){
        dto = quartoService.save(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<QuartoDTO> update (@RequestBody QuartoDTO dto, @PathVariable Long id ){
        dto = quartoService.update(id,dto);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<QuartoDTO> findById(@PathVariable long id){
        QuartoDTO entity = quartoService.findById(id);
        return ResponseEntity.ok().body(entity);
    }
    @GetMapping
    public ResponseEntity<Page<QuartoDTO>> findAll(Pageable pageable) {
        Page<QuartoDTO> list = quartoService.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
