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

import model.Conteudo;
import persistence.ConteudoDao;
import persistence.GenericDao;

@WebServlet("/conteudo")
public class ConteudoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ConteudoServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dCod = request.getParameter("dCod");
		String cCod = request.getParameter("cCod");
		
		List<Conteudo> conteudos = new ArrayList<>();
		String erro = "";

		try {
			if (dCod != null && cCod != null) {
				GenericDao gDao = new GenericDao();
				ConteudoDao cDao = new ConteudoDao(gDao);
				conteudos = cDao.listarPorDisciplina(Integer.parseInt(dCod), Integer.parseInt(cCod));
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			request.setAttribute("erro", erro);
			request.setAttribute("conteudos", conteudos);
			request.getRequestDispatcher("WEB-INF/view/conteudo.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String cmd = request.getParameter("botao");
		String dCod = request.getParameter("disciplinaCod");
		String cCod = request.getParameter("cursoCod");
		String desc = request.getParameter("descricao");
		String descAntiga = request.getParameter("descricaoAntiga");
		
		String saida = "";
		String erro = "";
		Conteudo cont = new Conteudo();
		List<Conteudo> conteudos = new ArrayList<>();

		try {
			GenericDao gDao = new GenericDao();
			ConteudoDao cDao = new ConteudoDao(gDao);

			if (dCod != null && !dCod.isEmpty()) {
				cont.setDisciplina_codigo(Integer.parseInt(dCod));
				cont.setCurso_codigo(Integer.parseInt(cCod));
				cont.setDescricao(desc);
			}

			if (cmd.equalsIgnoreCase("Inserir")) {
				saida = cDao.inserir(cont);
			} 
			else if (cmd.equalsIgnoreCase("Atualizar")) {
				saida = cDao.atualizarComDescricaoAntiga(cont, descAntiga);
			} 
			else if (cmd.equalsIgnoreCase("Excluir")) {
				saida = cDao.excluir(cont);
			} 
			else if (cmd.equalsIgnoreCase("Buscar")) {
				cont = cDao.buscar(cont);
			}

			if (dCod != null && !dCod.isEmpty()) {
				conteudos = cDao.listarPorDisciplina(Integer.parseInt(dCod), Integer.parseInt(cCod));
			}

		} catch (SQLException | ClassNotFoundException | NumberFormatException e) {
			erro = "Erro no processamento: " + e.getMessage();
		} finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("conteudo", cont);
			request.setAttribute("conteudos", conteudos);
			request.setAttribute("dCodPersist", dCod);
			request.setAttribute("cCodPersist", cCod);

			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/view/conteudo.jsp");
			dispatcher.forward(request, response);
		}
	}
}