package csd230.s26.lab1.repositories;

import com.github.javafaker.Faker;
import csd230.s26.lab1.entities.BookEntity;
import csd230.s26.lab1.entities.GuitarEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase; // Fixed import
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;       // Fixed import
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;     // Added missing import
import java.util.Optional; // Added missing import

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Use real MySQL, not H2
@Transactional(propagation = Propagation.NOT_SUPPORTED) // Don't rollback so data persists for inspection
class ProductRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GuitarRepository guitarRepository;

    @Test
    void testSaveAndRetrieveBook() {
        Faker faker = new Faker();

        // 1. Create a fake book
        BookEntity book = new BookEntity(
                faker.book().author(),
                faker.book().title(),
                29.99,
                10
        );

        // 2. Save to database
        bookRepository.save(book);
        Long savedId = book.getId();
        assertNotNull(savedId, "ID should be generated upon saving");

        // 3. Retrieve and Verify
        BookEntity foundBook = bookRepository.findById(savedId).orElseThrow();
        assertEquals(book.getTitle(), foundBook.getTitle());
        assertEquals(book.getAuthor(), foundBook.getAuthor());

        System.out.println("Successfully verified book: " + foundBook.getTitle());
    }

    @Test
    void testCRUDDeleteBook() {
        Faker faker = new Faker();
        BookEntity book = new BookEntity(
                faker.book().author(),
                faker.book().title(),
                19.99,
                5
        );

        bookRepository.save(book);
        Long id = book.getId();
        assertNotNull(id);

        bookRepository.delete(book);

        Optional<BookEntity> deletedBook = bookRepository.findById(id);
        assertTrue(deletedBook.isEmpty(), "Book should not exist in the database after deletion");
        System.out.println("Successfully verified deletion of Book ID: " + id);
    }

    @Test
    void testDerivedQueryFindByAuthor() {
        Faker faker = new Faker();
        String uniqueAuthor = "TestAuthor-" + faker.crypto().md5().substring(0, 5);

        BookEntity book = new BookEntity(
                uniqueAuthor,
                faker.book().title(),
                15.99,
                20
        );
        bookRepository.save(book);

        List<BookEntity> results = bookRepository.findByAuthor(uniqueAuthor);
        assertFalse(results.isEmpty(), "Derived query should return at least one matching book");
        assertEquals(uniqueAuthor, results.get(0).getAuthor());
        System.out.println("Successfully verified derived query for author: " + uniqueAuthor);
    }

    @Test
    void testSaveAndRetrieveNicheGuitar() {
        Faker faker = new Faker();
        String brandName = faker.company().name();

        GuitarEntity guitar = new GuitarEntity(
                brandName,
                "New",
                6,
                true,
                899.99
        );

        guitarRepository.save(guitar);
        Long guitarId = guitar.getId();
        assertNotNull(guitarId, "Niche guitar entity should generate an ID upon saving");

        GuitarEntity foundGuitar = guitarRepository.findById(guitarId).orElseThrow();
        assertEquals(brandName, foundGuitar.getBrand());
        assertEquals(899.99, foundGuitar.getPrice());
        System.out.println("Successfully verified niche entity persistence for brand: " + foundGuitar.getBrand());
    }
}