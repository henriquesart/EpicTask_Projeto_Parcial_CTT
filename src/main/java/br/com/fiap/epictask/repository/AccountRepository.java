package br.com.fiap.epictask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictask.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Page<Account> findByNomeLike(String string, Pageable pageable);

}
