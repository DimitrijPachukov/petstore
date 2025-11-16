package mk.ukim.finki.wp.petstore.repository;

import mk.ukim.finki.wp.petstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}