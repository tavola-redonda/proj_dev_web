package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.User;


public class UserDAO {

	/**
	 * Criar usuário
	 * @param user User object with nome, email, senha_hash, telefone, endereco, is_admin
	 * @return true if successful, false otherwise
	 */
	public boolean create(User user) {
	    String sql = "INSERT INTO usuarios (nome, email, senha_hash, telefone, endereco, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
	        
	        pst.setString(1, user.getNome());
	        pst.setString(2, user.getEmail());
	        pst.setString(3, user.getSenha_hash());
	        pst.setString(4, user.getTeleFone());
	        pst.setString(5, user.getEndereco());
	        pst.setBoolean(6, user.getIs_admin() != null ? user.getIs_admin() : false);
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            ResultSet rs = pst.getGeneratedKeys();
	            if (rs.next()) {
	                user.setId(rs.getInt(1));
	                System.out.println("✓ Usuário criado com sucesso. ID: " + user.getId());
	                return true;
	            }
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao criar usuário: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Procurar usuário por ID
	 * @param id User ID
	 * @return User object or null if not found
	 */
	public User findById(int id) {
	    String sql = "SELECT * FROM usuarios WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            return mapRowToUser(rs);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao buscar usuário por ID: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Procurar usuário por email
	 * @param email User email
	 * @return User object or null if not found
	 */
	public User findByEmail(String email) {
	    String sql = "SELECT * FROM usuarios WHERE email = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, email);
	        ResultSet rs = pst.executeQuery();
	        
	        if (rs.next()) {
	            return mapRowToUser(rs);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao buscar usuário por email: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	
	/**
	 * Retornar todos usuários
	 * @return List of all users
	 */
	public List<User> findAll() {
	    List<User> usuarios = new ArrayList<>();
	    String sql = "SELECT * FROM usuarios";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql);
	         ResultSet rs = pst.executeQuery()) {
	        
	        while (rs.next()) {
	            usuarios.add(mapRowToUser(rs));
	        }
	        System.out.println("✓ " + usuarios.size() + " usuários recuperados do banco");
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao listar usuários: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return usuarios;
	}
	
	/**
	 * Atualizar informações do usuário
	 * @param user User object with updated data
	 * @return true if successful, false otherwise
	 */
	public boolean update(User user) {
	    String sql = "UPDATE usuarios SET nome = ?, email = ?, senha_hash = ?, telefone = ?, endereco = ?, is_admin = ? WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setString(1, user.getNome());
	        pst.setString(2, user.getEmail());
	        pst.setString(3, user.getSenha_hash());
	        pst.setString(4, user.getTeleFone());
	        pst.setString(5, user.getEndereco());
	        pst.setBoolean(6, user.getIs_admin() != null ? user.getIs_admin() : false);
	        pst.setInt(7, user.getId());
	        
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Usuário atualizado com sucesso. ID: " + user.getId());
	            return true;
	        } else {
	            System.out.println("✗ Nenhum usuário encontrado com ID: " + user.getId());
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao atualizar usuário: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Deletar usuário
	 * @param id User ID
	 * @return true if successful, false otherwise
	 */
	public boolean delete(int id) {
	    String sql = "DELETE FROM usuarios WHERE id = ?";
	    
	    try (Connection con = ConnectionFactory.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        
	        pst.setInt(1, id);
	        int rowsAffected = pst.executeUpdate();
	        
	        if (rowsAffected > 0) {
	            System.out.println("✓ Usuário deletado com sucesso. ID: " + id);
	            return true;
	        } else {
	            System.out.println("✗ Nenhum usuário encontrado com ID: " + id);
	        }
	    } catch (Exception e) {
	        System.err.println("✗ Erro ao deletar usuário: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return false;
	}
	
	/**
	 * Validar login usuário
	 * @param email User email
	 * @param senha User password
	 * @return User object if credentials are valid, null otherwise
	 */
	public User validarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha_hash = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, email);
            pst.setString(2, senha); 
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                System.out.println("✓ Login realizado com sucesso para: " + email);
                return mapRowToUser(rs);
            } else {
                System.out.println("✗ Credenciais inválidas para: " + email);
            }
        } catch (Exception e) {
            System.err.println("✗ Erro ao validar login: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
	
	/**
	 * Método auxiliar para mapear uma linha do ResultSet para um objeto User
	 */
	private User mapRowToUser(ResultSet rs) throws Exception {
	    User u = new User();
	    u.setId(rs.getInt("id"));
	    u.setNome(rs.getString("nome"));
	    u.setEmail(rs.getString("email"));
	    u.setTelefone(rs.getString("telefone"));
	    u.setEndereco(rs.getString("endereco"));
	    u.setIs_admin(rs.getBoolean("is_admin"));
	    u.setSenha_hash(rs.getString("senha_hash"));
	    return u;
	}
}
