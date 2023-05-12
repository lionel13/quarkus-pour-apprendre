package fr.varex13.quarkuspourapprendre.domain.person;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.util.Collections.EMPTY_SET;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;
import fr.varex13.quarkuspourapprendre.domain.person.impl.PersonServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PersonServiceTest {

    private PersonService personService;

    private PersonRepository personRepository;

    @BeforeEach
    void init() {
        personRepository = mock(PersonRepository.class);
        personService = new PersonServiceImpl(personRepository);
    }

    @Nested
    class FindAll {

        @Test
        void WhenNoPersonsAreFindThenAnEmptySetIsReturned() {
            when(personRepository.getAllPersons()).thenReturn(EMPTY_SET);

            Set<Person> allPersons = personService.getAllPersons();
            assertThat(allPersons, notNullValue());
            assertThat(allPersons, hasSize(0));
        }

        @Test
        void WhenPersonsAreFindThenThesePersonsAreReturned() {
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
            when(personRepository.getAllPersons()).thenReturn(Set.of(
                    person1,
                    person2
            ));

            Set<Person> allPersons = personService.getAllPersons();
            assertThat(allPersons, notNullValue());
            assertThat(allPersons, hasSize(2));
            assertThat(allPersons, containsInAnyOrder(person1, person2));
        }
    }

    @Nested
    class FindByUuid {

        @Test
        void WhenNoPersonIsFindThenAnExceptionIsThrowed() {
            when(personRepository.getPerson(any())).thenReturn(Optional.empty());
            Executable allPersons = () -> personService.getPerson(UUID.randomUUID());
            PersonNotExistsException personNotExistsException = Assertions.assertThrowsExactly(PersonNotExistsException.class, allPersons);
            assertThat(personNotExistsException.getMessage(), is(("La personne n'existe pas")));
        }

        @Test
        void WhenPersonisFindThenThisPersonIsReturned() throws PersonNotExistsException {
            UUID uuid = UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d");
            Person person = Person.personBuilder()
                    .uuid(uuid)
                    .nom("Nom1")
                    .dateNaissance(LocalDate.of(1980, JANUARY, 1))
                    .build();
            when(personRepository.getPerson(uuid)).thenReturn(Optional.of(person));
            Person personRetrieved = personService.getPerson(uuid);
            assertThat(personRetrieved, notNullValue());
            assertThat(personRetrieved, equalTo(person));
        }
    }

    @Nested
    class addPerson {

        @Test
        void WhenAPersonIsAddedThenThisPersonIsReturned() {
            UUID uuid = UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d");
            Person person = Person.personBuilder()
                    .uuid(uuid)
                    .nom("Nom1")
                    .dateNaissance(LocalDate.of(1980, JANUARY, 1))
                    .build();
            when(personRepository.addPerson(person)).thenReturn(person);
            Person personadded = personService.addPerson(person);
            assertThat(personadded, notNullValue());
            assertThat(personadded, equalTo(person));
        }
    }

    @Nested
    class removePersonByUuid {

        @Test
        void WhenAPersonIsRemovedThenNothingAppend() {
            UUID uuid = UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d");
            doNothing().when(personRepository).removePersonByUuid(uuid);
            Assertions.assertDoesNotThrow(() -> personService.removePersonByUuid(uuid));
        }
    }

}