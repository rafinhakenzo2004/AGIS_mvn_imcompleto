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
import model.Matricula;

/**
 * SOLID: Single Responsibility Principle (SRP)
 * Esta classe possui a responsabilidade única de gerenciar a persistência 
 * dos dados de Matricula, não interferindo em regras de negócio complexas ou conexão bruta.
 */

/**
 * No excluir:
 * SOLID: Interface Segregation Principle (ISP)
 * Embora implementemos ICrud, a regra de negócio impede a exclusão.
 * Em um design mais segregado, poderíamos ter interfaces separadas para 
 * operações de consulta e operações de modificação.
 */

/**
 * SOLID: Dependency Inversion Principle (DIP)
 * Em vez de instanciar o GenericDao internamente, ele é recebido via construtor.
 * Isso facilita a manutenção e permite que o DAO dependa de uma abstração de conexão.
 */

public class MatriculaDao implements ICrud<Matricula> {

	private GenericDao gDao;

	public MatriculaDao(GenericDao gDao) {
		this.gDao = gDao;
	}
	
	@Override
	public String inserir(Matricula m) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_insere_matricula(?, ?, ?, ?, ?, ?)}";
		
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setString(1, m.getAluno_ra());
			cs.setInt(2, m.getDisciplina_codigo());
			cs.setInt(3, m.getCurso_codigo());
			cs.setInt(4, m.getAno());
			cs.setInt(5, m.getSemestre());
			cs.registerOutParameter(6, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(6);
		}
	}
	
	@Override
	public String excluir(Matricula m) {
		return "Regulamento: Não é permitido remover disciplinas após a matrícula realizada.";
	}

	public List<Matricula> listarMatriculasAluno(String ra) throws SQLException, ClassNotFoundException {
		List<Matricula> lista = new ArrayList<>();
		String sql = "SELECT aluno_ra, disciplina_codigo, curso_codigo, ano, semestre FROM matricula WHERE aluno_ra = ?";

		try (Connection c = gDao.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, ra);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Matricula m = new Matricula();
					m.setAluno_ra(rs.getString("aluno_ra"));
					m.setDisciplina_codigo(rs.getInt("disciplina_codigo"));
					m.setCurso_codigo(rs.getInt("curso_codigo"));
					m.setAno(rs.getInt("ano"));
					m.setSemestre(rs.getInt("semestre"));
					lista.add(m);
				}
			}
		}
		return lista;
	}

	public List<Disciplina> listarDisciplinasDisponiveis(String ra) throws SQLException, ClassNotFoundException {
		List<Disciplina> disponiveis = new ArrayList<>();
		String sql = "{CALL sp_lista_disponiveis_aluno(?)}";

		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setString(1, ra);
			
			try (ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					Disciplina d = new Disciplina();
					d.setCodigo(rs.getInt("codigo"));
					d.setNome(rs.getString("nome"));
					d.setCurso_codigo(rs.getInt("curso_codigo"));
					d.setQtd_horas_total(rs.getInt("qtd_horas_total"));
					d.setHora_inicio(rs.getString("hora_inicio"));
					d.setDia_semana(rs.getString("dia_semana"));
					disponiveis.add(d);
				}
			}
		}
		return disponiveis;
	}

	@Override
	public Matricula buscar(Matricula m) throws SQLException, ClassNotFoundException {
		String sql = "SELECT aluno_ra, disciplina_codigo, curso_codigo, ano, semestre " +
		             "FROM matricula WHERE aluno_ra = ? AND disciplina_codigo = ? AND curso_codigo = ?";
		
		try (Connection c = gDao.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql)) {
			
			ps.setString(1, m.getAluno_ra());
			ps.setInt(2, m.getDisciplina_codigo());
			ps.setInt(3, m.getCurso_codigo());
			
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					preencherMatricula(m, rs);
				} else {
					m = null;
				}
			}
		}
		return m;
	}

	@Override
	public List<Matricula> listar() throws SQLException, ClassNotFoundException {
		List<Matricula> lista = new ArrayList<>();
		String sql = "SELECT aluno_ra, disciplina_codigo, curso_codigo, ano, semestre FROM matricula";
		
		try (Connection c = gDao.getConnection();
			 PreparedStatement ps = c.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Matricula m = new Matricula();
				preencherMatricula(m, rs);
				lista.add(m);
			}
		}
		return lista;
	}

	@Override
	public String atualizar(Matricula m) throws SQLException, ClassNotFoundException {
		String sql = "{CALL sp_atualiza_matricula(?, ?, ?, ?, ?, ?)}";
		
		try (Connection c = gDao.getConnection();
			 CallableStatement cs = c.prepareCall(sql)) {
			
			cs.setString(1, m.getAluno_ra());
			cs.setInt(2, m.getDisciplina_codigo());
			cs.setInt(3, m.getCurso_codigo());
			cs.setInt(4, m.getAno());
			cs.setInt(5, m.getSemestre());
			cs.registerOutParameter(6, Types.VARCHAR);
			
			cs.execute();
			return cs.getString(6);
		}
	}

	private void preencherMatricula(Matricula m, ResultSet rs) throws SQLException {
		m.setAluno_ra(rs.getString("aluno_ra"));
		m.setDisciplina_codigo(rs.getInt("disciplina_codigo"));
		m.setCurso_codigo(rs.getInt("curso_codigo"));
		m.setAno(rs.getInt("ano"));
		m.setSemestre(rs.getInt("semestre"));
	}
}