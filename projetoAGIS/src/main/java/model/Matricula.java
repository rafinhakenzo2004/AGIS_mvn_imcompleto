package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula {
	private String aluno_ra;
	private int disciplina_codigo;
	private int curso_codigo;
	private int ano;
	private int semestre;
}
