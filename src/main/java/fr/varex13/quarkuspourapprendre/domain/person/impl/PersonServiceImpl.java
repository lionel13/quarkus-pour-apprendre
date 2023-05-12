package fr.varex13.quarkuspourapprendre.domain.person.impl;

import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.PersonRepository;
import fr.varex13.quarkuspourapprendre.domain.person.PersonService;
import fr.varex13.quarkuspourapprendre.domain.person.exception.PersonNotExistsException;

public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;

    public PersonServiceImpl(final PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Set<Person> getAllPersons() {
        return personRepository.getAllPersons();
    }

    @Override
    public Person getPerson(final UUID uuid) throws PersonNotExistsException {
        return personRepository.getPerson(uuid).orElseThrow(PersonNotExistsException::new);
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
