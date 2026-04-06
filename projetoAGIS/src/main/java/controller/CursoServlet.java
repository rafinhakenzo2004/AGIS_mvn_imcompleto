package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Curso;
import persistence.CursoDao;
import persistence.GenericDao;

@WebServlet("/curso")
public class CursoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		listar(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Botão clicado: " + request.getParameter("botao"));
		request.setCharacterEncoding("UTF-8");
		String cmd = request.getParameter("botao");
		String saida = "";
		String erro = "";
		Curso c = new Curso();
		
		if (cmd == null) {
	        cmd = "";
	    }

		try {
			GenericDao gDao = new GenericDao();
			CursoDao cDao = new CursoDao(gDao);
			
			if (!cmd.equalsIgnoreCase("Listar") && !cmd.isEmpty()) {
	            c = montarCurso(request);
	        }
			
			if (cmd.contains("Inserir") || cmd.contains("Atualizar") || cmd.contains("Excluir") || cmd.contains("Buscar")) {
				c = montarCurso(request);
			}

			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = cDao.inserir(c);
				c = new Curso(); 
			} else if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = cDao.atualizar(c);
				c = new Curso();
			} else if (cmd.equalsIgnoreCase("Excluir")) {
				saida = cDao.excluir(c);
				c = new Curso();
			} else if (cmd.equalsIgnoreCase("Buscar")) {
				c = cDao.buscar(c);
				if (c == null) {
					saida = "Curso não encontrado.";
					c = new Curso();
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("curso", c);
			listar(request, response); 
		}
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Curso> cursos = new ArrayList<>();
		try {
			GenericDao gDao = new GenericDao();
			CursoDao cDao = new CursoDao(gDao);
			cursos = cDao.listar();
			
			System.out.println("DEBUG SERVLET: Quantidade de cursos encontrados: " + cursos.size());
			
		} catch (ClassNotFoundException | SQLException e) {
			request.setAttribute("erro", "Erro ao listar: " + e.getMessage());
		}
		request.setAttribute("cursos", cursos); 
		request.getRequestDispatcher("WEB-INF/view/curso.jsp").forward(request, response);
	}

	private Curso montarCurso(HttpServletRequest req) {
		Curso c = new Curso();
		String cod = req.getParameter("codigo");
		c.setCodigo(cod == null || cod.isEmpty() ? 0 : Integer.parseInt(cod));
		c.setNome(req.getParameter("nome"));
		c.setSigla(req.getParameter("sigla"));
		c.setCarga_hr(req.getParameter("cargaHoraria"));
		
		String nota = req.getParameter("notaEnade");
		c.setNota_enade(nota == null || nota.isEmpty() ? 0 : Integer.parseInt(nota));
		return c;
	}
}