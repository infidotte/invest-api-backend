package api.invest.repository;

import api.invest.entity.User;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends CrudRepository<User, Long> {

    Mono<User> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);
}
