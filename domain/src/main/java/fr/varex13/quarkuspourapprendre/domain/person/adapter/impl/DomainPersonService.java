package fr.varex13.quarkuspourapprendre.domain.person.adapter.impl;

import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.adapter.PersonRepository;
import fr.varex13.quarkuspourapprendre.domain.person.adapter.PersonService;
import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;

public class DomainPersonService implements PersonService {

    private final PersonRepository personRepository;

    public DomainPersonService(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Set<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    @Override
    public Person getPerson(final UUID uuid) throws PersonNotExistsException {
        return personRepository.getPerson(uuid).orElseThrow(() -> new PersonNotExistsException(uuid));
    }

    @Override
    public Person addPerson(final Person person) {
        return personRepository.addPerson(person);
    }

    @Override
    public void removePersonByUuid(UUID uuid) {
        personRepository.removePersonByUuid(uuid);
    }
}
