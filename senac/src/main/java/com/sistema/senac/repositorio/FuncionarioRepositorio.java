	package com.sistema.senac.repositorio;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.sistema.senac.modelo.Funcionario;
	
	public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {
		
	}
	
