package dao;

import java.sql.Connection;
import dao.ConnectionFactory;
import java.sql.DriverManager;

public class ProdutoDAO {

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbteste?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";
	
	
	public void testeConexao() {
		try {
			Connection con = ConnectionFactory.getConnection();
			System.out.println(con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
