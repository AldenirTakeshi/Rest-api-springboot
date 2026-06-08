package br.com.takeshi.spring_boot_rest.service;

import br.com.takeshi.spring_boot_rest.controllers.UserController;
import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDTO;
import br.com.takeshi.spring_boot_rest.exception.ResourceNotFoundException;
import static br.com.takeshi.spring_boot_rest.mapper.ObjectMapper.parseObject;

import br.com.takeshi.spring_boot_rest.model.UserEntity;
import br.com.takeshi.spring_boot_rest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final AtomicLong counter = new AtomicLong();
    private static  final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    public UserDTO create(UserDTO user){
        logger.info("Creating user");
        var entity = parseObject(user, UserEntity.class);
        var savedEntity = userRepository.save(entity);
        var dto = parseObject(savedEntity, UserDTO.class);
        addHateoasLinks(user);
        return dto;
    }


    public Page<UserDTO> findAll(Pageable pageable){
        logger.info("Finding all users");

        var user = userRepository.findAll(pageable);

        var userWithLink = user.map(u ->{
            var dto = parseObject(u, UserDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        return userWithLink;
    }

    public UserDTO findById(Long id){
        logger.info("Finding user with id: {}", id);
        var entity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
        var dto = parseObject(entity, UserDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public UserDTO update(Long id, UserDTO user){
        logger.info("Updating user with id: {}", id);
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
        if (user.getFirstName() != null) {
            entity.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            entity.setLastName(user.getLastName());
        }
        if (user.getAddress() != null) {
            entity.setAddress(user.getAddress());
        }
        if (user.getGender() != null) {
            entity.setGender(user.getGender());
        }
        var dto = parseObject(userRepository.save(entity), UserDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting user with id: {}", id);
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        userRepository.delete(entity);
    }

    @Transactional
    public UserDTO disableUser(Long id) {
        logger.info("Disabling user with id: {}", id);
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));
        entity.setEnabled(false);
        var dto = parseObject(userRepository.save(entity), UserDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    private static void addHateoasLinks(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll(1,12)).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
