package fr.varex13.quarkuspourapprendre.front.person;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;

@QuarkusTest
class PersonResourceIT {

    @Test
    void FindAllEndpoint() throws JsonProcessingException {
        ValidatableResponse validatableResponse = given()
                .when().get("/api/persons")
                .then()
                .assertThat()
                .statusCode(200);
        final String personsDtoAsString = validatableResponse
                .extract()
                .body()
                .asString();

        PersonsDto personsDto = StringToPersonsDto(personsDtoAsString);

        assertThat(personsDto, notNullValue());
        assertThat(personsDto.getPersonDtos(), notNullValue());
        assertThat(personsDto.getPersonDtos(), hasSize(2));
    }

    private PersonsDto StringToPersonsDto(String personsDtoAsString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node =  objectMapper.readTree(personsDtoAsString);
       Set<PersonDto> personsDto = objectMapper.convertValue(node.findPath("personDtos"), new TypeReference<>() {
        });
        return PersonsDto.createPersonsDto(personsDto);
    }
}
