package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Pedido;


public class PedidoDAO {

	/**
	 * Criar novo pedido
	 * @param pedido Pedido object with usuarioId, status, totalPreco, endereco
	 * @return true if successful, false otherwise
	 */
	public boolean create(Pedido pedido) {
	    String sql = "INSERT INTO pedidos (usuario_id, data_pedido, status, total_preco, endereco) VALUES (?, NOW(), ?, ?, ?)";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setInt(1, pedido.getUsuarioId());
	        pst.setString(2, pedido.getStatus() != null ? pedido.getStatus() : "PENDING");
	        pst.setDouble(3, pedido.getTotalPreco());
	        pst.setString(4, pedido.getEndereco());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                pedido.setId(rs.getInt(1));
	                System.out.println("✓ Pedido criado com sucesso. ID: " + pedido.getId());
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao criar pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Procurar pedido por ID
	 * @param id Order ID
	 * @return Pedido object or null if not found
	 */
	public Pedido findById(int id) {
	    String sql = "SELECT * FROM pedidos WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            return mapRowToPedido(rs);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao buscar pedido por ID: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Listar pedidos de um usuário
	 * @param usuarioId User ID
	 * @return List of Pedido objects for the user
	 */
	public List<Pedido> findByUsuarioId(int usuarioId) {
	    List<Pedido> pedidos = new ArrayList<>();
	    String sql = "SELECT * FROM pedidos WHERE usuario_id = ? ORDER BY data_pedido DESC";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, usuarioId);
	        ResultSet rs = pst.executeQuery();
	        
	        while (rs.next()) {
	            pedidos.add(mapRowToPedido(rs));
	        }
	        System.out.println("✓ " + pedidos.size() + " pedidos encontrados para usuário: " + usuarioId);
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar pedidos do usuário: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return pedidos;
	}
	
	/**
	 * Retornar todos pedidos
	 * @return List of all Pedido objects
	 */
	public List<Pedido> findAll() {
	    List<Pedido> pedidos = new ArrayList<>();
	    String sql = "SELECT * FROM pedidos ORDER BY data_pedido DESC";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	            pedidos.add(mapRowToPedido(rs));
	        }
	        System.out.println("✓ " + pedidos.size() + " pedidos recuperados");
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar todos os pedidos: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return pedidos;
	}
	
	/**
	 * Atualizar pedido
	 * @param pedido Pedido object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean update(Pedido pedido) {
	    String sql = "UPDATE pedidos SET status = ?, total_preco = ?, endereco = ? WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, pedido.getStatus());
	        pst.setDouble(2, pedido.getTotalPreco());
	        pst.setString(3, pedido.getEndereco());
	        pst.setInt(4, pedido.getId());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Pedido atualizado com sucesso. ID: " + pedido.getId());
	            return true;
	        } else {
	            System.out.println("✗ Nenhum pedido encontrado com ID: " + pedido.getId());
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao atualizar pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Deletar pedido
	 * @param id Order ID
	 * @return true if successful, false otherwise
	 */
	public boolean delete(int id) {
	    String sql = "DELETE FROM pedidos WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Pedido deletado com sucesso. ID: " + id);
	            return true;
	        } else {
	            System.out.println("✗ Nenhum pedido encontrado com ID: " + id);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao deletar pedido: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Método auxiliar para mapear uma linha do ResultSet para um objeto Pedido
	 */
	private Pedido mapRowToPedido(ResultSet rs) throws Exception {
	    Pedido p = new Pedido();
	    p.setId(rs.getInt("id"));
	    p.setUsuarioId(rs.getInt("usuario_id"));
	    
	    Timestamp timestamp = rs.getTimestamp("data_pedido");
	    if (timestamp != null) {
	        p.setDataPedido(timestamp.toLocalDateTime());
	    }
	    
	    p.setStatus(rs.getString("status"));
	    p.setTotalPreco(rs.getDouble("total_preco"));
	    p.setEndereco(rs.getString("endereco"));
	    
	    return p;
	}
}
