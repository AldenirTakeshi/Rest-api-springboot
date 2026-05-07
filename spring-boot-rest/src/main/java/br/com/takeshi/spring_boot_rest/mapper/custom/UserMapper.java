package br.com.takeshi.spring_boot_rest.mapper.custom;

import br.com.takeshi.spring_boot_rest.data.dto.v2.UserDtoV2;
import br.com.takeshi.spring_boot_rest.model.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserMapper {

    public UserDtoV2 convertEntityToDto(UserEntity user) {
        UserDtoV2 userDtoV2 = new UserDtoV2();
        userDtoV2.setId(user.getId());
        userDtoV2.setFirstName(user.getFirstName());
        userDtoV2.setLastName(user.getLastName());
        userDtoV2.setBirthday(new Date());
        userDtoV2.setAddress(user.getAddress());
        userDtoV2.setGender(user.getGender());
        return userDtoV2;
    }

    public UserEntity convetDtoToEntity(UserDtoV2 user) {
        UserEntity UserEntity = new UserEntity();
        UserEntity.setFirstName(user.getFirstName());
        UserEntity.setLastName(user.getLastName());
//        UserEntity.setBirthday(new Date());
        UserEntity.setAddress(user.getAddress());
        UserEntity.setGender(user.getGender());
        return UserEntity;
    }
}
