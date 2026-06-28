package br.com.takeshi.spring_boot_rest.integrationtests.swagger;

import br.com.takeshi.spring_boot_rest.config.TestConfigs;
import br.com.takeshi.spring_boot_rest.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerPage() {
        var content = RestAssured.given().basePath("/swagger-ui/index.html").port(TestConfigs.SERVER_PORT)
                .when().get().then().statusCode(200).extract().body().asString();
        assertTrue(content.contains("Swagger UI"));
    }

}
