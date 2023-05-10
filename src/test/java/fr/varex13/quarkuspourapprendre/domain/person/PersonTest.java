package fr.varex13.quarkuspourapprendre.domain.person;

import static java.time.Month.JANUARY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class PersonTest {


    @Test
    void QuandOnCreeUnePersonAlorsOnDoitRetrouverCesValeurs() {

       final Person person = Person.personBuilder()
                .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d"))
                .nom("Nom")
                .dateNaissance(LocalDate.of(1980, JANUARY, 1))
                .build();

        assertThat(person, notNullValue());
        assertThat(person.getUuid(), is(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d")));
        assertThat(person.getNom(), is("Nom"));
        assertThat(person.getDateNaissance(), is(LocalDate.of(1980, JANUARY, 1)));
        assertThat(person.toString(), is("Person{" +
                "uuid=" + UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d") +
                ", nom='Nom'" +
                ", dateNaissance=" + LocalDate.of(1980, JANUARY, 1) +
                '}'));

    }
    @Nested
    class Uuid {
        @Test
        void QuandOnCreeUnePersonAvecUnUuidVideAlorsOnLeveUneException() {

            final Executable nom = () ->
                    Person.personBuilder()
                            .nom("Nom")
                            .dateNaissance(LocalDate.now())
                            .build();
            final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, nom);

            assertThat(illegalArgumentException, notNullValue());
            assertThat(illegalArgumentException.getMessage(), is("uuid ne doit pas être null"));
        }
    }

    @Nested
    class Nom {
        @Test
        void QuandOnCreeUnePersonAvecUnNomVideAlorsOnLeveUneException() {

            final Executable nom = () ->
                    Person.personBuilder()
                            .uuid(UUID.randomUUID())
                            .dateNaissance(LocalDate.now())
                            .build();
            final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, nom);

            assertThat(illegalArgumentException, notNullValue());
            assertThat(illegalArgumentException.getMessage(), is("nom ne doit pas être null"));
        }


        @Test
        void QuandOnCreeUnePersonAvecUnNomDeLongeurNulleAlorsOnLeveUneException() {

            final Executable nom = () ->
                    Person.personBuilder()
                            .uuid(UUID.randomUUID())
                            .nom("")
                            .dateNaissance(LocalDate.now())
                            .build();
            final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, nom);

            assertThat(illegalArgumentException, notNullValue());
            assertThat(illegalArgumentException.getMessage(), is("nom doit être au moins de longueur 1"));
        }
    }

    @Nested
    class DateNaissance{
        @Test
        void QuandOnCreeUnePersonAvecUneDateDeNaissanceVideAlorsOnLeveUneException() {

            final Executable nom = () ->
                    Person.personBuilder()
                            .uuid(UUID.randomUUID())
                            .nom("Nom")
                            .build();
            final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, nom);

            assertThat(illegalArgumentException, notNullValue());
            assertThat(illegalArgumentException.getMessage(), is("dateNaissance ne doit pas être null"));
        }
        @Test
        void QuandOnCreeUnePersonAvecUneDateDeNaissanceDansLeFuturAlorsOnLeveUneException() {

            final Executable nom = () ->
                    Person.personBuilder()
                            .uuid(UUID.randomUUID())
                            .nom("Nom")
                            .dateNaissance(LocalDate.now().plusDays(1))
                            .build();
            final IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, nom);

            assertThat(illegalArgumentException, notNullValue());
            assertThat(illegalArgumentException.getMessage(), is("dateNaissance ne doit pas être dans le futur"));
        }
    }
}