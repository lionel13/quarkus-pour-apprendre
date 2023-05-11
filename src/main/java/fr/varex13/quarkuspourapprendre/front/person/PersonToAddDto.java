package fr.varex13.quarkuspourapprendre.front.person;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.varex13.quarkuspourapprendre.domain.person.Person;

public class PersonToAddDto {
    private final String name;
    private final String birth;

    private PersonToAddDto(final String name,
                           final String birth) {
        this.name = name;
        this.birth = birth;
    }

    @JsonCreator
    public static PersonToAddDto createPersonDto(@JsonProperty("name") final String name,
                                                 @JsonProperty("birth") final String birth) {
        return new PersonToAddDto(name, birth);
    }


    public static Person createDomainfromDto(final PersonToAddDto person) {
        return Person.personBuilder()
                .uuid(UUID.randomUUID())
                .nom(person.getName())
                .dateNaissance(LocalDate.parse(person.getBirth())).build();
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
