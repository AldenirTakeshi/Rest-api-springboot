package br.com.takeshi.spring_boot_rest.integrationtests.dto.wrappers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class WrapperUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("_embedded")
    private UserEmbeddedDTO embedded;

    public WrapperUserDTO() {}

    public UserEmbeddedDTO getEmbedded() {
        return embedded;
    }

    public void setEmbedded(UserEmbeddedDTO embedded) {
        this.embedded = embedded;
    }
}
