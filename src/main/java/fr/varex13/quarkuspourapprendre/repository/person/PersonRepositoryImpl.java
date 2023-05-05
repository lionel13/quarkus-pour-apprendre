package fr.varex13.quarkuspourapprendre.repository.person;

import static java.time.Month.MARCH;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import fr.varex13.quarkuspourapprendre.domain.person.Person;
import fr.varex13.quarkuspourapprendre.domain.person.PersonRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepositoryImpl implements PersonRepository {

    private final Set<Person> persons = new HashSet<>() {{
        add(Person.personBuilder()
                .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0d"))
                .nom("AZERTYUIOP")
                .dateNaissance(LocalDate.now())
                .build());
        add(Person.personBuilder()
                .uuid(UUID.fromString("4fc3f66c-8e76-4b53-9889-c78256836b0e"))
                .nom("POIUYTREZA")
                .dateNaissance(LocalDate.of(1977, MARCH, 24))
                .build());
    }};

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
