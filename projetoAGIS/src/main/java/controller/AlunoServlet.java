package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.Aluno;
import model.Curso;
import persistence.AlunoDao;
import persistence.CursoDao; 
import persistence.GenericDao;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AlunoServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String erro = "";
        List<Aluno> alunos = new ArrayList<>();
        
        try {
            GenericDao gDao = new GenericDao();
            AlunoDao aDao = new AlunoDao(gDao);
            alunos = aDao.listar();
            
            carregarCursos(request);
            
        } catch (ClassNotFoundException | SQLException e) {
            erro = "Erro ao carregar dados: " + e.getMessage();
        } finally {
            request.setAttribute("erro", erro);
            request.setAttribute("alunos", alunos);
            request.getRequestDispatcher("WEB-INF/view/aluno.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String cmd = request.getParameter("botao");
        String ra = request.getParameter("ra");
        String saida = "";
        String erro = "";
        
        Aluno a = new Aluno();
        List<Aluno> alunos = new ArrayList<>();

        try {
            GenericDao gDao = new GenericDao();
            AlunoDao aDao = new AlunoDao(gDao);

            carregarCursos(request);

            if (cmd.contains("Inserir") || cmd.contains("Atualizar")) {
                a = montarAluno(request);
            }
            
            if (ra != null && !ra.isEmpty() && !ra.equalsIgnoreCase("Gerado")) {
                a.setRa(ra);
            }

            if (cmd.equalsIgnoreCase("Inserir")) {
                saida = aDao.inserir(a);
                a = new Aluno(); 
            } else if (cmd.equalsIgnoreCase("Atualizar")) {
                saida = aDao.atualizar(a);
                a = new Aluno();
            } else if (cmd.equalsIgnoreCase("Excluir")) {
                saida = aDao.excluir(a);
                a = new Aluno();
            } else if (cmd.equalsIgnoreCase("Buscar")) {
                a = aDao.buscar(a);
                if (a == null || a.getRa() == null) {
                    saida = "Aluno não encontrado.";
                    a = new Aluno();
                }
            }
            
            alunos = aDao.listar();

        } catch (ClassNotFoundException | SQLException | NumberFormatException e) {
            erro = "Erro na operação: " + e.getMessage();
        } finally {
            request.setAttribute("saida", saida);
            request.setAttribute("erro", erro);
            request.setAttribute("aluno", a);
            request.setAttribute("alunos", alunos);
            request.getRequestDispatcher("WEB-INF/view/aluno.jsp").forward(request, response);
        }
    }

    private void carregarCursos(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        GenericDao gDao = new GenericDao();
        CursoDao cDao = new CursoDao(gDao);
        List<Curso> listaCursos = cDao.listar(); 
        request.setAttribute("cursos", listaCursos);
    }

    private Aluno montarAluno(HttpServletRequest req) {
        Aluno al = new Aluno();
        String ra = req.getParameter("ra");
        if (ra != null && !ra.isEmpty() && !ra.equalsIgnoreCase("Gerado")) {
            al.setRa(ra);
        }
        
        al.setCpf(req.getParameter("cpf"));
        al.setNome(req.getParameter("nome"));
        al.setNome_social(req.getParameter("nomeSocial"));
        al.setDt_nasc(LocalDate.parse(req.getParameter("dataNasc")));
        al.setTelefone(req.getParameter("telefone"));
        al.setEmail_pessoal(req.getParameter("emailPessoal"));
        al.setEmail_corporativo(req.getParameter("emailCorp"));
        al.setDt_con_2grau(LocalDate.parse(req.getParameter("data2G")));
        al.setInstituicao_2grau(req.getParameter("inst2G"));
        al.setPt_vestibular(Integer.parseInt(req.getParameter("pontosVest")));
        al.setPos_vestibular(Integer.parseInt(req.getParameter("posVest")));
        al.setAno_ingresso(Integer.parseInt(req.getParameter("anoIngresso")));
        al.setSem_ingresso(Integer.parseInt(req.getParameter("semIngresso")));
        al.setTurno(req.getParameter("turno"));
        al.setCurso_codigo(Integer.parseInt(req.getParameter("cursoCod")));
        return al;
    }
}