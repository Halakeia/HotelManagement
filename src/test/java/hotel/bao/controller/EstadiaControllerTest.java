package hotel.bao.controller;

import hotel.bao.dtos.EstadiaDTO;
import hotel.bao.dtos.UsuarioDTO;
import hotel.bao.dtos.QuartoDTO;
import hotel.bao.entities.Quarto;
import hotel.bao.entities.Usuario;
import hotel.bao.service.EstadiaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EstadiaController.class)
class EstadiaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EstadiaService estadiaService;

    @Test
    @DisplayName("POST /estadia - should create a new estadia")
    void save() throws Exception {
        String json = """
            {
              "cliente": { "id": 1 },
              "quarto": { "id": 2 },
              "dataEntrada": "2025-07-10",
              "dataSaida": "2025-07-15"
            }
            """;

        EstadiaDTO estadiaDTO = new EstadiaDTO();
        estadiaDTO.setId(1);
        estadiaDTO.setCliente(new Usuario());
        estadiaDTO.getCliente().setId(1L);
        estadiaDTO.setQuarto(new Quarto());
        estadiaDTO.getQuarto().setId(2);
        estadiaDTO.setDataEntrada(LocalDate.of(2025, 7, 10));
        estadiaDTO.setDataSaida(LocalDate.of(2025, 7, 15));

        Mockito.when(estadiaService.save(any(EstadiaDTO.class))).thenReturn(estadiaDTO);

        mockMvc.perform(post("/estadia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.quarto.id").value(2))
                .andExpect(jsonPath("$.dataEntrada").value("2025-07-10"))
                .andExpect(jsonPath("$.dataSaida").value("2025-07-15"));
    }

    @Test
    @DisplayName("PUT /estadia/{id} - should update an estadia")
    void update() throws Exception {
        String json = """
            {
              "cliente": { "id": 1 },
              "quarto": { "id": 2 },
              "dataEntrada": "2025-07-11",
              "dataSaida": "2025-07-16"
            }
            """;

        EstadiaDTO estadiaDTO = new EstadiaDTO();
        estadiaDTO.setId(1);
        estadiaDTO.setCliente(new Usuario());
        estadiaDTO.getCliente().setId(1L);
        estadiaDTO.setQuarto(new Quarto());
        estadiaDTO.getQuarto().setId(2);
        estadiaDTO.setDataEntrada(LocalDate.of(2025, 7, 11));
        estadiaDTO.setDataSaida(LocalDate.of(2025, 7, 16));

        Mockito.when(estadiaService.update(eq(1L), any(EstadiaDTO.class))).thenReturn(estadiaDTO);

        mockMvc.perform(put("/estadia/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.quarto.id").value(2))
                .andExpect(jsonPath("$.dataEntrada").value("2025-07-11"))
                .andExpect(jsonPath("$.dataSaida").value("2025-07-16"));
    }

    @Test
    @DisplayName("GET /estadia/{id} - should return an estadia by id")
    void findById() throws Exception {
        EstadiaDTO estadiaDTO = new EstadiaDTO();
        estadiaDTO.setId(1);
        estadiaDTO.setCliente(new Usuario());
        estadiaDTO.getCliente().setId(1L);
        estadiaDTO.setQuarto(new Quarto());
        estadiaDTO.getQuarto().setId(2);
        estadiaDTO.setDataEntrada(LocalDate.of(2025, 7, 10));
        estadiaDTO.setDataSaida(LocalDate.of(2025, 7, 15));

        Mockito.when(estadiaService.findById(1L)).thenReturn(estadiaDTO);

        mockMvc.perform(get("/estadia/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cliente.id").value(1))
                .andExpect(jsonPath("$.quarto.id").value(2))
                .andExpect(jsonPath("$.dataEntrada").value("2025-07-10"))
                .andExpect(jsonPath("$.dataSaida").value("2025-07-15"));
    }

    @Test
    @DisplayName("GET /estadia - should return paged list of estadias")
    void findAll() throws Exception {
        EstadiaDTO estadiaDTO = new EstadiaDTO();
        estadiaDTO.setId(1);
        estadiaDTO.setCliente(new Usuario());
        estadiaDTO.getCliente().setId(1L);
        estadiaDTO.setQuarto(new Quarto());
        estadiaDTO.getQuarto().setId(2);
        estadiaDTO.setDataEntrada(LocalDate.of(2025, 7, 10));
        estadiaDTO.setDataSaida(LocalDate.of(2025, 7, 15));

        Mockito.when(estadiaService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(estadiaDTO)));

        mockMvc.perform(get("/estadia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].cliente.id").value(1))
                .andExpect(jsonPath("$.content[0].quarto.id").value(2))
                .andExpect(jsonPath("$.content[0].dataEntrada").value("2025-07-10"))
                .andExpect(jsonPath("$.content[0].dataSaida").value("2025-07-15"));
    }

    @Test
    @DisplayName("DELETE /estadia/{id} - should delete an estadia")
    void delete() throws Exception {
        Mockito.doNothing().when(estadiaService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/estadia/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
