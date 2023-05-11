package fr.varex13.quarkuspourapprendre.front.person;

import static fr.varex13.quarkuspourapprendre.front.person.PersonDto.createPersonDto;
import static java.time.Month.MARCH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.PersonService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
class PersonResourceTest {

    @Inject
    private PersonResource personResource;
    @InjectMock
    private PersonService personService;

    @Test
    @Disabled("En attente TI OK")
    void FindAllEndpoint() {

        //GIVEN
        final UUID uuid1 = UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d");
        final String name1 = "AZERTYUIOP";
        final LocalDate dateNaissance1 = LocalDate.now();

        final UUID uuid2 = UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0e");
        final String name2 = "POIUYTREZA";
        final LocalDate dateNaissance2 = LocalDate.of(1977, MARCH, 24);

        //WHEN
        Mockito.when(personService.getAllPersons()).thenReturn(Set.of(
                createPerson(uuid1, name1, dateNaissance1),
                createPerson(uuid2, name2, dateNaissance2)));
        final PersonsDto personsDto = personResource.findAll();

        //THEN
        assertThat(personsDto, notNullValue());
        assertThat(personsDto.getPersonDtos(), hasSize(2));
        assertThat(personsDto.getPersonDtos(), containsInAnyOrder(
                createPersonDto(uuid1.toString(), name1, dateNaissance1.toString()),
                createPersonDto(uuid2.toString(), name2, dateNaissance2.toString())
        ));
    }

    private static Person createPerson(UUID uuid1, String name1, LocalDate dateNaissance1) {
        return Person.personBuilder()
                .uuid(uuid1)
                .nom(name1)
                .dateNaissance(dateNaissance1)
                .build();
    }
}