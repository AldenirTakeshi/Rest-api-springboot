package br.com.takeshi.spring_boot_rest.integrationtests.controllers.withjson;

import br.com.takeshi.spring_boot_rest.config.TestConfigs;
import br.com.takeshi.spring_boot_rest.integrationtests.dto.UserDTO;
import br.com.takeshi.spring_boot_rest.integrationtests.dto.wrappers.WrapperUserDTO;
import br.com.takeshi.spring_boot_rest.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
class UserControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static UserDTO userDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        specification = new RequestSpecBuilder()
                .setBasePath("/api/user/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        userDTO = new UserDTO();
    }

    @Test
    @Order(1)
    void create() throws Exception {
        mockUser();

        var content = given(specification)
                .contentType(ContentType.JSON)
                .body(userDTO)
                .when().post()
                .then().statusCode(200)
                .extract().body().asString();

        UserDTO created = objectMapper.readValue(content, UserDTO.class);
        userDTO = created;

        assertNotNull(created.getId());
        assertTrue(created.getId() > 0);
        assertEquals("Linus", created.getFirstName());
        assertEquals("Torvalds", created.getLastName());
        assertEquals("Helsinki - Finland", created.getAddress());
        assertEquals("Male", created.getGender());
    }

    @Test
    @Order(2)
    void findById() throws Exception {
        var content = given(specification)
                .pathParam("id", userDTO.getId())
                .when().get("/{id}")
                .then().statusCode(200)
                .extract().body().asString();

        UserDTO found = objectMapper.readValue(content, UserDTO.class);

        assertEquals(userDTO.getId(), found.getId());
        assertEquals("Linus", found.getFirstName());
        assertEquals("Torvalds", found.getLastName());
        assertEquals("Helsinki - Finland", found.getAddress());
        assertEquals("Male", found.getGender());
    }

    @Test
    @Order(3)
    void update() throws Exception {
        userDTO.setLastName("Benedict Torvalds");

        var content = given(specification)
                .contentType(ContentType.JSON)
                .pathParam("id", userDTO.getId())
                .body(userDTO)
                .when().put("/{id}")
                .then().statusCode(200)
                .extract().body().asString();

        UserDTO updated = objectMapper.readValue(content, UserDTO.class);

        assertEquals(userDTO.getId(), updated.getId());
        assertEquals("Linus", updated.getFirstName());
        assertEquals("Benedict Torvalds", updated.getLastName());
    }

    @Test
    @Order(4)
    void findUserByName() throws Exception {
        var content = given(specification)
                .pathParam("firstName", "Linus")
                .queryParam("page", 0)
                .queryParam("size", 12)
                .queryParam("direction", "asc")
                .when().get("/findUserByName/{firstName}")
                .then().statusCode(200)
                .extract().body().asString();

        WrapperUserDTO wrapper = objectMapper.readValue(content, WrapperUserDTO.class);
        List<UserDTO> users = wrapper.getEmbedded().getUser();

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.stream().allMatch(u -> "Linus".equals(u.getFirstName())));
    }

    @Test
    @Order(5)
    void findAll() throws Exception {
        var content = given(specification)
                .queryParam("page", 0)
                .queryParam("size", 12)
                .queryParam("direction", "asc")
                .when().get()
                .then().statusCode(200)
                .extract().body().asString();

        WrapperUserDTO wrapper = objectMapper.readValue(content, WrapperUserDTO.class);
        List<UserDTO> users = wrapper.getEmbedded().getUser();

        assertNotNull(users);
        assertFalse(users.isEmpty());

        UserDTO first = users.get(0);
        assertNotNull(first.getId());
        assertNotNull(first.getFirstName());
    }

    @Test
    @Order(6)
    void disableUser() {
        given(specification)
                .pathParam("id", userDTO.getId())
                .when().patch("/{id}/disable")
                .then().statusCode(200)
                .body("id", equalTo(userDTO.getId().intValue()))
                .body("enabled", equalTo(false));
    }

    @Test
    @Order(7)
    void delete() {
        given(specification)
                .pathParam("id", userDTO.getId())
                .when().delete("/{id}")
                .then().statusCode(204);
    }

    private static void mockUser() {
        userDTO.setFirstName("Linus");
        userDTO.setLastName("Torvalds");
        userDTO.setAddress("Helsinki - Finland");
        userDTO.setGender("Male");
    }
}
