<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">
                <i class="fas fa-graduation-cap me-2"></i>AGIS
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="index.jsp">
                            <i class="fas fa-home me-1"></i>Início
                        </a>
                    </li>
                    
                    </ul>

                <div class="d-flex align-items-center">
                    <div class="dropdown">
                        <a href="#" class="d-block link-light text-decoration-none dropdown-toggle" 
                           id="dropdownUser" data-bs-toggle="dropdown">
                            <span class="me-2 user-greeting">Usuário</span>
                            <i class="fas fa-user-circle fa-lg text-white"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end">
                            <li><a class="dropdown-item" href="#"><i class="fas fa-user me-2"></i> Perfil</a></li>
                            <li><a class="dropdown-item" href="#"><i class="fas fa-cog me-2"></i> Configurações</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <a class="dropdown-item text-danger" href="logout">
                                    <i class="fas fa-sign-out-alt me-2"></i> Sair
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </nav>
</header>