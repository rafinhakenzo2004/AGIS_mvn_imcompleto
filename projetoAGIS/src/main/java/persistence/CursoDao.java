package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Curso;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Curso, não interferindo em regras de negócio complexas ou conexão bruta.
 */

public class CursoDao implements ICrud<Curso> {
	
	private GenericDao gDao;
	
	public CursoDao (GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Curso buscar(Curso cu) throws SQLException, ClassNotFoundException {
	    String sql = "SELECT codigo, nome, carga_hr, sigla, nota_enade FROM curso WHERE codigo = ?";
	               
	    try (Connection c = gDao.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {
	        
	        ps.setInt(1, cu.getCodigo()); 
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                preencherCurso(cu, rs);
	            } else {
	                cu = null; 
	            }
	        }
	    }
	    return cu;
	}
	
	@Override
	public List<Curso> listar() throws SQLException, ClassNotFoundException {
		List<Curso> cursos = new ArrayList<>();
		String sql = "SELECT * FROM curso";
	               
		try (Connection c = gDao.getConnection();
		     PreparedStatement ps = c.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
		     
			while (rs.next()) {
				Curso cu = new Curso();
				preencherCurso(cu, rs);
		        cursos.add(cu);
			}
		}
		return cursos;
	}
	
	@Override
	public String inserir(Curso curso) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_insere_curso(?, ?, ?, ?, ?, ?)}";
		
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, curso.getCodigo());
			cs.setString(2, curso.getNome());
			cs.setString(3, curso.getCarga_hr());
			cs.setString(4, curso.getSigla());
			cs.setInt(5, curso.getNota_enade());
			cs.registerOutParameter(6, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(6);
		}
	}
	
	@Override
	public String atualizar(Curso curso) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_atualiza_curso(?, ?, ?, ?, ?, ?)}";
		
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, curso.getCodigo());
			cs.setString(2, curso.getNome());
			cs.setString(3, curso.getCarga_hr());
			cs.setString(4, curso.getSigla());
			cs.setInt(5, curso.getNota_enade());
			cs.registerOutParameter(6, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(6);
		}
	}
	
	@Override
	public String excluir(Curso curso) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_exclui_curso(?, ?)}";
		
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setInt(1, curso.getCodigo());
			cs.registerOutParameter(2, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(2);
		}
	}
	
	private void preencherCurso(Curso cu, ResultSet rs) throws SQLException {
	    cu.setCodigo(rs.getInt("codigo"));
	    cu.setNome(rs.getString("nome"));
	    cu.setCarga_hr(rs.getString("carga_hr"));
	    cu.setSigla(rs.getString("sigla"));
	    cu.setNota_enade(rs.getInt("nota_enade"));
	}
	
}
