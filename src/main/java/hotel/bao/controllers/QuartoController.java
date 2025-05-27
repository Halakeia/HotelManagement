package hotel.bao.controllers;

import hotel.bao.assemblers.QuartoAssembler;
import hotel.bao.dtos.QuartoDTO;
import hotel.bao.service.QuartoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.data.domain.Pageable;

import java.net.URI;

@RestController
@RequestMapping("/quarto")
@Tag(name = "quarto", description = "Controller/Resource for room management")
public class QuartoController {
    @Autowired
    private QuartoService quartoService;
    @Autowired
    private QuartoAssembler assembler;

    @PostMapping(produces = "application/json")
    @Operation(
            description = "Create a new room",
            summary = "Create a new room",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad request", responseCode = "400")
            }
    )
    public ResponseEntity<EntityModel<QuartoDTO>> save(@RequestBody QuartoDTO dto) {
        dto = quartoService.save(dto);
        EntityModel<QuartoDTO> quartoModel = assembler.toModel(dto);
        return ResponseEntity
                .created(quartoModel.getRequiredLink("self").toUri())
                .body(quartoModel);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Update a room",
            summary = "Update a room",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<EntityModel<QuartoDTO>> update(@RequestBody QuartoDTO dto, @PathVariable long id) {
        dto = quartoService.update(id, dto);
        return ResponseEntity.ok().body(assembler.toModel(dto));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Operation(
            description = "Get a room by id",
            summary = "Get a room by id",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<EntityModel<QuartoDTO>> findById(@PathVariable long id) {
        QuartoDTO dto = quartoService.findById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping(produces = "application/json")
    @Operation(
            description = "Get all rooms",
            summary = "Get all rooms",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            }
    )
    public ResponseEntity<Page<EntityModel<QuartoDTO>>> findAll(Pageable pageable) {
        Page<QuartoDTO> list = quartoService.findAll(pageable);
        Page<EntityModel<QuartoDTO>> pagedModel = list.map(dto -> assembler.toModel(dto));
        return ResponseEntity.ok().body(pagedModel);
    }
    @DeleteMapping(value="/{id}")
    @Operation(
            description = "Delete a room",
            summary = "Delete a room",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable long id){
        quartoService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
