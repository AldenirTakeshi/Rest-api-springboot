package br.com.takeshi.spring_boot_rest.repository;

import br.com.takeshi.spring_boot_rest.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity p SET p.enabled = false WHERE p.id =:id")
    void disableUser(@Param("id") Long id);

    @Query("SELECT p FROM UserEntity p WHERE p.firstName LIKE LOWER(CONCAT('%',:firstName,'%'))")
    Page<UserEntity> findUserByName(@Param("firstName") String firstName, Pageable pageable);
}
