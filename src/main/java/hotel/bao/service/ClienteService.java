package hotel.bao.service;

import hotel.bao.dtos.ClienteDTO;
import hotel.bao.entities.Cliente;
import hotel.bao.repository.ClienteRepository;
import hotel.bao.service.exceptions.DatabaseException;
import hotel.bao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Saves a new Cliente entity based on the provided ClienteDTO and returns the saved entity as a DTO.
     *
     * @param clienteDTO the data transfer object containing the information for the cliente to be saved
     * @return the saved Cliente entity as a ClienteDTO
     */
    @Transactional
    public ClienteDTO save(ClienteDTO clienteDTO) {
        Cliente entity = new Cliente();
        copyDtoToEntity(clienteDTO, entity);
        entity = clienteRepository.save(entity);
        return new ClienteDTO(entity);
    }

    /**
     * Updates an existing Cliente entity identified by the given ID with the details provided in the ClienteDTO.
     * Validates unique fields and persists the changes to the database.
     *
     * @param id  the ID of the Cliente entity to be updated
     * @param dto the data transfer object containing the updated information for the Cliente
     * @return a ClienteDTO representing the updated Cliente entity
     * @throws IllegalArgumentException if the provided DTO is null or if unique field validation fails
     * @throws ResourceNotFound         if no Cliente entity is found with the given ID
     */
    @Transactional
    public ClienteDTO update(long id, ClienteDTO dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("DTO não pode ser nulo");
            }
            Cliente entity = clienteRepository.getReferenceById(id);
            validateUniqueFields(dto, id);
            copyDtoToEntity(dto, entity);
            entity = clienteRepository.save(entity);
            return new ClienteDTO(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Cliente não foi encontrado: " + id);
        }
    }

    /**
     * Validates that the email and login fields in the provided ClienteDTO object are unique,
     * excluding a specific client identified by its id. Throws an IllegalArgumentException
     * if a duplicate email or login is found.
     *
     * @param dto the ClienteDTO object containing the email and login to be validated
     * @param id  the ID of the client to be excluded from the validation
     * @throws IllegalArgumentException if the email or login is already in use by another client
     */
    private void validateUniqueFields(ClienteDTO dto, long id) {
        // Validação de campos únicos
        if (clienteRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new IllegalArgumentException("Email já está em uso");
        }
        if (clienteRepository.existsByLoginAndIdNot(dto.getLogin(), id)) {
            throw new IllegalArgumentException("Login já está em uso");
        }
    }

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCelular(dto.getCelular());
        entity.setLogin(dto.getLogin());
        entity.setSenha(dto.getSenha());
    }

    @Transactional(readOnly = true)
    public ClienteDTO findById(long id) {
        var entity = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Cliente não encontrado: " + id));
        return new ClienteDTO(entity);
    }

    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> list = clienteRepository.findAll(pageable);
        return list.map(ClienteDTO::new);
    }

    public void delete(long id) {
        if(!clienteRepository.existsById(id)){
            throw new ResourceNotFound("Cliente não foi encontrado: " + id);
        }
        try{
            clienteRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }
}