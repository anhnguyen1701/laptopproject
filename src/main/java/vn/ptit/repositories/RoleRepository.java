package vn.ptit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ptit.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer>{

}
