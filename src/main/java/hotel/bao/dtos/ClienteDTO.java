package hotel.bao.dtos;


import hotel.bao.entities.Cliente;

public class ClienteDTO {


    private long id;

    private String nome;


    private String email;


    private String login;


    private String senha;

    private String celular;

    public ClienteDTO() {
    }

    public ClienteDTO(long id, String nome, String email, String login, String senha, String celular) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.celular = celular;
    }

    public ClienteDTO(Cliente entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.email = entity.getEmail();
        this.login = entity.getLogin();
        this.senha = entity.getSenha();
        this.celular = entity.getCelular();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
