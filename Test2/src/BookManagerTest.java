import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookManagerTest {

    @Test
    void addBookIncreasesCount() {
        BookManager manager = new BookManager();

        manager.addBook(new Book("Clean Code"));
        manager.addBook(new Book("Effective Java"));

        assertEquals(2, manager.count());
    }

    @Test
    void findBookByTitle() {
        BookManager manager = new BookManager();

        manager.addBook(new Book("Clean Code"));

        Book found = manager.findByTitle("Clean Code");

        assertNotNull(found);
        assertEquals("Clean Code", found.getTitle());
    }

    @Test
    void checkBookExistsByTitle() {
        BookManager manager = new BookManager();

        manager.addBook(new Book("Clean Code"));

        assertTrue(manager.existsByTitle("Clean Code"));
    }

    @Test
    void returnsFalseWhenBookTitleDoesNotExist() {
        BookManager manager = new BookManager();

        manager.addBook(new Book("Clean Code"));

        assertFalse(manager.existsByTitle("Unknown Book"));
    }
}