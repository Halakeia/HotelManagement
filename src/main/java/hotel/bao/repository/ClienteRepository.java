package hotel.bao.repository;

import hotel.bao.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    //TODO: implementar os métodos de verificação
    boolean existsByEmailAndIdNot(String email, long id);

    boolean existsByLoginAndIdNot(String login, long id);
}
