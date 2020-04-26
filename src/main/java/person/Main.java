package person;

import com.github.javafaker.Faker;
import legoset.model.LegoSet;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Main {

    public static Person randomPerson() {
        Person person = new Person();
        Faker faker = new Faker();
        person.setName(faker.name().fullName());
        Date date = faker.date().birthday();
        person.setDob(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        person.setGender(faker.options().option(Person.Gender.values()));
        Address address = new Address();
        address.city = faker.address().city();
        address.country = faker.address().country();
        address.state = faker.address().state();
        address.streetAddress = faker.address().streetAddress();
        address.zip = faker.address().zipCode();
        person.setAddress(address);
        person.setEmail(faker.internet().emailAddress());
        person.setProfession(faker.company().profession());
        return person;
    }

    public static void main(String[] args) {
        int n = 1000;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-example");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (int i = 1; i <= n; i++) {
                em.persist(randomPerson());
            }
            //System.out.println(em.createNativeQuery("SELECT * FROM Person", Person.class).getResultList());
            em.getTransaction().commit();
        }
        finally {
            em.close();
            emf.close();
        }
    }
}
