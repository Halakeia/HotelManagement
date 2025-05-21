package hotel.bao.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_quarto")
public class Quarto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String descricao;
    private float preco;
    private String imageUrl;

    public Quarto() {
    }

    public Quarto(int id, String descricao, float preco, String imageUrl) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.imageUrl = imageUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
