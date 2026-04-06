package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Conteudo;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Conteudo, não interferindo em regras de negócio complexas ou conexão bruta.
 */

public class ConteudoDao implements ICrud<Conteudo> {

	private GenericDao gDao;

	public ConteudoDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public String inserir(Conteudo cont) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_insere_conteudo(?, ?, ?, ?)}";
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, cont.getDisciplina_codigo());
			cs.setInt(2, cont.getCurso_codigo());
			cs.setString(3, cont.getDescricao());
			cs.registerOutParameter(4, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(4);
		}
	}

	@Override
	public String atualizar(Conteudo cont) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_atualiza_conteudo(?, ?, ?, ?)}";
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, cont.getDisciplina_codigo());
			cs.setInt(2, cont.getCurso_codigo());
			cs.setString(3, cont.getDescricao()); 
			cs.registerOutParameter(4, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(4);
		}
	}

	@Override
	public String excluir(Conteudo cont) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_exclui_conteudo(?, ?, ?, ?)}";
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, cont.getDisciplina_codigo());
			cs.setInt(2, cont.getCurso_codigo());
			cs.setString(3, cont.getDescricao());
			cs.registerOutParameter(4, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(4);
		}
	}

	@Override
	public Conteudo buscar(Conteudo cont) throws SQLException, ClassNotFoundException {
		String sql = "SELECT disciplina_codigo, curso_codigo, descricao FROM conteudo " +
		             "WHERE disciplina_codigo = ? AND curso_codigo = ? AND descricao = ?";
		try (Connection c = gDao.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setInt(1, cont.getDisciplina_codigo());
			ps.setInt(2, cont.getCurso_codigo());
			ps.setString(3, cont.getDescricao());
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					preencherConteudo(cont, rs);
				} else {
					cont = null;
				}
			}
		}
		return cont;
	}

	@Override
	public List<Conteudo> listar() throws SQLException, ClassNotFoundException {
		List<Conteudo> lista = new ArrayList<>();
		String sql = "SELECT disciplina_codigo, curso_codigo, descricao FROM conteudo";
		try (Connection c = gDao.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Conteudo ct = new Conteudo();
				preencherConteudo(ct, rs);
				lista.add(ct);
			}
		}
		return lista;
	}
	
	public String atualizarComDescricaoAntiga(Conteudo cont, String descAntiga) throws SQLException, ClassNotFoundException {
	    String sql = "{CALL sp_atualiza_conteudo(?, ?, ?, ?, ?)}";
	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.setInt(1, cont.getDisciplina_codigo());
	        cs.setInt(2, cont.getCurso_codigo());
	        cs.setString(3, descAntiga);      
	        cs.setString(4, cont.getDescricao()); 
	        cs.registerOutParameter(5, Types.VARCHAR);
	        
	        cs.execute();
	        return cs.getString(5);
	    }
	}

	public List<Conteudo> listarPorDisciplina(int dCod, int cCod) throws SQLException, ClassNotFoundException {
	    List<Conteudo> lista = new ArrayList<>();
	    String sql = "SELECT disciplina_codigo, curso_codigo, descricao FROM conteudo " +
	                 "WHERE disciplina_codigo = ? AND curso_codigo = ?";
	    
	    try (Connection c = gDao.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {
	        
	        ps.setInt(1, dCod);
	        ps.setInt(2, cCod);
	        
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Conteudo ct = new Conteudo();
	                preencherConteudo(ct, rs);
	                lista.add(ct);
	            }
	        }
	    }
	    return lista;
	}	
	
	private void preencherConteudo(Conteudo cont, ResultSet rs) throws SQLException {
		cont.setDisciplina_codigo(rs.getInt("disciplina_codigo"));
		cont.setCurso_codigo(rs.getInt("curso_codigo"));
		cont.setDescricao(rs.getString("descricao"));
	}
}