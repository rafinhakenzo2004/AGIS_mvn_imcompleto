<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <jsp:include page="fragmentos/head.jsp" />
    <title>AGIS - Gerenciar Conteúdo</title>
</head>
<body class="bg-light">

    <jsp:include page="fragmentos/header.jsp" />

    <main class="container mt-4 mb-5">
        <div class="row mb-3">
            <div class="col-12 text-center">
                <h2 class="display-6"><i class="fas fa-book-open me-2"></i>Conteúdo Programático</h2>
                <p class="text-muted small">Defina os tópicos e ementas para cada disciplina cadastrada.</p>
                <hr>
            </div>
        </div>

        <%-- Alertas de Feedback --%>
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
                <span><i class="fas fa-edit me-2"></i>Cadastro de Conteúdo</span>
            </div>
            <div class="card-body">
                <form action="conteudo" method="post" class="row g-3">
                    
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Cód. Disciplina *</label>
                        <input type="number" name="disciplinaCod" class="form-control" required 
                               value="${not empty conteudo.disciplina_codigo ? conteudo.disciplina_codigo : dCodPersist}">
                    </div>
                    
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Cód. Curso *</label>
                        <input type="number" name="cursoCod" class="form-control" required 
                               value="${not empty conteudo.curso_codigo ? conteudo.curso_codigo : cCodPersist}">
                    </div>

                    <div class="col-md-6">
                        <label class="form-label fw-bold">Descrição do Conteúdo *</label>
                        <input type="text" name="descricao" class="form-control" required 
                               value="${conteudo.descricao}" placeholder="Ex: Introdução ao Kernel do Windows">
                        <%-- Campo oculto para permitir a atualização da descrição (PK do conteúdo) --%>
                        <input type="hidden" name="descricaoAntiga" value="${conteudo.descricao}">
                    </div>

                    <div class="col-12 mt-4 d-flex justify-content-center gap-2">
                        <button type="submit" name="botao" value="Inserir" class="btn btn-success px-4">
                            <i class="fas fa-plus me-1"></i> Inserir
                        </button>
                        <button type="submit" name="botao" value="Atualizar" class="btn btn-warning px-4">
                            <i class="fas fa-save me-1"></i> Atualizar
                        </button>
                        <button type="submit" name="botao" value="Excluir" class="btn btn-danger px-4" formnovalidate>
                            <i class="fas fa-trash me-1"></i> Excluir
                        </button>
                        <button type="submit" name="botao" value="Buscar" class="btn btn-primary px-4" formnovalidate>
                            <i class="fas fa-search me-1"></i> Buscar
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <%-- Tabela de Tópicos --%>
        <c:if test="${not empty conteudos}">
            <div class="card shadow border-0">
                <div class="card-header bg-secondary text-white fw-bold">
                    <i class="fas fa-list me-2"></i>Tópicos da Disciplina
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0 align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>Disciplina</th>
                                <th>Curso</th>
                                <th>Conteúdo / Tópico</th>
                                <th class="text-center">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="ct" items="${conteudos}">
                                <tr>
                                    <td><span class="badge bg-light text-dark border">${ct.getDisciplina_codigo()}</span></td>
                                    <td><span class="badge bg-light text-dark border">${ct.getCurso_codigo()}</span></td>
                                    <td class="fw-bold text-primary">${ct.descricao}</td>
                                    <td class="text-center">
                                        <form action="conteudo" method="post" style="display:inline;">
                                            <input type="hidden" name="disciplinaCod" value="${ct.getDisciplina_codigo()}">
                                            <input type="hidden" name="cursoCod" value="${ct.getCurso_codigo()}">
                                            <input type="hidden" name="descricao" value="${ct.descricao}">
                                            <button type="submit" name="botao" value="Buscar" class="btn btn-sm btn-outline-info">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                        </form>
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