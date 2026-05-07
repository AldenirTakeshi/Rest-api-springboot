package br.com.takeshi.spring_boot_rest.unitetests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;

import br.com.takeshi.spring_boot_rest.data.dto.v1.UserDto;
import br.com.takeshi.spring_boot_rest.model.UserEntity;

public class MockPerson {


    public UserEntity mockEntity() {
        return mockEntity(0);
    }
    
    public UserDto mockDTO() {
        return mockDTO(0);
    }
    
    public List<UserEntity> mockEntityList() {
        List<UserEntity> persons = new ArrayList<UserEntity>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<UserDto> mockDTOList() {
        List<UserDto> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockDTO(i));
        }
        return persons;
    }
    
    public UserEntity mockEntity(Integer number) {
        UserEntity person = new UserEntity();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public UserDto mockDTO(Integer number) {
        UserDto person = new UserDto();
        person.setAddress("Address Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }
}