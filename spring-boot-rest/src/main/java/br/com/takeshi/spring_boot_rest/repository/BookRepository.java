package br.com.takeshi.spring_boot_rest.repository;

import br.com.takeshi.spring_boot_rest.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {}
