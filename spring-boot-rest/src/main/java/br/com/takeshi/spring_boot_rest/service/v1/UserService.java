package br.com.takeshi.spring_boot_rest.service.v1;

import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDto;
import br.com.takeshi.spring_boot_rest.exception.ResourceNotFoundException;
import static br.com.takeshi.spring_boot_rest.mapper.ObjectMapper.parseListObject;
import static br.com.takeshi.spring_boot_rest.mapper.ObjectMapper.parseObject;

import br.com.takeshi.spring_boot_rest.model.UserEntity;
import br.com.takeshi.spring_boot_rest.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        return parseObject(savedEntity, UserDto.class);
    }


    public List<UserDto> findAll(){
        logger.info("Finding all users");
        return parseListObject(userRepository.findAll(), UserDto.class);
    }

    public UserDto findById(Long id){
        logger.info("Finding user with id: {}", id);
        var entity = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No Records found for this ID"));
        return parseObject(entity, UserDto.class);
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
        return parseObject(userRepository.save(entity), UserDto.class);
    }

    public void delete(Long id){
        logger.info("Deleting user with id: {}", id);
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Records found for this ID"));

        userRepository.delete(entity);
    }
}
