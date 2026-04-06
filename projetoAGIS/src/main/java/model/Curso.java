package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Curso {
	private int codigo;
	private String nome;
	private String carga_hr;
	private String sigla;
	private int nota_enade;
}