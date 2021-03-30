package pos_java_jdbc.pos_java_jdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import dao.UserPosDAO;
import model.ClassInnerJoinFone;
import model.Telefone;
import model.UserPosJava;

public class TesteBancoJdbc {

	@Test
	public void initBanco() throws SQLException {

		UserPosJava userposjava = new UserPosJava();
		UserPosDAO userposdao = new UserPosDAO();

		userposjava.setNome("Lucas");
		userposjava.setEmail("Lucasramon@gmail.com");

		userposdao.salvar(userposjava);

	}

	@Test
	public void initListar() {
		UserPosDAO dao = new UserPosDAO();
		try {
			java.util.List<UserPosJava> list = dao.listar();

			for (UserPosJava userPosJava : list) {
				System.out.println(userPosJava);
				System.out.println("+++++++++++++++++++++++++++++++++++++++");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void initBuscarIndividual() throws Exception {
		UserPosDAO dao = new UserPosDAO();
		UserPosJava userPosJava = dao.buscar(2L);
		System.out.println(userPosJava);
	}

	@Test
	public void initAtualizar() throws Exception {
		UserPosDAO dao = new UserPosDAO();
		UserPosJava objetoBanco = dao.buscar(1L);
		objetoBanco.setNome("Ramon Freitas");

		dao.atualizar(objetoBanco);

	} 


	@Test
	public void initDeletar() {

		try {
			UserPosDAO dao = new UserPosDAO();
			
			dao.deletar(5L);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testeInsertTelefone() {
		try {
			Telefone tel = new Telefone();
			//tel.setId(1L);
			tel.setNumero("98789999");
			tel.setTipo("Celular");
			tel.setUsuario(7L);
			
			UserPosDAO dao = new UserPosDAO();
			dao.salvarTelefone(tel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void TesteCarregaFoneUser() {
		
		UserPosDAO dao = new UserPosDAO();
		List<ClassInnerJoinFone> classInnerJoinFones = dao.listaInnerJoinFone(2L);
		
		for (ClassInnerJoinFone classInnerJoinFone : classInnerJoinFones) {
			System.out.println(classInnerJoinFone);
		}
	}
	
	@Test
	public void TesteDeleteFoneUser() {
		
		UserPosDAO dao = new UserPosDAO();
		dao.deletarFonePorUser(8L);
		
		
	}
}

