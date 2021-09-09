package br.com.fiap.epictask.controller.api;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.fiap.epictask.model.Account;
import br.com.fiap.epictask.repository.AccountRepository;

@RestController
@RequestMapping("/api/user")
public class ApiAccountController {

	@Autowired
	private AccountRepository repository;

	@GetMapping()
	public Page<Account> index(@RequestParam(required = false) String nome,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		if (nome == null) {
			return repository.findAll(pageable);
		}
		return repository.findByNomeLike("%" + nome + "%", pageable);

	}

	@PostMapping()
	public ResponseEntity<Account> create(@RequestBody Account account, UriComponentsBuilder uriBuilder) {
		repository.save(account);
		URI uri = uriBuilder.path("/api/task/{id}").buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(uri).body(account);
	}

	@GetMapping("{id}")
	public ResponseEntity<Account> get(@PathVariable Long id) {
		Optional<Account> account = repository.findById(id);

		if (account.isPresent())
			return ResponseEntity.ok(account.get());

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Account> delete(@PathVariable Long id) {
		Optional<Account> account = repository.findById(id);

		if (account.isEmpty())
			return ResponseEntity.notFound().build();
		
		repository.deleteById(id);

		return ResponseEntity.ok().build();

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Account> update(@RequestBody Account account, @PathVariable Long id) {
		repository.findById(id);
		
		account.setId(id);
		
		repository.save(account);

		return ResponseEntity.ok().build();
	}

}
