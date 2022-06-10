package kz.app.jwtappdemo.repository;

import kz.app.jwtappdemo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>
{
    Role findByName(String name);
}
