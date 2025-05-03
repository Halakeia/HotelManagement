package hotel.bao.dtos;

public class QuartoDTO {
    private int id;
    private String descricao;
    private float preco;
    private String imageUrl;

    public QuartoDTO() {
    }

    public QuartoDTO(int id, String descricao, float preco, String imageUrl) {
        this.id = id;
        this.descricao = descricao;
        this.preco = preco;
        this.imageUrl = imageUrl;
    }

    public int getId() {
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
