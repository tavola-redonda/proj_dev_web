package model;

public class Produto {
	private int id;
	private String nome;
	private String descricao;
	private Double preco;
	private String imagem_url;
	
	
	public Produto() {}
	
	
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
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	public String getImagem_url() {
		return imagem_url;
	}
	public void setImagem_url(String imagem_url) {
		this.imagem_url = imagem_url;
	}
}
