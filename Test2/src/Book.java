public class Book {
    private final String title;

    public Book(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be empty");
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}