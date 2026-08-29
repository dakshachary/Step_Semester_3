public class M1_LibraryBook {

    static class LibraryBook {
        private String title;
        private String isbn;
        private boolean catalogued;

        // Primary constructor
        public LibraryBook(String title, String isbn) {
            this.title = title;
            this.isbn = (isbn == null || isbn.trim().isEmpty()) ? "PENDING" : isbn;
            this.catalogued = true;
        }

        // Overloaded constructor chains via this(...)
        public LibraryBook(String title) {
            this(title, "PENDING");
        }

        public void printStatus() {
            System.out.println(title + " | " + isbn + " | Catalogued: " + catalogued);
        }
    }

    public static void main(String[] args) {
        String[] titles = {"Clean Code", "Untitled Draft", "1984", "Notes"};
        String[] isbns = {"978-0132350884", "", "9780451524935", ""};

        for (int i = 0; i < titles.length; i++) {
            LibraryBook book = (isbns[i] == null || isbns[i].isEmpty())
                    ? new LibraryBook(titles[i])
                    : new LibraryBook(titles[i], isbns[i]);
            book.printStatus();
        }
    }
}