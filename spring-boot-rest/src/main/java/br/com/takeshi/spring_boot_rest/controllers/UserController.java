package br.com.takeshi.spring_boot_rest.controllers;

import br.com.takeshi.spring_boot_rest.controllers.docs.UserControllerDocs;
import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDTO;
import br.com.takeshi.spring_boot_rest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1")
@Tag(name = "User", description = "Endpoints for managing Users")
public class UserController implements UserControllerDocs {

    @Autowired
    private UserService userService;

    @PostMapping
    @Override
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public UserDTO findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @PutMapping("/{id}")
    @Override
    public UserDTO update(@PathVariable("id") Long id, @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
