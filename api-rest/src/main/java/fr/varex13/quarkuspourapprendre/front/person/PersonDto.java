package fr.varex13.quarkuspourapprendre.front.person;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.varex13.quarkuspourapprendre.domain.person.Person;

public class PersonDto {

    private final String uuid;
    private final String name;
    private final String birth;

    private PersonDto(final String uuid,
                      final String name,
                      final String birth) {
        this.uuid = uuid;
        this.name = name;
        this.birth = birth;
    }

    @JsonCreator
    public static PersonDto createPersonDto(@JsonProperty("uuid") final String uuid,
                                            @JsonProperty("name") final String name,
                                            @JsonProperty("birth") final String birth) {
        return new PersonDto(uuid, name, birth);
    }

    public static PersonDto createPersonfromDomain(final Person person) {
        return new PersonDto(person.getUuid().toString(), person.getNom(), person.getDateNaissance().toString());
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDto personDto = (PersonDto) o;

        if (!Objects.equals(uuid, personDto.uuid)) return false;
        if (!Objects.equals(name, personDto.name)) return false;
        return Objects.equals(birth, personDto.birth);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birth != null ? birth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
