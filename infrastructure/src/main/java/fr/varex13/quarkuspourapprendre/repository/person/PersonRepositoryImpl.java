package fr.varex13.quarkuspourapprendre.repository.person;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.adapter.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {

    private final Set<Person> persons = new HashSet<>();

    @Override
    public Optional<Person> getPerson(final UUID uuid) {
        return persons.stream().filter(person -> person.getUuid().equals(uuid)).findFirst();
    }

    @Override
    public Set<Person> getAllPersons() {
        return persons;
    }

    @Override
    public Person addPerson(final Person person) {
        persons.add(person);
        return person;
    }

    @Override
    public void removePersonByUuid(final UUID uuid) {
        persons.removeIf(person -> person.getUuid().equals(uuid));
    }
}
