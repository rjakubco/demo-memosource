package org.test.memsource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.test.memsource.model.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUsername(String username);
}
