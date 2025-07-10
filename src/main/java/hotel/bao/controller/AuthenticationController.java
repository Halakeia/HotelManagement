package hotel.bao.controller;

import hotel.bao.dtos.AuthenticationDTO;
import hotel.bao.dtos.LoginResponseDTO;
import hotel.bao.dtos.RegisterDTO;
import hotel.bao.entities.Usuario;
import hotel.bao.repository.UsuarioRepository;
import hotel.bao.security.config.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    @Operation(
            summary = "Realiza o login do usuário",
            description = "Autentica o usuário e retorna um token JWT em caso de sucesso.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login bem-sucedido com token JWT",
                            content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
                    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
            }
    )
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    @Operation(
            summary = "Registra um novo usuário",
            description = "Cria um novo usuário no sistema com login, senha e role definidos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Usuário com login já existente")
            }
    )
    public ResponseEntity register(@RequestBody @Valid RegisterDTO dto) {
        if(this.userRepository.findByLogin(dto.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        Usuario user = new Usuario(dto.login(), encryptedPassword, dto.roles());

        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
