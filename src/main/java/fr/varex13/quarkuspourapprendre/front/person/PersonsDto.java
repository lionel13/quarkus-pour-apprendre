package fr.varex13.quarkuspourapprendre.front.person;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fr.varex13.quarkuspourapprendre.domain.person.Person;

public class PersonsDto {

    private final Set<PersonDto> personDtos;

    private PersonsDto(Set<PersonDto> personDtos) {
        this.personDtos = personDtos;
    }

    @JsonCreator
    public static PersonsDto createPersonsDto(@JsonProperty("personDtos")
                                              @JsonDeserialize(using = PersonDtosDeserializer.class) final Set<PersonDto> persons) {
        return new PersonsDto(persons);
    }

    public static PersonsDto createPersonsDtoFromPersons(final Set<Person> persons) {
        return new PersonsDto(persons.stream().map(PersonDto::createPersonfromDomain).collect(Collectors.toSet()));
    }

    public Set<PersonDto> getPersonDtos() {
        return personDtos;
    }

    @Override
    public String toString() {
        return "PersonsDto{" +
                "personDtos=" + personDtos +
                '}';
    }

    private static class PersonDtosDeserializer extends JsonDeserializer<Set<PersonDto>> {
        @Override
        public Set<PersonDto> deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(jsonParser);
            return mapper.convertValue(node.findValues("personDtos"), new TypeReference<>() {
            });
        }
    }
}
