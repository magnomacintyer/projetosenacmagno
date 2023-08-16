	package com.sistema.senac.repositorio;
	
	import org.springframework.data.jpa.repository.JpaRepository;
	
	import com.sistema.senac.modelo.Fornecedor;
	
	public interface FornecedorRepositorio extends JpaRepository<Fornecedor, Long> {
		
	}
	
