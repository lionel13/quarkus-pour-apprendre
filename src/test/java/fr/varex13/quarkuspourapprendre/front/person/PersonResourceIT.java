package fr.varex13.quarkuspourapprendre.front.person;

import static fr.varex13.quarkuspourapprendre.front.person.PersonsDto.createPersonsDtoFromPersons;
import static io.restassured.RestAssured.given;
import static java.lang.Boolean.FALSE;
import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.CREATED;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.NOT_FOUND;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.NO_CONTENT;
import static org.jboss.resteasy.reactive.RestResponse.StatusCode.OK;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.PersonRepository;
import fr.varex13.quarkuspourapprendre.front.ErrorMessage;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@QuarkusTest
class PersonResourceIT {

    @Inject
    private PersonRepository personRepository;

    @BeforeEach
    void init() {
        purgeData();
    }

    @Nested
    class findAll {

        @Test
        void WhenNoPersonsAreFindThenAnEmptySetIsReturned() throws JsonProcessingException {
            ValidatableResponse validatableResponse = given()
                    .when().get("/api/persons")
                    .then()
                    .assertThat()
                    .statusCode(OK);
            final String personsDtoAsString = validatableResponse
                    .extract()
                    .body()
                    .asString();

            PersonsDto personsDto = stringToPersonsDto(personsDtoAsString);

            assertThat(personsDto, notNullValue());
            assertThat(personsDto.getPersonDtos(), notNullValue());
            assertThat(personsDto.getPersonDtos(), hasSize(0));
        }

        @Test
        void WhenPersonsAreFindThenThesePersonsAreReturned() throws JsonProcessingException {
            Person person1 = Person.personBuilder()
                    .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d"))
                    .nom("Nom1")
                    .dateNaissance(LocalDate.of(1980, JANUARY, 1))
                    .build();
            Person person2 = Person.personBuilder()
                    .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0e"))
                    .nom("Nom2")
                    .dateNaissance(LocalDate.of(1977, DECEMBER, 31))
                    .build();
            personRepository.addPerson(person1);
            personRepository.addPerson(person2);

            ValidatableResponse validatableResponse = given()
                    .when().get("/api/persons")
                    .then()
                    .assertThat()
                    .statusCode(OK);
            final String personsDtoAsString = validatableResponse
                    .extract()
                    .body()
                    .asString();

            PersonsDto personsDto = stringToPersonsDto(personsDtoAsString);

            assertThat(personsDto, notNullValue());
            assertThat(personsDto.getPersonDtos(), notNullValue());
            assertThat(personsDto.getPersonDtos(), hasSize(2));
            assertThat(personsDto, equalTo(createPersonsDtoFromPersons(Set.of(person1,
                    person2))));
        }
    }

    @Nested
    class FindByUuid {

        @Test
        void WhenNoPersonIsFindThenAnExceptionIsThrowed() {
            final UUID uuid = UUID.randomUUID();
            ValidatableResponse validatableResponse = given()
                    .when().get("/api/persons/" + uuid)
                    .then()
                    .assertThat()
                    .statusCode(NOT_FOUND);
            final ErrorMessage errorMessage = validatableResponse
                    .extract()
                    .body()
                    .as(ErrorMessage.class);
            assertThat(errorMessage, notNullValue());
            assertThat(errorMessage.message(), is("La personne avec l'UUID '" + uuid + "' n'existe pas"));
            assertThat(errorMessage.status(), is(FALSE));
        }

        @Test
        void WhenPersonisFindThenThisPersonIsReturned() throws JsonProcessingException {
            Person person = Person.personBuilder()
                    .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d"))
                    .nom("Nom1")
                    .dateNaissance(LocalDate.of(1980, JANUARY, 1))
                    .build();
            personRepository.addPerson(person);

            ValidatableResponse validatableResponse = given()
                    .when().get("/api/persons/" + UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d"))
                    .then()
                    .assertThat()
                    .statusCode(OK);
            final String personsDtoAsString = validatableResponse
                    .extract()
                    .body()
                    .asString();

            PersonDto personsDto = stringToPersonDto(personsDtoAsString);
            assertThat(personsDto, notNullValue());
            assertThat(personsDto.getUuid(), is("4fc3f66c-8e76-4b53-9889-c78256836b0d"));
            assertThat(personsDto.getName(), is("Nom1"));
            assertThat(personsDto.getBirth(), is("1980-01-01"));
        }
    }

    @Nested
    class addPerson {

        @Test
        void WhenAPersonIsAddedThenThisPersonIsReturned() throws JsonProcessingException {
            ValidatableResponse validatableResponse =
                    given().
                            contentType(ContentType.JSON)
                            .body(PersonToAddDto.createPersonToAddDto("Nom1", "1980-01-01"))
                            .when()
                            .post("/api/persons")
                            .then()
                            .assertThat()
                            .statusCode(CREATED);

            final String personsDtoAsString = validatableResponse
                    .extract()
                    .body()
                    .asString();

            PersonDto personsDto = stringToPersonDto(personsDtoAsString);
            assertThat(personsDto, notNullValue());
            assertThat(personsDto.getUuid(), notNullValue());
            assertThat(personsDto.getName(), is("Nom1"));
            assertThat(personsDto.getBirth(), is("1980-01-01"));
        }
    }

    @Nested
    class removePersonByUuid {

        @Test
        void WhenAPersonIsRemovedThenNothingAppend() {
            ValidatableResponse validatableResponse = given()
                    .when().delete("/api/persons/" + UUID.randomUUID())
                    .then()
                    .assertThat()
                    .statusCode(NO_CONTENT);
        }
    }

    private PersonDto stringToPersonDto(String personDtoAsString) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode node = objectMapper.readTree(personDtoAsString);
        return objectMapper.convertValue(node, new TypeReference<>() {
        });
    }

    private PersonsDto stringToPersonsDto(String personsDtoAsString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(personsDtoAsString);
        Set<PersonDto> personsDto = objectMapper.convertValue(
                node.findPath("personDtos"),
                new TypeReference<>() {
                });
        return PersonsDto.createPersonsDto(personsDto);
    }

    private void purgeData() {
        personRepository.getAllPersons().clear();
    }
}
