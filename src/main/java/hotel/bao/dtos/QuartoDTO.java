package hotel.bao.dtos;

import hotel.bao.entities.Quarto;

public class QuartoDTO {
    private long id;
    private String descricao;
    private float preco;
    private String imageUrl;

    public QuartoDTO() {
    }

    public QuartoDTO(long id, String descricao, float preco, String imageUrl) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.imageUrl = imageUrl;
    }

    public QuartoDTO(Quarto entity) {
        this.id = entity.getId();
        this.descricao = entity.getDescricao();
        this.preco = entity.getPreco();
        this.imageUrl = entity.getImageUrl();
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
