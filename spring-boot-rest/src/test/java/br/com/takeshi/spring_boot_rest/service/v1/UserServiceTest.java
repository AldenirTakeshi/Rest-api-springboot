package br.com.takeshi.spring_boot_rest.service.v1;

import br.com.takeshi.spring_boot_rest.model.UserEntity;
import br.com.takeshi.spring_boot_rest.repository.UserRepository;
import br.com.takeshi.spring_boot_rest.unitetests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    MockPerson input;

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        input = new MockPerson();
    }

    @Test
    void findById() {
        UserEntity userEntity = input.mockEntity(1);
        userEntity.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        var res = userService.findById(1L);

        assertNotNull(res);
        assertNotNull(res.getId());
        assertNotNull(res.getLinks());

        assertNotNull(res.getLinks().stream().anyMatch(link -> link.getRel().value().equals("self")
                && link.getHref().endsWith("/api/user/v1/6") && link.getType().equals("GET"))
        );
    }

    @Test
    void findAll() {

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
    }

}