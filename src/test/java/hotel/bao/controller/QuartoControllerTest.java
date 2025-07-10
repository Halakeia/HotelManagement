package hotel.bao.controller;

import hotel.bao.dtos.QuartoDTO;
import hotel.bao.service.QuartoService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuartoController.class)
class QuartoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuartoService quartoService;

    @Test
    @DisplayName("POST /quarto - should create a new room")
    void save() throws Exception {
        String json = """
            {
              "nome": "Quarto Luxo",
              "descricao": "Quarto com vista para o mar",
              "preco": 250.0
            }
            """;

        QuartoDTO quartoDTO = new QuartoDTO();
        // Defina campos do quartoDTO se quiser

        Mockito.when(quartoService.save(any(QuartoDTO.class))).thenReturn(quartoDTO);

        mockMvc.perform(post("/quarto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /quarto/{id} - should update a room")
    void update() throws Exception {
        String json = """
            {
              "nome": "Quarto Luxo Atualizado",
              "descricao": "Atualizado",
              "preco": 300.0
            }
            """;

        QuartoDTO quartoDTO = new QuartoDTO();
        Mockito.when(quartoService.update(eq(1L), any(QuartoDTO.class))).thenReturn(quartoDTO);

        mockMvc.perform(put("/quarto/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /quarto/{id} - should return a room by id")
    void findById() throws Exception {
        QuartoDTO quartoDTO = new QuartoDTO();
        // Defina campos se quiser

        Mockito.when(quartoService.findById(1L)).thenReturn(quartoDTO);

        mockMvc.perform(get("/quarto/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /quarto - should return paged list of rooms")
    void findAll() throws Exception {
        QuartoDTO quartoDTO = new QuartoDTO();
        // Defina campos se quiser

        Mockito.when(quartoService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(quartoDTO)));

        mockMvc.perform(get("/quarto"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("DELETE /quarto/{id} - should delete a room")
    void delete() throws Exception {
        Mockito.doNothing().when(quartoService).delete(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/quarto/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
