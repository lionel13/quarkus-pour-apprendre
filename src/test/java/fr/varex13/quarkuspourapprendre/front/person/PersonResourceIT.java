package fr.varex13.quarkuspourapprendre.front.person;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

@QuarkusIntegrationTest
class PersonResourceIT {

    @Test
    void FindAllEndpoint() {

        PersonsDto personsDto = given()
                .when().get("/api/persons")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .body()
                .as(PersonsDto.class);
        System.out.println(personsDto);
    }

}