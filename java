import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("LESSON 14 - BOOK EQUALITY TEST");
        System.out.println("========================================");

        // Two different objects with the same information
        BookOld oldA = new BookOld("Java Basics", "John Smith", 2020);
        BookOld oldB = new BookOld("Java Basics", "John Smith", 2020);

        Book newA = new Book("Java Basics", "John Smith", 2020);
        Book newB = new Book("Java Basics", "John Smith", 2020);

        // Check 1: equals()
        boolean oldEquals = oldA.equals(oldB);
        boolean newEquals = newA.equals(newB);

        // Check 2: HashSet duplicate
        Set<BookOld> oldSet = new HashSet<>();
        oldSet.add(oldA);
        oldSet.add(oldB);

        Set<Book> newSet = new HashSet<>();
        newSet.add(newA);
        newSet.add(newB);

        // Check 3: ArrayList contains()
        List<BookOld> oldList = new ArrayList<>();
        oldList.add(oldA);

        List<Book> newList = new ArrayList<>();
        newList.add(newA);

        // Check 4: HashMap lookup
        Map<BookOld, String> oldMap = new HashMap<>();
        oldMap.put(oldA, "Found");

        Map<Book, String> newMap = new HashMap<>();
        newMap.put(newA, "Found");

        System.out.printf("%-25s %-15s %-15s%n",
                "CHECK", "BEFORE", "AFTER");
        System.out.println("-------------------------------------------------------");

        System.out.printf("%-25s %-15s %-15s%n",
                "a.equals(b)", oldEquals, newEquals);

        System.out.printf("%-25s %-15d %-15d%n",
                "HashSet size", oldSet.size(), newSet.size());

        System.out.printf("%-25s %-15s %-15s%n",
                "list.contains(b)",
                oldList.contains(oldB), newList.contains(newB));

        System.out.printf("%-25s %-15s %-15s%n",
                "HashMap lookup",
                oldMap.get(oldB), newMap.get(newB));

        // Broken middle case
        System.out.println("\nBROKEN CASE: EQUALS WITHOUT HASHCODE");
        System.out.println("----------------------------------------");

        BookBroken brokenA =
                new BookBroken("Java Basics", "John Smith", 2020);
        BookBroken brokenB =
                new BookBroken("Java Basics", "John Smith", 2020);

        Map<BookBroken, String> brokenMap = new HashMap<>();
        brokenMap.put(brokenA, "Found");

        System.out.println("equals(): " + brokenA.equals(brokenB));
        System.out.println("HashMap lookup: " + brokenMap.get(brokenB));
        System.out.println("Same hashCode? "
                + (brokenA.hashCode() == brokenB.hashCode()));

        // Comparable: sorting by publication year
        System.out.println("\nSORTING BOOKS BY YEAR");
        System.out.println("----------------------------------------");

        List<Book> books = new ArrayList<>();
        books.add(new Book("Advanced Java", "Mary Lee", 2023));
        books.add(new Book("Java Basics", "John Smith", 2020));
        books.add(new Book("OOP Concepts", "David Brown", 2021));

        Collections.sort(books);

        for (Book book : books) {
            System.out.println(book);
        }
    }
}

// Original class: no overrides
class BookOld {
    String title;
    String author;
    int year;

    public BookOld(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}

// Broken class: equals() only
class BookBroken {
    String title;
    String author;
    int year;

    public BookBroken(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BookBroken)) return false;

        BookBroken other = (BookBroken) obj;

        return year == other.year
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author);
    }

    // Intentionally missing hashCode()
}

// Correct class: equals(), hashCode(), Comparable
class Book implements Comparable<Book> {
    String title;
    String author;
    int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;

        Book other = (Book) obj;

        return year == other.year
                && Objects.equals(title, other.title)
                && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

    @Override
    public int compareTo(Book other) {
        return Integer.compare(this.year, other.year);
    }

    @Override
    public String toString() {
        return title + " - " + author + " (" + year + ")";
    }
}
