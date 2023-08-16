package com.sistema.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.senac.modelo.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {
	

}
