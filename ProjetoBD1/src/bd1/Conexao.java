package bd1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Exemplo simples para conectar banco de dados Oracle usando o Oracle JDBC
 * driver thin Certifique-se de que você tem o Oracle JDBC driver thin em seu
 * classpath antes de executar este exemplo
 * 
 * @Author Ronycley Gonçalves Agra
 * @since 13/11/2013
 */
public class Conexao {

	private String driver = null;
	private String driverUrl = null;
	private String servidor = null;
	private String database = null;
	private String porta = null;
	private String usuario = null;
	private String senha = null;
	private Connection conexao = null;
	private String urlConexao = null;
	private Statement smnt = null;
	private ResultSet rs = null;
	
	public Conexao(){
		this.driver = "oracle.jdbc.OracleDriver";
		this.driverUrl = "jdbc:oracle:thin:@";
		this.servidor = "localhost";
		this.database = "XE";
		this.usuario = "projetoBD";
		this.senha = "projetoBD";
		this.porta = "1521";
		this.urlConexao = this.driverUrl+this.servidor+":"+this.porta+":"+this.database;
		
	}
	
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriverUrl() {
		return driverUrl;
	}

	public void setDriverUrl(String driverUrl) {
		this.driverUrl = driverUrl;
	}

	public String getServidor() {
		return servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

	public String getUrlConexao() {
		return urlConexao;
	}

	public void setUrlConexao(String urlConexao) {
		this.urlConexao = urlConexao;
	}

	public Connection conecta(){
		Connection conn = null;
		//Reconhece o driver
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			System.out
					.println("Driver para ORACLE não encontrado!\nErro interno: ");
			e.printStackTrace();
			return null;
		}

		//Estabele a conexão
		try {
			this.setConexao(DriverManager.getConnection(this.getUrlConexao(),this.getUsuario(),this.getSenha()));
			conn = this.getConexao();
		} catch (SQLException e) {
			System.out
					.println("Erro ao obter conexão ao banco de dados XE\nErro interno: ");
			e.printStackTrace();
		}
		
		System.out.println("CONEXÃO ESTABELECIDA...");
		return conn;
	}
	
	public boolean desconecta(){
		//Finaliza a conexão
		try {
			this.getConexao().close();
			this.setConexao(null);
		} catch (SQLException e) {
			System.out.println("Ocorreu erro ao terminar a conexão ao banco de dados!\nErro interno: ");
			e.printStackTrace();
		}
		System.out.println("CONEXÃO ENCERRADA...");
		return true;
	}	
	
	public boolean executa(String sql){
		//Cria o objeto com a instrução
		try {
			smnt = this.getConexao().createStatement();
		} catch (SQLException e) {
			System.out.println("Erro ao criar instrução SQL\nErro interno: ");
			e.printStackTrace();
		}

		try {
			rs = smnt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Nome "+rs.getString("id")+": "+rs.getString("nome"));
			}
		} catch (SQLException e) {
			System.out.println("Ocorreu erro ao executar sql!\nErro interno: ");
			e.printStackTrace();
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		Conexao con = new Conexao();

		con.conecta();
		con.executa("Select * from teste");
		con.desconecta();
		
	}
}