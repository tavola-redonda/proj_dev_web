package model;

public class ItemPedido {
	private int id;
	private int pedidoId;
	private int itemCardapioId;
	private int quantidade;
	private Double precoUnitario;

	public ItemPedido() {
		super();
	}

	public ItemPedido(int pedidoId, int itemCardapioId, int quantidade, Double precoUnitario) {
		super();
		this.pedidoId = pedidoId;
		this.itemCardapioId = itemCardapioId;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
	}

	public ItemPedido(int id, int pedidoId, int itemCardapioId, int quantidade, Double precoUnitario) {
		super();
		this.id = id;
		this.pedidoId = pedidoId;
		this.itemCardapioId = itemCardapioId;
		this.quantidade = quantidade;
		this.precoUnitario = precoUnitario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(int pedidoId) {
		this.pedidoId = pedidoId;
	}

	public int getItemCardapioId() {
		return itemCardapioId;
	}

	public void setItemCardapioId(int itemCardapioId) {
		this.itemCardapioId = itemCardapioId;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Double precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	@Override
	public String toString() {
		return "ItemPedido [id=" + id + ", pedidoId=" + pedidoId + ", itemCardapioId=" + itemCardapioId
				+ ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario + "]";
	}
}
