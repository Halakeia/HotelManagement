package hotel.bao.dtos;
import hotel.bao.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

public class UsuarioDTO {

    private Long id;
    @NotBlank(message = "campo obrigatório")
    private String firstName;
    private String lastName;

    @Email(message = "Favor informar um e-mail válido")
    private String email;

    private Set<RoleDTO> roles = new HashSet<>();

    public UsuarioDTO() {

    }

    public UsuarioDTO(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UsuarioDTO(Usuario Usuario) {
        id = Usuario.getId();
        firstName = Usuario.getFirstName();
        lastName = Usuario.getLastName();
        email = Usuario.getEmail();

        Usuario
                .getRoles()
                .forEach(role -> roles.add(new RoleDTO(role)) );

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}



