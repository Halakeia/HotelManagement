package hotel.bao.controller;

import hotel.bao.assemblers.EstadiaAssembler;
import hotel.bao.dtos.QuartoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.service.EstadiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Estadia", description = "Operações relacionadas às estadias de clientes")
@RestController
@RequestMapping("/estadia")
public class EstadiaController {

    @Autowired
    private EstadiaService estadiaService;

    @Autowired
    private EstadiaAssembler assembler;

    @PostMapping
    @Operation(
            summary = "Criar nova estadia",
            description = "Cria uma nova estadia no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Estadia criada com sucesso",
                            content = @Content(schema = @Schema(implementation = EstadiaDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<EntityModel<EstadiaDTO>> save(@Valid @RequestBody EstadiaDTO dto) {
        dto = estadiaService.save(dto);
        EntityModel<EstadiaDTO> estadiaModel = assembler.toModel(dto);
        return ResponseEntity
                .created(estadiaModel.getRequiredLink("self").toUri())
                .body(estadiaModel);
    }


    @PutMapping(value = "/{id}")
    @Operation(
            summary = "Atualizar estadia",
            description = "Atualiza uma estadia existente com base no ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estadia atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = EstadiaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Estadia não encontrada")
            }
    )
    public ResponseEntity<EntityModel<EstadiaDTO>>update(@Valid @RequestBody EstadiaDTO dto,
                                             @PathVariable Long id) {
        dto = estadiaService.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Buscar estadia por ID",
            description = "Retorna os dados de uma estadia com base no ID informado.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estadia encontrada",
                            content = @Content(schema = @Schema(implementation = EstadiaDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Estadia não encontrada")
            }
    )
    public ResponseEntity<EntityModel<EstadiaDTO>> findById(@PathVariable long id) {
        EstadiaDTO dto = estadiaService.findById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar estadias",
            description = "Retorna uma lista paginada de todas as estadias registradas.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de estadias retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = EstadiaDTO.class)))
            }
    )
    public ResponseEntity<Page<EntityModel<EstadiaDTO>>>findAll(Pageable pageable) {
        Page<EstadiaDTO> list = estadiaService.findAll(pageable);
        Page<EntityModel<EstadiaDTO>> pageModel = list.map(assembler::toModel);
        return ResponseEntity.ok(pageModel);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Deletar estadia",
            description = "Remove uma estadia com base no ID informado.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Estadia deletada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Estadia não encontrada")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable long id) {
        estadiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
