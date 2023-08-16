package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Funcionario;
import com.sistema.senac.repositorio.FuncionarioRepositorio;
import com.sistema.senac.repositorio.CidadeRepositorio;

@Controller
public class FuncionarioControle {
	
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cadastroFuncionario")
	public ModelAndView cadastrar(Funcionario funcionario) { 
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		mv.addObject("funcionario",funcionario);
		return mv;
	}
	
	@PostMapping("/salvarFuncionario")
	public ModelAndView salvar(Funcionario funcionario, BindingResult result) { 
		if(result.hasErrors()) {
			return cadastrar(funcionario);
	}
		funcionarioRepositorio.saveAndFlush(funcionario);
		return cadastrar(new Funcionario());
	}
	
	@GetMapping("/listarFuncionario")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarFuncionario/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);  //busca funcionario por id e armazena
		return cadastrar(funcionario.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerFuncionario/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
		funcionarioRepositorio.delete(funcionario.get());
		return listar();
	}

}
