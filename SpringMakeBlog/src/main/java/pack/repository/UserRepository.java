package pack.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import pack.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);		// emial로 사용자 정보를 가져옴

}
