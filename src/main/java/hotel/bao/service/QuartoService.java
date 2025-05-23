package hotel.bao.service;

import hotel.bao.entities.Quarto;
import hotel.bao.repository.QuartoRepository;
import hotel.bao.dtos.QuartoDTO;
import hotel.bao.service.exceptions.DatabaseException;
import hotel.bao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Service
public class QuartoService {

    @Autowired
    private QuartoRepository quartoRepository;

    // Método para salvar um novo quarto
    @Transactional
    public QuartoDTO save(QuartoDTO quartoDTO) {
       Quarto entity = new Quarto();
       copyDtoToEntity(quartoDTO, entity);
       entity = quartoRepository.save(entity);
       return new QuartoDTO(entity);
    }

    // Método para encontrar todos os quartos, retornando uma lista de DTOs
    @Transactional(readOnly = true)
    public Page<QuartoDTO> findAll(Pageable pageable) {
       Page<Quarto> list = quartoRepository.findAll(pageable);
       return list.map(QuartoDTO::new);
    }

    // Método para encontrar um quarto por ID, retornando um DTO
    @Transactional(readOnly = true)
    public QuartoDTO findById(Long id) {
        Quarto quarto = quartoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Quarto não foi encontrado " + id));
        return new QuartoDTO(quarto);
    }


    // Método de atualização de quarto, usando DTO para entrada e retornando DTO
    @Transactional
    public QuartoDTO update(long id, QuartoDTO dto) {
        try{
            Quarto entity = quartoRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = quartoRepository.save(entity);

            return new QuartoDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFound("Quarto não foi encontrado "+id);

        }

    }

    // Método de exclusão de quarto
    @Transactional
    public void delete(long id) {
       if(!quartoRepository.existsById(id)) {
           throw new ResourceNotFound("Quarto não foi encontrado "+id);
       }
       try{
           quartoRepository.deleteById(id);
       }catch (DataIntegrityViolationException e){
           throw new DatabaseException("Violação de integridade");
       }

    }
    private void copyDtoToEntity(QuartoDTO dto, Quarto entity) {
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setImageUrl(dto.getImageUrl());

    }
}
