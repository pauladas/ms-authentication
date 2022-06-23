package br.com.devjansen.repository;

import br.com.devjansen.model.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDomain, String> {
}
