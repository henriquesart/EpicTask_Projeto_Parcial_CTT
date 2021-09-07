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

import br.com.fiap.epictask.model.Task;
import br.com.fiap.epictask.repository.TaskRepository;

@Controller
public class TaskController {
	
	@Autowired
	private TaskRepository repository;

	@RequestMapping("/home")
	public String index() {
		return "index";
	}

	@GetMapping("/list")
	public ModelAndView listagem() {
		ModelAndView modelAndView = new ModelAndView("list");
		List<Task> list = repository.findAll();
		modelAndView.addObject("list", list);
		return modelAndView;
	}
	
	@PostMapping("/list")
	public String save(@Valid Task task, BindingResult result) {
		if(result.hasErrors()) {
			return "register";
		}
		repository.save(task);
		return "index";
	}

	@RequestMapping("/register")
	public String register(Task task) {
		return "register";
	}

}
