package br.com.fiap.epictask.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.fiap.epictask.model.Account;
import br.com.fiap.epictask.repository.AccountRepository;

@Controller
public class AccountController {
	
	@Autowired
	private AccountRepository repository;

	@RequestMapping("/home")
	public String index() {
		return "index";
	}

	@GetMapping("/list")
	public ModelAndView listagem() {
		ModelAndView modelAndView = new ModelAndView("list");
		List<Account> list = repository.findAll();
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	
	@PostMapping("/list")
	public String save(@Valid Account account, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		repository.save(account);
		return "index";
	}

	@RequestMapping("/register")
	public String register(Account account) {
		return "register";
	}

}
