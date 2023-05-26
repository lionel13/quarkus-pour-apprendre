package fr.varex13.quarkuspourapprendre.domain.person.adapter;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;

public interface PersonRepository {

    Optional<Person> getPerson(UUID uuid);

    Set<Person> getAllPersons();

    Person addPerson(Person person);

    void removePersonByUuid(UUID uuid);
}
