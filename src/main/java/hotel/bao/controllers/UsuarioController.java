package hotel.bao.controllers;

import hotel.bao.assemblers.UsuarioAssembler;
import hotel.bao.dtos.UsuarioDTO;
import hotel.bao.dtos.UsuarioInsertDTO;
import hotel.bao.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuario")
@Tag(name="usuario", description = "Controller/Resource for user")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;
    @Autowired
    private UsuarioAssembler assembler;


    @GetMapping(produces = "application/json")
    @Operation(
            description = "Get all users",
            summary = "Get all users",
            responses = {
                    @ApiResponse(description = "ok", responseCode = "200")
        }
)
public ResponseEntity<Page<EntityModel<UsuarioDTO>>> findAll(Pageable pageable) {
    Page<UsuarioDTO> list = userService.findAll(pageable);
    Page<EntityModel<UsuarioDTO>> pagedModel = list.map(dto -> assembler.toModel(dto));
    return ResponseEntity.ok().body(pagedModel);
}

    @GetMapping(value = "/{id}", produces="application/json")
    @Operation(
            description = "Get a user",
            summary = "Get a user",
            responses = {
                    @ApiResponse(description = "ok", responseCode = "200"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<EntityModel<UsuarioDTO>> findById(@PathVariable Long id) {
        UsuarioDTO dto = userService.findById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }


    @PostMapping(produces = "application/json")
    public ResponseEntity<EntityModel<UsuarioDTO>> insert(@Valid @RequestBody UsuarioInsertDTO dto) {
        UsuarioDTO user = userService.insert(dto);
        EntityModel<UsuarioDTO> userModel = assembler.toModel(user);
        return ResponseEntity
            .created(userModel.getRequiredLink("self").toUri())
            .body(userModel);
}

    @PutMapping(value = "/{id}", produces="application/json")
    @Operation(
            description = "Update a user",
            summary = "Update a user",
            responses = {
                    @ApiResponse(description = "ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbbiden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<EntityModel<UsuarioDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioDTO dto) {
        dto = userService.update(id, dto);
        return ResponseEntity.ok().body(assembler.toModel(dto));
    }


    @DeleteMapping(value = "/{id}")
    @Operation(
            description = "Delete a user",
            summary = "Delete a user",
            responses = {
                    @ApiResponse(description = "ok", responseCode = "200"),
                    @ApiResponse(description = "Bad request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Forbbiden", responseCode = "403"),
                    @ApiResponse(description = "Not found", responseCode = "404")
            }
    )
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }






}