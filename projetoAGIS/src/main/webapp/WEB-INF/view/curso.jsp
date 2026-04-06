<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <title>AGIS - Gestão de Cursos</title>
</head>
<body class="bg-light">

    <jsp:include page="fragmentos/header.jsp" />
    
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

    <main class="container mt-4">
        <div class="card shadow mb-4">
            <div class="card-header bg-dark text-white">
                <i class="fas fa-university me-2"></i>Informações do Curso
            </div>
            <div class="card-body">
                <form action="curso" method="post" class="row g-3">
                    <div class="col-md-2">
                        <label class="form-label fw-bold">Código</label>
                        <input type="number" name="codigo" class="form-control" value="${curso.codigo}">
                    </div>
                    <div class="col-md-7">
                        <label class="form-label fw-bold">Nome do Curso *</label>
                        <input type="text" name="nome" class="form-control" required value="${curso.nome}">
                    </div>
                    <div class="col-md-3">
                        <label class="form-label fw-bold">Sigla *</label>
                        <input type="text" name="sigla" class="form-control" required value="${curso.sigla}">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Carga Horária (Horas) *</label>
                        <input type="text" name="cargaHoraria" class="form-control" required value="${curso.carga_hr}">
                    </div>
                    <div class="col-md-6">
                        <label class="form-label fw-bold">Nota ENADE *</label>
                        <input type="number" name="notaEnade" class="form-control" required value="${curso.nota_enade}" min="1" max="5">
                    </div>

                    <div class="col-12 mt-4 d-flex justify-content-center gap-2">
                        <button type="submit" name="botao" value="Inserir" class="btn btn-success">Inserir</button>
                        <button type="submit" name="botao" value="Atualizar" class="btn btn-warning">Atualizar</button>
                        <button type="submit" name="botao" value="Excluir" class="btn btn-danger" formnovalidate>Excluir</button>
                        <button type="submit" name="botao" value="Buscar" class="btn btn-primary" formnovalidate>Buscar</button>
                        <button type="submit" name="botao" value="Listar" class="btn btn-secondary" formnovalidate>Listar Todos</button>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty cursos}">
            <div class="card shadow">
                <div class="table-responsive">
                    <table class="table table-striped table-hover mb-0">
                        <thead class="table-dark">
                            <tr>
                                <th>Código</th>
                                <th>Sigla</th>
                                <th>Nome</th>
                                <th>Carga Hr</th>
                                <th>ENADE</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${cursos}">
                                <tr>
                                    <td>${c.codigo}</td>
                                    <td><span class="badge bg-primary">${c.sigla}</span></td>
                                    <td>${c.nome}</td>
                                    <td>${c.carga_hr} horas</td>
                                    <td>${c.nota_enade}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:if>
    </main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>