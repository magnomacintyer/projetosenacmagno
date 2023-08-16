	package com.sistema.senac.repositorio;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.sistema.senac.modelo.Cliente;
	
	public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
		
	}
	
