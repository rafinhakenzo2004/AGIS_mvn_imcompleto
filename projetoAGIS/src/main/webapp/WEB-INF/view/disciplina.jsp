<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <jsp:include page="fragmentos/head.jsp" />
    <title>AGIS - Gerenciar Disciplina</title>
</head>
<body class="bg-light">

    <jsp:include page="fragmentos/header.jsp" />

    <main class="container mt-4 mb-5">
        <div class="row mb-3">
            <div class="col-12 text-center">
                <h2 class="display-6"><i class="fas fa-book me-2"></i>Gestão de Disciplinas</h2>
                <p class="text-muted small">Gerencie a grade curricular e horários das disciplinas por curso.</p>
                <hr>
            </div>
        </div>

        <c:if test="${not empty saida}">
            <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                <i class="fas fa-check-circle me-2"></i> ${saida}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty erro}">
            <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i> ${erro}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <div class="card shadow mb-4">
            <div class="card-header bg-dark text-white d-flex justify-content-between align-items-center">
                <span><i class="fas fa-edit me-2"></i>Cadastro de Disciplina</span>
            </div>
            <div class="card-body">
                <form action="disciplina" method="post" class="row g-3">
                    
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Código Disciplina *</label>
                        <input type="number" name="codigo" class="form-control" required 
                               value="${disciplina.codigo}" placeholder="Ex: 1001">
                    </div>

                    <div class="col-md-5">
                        <label class="form-label fw-bold">Nome da Disciplina *</label>
                        <input type="text" name="nome" class="form-control" required 
                               value="${disciplina.nome}" placeholder="Ex: Engenharia de Software III">
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-bold">Curso Vinculado *</label>
                        <select name="curso_codigo" class="form-select" required>
                            <option value="" disabled ${empty disciplina.curso_codigo ? 'selected' : ''}>Selecione o Curso...</option>
                            <c:forEach var="c" items="${cursos}">
                                <option value="${c.codigo}" ${disciplina.curso_codigo == c.codigo ? 'selected' : ''}>
                                    ${c.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-bold">Carga Horária Total *</label>
                        <input type="number" name="horas" class="form-control" required 
                               value="${disciplina.qtd_horas_total}" placeholder="Ex: 80">
                    </div>

                    <div class="col-md-4">
						<label for="horario">Horário de Início *</label>
						<select class="form-control" name="horarioInicio" id="horario">
						    <option value="">Selecione o horário...</option>
						    <option value="13:00-4">Iniciando às 13:00 com 4 aulas de duração (Até 16h30)</option>
						    <option value="13:00-2">Iniciando às 13:00 com 2 aulas de duração (Até 14h40)</option>
						    <option value="14:50-4">Iniciando às 14:50 com 4 aulas de duração (Até 18h20)</option>
						    <option value="14:50-2">Iniciando às 14:50 com 2 aulas de duração (Até 16h30)</option>
						    <option value="16:40-2">Iniciando às 16:40 com 2 aulas de duração (Até 18h20)</option>
						</select>
                    </div>

                    <div class="col-md-4">
                        <label class="form-label fw-bold">Dia da Semana *</label>
                        <select name="dia" class="form-select" required>
                            <option value="Segunda-feira" ${disciplina.dia_semana == 'Segunda-feira' ? 'selected' : ''}>Segunda-feira</option>
                            <option value="Terça-feira" ${disciplina.dia_semana == 'Terça-feira' ? 'selected' : ''}>Terça-feira</option>
                            <option value="Quarta-feira" ${disciplina.dia_semana == 'Quarta-feira' ? 'selected' : ''}>Quarta-feira</option>
                            <option value="Quinta-feira" ${disciplina.dia_semana == 'Quinta-feira' ? 'selected' : ''}>Quinta-feira</option>
                            <option value="Sexta-feira" ${disciplina.dia_semana == 'Sexta-feira' ? 'selected' : ''}>Sexta-feira</option>
                            <option value="Sábado" ${disciplina.dia_semana == 'Sábado' ? 'selected' : ''}>Sábado</option>
                        </select>
                    </div>

                    <div class="col-12 mt-4 d-flex justify-content-center gap-2">
                        <button type="submit" name="botao" value="Inserir" class="btn btn-success px-4 shadow-sm">
                            <i class="fas fa-plus me-1"></i> Inserir
                        </button>
                        <button type="submit" name="botao" value="Atualizar" class="btn btn-warning px-4 shadow-sm">
                            <i class="fas fa-save me-1"></i> Atualizar
                        </button>
                        <button type="submit" name="botao" value="Excluir" class="btn btn-danger px-4 shadow-sm" formnovalidate>
                            <i class="fas fa-trash me-1"></i> Excluir
                        </button>
                        <button type="submit" name="botao" value="Buscar" class="btn btn-primary px-4 shadow-sm" formnovalidate>
                            <i class="fas fa-search me-1"></i> Buscar
                        </button>
						<button type="submit" name="botao" value="Listar" class="btn btn-secondary px-4 shadow-sm" formnovalidate>
						    <i class="fas fa-list me-1"></i> Listar Todas
						</button>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty disciplinas}">
            <div class="card shadow border-0">
                <div class="card-header bg-secondary text-white fw-bold">
                    <i class="fas fa-list-ul me-2"></i>Grade de Disciplinas
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0 align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Código</th>
                                <th>Disciplina</th>
                                <th>Curso (Cod)</th>
                                <th>Carga Hr</th>
                                <th>Horário/Dia</th>
                                <th class="text-center">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="d" items="${disciplinas}">
                                <tr>
                                    <td class="fw-bold">${d.codigo}</td>
                                    <td>${d.nome}</td>
                                    <td><span class="badge bg-outline-dark border text-dark">${d.curso_codigo}</span></td>
                                    <td>${d.qtd_horas_total}h</td>
                                    <td><small>${d.hora_inicio} - ${d.dia_semana}</small></td>
                                    <td class="text-center">
                                        <a href="disciplina?acao=excluir&cod=${d.codigo}&cCod=${d.curso_codigo}" 
                                           class="btn btn-sm btn-outline-danger" 
                                           onclick="return confirm('Excluir esta disciplina?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </main>

    <jsp:include page="fragmentos/footer.jsp" />

</body>
</html>