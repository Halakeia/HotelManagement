package hotel.bao.service;


import hotel.bao.dtos.RoleDTO;
import hotel.bao.dtos.UsuarioDTO;
import hotel.bao.entities.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Configuration;
import hotel.bao.dtos.UsuarioInsertDTO;
import hotel.bao.entities.Usuario;
import hotel.bao.repository.RoleRepository;
import hotel.bao.repository.UsuarioRepository;
import hotel.bao.service.exceptions.DatabaseException;
import hotel.bao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@Configuration
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findAll(Pageable pageable) {

        Page<Usuario> list = repository.findAll(pageable);
        return list.map(u -> new UsuarioDTO(u));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {

        Optional<Usuario> opt = repository.findById(id);
        Usuario Usuario = opt.orElseThrow(
                () -> new ResourceNotFound("Usuario not found"));
        return new UsuarioDTO(Usuario);
    }

    @Transactional
    public UsuarioDTO insert(UsuarioInsertDTO dto) {

        Usuario entity = new Usuario();
        copyDtoToEntity(dto,entity);
        entity.setPassword(
                passwordEncoder.encode(dto.getPassword()));
        Usuario novo = repository.save(entity);
        return new UsuarioDTO(novo);

    }



    private void copyDtoToEntity(UsuarioDTO dto, Usuario entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO role : dto.getRoles()) {
            Role r =
                    roleRepository.getReferenceById(role.getId());
            entity.getRoles().add(r);

        }


    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {

        try {
            Usuario entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UsuarioDTO(entity);

        }
        catch(EntityNotFoundException e) {
            throw new ResourceNotFound("Usuario not found " + id);
        }
    }


    @Transactional
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFound("Usuario not found " + id);
        }
        try {
            repository.deleteById(id);
        }
        catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }


}




