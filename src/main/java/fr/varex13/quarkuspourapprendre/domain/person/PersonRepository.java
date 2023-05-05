package fr.varex13.quarkuspourapprendre.domain.person;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface PersonRepository {

    Optional<Person> getPerson(UUID uuid);

    Set<Person> getAllPersons();

    Person addPerson(Person person);

    void removePersonByUuid(UUID uuid);
}
