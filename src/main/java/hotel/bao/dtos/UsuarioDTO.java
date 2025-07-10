package hotel.bao.dtos;
import hotel.bao.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

public class UsuarioDTO extends RepresentationModel {

    private Long id;
    @NotBlank(message = "campo nome obrigatório")
    private String name;
    @Email(message = "Favor informar um e-mail válido")
    private String email;
    @NotBlank(message = "campo login obrigatório")
    private String login;
    @NotBlank(message = "campo celular obrigatório")
    private String celular;



    private Set<RoleDTO> roles = new HashSet<>();

    public UsuarioDTO() {

    }

    public UsuarioDTO(Long id, String name, String lastName, String email, String celular, String Login) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.celular = celular;
        this.login= login;
    }

    public UsuarioDTO(Usuario Usuario) {
        id = Usuario.getId();
        name = Usuario.getName();
        email = Usuario.getEmail();
        celular = Usuario.getCelular();
        login = Usuario.getLogin();

        Usuario
                .getRoles()
                .forEach(role -> roles.add(new RoleDTO(role)) );

    }
    public String getCelular(){
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", celular='" + celular + '\'' +
                ", roles=" + roles +
                '}';
    }
}



