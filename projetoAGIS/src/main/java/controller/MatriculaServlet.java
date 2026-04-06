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

import model.Matricula;
import model.Disciplina;
import persistence.GenericDao;
import persistence.MatriculaDao;

//No doPost:
/**
* SOLID: Liskov Substitution Principle (LSP)
* Podemos declarar a variável como a interface ICrud e instanciar como MatriculaDao.
* O sistema funcionará perfeitamente com o polimorfismo.
*/

@WebServlet("/matricula")
public class MatriculaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MatriculaServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/view/matricula.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String cmd = request.getParameter("botao");
        String raBusca = request.getParameter("raBusca"); 
        String raAluno = request.getParameter("raAluno"); 
        
        String ra = (raBusca != null && !raBusca.isEmpty()) ? raBusca : raAluno;
        
        String saida = "";
        String erro = "";
        
        List<Disciplina> disciplinasDisponiveis = new ArrayList<>();
        List<Matricula> matriculasEfetuadas = new ArrayList<>();

        try {
            GenericDao gDao = new GenericDao();
            MatriculaDao mDao = new MatriculaDao(gDao);

            if (cmd.equalsIgnoreCase("Matricular")) {
                Matricula m = montarMatricula(request);
                saida = mDao.inserir(m); 
                ra = m.getAluno_ra(); 
            }

            if (ra != null && !ra.isEmpty()) {
                disciplinasDisponiveis = mDao.listarDisciplinasDisponiveis(ra);
                matriculasEfetuadas = mDao.listarMatriculasAluno(ra);
                
                if (disciplinasDisponiveis.isEmpty() && matriculasEfetuadas.isEmpty() && !cmd.equalsIgnoreCase("Matricular")) {
                    saida = "Nenhum dado encontrado para o RA informado.";
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            erro = "Erro: " + e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("ra", ra);
            request.setAttribute("disciplinas", disciplinasDisponiveis);
            request.setAttribute("matriculas", matriculasEfetuadas);
            request.setAttribute("anoAtual", 2026); 
            request.setAttribute("semestreAtual", 1);
            request.getRequestDispatcher("WEB-INF/view/matricula.jsp").forward(request, response);
        }
    }

    private Matricula montarMatricula(HttpServletRequest req) {
        System.out.println("--- DADOS RECEBIDOS PARA MATRÍCULA ---");
        System.out.println("RA: " + req.getParameter("raAluno"));
        System.out.println("Cod Disc: " + req.getParameter("txtDisciplina"));
        System.out.println("Cod Curso: " + req.getParameter("txtCurso"));
        System.out.println("Ano: " + req.getParameter("txtAno"));
        System.out.println("Semestre: " + req.getParameter("txtSemestre"));
        System.out.println("---------------------------------------");

        Matricula m = new Matricula();
        m.setAluno_ra(req.getParameter("raAluno"));
        m.setDisciplina_codigo(Integer.parseInt(req.getParameter("txtDisciplina")));
        m.setCurso_codigo(Integer.parseInt(req.getParameter("txtCurso")));
        m.setAno(Integer.parseInt(req.getParameter("txtAno")));
        m.setSemestre(Integer.parseInt(req.getParameter("txtSemestre")));
        return m;
    }
}