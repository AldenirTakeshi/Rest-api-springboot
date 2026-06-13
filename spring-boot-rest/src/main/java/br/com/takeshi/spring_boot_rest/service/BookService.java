package br.com.takeshi.spring_boot_rest.service;

import br.com.takeshi.spring_boot_rest.controllers.books.BookController;
import br.com.takeshi.spring_boot_rest.data.dto.v1.BookDTO;
import br.com.takeshi.spring_boot_rest.model.BookEntity;
import br.com.takeshi.spring_boot_rest.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final PagedResourcesAssembler<BookDTO> assembler;

    public BookService(BookRepository bookRepository, PagedResourcesAssembler<BookDTO> assembler) {
        this.bookRepository = bookRepository;
        this.assembler = assembler;
    }

    public BookDTO create(BookDTO bookDTO) {
        logger.info("Creating book");
        BookEntity entity = convertToEntity(bookDTO);
        BookEntity entitySaved = bookRepository.save(entity);
        BookDTO dto = convertToDTO(entitySaved);
        addHateoasLinks(dto);
        return dto;
    }

    public PagedModel<EntityModel<BookDTO>> findAll(Pageable pageable) {
        logger.info("Finding all books");

        Page<BookDTO> page = bookRepository.findAll(pageable)
                .map(entity -> {
                    BookDTO dto = convertToDTO(entity);
                    addHateoasLinks(dto);
                    return dto;
                });

        var direction = pageable.getSort().stream().findFirst()
                .map(order -> order.getDirection().name().toLowerCase())
                .orElse("asc");

        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
                .methodOn(BookController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), direction))
                .withSelfRel();

        return assembler.toModel(page, selfLink);
    }

    public BookDTO findById(Long id) {
        logger.info("Finding book with id: {}", id);
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));
        BookDTO dto = convertToDTO(entity);
        addHateoasLinks(dto);
        return dto;
    }

    public BookDTO update(Long id, BookDTO bookDTO) {
        logger.info("Updating book with id: {}", id);
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com o ID: " + id));

        entity.setAuthor(bookDTO.getAuthor());
        entity.setTitle(bookDTO.getTitle());
        entity.setPrice(bookDTO.getPrice());

        if (bookDTO.getLaunchDateFormatted() != null) {
            entity.setLaunchDate(LocalDateTime.parse(bookDTO.getLaunchDateFormatted()));
        }

        BookDTO dto = convertToDTO(bookRepository.save(entity));
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id) {
        logger.info("Deleting book with id: {}", id);
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Livro não encontrado com o ID: " + id);
        }
        bookRepository.deleteById(id);
    }

    private static void addHateoasLinks(BookDTO dto) {
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getId())).withSelfRel());
        dto.add(linkTo(methodOn(BookController.class).findAll(0, 12, "asc")).withRel("findAll"));
        dto.add(linkTo(methodOn(BookController.class).create(dto)).withRel("create"));
        dto.add(linkTo(methodOn(BookController.class).update(dto.getId(), dto)).withRel("update"));
        dto.add(linkTo(methodOn(BookController.class).delete(dto.getId())).withRel("delete"));
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
            entity.setLaunchDate(LocalDateTime.parse(dto.getLaunchDateFormatted()));
        }
        return entity;
    }
}
