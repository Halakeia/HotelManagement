package hotel.bao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import hotel.bao.service.DatabaseCleanupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/admin/database")
@Tag(name = "admin", description = "Controller para operações administrativas")
public class DatabaseCleanupController {

    @Autowired
    private DatabaseCleanupService databaseCleanupService;

    @DeleteMapping("/cleanup")
    @Operation(
        description = "Limpa todos os dados do banco de dados",
        summary = "Limpa o banco de dados",
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204"),
            @ApiResponse(description = "Unauthorized", responseCode = "401"),
            @ApiResponse(description = "Forbidden", responseCode = "403")
        }
    )
    public ResponseEntity<Void> limparBancoDeDados() {
        databaseCleanupService.limparBancoDeDados();
        return ResponseEntity.noContent().build();
    }
}
