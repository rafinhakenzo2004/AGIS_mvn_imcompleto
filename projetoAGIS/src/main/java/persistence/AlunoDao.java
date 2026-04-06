package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Aluno, não interferindo em regras de negócio complexas ou conexão bruta.
 */

public class AlunoDao implements ICrud<Aluno> {
	
	private GenericDao gDao;
	
	public AlunoDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	@Override
	public Aluno buscar(Aluno a) throws SQLException, ClassNotFoundException {
	    String sql = "SELECT ra, cpf, nome, nome_social, dt_nasc, telefone, "
	               + "email_pessoal, email_corporativo, dt_con_2grau, "
	               + "instituicao_2grau, pt_vestibular, pos_vestibular, "
	               + "ano_ingresso, sem_ingresso, sem_lim_grad, ano_lim_grad, "
	               + "turno, curso_codigo FROM aluno WHERE ra = ?";
	               
	    try (Connection c = gDao.getConnection();
	         PreparedStatement ps = c.prepareStatement(sql)) {
	        
	        ps.setString(1, a.getRa()); 
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                preencherAluno(a, rs);
	            } else {
	                a = null; 
	            }
	        }
	    }
	    return a;
	}

	@Override
	public List<Aluno> listar() throws SQLException, ClassNotFoundException {
		List<Aluno> alunos = new ArrayList<>();
		String sql = "SELECT ra, cpf, nome, nome_social, dt_nasc, telefone, "
	               + "email_pessoal, email_corporativo, dt_con_2grau, "
	               + "instituicao_2grau, pt_vestibular, pos_vestibular, "
	               + "ano_ingresso, sem_ingresso, sem_lim_grad, ano_lim_grad, "
	               + "turno, curso_codigo FROM aluno";
	               
		try (Connection c = gDao.getConnection();
		     PreparedStatement ps = c.prepareStatement(sql);
		     ResultSet rs = ps.executeQuery()) {
		     
			while (rs.next()) {
				Aluno a = new Aluno();
				preencherAluno(a, rs);
		        alunos.add(a);
			}
		}
		return alunos;
	}

	@Override
	public String inserir(Aluno a) throws SQLException, ClassNotFoundException {
	    String sql = "{CALL sp_insere_aluno(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	    
	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.registerOutParameter(1, Types.VARCHAR);
	        
	        cs.setString(2, a.getCpf());
	        cs.setString(3, a.getNome());
	        cs.setString(4, a.getNome_social());
	        cs.setDate(5, Date.valueOf(a.getDt_nasc())); 
	        cs.setString(6, a.getTelefone());
	        cs.setString(7, a.getEmail_pessoal());
	        cs.setString(8, a.getEmail_corporativo());
	        cs.setDate(9, Date.valueOf(a.getDt_con_2grau()));
	        cs.setString(10, a.getInstituicao_2grau());
	        cs.setInt(11, a.getPt_vestibular());
	        cs.setInt(12, a.getPos_vestibular());
	        cs.setInt(13, a.getAno_ingresso());
	        cs.setInt(14, a.getSem_ingresso());
	        cs.setString(15, a.getTurno());
	        cs.setInt(16, a.getCurso_codigo());
	        
	        cs.execute();
	        
	        String raGerado = cs.getString(1);
	        return "Aluno cadastrado com sucesso! RA: " + raGerado;
	    }
	}
	@Override
	public String atualizar(Aluno a) throws SQLException, ClassNotFoundException {
	    String sql = "{CALL sp_atualiza_aluno(?, ?, ?, ?, ?, ?, ?, ?)}";
	    
	    try (Connection c = gDao.getConnection();
	         CallableStatement cs = c.prepareCall(sql)) {
	        
	        cs.setString(1, a.getRa());
	        cs.setString(2, a.getNome());
	        cs.setString(3, a.getNome_social());
	        cs.setString(4, a.getTelefone());
	        cs.setString(5, a.getEmail_pessoal());
	        cs.setString(6, a.getEmail_corporativo());
	        cs.setString(7, a.getTurno());
	        cs.registerOutParameter(8, Types.VARCHAR);
	        
	        cs.execute();
	        return cs.getString(8);
	    }
	}

	@Override
	public String excluir(Aluno a) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_exclui_aluno(?, ?)}";
		try (Connection c = gDao.getConnection();
		     CallableStatement cs = c.prepareCall(sql)) {
		    
		    cs.setString(1, a.getRa());
		    cs.registerOutParameter(2, Types.VARCHAR); 
		    
		    cs.execute();
		    return cs.getString(2);
		}
	}

	private void preencherAluno(Aluno a, ResultSet rs) throws SQLException {
	    a.setRa(rs.getString("ra"));
	    a.setCpf(rs.getString("cpf"));
	    a.setNome(rs.getString("nome"));
	    a.setNome_social(rs.getString("nome_social"));
	    a.setDt_nasc(rs.getDate("dt_nasc").toLocalDate());
	    a.setTelefone(rs.getString("telefone"));
	    a.setEmail_pessoal(rs.getString("email_pessoal"));
	    a.setEmail_corporativo(rs.getString("email_corporativo"));
	    a.setDt_con_2grau(rs.getDate("dt_con_2grau").toLocalDate());
	    a.setInstituicao_2grau(rs.getString("instituicao_2grau"));
	    a.setPt_vestibular(rs.getInt("pt_vestibular"));
	    a.setPos_vestibular(rs.getInt("pos_vestibular"));
	    a.setAno_ingresso(rs.getInt("ano_ingresso"));
	    a.setSem_ingresso(rs.getInt("sem_ingresso"));
	    a.setSem_lim_grad(rs.getInt("sem_lim_grad"));
	    a.setAno_lim_grad(rs.getInt("ano_lim_grad"));
	    a.setTurno(rs.getString("turno"));
	    a.setCurso_codigo(rs.getInt("curso_codigo"));
	}
}