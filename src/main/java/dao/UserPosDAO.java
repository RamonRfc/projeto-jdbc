package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.ClassInnerJoinFone;
import model.Telefone;
import model.UserPosJava;

public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {

		connection = SingleConnection.getConnection();
	}

	public void salvar(UserPosJava userposjava) throws SQLException {

		String sql = "insert into userposjava (nome, email) values (?, ?)";
		PreparedStatement insert = connection.prepareStatement(sql);
		insert.setString(1, userposjava.getNome());
		insert.setString(2, userposjava.getEmail());
		insert.execute();
		connection.commit(); // SALVA NO BANCO
	}
	
	public List<ClassInnerJoinFone> listaInnerJoinFone (long idUser){
		
		List<ClassInnerJoinFone> classeInnerJoinFone = new ArrayList<ClassInnerJoinFone>();
		String sql = " select numero,nome, email from telefoneuser as fone\r\n" + 
				"inner join userposjava as posjava on fone.usuariopessoa = posjava.id where posjava.id = 2 ";
		try {
			PreparedStatement selectInnerJoin = connection.prepareStatement(sql);
			ResultSet resultado = selectInnerJoin.executeQuery();

			while (resultado.next()) {
				ClassInnerJoinFone classeInner = new ClassInnerJoinFone();
				classeInner.setNumero(resultado.getString("numero"));
				classeInner.setNome(resultado.getString("nome"));
				classeInner.setEmail(resultado.getString("email"));

				classeInnerJoinFone.add(classeInner);
			}

			return classeInnerJoinFone;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classeInnerJoinFone;
	}
	
	
	public void salvarTelefone(Telefone tel) {
		try {
			String sql = "INSERT INTO telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?)";
			PreparedStatement insertTelefone = connection.prepareStatement(sql);
			//insertTelefone.setLong(1, tel.getId());
			insertTelefone.setString(1, tel.getNumero());
			insertTelefone.setString(2, tel.getTipo());
			insertTelefone.setLong(3, tel.getUsuario());
			insertTelefone.execute();
			connection.commit();
			
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public List<UserPosJava> listar() throws SQLException {

		List<UserPosJava> listaDoBanco = new ArrayList<UserPosJava>();
		String sql = "select * from userposjava";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet resultado = select.executeQuery();

		while (resultado.next()) {
			UserPosJava userposjava = new UserPosJava();
			userposjava.setId(resultado.getLong("id"));
			userposjava.setNome(resultado.getString("nome"));
			userposjava.setEmail(resultado.getString("email"));

			listaDoBanco.add(userposjava);
		}

		return listaDoBanco;

	}

	public UserPosJava buscar(Long id) throws Exception {

		UserPosJava retorno = new UserPosJava();
		String sql = "select * from userposjava where id = " + id;
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet resultado = select.executeQuery();

		while (resultado.next()) {

			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));

		}
		return retorno;
	}

	public void atualizar(UserPosJava userPosJava) {
		try {
			String sql = "update userposjava set nome = ? where id = " + userPosJava.getId();
			PreparedStatement update = connection.prepareStatement(sql);
			update.setString(1, userPosJava.getNome());

			update.execute();
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void deletar(Long id) {

		try {

			String sql = "delete from userposjava where id = " + id;
			PreparedStatement delete = connection.prepareStatement(sql);
			delete.execute();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}
	
	public void deletarFonePorUser(Long idUser) {

		try {
			
			String sqlFone = "delete from telefoneuser where usuariopessoa = " + idUser;
			String sqlUser = "delete from userposjava where id =" + idUser;
			
			PreparedStatement deleteFone = connection.prepareStatement(sqlFone);
			deleteFone.executeUpdate();
			connection.commit();
			PreparedStatement deleteFUser = connection.prepareStatement(sqlUser);
			deleteFUser.executeUpdate();
			connection.commit();

		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

	}

}
