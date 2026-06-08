package br.com.takeshi.spring_boot_rest.controllers;

import br.com.takeshi.spring_boot_rest.controllers.docs.UserControllerDocs;
import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDTO;
import br.com.takeshi.spring_boot_rest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    public ResponseEntity<Page<UserDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(userService.findAll(pageable));
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

    @PatchMapping("/{id}/disable")
    public UserDTO disableUser(@PathVariable("id") Long id) {
        return userService.disableUser(id);
    }
}
