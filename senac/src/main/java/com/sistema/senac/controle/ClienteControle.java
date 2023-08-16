package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Cliente;
import com.sistema.senac.repositorio.ClienteRepositorio;
import com.sistema.senac.repositorio.CidadeRepositorio;

@Controller
public class ClienteControle {
	
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cadastroCliente")
	public ModelAndView cadastrar(Cliente cliente) { 
		ModelAndView mv = new ModelAndView("administrativo/clientes/cadastro");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		mv.addObject("cliente",cliente);
		return mv;
		
	}
	
	
	@PostMapping("/salvarCliente")
	public ModelAndView salvar(Cliente cliente, BindingResult result) { 
		if(result.hasErrors()) {
			return cadastrar(cliente);
	}
		clienteRepositorio.saveAndFlush(cliente);
		return cadastrar(new Cliente());
	}
	
	@GetMapping("/listarCliente")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/clientes/lista");
		mv.addObject("listaClientes", clienteRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarCliente/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);  //busca cliente por id e armazena
		return cadastrar(cliente.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerCliente/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cliente> cliente = clienteRepositorio.findById(id);
		clienteRepositorio.delete(cliente.get());
		return listar();
	}

}
