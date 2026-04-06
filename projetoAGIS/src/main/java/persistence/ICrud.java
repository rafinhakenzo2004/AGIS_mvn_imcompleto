package persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * SOLID: Open/Closed Principle (OCP)
 * O uso de Generics (<T>) permite que a interface seja estendida para qualquer 
 * nova entidade (Aluno, Disciplina) sem a necessidade de alterar esta interface base.
 */

public interface ICrud<T> {

	public T buscar(T t) throws SQLException, ClassNotFoundException;
	public List<T> listar() throws SQLException, ClassNotFoundException;
	public String inserir(T t) throws SQLException, ClassNotFoundException;
	public String atualizar(T t) throws SQLException, ClassNotFoundException;
	public String excluir(T t) throws SQLException, ClassNotFoundException;
}