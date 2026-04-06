package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conteudo {
	private int disciplina_codigo;
	private int curso_codigo;
	private String descricao;
}
