package br.com.takeshi.spring_boot_rest.controllers.docs;

import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserControllerDocs {

    UserDTO create(@RequestBody UserDTO user);
    @Operation(summary = "Find User",
            description = "Find User by your Id",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    UserDTO findById(@PathVariable("id") Long id);
    @Operation(summary = "Find all Users",
            description = "Find all Users",
            tags = {"User"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))
                            )
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })

    ResponseEntity<Page<UserDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size
    );

    UserDTO update(@PathVariable("id") Long id, @RequestBody UserDTO user);

    ResponseEntity<?> delete(@PathVariable("id") Long id);
}
