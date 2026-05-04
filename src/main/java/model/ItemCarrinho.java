package model;

import java.util.List;
import java.util.Map;


public class ItemCarrinho {

	private int id;
	private int usuarioId;
	private int itemCardapioId;
	private ItemCardapio itemCardapio;
    private int quantidade;

    public ItemCarrinho() {
        super();
    }

    public ItemCarrinho(ItemCardapio itemCardapio, int quantidade) {
        this.itemCardapio = itemCardapio;
        this.quantidade = quantidade;
        if (itemCardapio != null) {
            this.itemCardapioId = itemCardapio.getId();
        }
    }

    public ItemCarrinho(int usuarioId, int itemCardapioId, int quantidade) {
        this.usuarioId = usuarioId;
        this.itemCardapioId = itemCardapioId;
        this.quantidade = quantidade;
    }


    public ItemCarrinho(int id, int usuarioId, int itemCardapioId, int quantidade) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.itemCardapioId = itemCardapioId;
        this.quantidade = quantidade;
    }

    public ItemCardapio getProduto() { return itemCardapio; }
    public void setProduto(ItemCardapio itemCardapio) { this.itemCardapio = itemCardapio; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

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

    public int getItemCardapioId() {
        return itemCardapioId;
    }

    public void setItemCardapioId(int itemCardapioId) {
        this.itemCardapioId = itemCardapioId;
    }

    public ItemCardapio getItemCardapio() {
        return itemCardapio;
    }

    public void setItemCardapio(ItemCardapio itemCardapio) {
        this.itemCardapio = itemCardapio;
    }

    public double getSubtotal() {
        if (itemCardapio != null) {
            return itemCardapio.getPreco() * quantidade;
        }
        return 0;
    }

    public double getSubtotal(double preco) {
        return preco * quantidade;
    }
	
    public String getSubtotalFormatado() {
        return String.format("R$ %.2f", this.getSubtotal());
    }

    public String getSubtotalFormatado(double preco) {
        return String.format("R$ %.2f", this.getSubtotal(preco));
    }
    
    public static double calcularTotal(List<ItemCarrinho> carrinho) {
        double total = 0;
        if (carrinho != null) {
            for (ItemCarrinho item : carrinho) {
                total += item.getSubtotal(); 
            }
        }
        return total;
    }

    public static double calcularTotal(List<ItemCarrinho> itens, Map<Integer, Double> precos) {
        double total = 0;
        if (itens != null) {
            for (ItemCarrinho item : itens) {
                Double preco = precos.getOrDefault(item.getItemCardapioId(), 0.0);
                total += item.getSubtotal(preco);
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "ItemCarrinho [id=" + id + ", usuarioId=" + usuarioId + ", itemCardapioId=" + itemCardapioId
                + ", quantidade=" + quantidade + "]";
    }
}

