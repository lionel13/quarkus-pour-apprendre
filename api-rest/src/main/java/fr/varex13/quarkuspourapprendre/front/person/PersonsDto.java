package fr.varex13.quarkuspourapprendre.front.person;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import fr.varex13.quarkuspourapprendre.domain.person.Person;

public class PersonsDto {

    private final Set<PersonDto> personDtos;

    private PersonsDto(Set<PersonDto> personDtos) {
        this.personDtos = personDtos;
    }

    public static PersonsDto createPersonsDtoFromPersons(final Set<Person> persons) {
        return new PersonsDto(persons.stream().map(PersonDto::createPersonfromDomain).collect(Collectors.toSet()));
    }

    public static PersonsDto createPersonsDto(final Set<PersonDto> personDtos) {
        return new PersonsDto(personDtos);
    }

    public Set<PersonDto> getPersonDtos() {
        return personDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonsDto that = (PersonsDto) o;

        return Objects.equals(personDtos, that.personDtos);
    }

    @Override
    public int hashCode() {
        return personDtos != null ? personDtos.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PersonsDto{" +
                "personDtos=" + personDtos +
                '}';
    }
}
