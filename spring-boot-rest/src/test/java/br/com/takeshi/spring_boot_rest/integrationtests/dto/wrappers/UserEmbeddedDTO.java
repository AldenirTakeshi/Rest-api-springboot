package br.com.takeshi.spring_boot_rest.integrationtests.dto.wrappers;

import br.com.takeshi.spring_boot_rest.integrationtests.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class UserEmbeddedDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("people")
    private List<UserDTO> user;

    public UserEmbeddedDTO() {
    }

    public UserEmbeddedDTO(List<UserDTO> user) {
        this.user = user;
    }

    public List<UserDTO> getUser() {
        return user;
    }

    public void setUser(List<UserDTO> user) {
        this.user = user;
    }
}