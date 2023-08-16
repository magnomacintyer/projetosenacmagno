package com.sistema.senac.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.senac.modelo.Venda;

public interface VendaRepositorio extends JpaRepository<Venda, Long> {
	

}
