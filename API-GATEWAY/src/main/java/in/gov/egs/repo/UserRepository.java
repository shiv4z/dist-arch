package in.gov.egs.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.gov.egs.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);
}
