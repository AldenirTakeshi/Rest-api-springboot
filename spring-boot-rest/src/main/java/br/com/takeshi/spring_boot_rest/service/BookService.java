package br.com.takeshi.spring_boot_rest.service;

import br.com.takeshi.spring_boot_rest.data.dto.v1.BookDTO;
import br.com.takeshi.spring_boot_rest.model.BookEntity;
import br.com.takeshi.spring_boot_rest.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDTO create(BookDTO bookDTO){
        BookEntity entity = convertToEntity(bookDTO);
        BookEntity entitySaved = bookRepository.save(entity);
        return convertToDTO(entitySaved);
    }

    public List<BookDTO> findAll() {
        List<BookEntity> books = bookRepository.findAll();
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BookDTO findById(Long id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
        return convertToDTO(entity);
    }

    public BookDTO update(Long id, BookDTO bookDTO){
        BookEntity entity = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));

        entity.setAuthor(bookDTO.getAuthor());
        entity.setTitle(bookDTO.getTitle());
        entity.setPrice(bookDTO.getPrice());

        if(bookDTO.getLaunchDateFormatted() != null){
            entity.setLaunchDate(LocalDateTime.parse(bookDTO.getLaunchDateFormatted()));
        }

        BookEntity updateEntity = bookRepository.save(entity);

        return convertToDTO(updateEntity);
    }

    public void delete(Long id){
        if(!bookRepository.existsById(id)){
            throw new RuntimeException("Livro não encontrado com o ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookDTO convertToDTO(BookEntity entity) {
        return new BookDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getAuthor(),
                entity.getPrice(),
                entity.getLaunchDate().toString()
        );
    }

    private BookEntity convertToEntity(BookDTO dto) {
        BookEntity entity = new BookEntity();

        entity.setAuthor(dto.getAuthor());
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());

        if (dto.getLaunchDateFormatted() != null) {
            entity.setLaunchDate(java.time.LocalDateTime.parse(dto.getLaunchDateFormatted()));
        }

        return entity;
    }
}
