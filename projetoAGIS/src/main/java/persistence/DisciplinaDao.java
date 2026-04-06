package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Disciplina;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Disciplina, não interferindo em regras de negócio complexas ou conexão bruta.
 */

public class DisciplinaDao implements ICrud<Disciplina> {

	private GenericDao gDao;
	
	public DisciplinaDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	@Override
	public Disciplina buscar(Disciplina d) throws SQLException, ClassNotFoundException {
	    String sql = "SELECT codigo, curso_codigo, nome, qtd_horas_total, hora_inicio, " +
	    			"dia_semana, duracao_hora_aula FROM disciplina WHERE codigo = ?";
	               
	    try (Connection c = gDao.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {
	        
	        ps.setInt(1, d.getCodigo()); 
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                preencherDisciplina(d, rs);
	            } else {
	                d = null; 
	            }
	        }
	    }
	    return d;
	}
	
	@Override
	public List<Disciplina> listar() throws SQLException, ClassNotFoundException {
		List<Disciplina> disciplinas = new ArrayList<>();
		String sql = "SELECT codigo, curso_codigo, nome, qtd_horas_total, hora_inicio,  "
				+ "dia_semana, duracao_hora_aula FROM disciplina";
	               
		try (Connection c = gDao.getConnection();
		     PreparedStatement ps = c.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
		     
			while (rs.next()) {
				Disciplina d = new Disciplina();
				preencherDisciplina(d, rs);
		        disciplinas.add(d);
			}
		}
		return disciplinas;
	}
	
	public List<Disciplina> listarPorAluno(String ra) throws SQLException, ClassNotFoundException {
	    List<Disciplina> disciplinas = new ArrayList<>();
	    String sql = "{CALL sp_lista_disciplina_aluno(?)}";

	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.setString(1, ra);
	        
	        try (ResultSet rs = cs.executeQuery()) {
	            while (rs.next()) {
	                Disciplina d = new Disciplina();
	                d.setCodigo(rs.getInt("disciplina_codigo"));
	                d.setNome(rs.getString("disciplina_nome"));
	                d.setQtd_horas_total(rs.getInt("disciplina_qtd_horas_total"));
	                d.setHora_inicio(rs.getString("disciplina_hora_inicio"));
	                d.setDia_semana(rs.getString("disciplina_dia_semana"));
	                d.setDuracao_hora_aula(rs.getInt("disciplina_duracao_hora_aula"));
	              
	                d.setCurso_codigo(rs.getInt("curso_codigo"));
	                
	                disciplinas.add(d);
	            }
	        }
	    }
	    return disciplinas;
	}
	
	@Override
	public String inserir(Disciplina d) throws SQLException, ClassNotFoundException {
		
	    String sql = "{CALL sp_insere_disciplina(?, ?, ?, ?, ?, ?, ?, ?)}";
	    
	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.setInt(1, d.getCodigo());
	        cs.setInt(2, d.getCurso_codigo());
	        cs.setString(3, d.getNome());
	        cs.setInt(4, d.getQtd_horas_total());
	        cs.setString(5, d.getHora_inicio());
	        cs.setString(6, d.getDia_semana());
	        cs.setInt(7, d.getDuracao_hora_aula());
	        
	        cs.registerOutParameter(8, Types.VARCHAR);
	        
	        cs.execute();
	        
	        return cs.getString(8);
	    }
	}
	
	@Override
	public String atualizar(Disciplina d) throws SQLException, ClassNotFoundException {
	    String sql = "{CALL sp_atualiza_disciplina(?, ?, ?, ?, ?, ?, ?, ?)}";
	    
	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.setInt(1, d.getCodigo());
	        cs.setInt(2, d.getCurso_codigo());
	        cs.setString(3, d.getNome());
	        cs.setInt(4, d.getQtd_horas_total());
	        cs.setString(5, d.getHora_inicio());
	        cs.setString(6, d.getDia_semana());
	        cs.setInt(7, d.getDuracao_hora_aula());
	        cs.registerOutParameter(8, Types.VARCHAR);
	        
	        cs.execute();
	        return cs.getString(8);
	    }
	}
	
	@Override
	public String excluir(Disciplina d) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_exclui_disciplina(?, ?, ?)}";
		try (Connection c = gDao.getConnection();
		     CallableStatement cs = c.prepareCall(sql)) {
		    
		    cs.setInt(1, d.getCodigo());
		    cs.setInt(2, d.getCurso_codigo());
		    cs.registerOutParameter(3, Types.VARCHAR); 
		    
		    cs.execute();
		    return cs.getString(3);
		}
	}

	private void preencherDisciplina(Disciplina d, ResultSet rs) throws SQLException {
	    d.setCodigo(rs.getInt("codigo"));
	    d.setCurso_codigo(rs.getInt("curso_codigo"));
	    d.setNome(rs.getString("nome"));
	    d.setQtd_horas_total(rs.getInt("qtd_horas_total"));
	    d.setHora_inicio(rs.getString("hora_inicio"));
	    d.setDia_semana(rs.getString("dia_semana"));
	    d.setDuracao_hora_aula(rs.getInt("duracao_hora_aula"));
	}
}
