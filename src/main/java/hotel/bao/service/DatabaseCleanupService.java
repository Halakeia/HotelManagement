package hotel.bao.service;

import hotel.bao.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hotel.bao.repository.*;

@Service
public class DatabaseCleanupService {

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorizationService authService;

    @Transactional
    public void limparBancoDeDados() {
        // Verifica se o usuário é admin
        if (!authService.isAuthenticated() ||
                !authService.getAuthenticatedUser().hasRole("ROLE_ADMIN")) {
            throw new SecurityException("Apenas administradores podem limpar o banco de dados");
        }

        // Deleta os dados na ordem correta para evitar violações de chave estrangeira
        estadiaRepository.deleteAll(); // Primeiro as estadias pois dependem de usuários e quartos

        // Remove todos os usuários exceto o admin logado
        Usuario adminLogado = authService.getAuthenticatedUser();
        usuarioRepository.findAll().forEach(usuario -> {
            if (!usuario.getId().equals(adminLogado.getId())) {
                usuarioRepository.delete(usuario);
            }
        });

        quartoRepository.deleteAll(); // Depois os quartos

        // Não deletamos as roles pois são dados básicos do sistema
    }
}