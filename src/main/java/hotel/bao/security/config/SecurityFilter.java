package hotel.bao.security.config;

import hotel.bao.entities.Usuario;
import hotel.bao.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UsuarioRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        String method = request.getMethod();

        System.out.println("=== DEBUG SECURITY FILTER ===");
        System.out.println("Path: " + requestPath);
        System.out.println("Method: " + method);

        // Lista de caminhos que devem pular completamente a autenticação JWT
        List<String> skipJwtPaths = Arrays.asList(
                "/auth/login",
                "/swagger-ui",
                "/v3/api-docs",
                "/h2-console"
        );

        // Verifica se deve pular o processamento JWT
        boolean skipJwt = skipJwtPaths.stream().anyMatch(path -> requestPath.startsWith(path));

        // GET /quarto também pula JWT (usuários não autenticados podem acessar)
        if (requestPath.equals("/quarto") && method.equals("GET")) {
            skipJwt = true;
        }

        System.out.println("Skip JWT: " + skipJwt);

        // Se não deve pular JWT, tenta extrair e validar o token
        if (!skipJwt) {
            var token = this.recoverToken(request);
            System.out.println("Token presente: " + (token != null));

            if (token != null) {
                try {
                    var login = tokenService.validateToken(token);
                    System.out.println("Login extraído do token: " + login);

                    UserDetails usuario = userRepository.findByLogin(login);
                    System.out.println("Usuário encontrado: " + (usuario != null));

                    if (usuario != null) {
                        System.out.println("Authorities do usuário: " + usuario.getAuthorities());

                        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        System.out.println("Autenticação configurada no SecurityContext");
                        System.out.println("Principal: " + authentication.getPrincipal().getClass().getSimpleName());
                        System.out.println("Authorities: " + authentication.getAuthorities());
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao validar token: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        // Verifica o estado final do SecurityContext
        var finalAuth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("SecurityContext final - Autenticado: " + (finalAuth != null && finalAuth.isAuthenticated()));
        if (finalAuth != null) {
            System.out.println("Final Authorities: " + finalAuth.getAuthorities());
        }
        System.out.println("=== FIM DEBUG ===\n");

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}