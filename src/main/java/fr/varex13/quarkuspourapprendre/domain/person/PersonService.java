package fr.varex13.quarkuspourapprendre.domain.person;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;

public interface PersonService {
    Set<Person> getAllPersons();

    Person getPerson(UUID uuid) throws PersonNotExistsException;

    Person addPerson(Person person);

    void removePersonByUuid(UUID uuid);
}
