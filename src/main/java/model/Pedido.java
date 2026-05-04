package model;

import java.time.LocalDateTime;

public class Pedido {
	private int id;
	private int usuarioId;
	private LocalDateTime dataPedido;
	private String status;
	private Double totalPreco;
	private String endereco;

	public Pedido() {
		super();
	}

	public Pedido(int usuarioId, LocalDateTime dataPedido, String status, Double totalPreco, String endereco) {
		super();
		this.usuarioId = usuarioId;
		this.dataPedido = dataPedido;
		this.status = status;
		this.totalPreco = totalPreco;
		this.endereco = endereco;
	}

	public Pedido(int id, int usuarioId, LocalDateTime dataPedido, String status, Double totalPreco, String endereco) {
		super();
		this.id = id;
		this.usuarioId = usuarioId;
		this.dataPedido = dataPedido;
		this.status = status;
		this.totalPreco = totalPreco;
		this.endereco = endereco;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalPreco() {
		return totalPreco;
	}

	public void setTotalPreco(Double totalPreco) {
		this.totalPreco = totalPreco;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", usuarioId=" + usuarioId + ", dataPedido=" + dataPedido + ", status=" + status
				+ ", totalPreco=" + totalPreco + ", endereco=" + endereco + "]";
	}
}
