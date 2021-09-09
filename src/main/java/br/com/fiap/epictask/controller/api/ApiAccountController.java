package br.com.fiap.epictask.controller.api;

import java.net.URI;
import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable("accounts")
	public Page<Account> index(@RequestParam(required = false) String nome,
			@PageableDefault(page = 0, size = 10) Pageable pageable) {

		if (nome == null) {
			return repository.findAll(pageable);
		}
		return repository.findByNomeLike("%" + nome + "%", pageable);

	}

	@PostMapping()
	@CacheEvict(value = "accounts", allEntries = true)
	public ResponseEntity<Account> create(@RequestBody @Valid Account account, UriComponentsBuilder uriBuilder) {
		repository.save(account);
		URI uri = uriBuilder.path("/api/task/{id}").buildAndExpand(account.getId()).toUri();
		return ResponseEntity.created(uri).body(account);
	}

	@GetMapping("{id}")
	public ResponseEntity<Account> get(@PathVariable Long id) {

		return ResponseEntity.of(repository.findById(id));
	}

	@DeleteMapping("{id}")
	@CacheEvict(value = "accounts", allEntries = true)
	public ResponseEntity<Account> delete(@PathVariable Long id) {
		Optional<Account> account = repository.findById(id);

		if (account.isEmpty())
			return ResponseEntity.notFound().build();

		repository.deleteById(id);

		return ResponseEntity.ok().build();

	}

	@PutMapping("{id}")
	@CacheEvict(value = "accounts", allEntries = true)
	public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody @Valid Account newAccount) {
		Optional<Account> optional = repository.findById(id);

		if (optional.isEmpty())
			return ResponseEntity.notFound().build();

		Account account = optional.get();
		account.setNome(newAccount.getNome());
		account.setEmail(newAccount.getEmail());
		account.setSenha(newAccount.getSenha());

		repository.save(account);

		return ResponseEntity.ok(account);
	}

}
