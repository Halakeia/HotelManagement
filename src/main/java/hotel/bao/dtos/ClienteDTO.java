package hotel.bao.dtos;


public class ClienteDTO {


    private int id;

    private String nome;


    private String email;


    private String login;


    private String senha;

    private String celular;

    public ClienteDTO() {
    }

    public ClienteDTO(int id, String nome, String email, String login, String senha, String celular) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.celular = celular;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
}
