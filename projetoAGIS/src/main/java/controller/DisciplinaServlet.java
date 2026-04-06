package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Curso;
import model.Disciplina;
import persistence.CursoDao;
import persistence.DisciplinaDao;
import persistence.GenericDao;

@WebServlet("/disciplina")
public class DisciplinaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DisciplinaServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String cod = request.getParameter("cod");
		String cCod = request.getParameter("cCod");

		Disciplina d = new Disciplina();
		String erro = "";
		List<Disciplina> disciplinas = new ArrayList<>();

		try {
			GenericDao gDao = new GenericDao();
			DisciplinaDao dDao = new DisciplinaDao(gDao);
			
			CursoDao cDao = new CursoDao(gDao);
			List<Curso> listaCursos = cDao.listar();
			request.setAttribute("cursos", listaCursos);

			if (acao != null && acao.equalsIgnoreCase("excluir")) {
				d.setCodigo(Integer.parseInt(cod));
				d.setCurso_codigo(Integer.parseInt(cCod));
				dDao.excluir(d);
				disciplinas = dDao.listar();
				d = null;
			} else {
				disciplinas = dDao.listar();
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("disciplina", d);
			request.setAttribute("disciplinas", disciplinas);
			request.getRequestDispatcher("WEB-INF/view/disciplina.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String saida = "";
		String erro = "";
		String cmd = request.getParameter("botao");
		
		Disciplina d = new Disciplina();
		List<Disciplina> disciplinas = new ArrayList<>();

		try {
			GenericDao gDao = new GenericDao();
			DisciplinaDao dDao = new DisciplinaDao(gDao);
			
			CursoDao cDao = new CursoDao(gDao);
			List<Curso> listaCursos = cDao.listar();
			request.setAttribute("cursos", listaCursos);

			String cod = request.getParameter("codigo");
			String cCod = request.getParameter("curso_codigo");
			String nome = request.getParameter("nome");
			String horas = request.getParameter("horas");
			String inicio = request.getParameter("horarioInicio");
			String dia = request.getParameter("dia");

			if (!cmd.equalsIgnoreCase("Listar")) {
				d.setCodigo(Integer.parseInt(cod));
				d.setCurso_codigo(Integer.parseInt(cCod));
			}

			if (cmd.equalsIgnoreCase("Inserir") || cmd.equalsIgnoreCase("Atualizar")) {
				d.setNome(nome);
				d.setQtd_horas_total(Integer.parseInt(horas));
				d.setHora_inicio(inicio);
				d.setDia_semana(dia);
			}

			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = dDao.inserir(d);
			} else if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = dDao.atualizar(d);
			} else if (cmd.equalsIgnoreCase("Excluir")) {
				saida = dDao.excluir(d);
			} else if (cmd.equalsIgnoreCase("Buscar")) {
				d = dDao.buscar(d);
			} else if (cmd.equalsIgnoreCase("Listar")) {
				disciplinas = dDao.listar();
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			saida = "";
			erro = "Erro: Verifique os campos preenchidos. " + e.getMessage();
		} finally {
			if (!cmd.equalsIgnoreCase("Buscar")) {
				d = null;
			}
			if (disciplinas.isEmpty() && !cmd.equalsIgnoreCase("Listar")) {
				disciplinas = null; 
			}
			
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("disciplina", d);
			request.setAttribute("disciplinas", disciplinas);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/disciplina.jsp");
			dispatcher.forward(request, response);
		}
	}
}