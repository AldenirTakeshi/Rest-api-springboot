package br.com.takeshi.spring_boot_rest.service.v1;

import br.com.takeshi.spring_boot_rest.controllers.UserController;
import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDto;
import br.com.takeshi.spring_boot_rest.exception.ResourceNotFoundException;
import static br.com.takeshi.spring_boot_rest.mapper.ObjectMapper.parseListObject;
import static br.com.takeshi.spring_boot_rest.mapper.ObjectMapper.parseObject;

import br.com.takeshi.spring_boot_rest.model.UserEntity;
import br.com.takeshi.spring_boot_rest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public UserDto create(UserDto user){
        logger.info("Creating user");
        var entity = parseObject(user, UserEntity.class);
        var savedEntity = userRepository.save(entity);
        var dto = parseObject(savedEntity, UserDto.class);
        addHateoasLinks(user);
        return dto;
    }


    public List<UserDto> findAll(){
        logger.info("Finding all users");
        var users = parseListObject(userRepository.findAll(), UserDto.class);
        users.forEach(user -> {
            addHateoasLinks(user);
        });
        return users;
    }

    public UserDto findById(Long id){
        logger.info("Finding user with id: {}", id);
        var entity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
        var dto = parseObject(entity, UserDto.class);
        addHateoasLinks(dto);
        return dto;
    }

    public UserDto update(Long id, UserDto user){
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
        var dto = parseObject(userRepository.save(entity), UserDto.class);
        addHateoasLinks(dto);
        return dto;
    }

    public void delete(Long id){
        logger.info("Deleting user with id: {}", id);
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        userRepository.delete(entity);
    }

    private static void addHateoasLinks(UserDto dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findAll()).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).create(dto)).withRel("create").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).update(dto.getId(), dto)).withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
    }
}
