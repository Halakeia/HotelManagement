package hotel.bao.controller;

import hotel.bao.dtos.UsuarioDTO;
import hotel.bao.dtos.UsuarioInsertDTO;
import hotel.bao.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService userService;

    @Test
    @DisplayName("GET /usuario - should return paged list of users")
    @WithMockUser(roles = {"ADMIN", "CLIENT"})
    void testFindAll() throws Exception {
        var userDto = new UsuarioDTO();
        // configure userDto fields se precisar

        Mockito.when(userService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(userDto)));

        mockMvc.perform(get("/usuario")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)));
    }

    @Test
    @DisplayName("GET /usuario/{id} - should return user by id")
    @WithMockUser(roles = {"ADMIN", "CLIENT"})
    void testFindById() throws Exception {
        var userDto = new UsuarioDTO();
        // configure userDto fields se precisar

        Mockito.when(userService.findById(1L)).thenReturn(userDto);

        mockMvc.perform(get("/usuario/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()); // ou outro campo do DTO
    }

    @Test
    @DisplayName("POST /usuario - admin can create user")
    @WithMockUser(roles = {"ADMIN"})
    void testInsert() throws Exception {
        String json = """
            {
              "login": "teste",
              "password": "senha123",
              "roles": [{"authority":"ROLE_CLIENT"}]
            }
            """;

        var userDto = new UsuarioDTO();
        // configure userDto se quiser

        Mockito.when(userService.insert(any())).thenReturn(userDto);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT /usuario/{id} - admin or client can update user")
    @WithMockUser(roles = {"CLIENT"})
    void testUpdate() throws Exception {
        String json = """
            {
              "login": "testeAtualizado",
              "password": "novaSenha",
              "roles": [{"authority":"ROLE_CLIENT"}]
            }
            """;

        var userDto = new UsuarioDTO();
        Mockito.when(userService.update(eq(1L), any())).thenReturn(userDto);

        mockMvc.perform(put("/usuario/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /usuario/{id} - only admin can delete user")
    @WithMockUser(roles = {"ADMIN"})
    void testDelete() throws Exception {
        Mockito.doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/usuario/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
