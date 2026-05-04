package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {

	
	public User validarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha_hash = ?";
        
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, email);
            pst.setString(2, senha); 
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setTelefone(rs.getString("telefone"));
                u.setEndereco(rs.getString("endereco"));
                u.setIs_admin(rs.getBoolean("is_admin"));

                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
}
