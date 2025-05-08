package hotel.bao.controllers;

import hotel.bao.controllers.exception.BusinessException;
import hotel.bao.dtos.ClienteDTO;
import hotel.bao.service.ClienteService;
import hotel.bao.service.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    /**
     * Deletes a client by their ID.
     *
     * @param id the unique identifier of the client to be deleted
     * @return ResponseEntity with no content on successful deletion
     */

    /**
     * Saves a new client based on the provided ClienteDTO and returns the created client with location URI.
     *
     * @param dto the data transfer object containing the client information to be saved
     * @return a ResponseEntity containing the saved ClienteDTO object and the location URI
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> save(@RequestBody ClienteDTO dto){
        dto = clienteService.save(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * Updates an existing client identified by the provided ID with the details in the given ClienteDTO.
     *
     * @param dto the data transfer object containing the updated client information
     * @param id  the unique identifier of the client to be updated
     * @return a ResponseEntity containing the updated ClienteDTO object
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> update (@RequestBody ClienteDTO dto, @PathVariable Long id ){
        dto = clienteService.update(id,dto);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Retrieves a client by its unique identifier.
     *
     * @param id the unique identifier of the client to be retrieved
     * @return a ResponseEntity containing the ClienteDTO object corresponding to the given id
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable long id){
        ClienteDTO dto = clienteService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Retrieves all clients with pagination support.
     *
     * @param pageable pagination information
     * @return ResponseEntity containing a Page of ClienteDTO objects
     */
    @GetMapping
    public ResponseEntity<Page<ClienteDTO>> findAll(Pageable pageable) {
        Page<ClienteDTO> list = clienteService.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Deletes a client based on the provided ID.
     *
     * @param id the unique identifier of the client to be deleted
     * @return ResponseEntity with no content if the deletion is successful
     * @throws BusinessException if the client cannot be deleted due to associated records in the system
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DatabaseException e) {
            throw new BusinessException("Não é possível excluir este cliente pois ele possui estadias registradas no sistema.");
        }
    }
}