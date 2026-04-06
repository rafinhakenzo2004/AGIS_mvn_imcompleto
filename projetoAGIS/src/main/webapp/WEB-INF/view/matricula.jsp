<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <jsp:include page="fragmentos/head.jsp" />
    <title>AGIS - Matrícula</title>
</head>
<body class="bg-light">

    <jsp:include page="fragmentos/header.jsp" />

    <main class="container mt-4 mb-5">
        <div class="row mb-3">
            <div class="col-12 text-center">
                <h2 class="display-6"><i class="fas fa-edit me-2"></i>Efetuar Matrícula</h2>
                <p class="text-muted small">Informe o RA para verificar disciplinas disponíveis e realizar a inscrição.</p>
                <hr>
            </div>
        </div>

        <c:if test="${not empty saida}">
            <div class="alert alert-info alert-dismissible fade show shadow-sm" role="alert">
                <i class="fas fa-info-circle me-2"></i> ${saida}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i> ${erro}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <%-- Busca por RA --%>
        <div class="card shadow-sm mb-4 border-0">
            <div class="card-body bg-dark rounded text-white">
                <form action="matricula" method="post" class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label class="form-label fw-bold">RA do Aluno</label>
                        <input type="text" name="raBusca" class="form-control" value="${ra}" placeholder="Digite o RA..." required>
                    </div>
                    <div class="col-md-4">
                        <button type="submit" name="botao" value="Buscar Aluno" class="btn btn-primary px-4">
                            <i class="fas fa-search me-1"></i> Consultar Disponibilidade
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty ra}">
            <div class="row">
                <div class="col-lg-7">
                    <div class="card shadow border-0 h-100">
                        <div class="card-header bg-success text-white fw-bold">
                            <i class="fas fa-unlock me-2"></i>Disciplinas Disponíveis
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover align-middle mb-0">
                                <thead class="table-light small">
                                    <tr>
                                        <th>Código</th>
                                        <th>Nome</th>
                                        <th>Dia/Hora</th>
                                        <th class="text-center">Ação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="d" items="${disciplinas}">
                                        <tr>
                                            <td class="fw-bold">${d.codigo}</td>
                                            <td>${d.nome} <br><small class="text-muted">${d.qtd_horas_total}h</small></td>
                                            <td><small>${d.dia_semana}<br>${d.hora_inicio}</small></td>
                                            <td class="text-center">
                                                <form action="matricula" method="post">
                                                    <input type="hidden" name="raAluno" value="${ra}">
                                                    <input type="hidden" name="txtDisciplina" value="${d.codigo}">
                                                    <input type="hidden" name="txtCurso" value="${d.curso_codigo}"> 
                                                    <input type="hidden" name="txtAno" value="${not empty anoAtual ? anoAtual : 2026}">
                                                    <input type="hidden" name="txtSemestre" value="${not empty semestreAtual ? semestreAtual : 1}">
                                                    
                                                    <button type="submit" name="botao" value="Matricular" class="btn btn-sm btn-success">
                                                        Matricular
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty disciplinas}">
                                        <tr><td colspan="4" class="text-center text-muted py-4">Nenhuma disciplina disponível para este RA.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <div class="col-lg-5">
                    <div class="card shadow border-0 h-100">
                        <div class="card-header bg-secondary text-white fw-bold">
                            <i class="fas fa-check-double me-2"></i>Matrículas do Semestre
                        </div>
                        <div class="card-body p-0">
                            <ul class="list-group list-group-flush">
                                <c:forEach var="m" items="${matriculas}">
                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                        <div>
                                            <span class="fw-bold text-primary">Cód: ${m.disciplina_codigo}</span><br>
                                            <small class="text-muted">Ano: ${m.ano} | Semestre: ${m.semestre}º</small>
                                        </div>
                                        <span class="badge bg-light text-dark border"><i class="fas fa-check text-success"></i> Confirmada</span>
                                    </li>
                                </c:forEach>
                                <c:if test="${empty matriculas}">
                                    <li class="list-group-item text-center text-muted py-4">Ainda não há matrículas efetivadas.</li>
                                </c:if>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </main>

    <jsp:include page="fragmentos/footer.jsp" />

</body>
</html>