package hotel.bao.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hotel.bao.entities.Role;

@Repository
public interface RoleRepository extends
        JpaRepository<Role, Long> {

}