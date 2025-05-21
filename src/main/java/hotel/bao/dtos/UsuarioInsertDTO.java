package hotel.bao.dtos;

public class UsuarioInsertDTO extends UsuarioDTO {

    private String password;

    public UsuarioInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}