package com.koskou.demo.JPA.Repository;

import com.koskou.demo.JPA.Entity.UserJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJPA, Integer> {

}
