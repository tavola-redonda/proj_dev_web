package model;

public class ItemCarrinho {

	private ItemCardapio itemCardapio;
    private int quantidade;

    public ItemCarrinho(ItemCardapio itemCardapio, int quantidade) {
        this.itemCardapio = itemCardapio;
        this.quantidade = quantidade;
    }

    public ItemCardapio getProduto() { return itemCardapio; }
    public void setProduto(ItemCardapio itemCardapio) { this.itemCardapio = itemCardapio; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getSubtotal() {
        return itemCardapio.getPreco() * quantidade;
    }
	
}
