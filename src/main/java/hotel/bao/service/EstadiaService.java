package hotel.bao.service;

import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.entities.Estadia;
import hotel.bao.repository.EstadiaRepository;
import hotel.bao.service.exceptions.DatabaseException;
import hotel.bao.service.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadiaService {
    @Autowired
    private EstadiaRepository estadiaRepository;
    @Autowired
    private UsuarioService clienteService;
    @Autowired
    private QuartoService quartoService;

    @Transactional(readOnly = true)
    public List<EstadiaDTO> findByClienteId(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }

        validarCliente(clienteId);

        return estadiaRepository.findByClienteId(clienteId)
                .stream()
                .map(EstadiaDTO::new)
                .toList();
    }

    private void validarCliente(long clienteId) {
        try {
            clienteService.findById(clienteId);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Cliente não encontrado: " + clienteId);
        }
    }

    private void validarQuarto(long quartoId) {
        try {
            quartoService.findById(quartoId);
        } catch (ResourceNotFound e) {
            throw new ResourceNotFound("Quarto não encontrado: " + quartoId);
        }
    }

    private void validarDatas(EstadiaDTO dto) {
        if (dto.getDataSaida().isBefore(dto.getDataEntrada()) ||
                dto.getDataSaida().isEqual(dto.getDataEntrada())) {
            throw new ResourceNotFound(
                    "A data de saída deve ser posterior à data de entrada"
            );
        }
    }

    private void validarDisponibilidadeQuarto(EstadiaDTO dto) {
        // Buscar estadias que se sobrepõem ao período solicitado
        boolean quartoIndisponivel = estadiaRepository.existsByQuartoIdAndPeriodoSobreposicao(
                dto.getQuarto().getId(),
                dto.getDataEntrada(),
                dto.getDataSaida()
        );

        if (quartoIndisponivel) {
            throw new ResourceNotFound(
                    "Quarto não está disponível no período solicitado"
            );
        }
    }

    /**
     * Salva uma nova Estadia entity baseado no EstadiaDTO e retorna a EstadiaDTO.
     * @param estadiaDTO
     * @return
     */
    @Transactional
    public EstadiaDTO save(EstadiaDTO estadiaDTO) {
        // Validações
        validarCliente(estadiaDTO.getCliente().getId());
        validarQuarto(estadiaDTO.getQuarto().getId());

        // Define data de saída como um dia após a entrada
        estadiaDTO.setDataSaida(estadiaDTO.getDataEntrada().plusDays(1));

        validarDatas(estadiaDTO);
        validarDisponibilidadeQuarto(estadiaDTO);

        // Salvamento
        Estadia entity = new Estadia();
        copyDtoToEntity(estadiaDTO, entity);
        entity = estadiaRepository.save(entity);
        return new EstadiaDTO(entity);
    }


    @Transactional(readOnly = true)
    public EstadiaDTO findById(long id) {
        var entity = estadiaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Estadia não encontrada: " + id));
        return new EstadiaDTO(entity);
    }
    @Transactional(readOnly = true)
    public Page<EstadiaDTO> findAll(Pageable pageable) {
        Page<Estadia> list = estadiaRepository.findAll(pageable);
        return list.map(EstadiaDTO::new);
    }

    @Transactional
    public void delete(long id) {
        if(!estadiaRepository.existsById(id)){
            throw new ResourceNotFound("Estadia não foi encontrada: " + id);
        }
        try{
            estadiaRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade");
        }
    }
    @Transactional
    public EstadiaDTO update(long id, EstadiaDTO dto) {
        try {
            Estadia entity = estadiaRepository.getReferenceById(id);
            
            // Validações
            validarCliente(dto.getCliente().getId());
            validarQuarto(dto.getQuarto().getId());
            validarDatas(dto);
            
            // Para update, precisamos ignorar a estadia atual na validação
            validarDisponibilidadeQuartoParaUpdate(dto, id);
            
            copyDtoToEntity(dto, entity);
            entity = estadiaRepository.save(entity);
            return new EstadiaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFound("Estadia não foi encontrada: " + id);
        }
    }

    private void copyDtoToEntity(EstadiaDTO dto, Estadia entity) {
        entity.setCliente(dto.getCliente());
        entity.setQuarto(dto.getQuarto());
        entity.setDataEntrada(dto.getDataEntrada());
        entity.setDataSaida(dto.getDataSaida());
    }

    private void validarDisponibilidadeQuartoParaUpdate(EstadiaDTO dto, long estadiaId) {
        boolean quartoIndisponivel = estadiaRepository.existsByQuartoIdAndPeriodoSobreposicaoExcluindoEstadia(
            dto.getQuarto().getId(),
            dto.getDataEntrada(),
            dto.getDataSaida(),
            estadiaId
        );
        
        if (quartoIndisponivel) {
            throw new ResourceNotFound("Quarto não está disponível no período solicitado");
        }
    }
}