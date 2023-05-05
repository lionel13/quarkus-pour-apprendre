package fr.varex13.quarkuspourapprendre.front.person;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

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

    @JsonbCreator
    public static PersonDto createPersonDto(@JsonbProperty("uuid") final String uuid,
                                            @JsonbProperty("name") final String name,
                                            @JsonbProperty("birth") final String birth) {
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
    public String toString() {
        return "PersonDto{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", birth='" + birth + '\'' +
                '}';
    }
}
