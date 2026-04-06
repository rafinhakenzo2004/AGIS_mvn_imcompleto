package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Disciplina {
	private int codigo;
	private int curso_codigo;
	private String nome;
	private int qtd_horas_total;
	private String hora_inicio;
	private String dia_semana;
	private int duracao_hora_aula;
}	
