import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();
    private final String FILE_NAME = "books.txt";

    public Library() {
        loadFromFile();
    }

    public void addBook(Book book) {
        books.add(book);
        saveToFile();
        System.out.println("Book added successfully!");
    }

    public void issueBook(int id) {
        for (Book book : books) {
            if (book.getBookId() == id && !book.isIssued()) {
                book.issueBook();
                saveToFile();
                System.out.println("Book issued!");
                return;
            }
        }
        System.out.println("Book not found or already issued.");
    }

    public void returnBook(int id) {
        for (Book book : books) {
            if (book.getBookId() == id && book.isIssued()) {
                book.returnBook();
                saveToFile();
                System.out.println("Book returned!");
                return;
            }
        }
        System.out.println("Book not found or not issued.");
    }

    public void displayAvailableBooks() {
        boolean found = false;
        for (Book book : books) {
            if (!book.isIssued()) {
                System.out.println(book.display());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books available.");
        }
    }

    // -------- FILE HANDLING --------

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                books.add(new Book(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2],
                        Boolean.parseBoolean(data[3])
                ));
            }
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}
