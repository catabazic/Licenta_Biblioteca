package com.example.library.Database;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirebaseMockData {
    private FirebaseFirestore db;

    public FirebaseMockData() {
        db = FirebaseFirestore.getInstance();
    }

    public void insertMockData() {
        /*String[] authors = {
                "Agatha Christie",
                "Terry Pratchett",
                "J.R.R. Tolkien",
                "Jane Austen",
                "Leo Tolstoy",
                "Charles Dickens",
                "Fyodor Dostoevsky",
                "Mark Twain",
                "Ernest Hemingway",
                "Virginia Woolf",
                "Haruki Murakami",
                "Gabriel Garcia Marquez",
                "Margaret Atwood",
                "George R.R. Martin",
                "Philip K. Dick",
                "Herman Melville",
                "Oscar Wilde",
                "Arthur Conan Doyle",
                "H.P. Lovecraft",
                "Kurt Vonnegut"
        };

        for (String author : authors) {
            Map<String, Object> authorMap = new HashMap<>();
            authorMap.put("name", author);
            db.collection("authors")
                    .add(authorMap);
        }*/

        /*String[] genres = {
                "Mystery",
                "Comedy",
                "Fantasy",
                "Romance",
                "Classic",
                "Science Fiction",
                "Horror",
                "Adventure",
                "Thriller",
                "Historical Fiction",
                "Dystopian",
                "Biography",
                "Young Adult",
                "Satire",
                "Poetry",
                "Crime",
                "Philosophy",
                "Self-Help",
                "Travel",
                "Western"
        };

        for (String genre : genres) {
            Map<String, Object> genreMap = new HashMap<>();
            genreMap.put("name", genre);
            db.collection("genres")
                    .add(genreMap);
        }*/


        addBook(db, "Murder on the Orient Express", null, "Unul dintre cele mai cunoscute romane de mister scrise de Agatha Christie", 12, "20-05-2023");
        addBook(db, "Guards! Guards!", null, "O carte din seria Discworld, plină de umor, scrisă de Terry Pratchett", 18, "20-05-2021");
        addBook(db, "The Hobbit", null, "O aventură epică despre un hobbit ce pornește într-o călătorie extraordinară, scrisă de J.R.R. Tolkien", 20, "20-05-2024");
        addBook(db, "Pride and Prejudice", null, "Un roman clasic despre dragoste și prejudecăți în societatea engleză din secolul al XIX-lea, scris de Jane Austen", 15, "20-05-2020");
        addBook(db, "War and Peace", null, "Un roman monumental despre război și pace în Rusia, scris de Lev Tolstoi", 10, "10-05-2023");
        addBook(db, "Great Expectations", null, "O poveste clasică despre aspirații și deziluzii, scrisă de Charles Dickens", 14, "10-05-2022");
        addBook(db, "Crime and Punishment", null, "Un roman psihologic despre crima și consecințele sale, scris de Fyodor Dostoevsky", 16, "10-05-2021");
        addBook(db, "The Adventures of Tom Sawyer", null, "O aventură clasică despre un băiat în Mississippi, scrisă de Mark Twain", 22, "10-05-2020");
        addBook(db, "The Old Man and the Sea", null, "O poveste despre un bătrân pescar și lupta sa cu un pește gigant, scrisă de Ernest Hemingway", 19, "10-01-2023");
        addBook(db, "To the Lighthouse", null, "Un roman modernist despre viața și conștiința unei familii, scris de Virginia Woolf", 11, "10-01-2021");
        addBook(db, "Norwegian Wood", null, "O poveste despre dragoste și pierdere în Tokyo, scrisă de Haruki Murakami", 17, "10-01-2022");
        addBook(db, "One Hundred Years of Solitude", null, "Un roman magic-realism despre familia Buendía, scris de Gabriel Garcia Marquez", 13, "10-01-2024");
        addBook(db, "The Handmaid's Tale", null, "O distopie despre o societate totalitaristă și rolul femeilor, scrisă de Margaret Atwood", 8, "10-01-2020");
        addBook(db, "A Game of Thrones", null, "Primul roman din seria A Song of Ice and Fire, scrisă de George R.R. Martin", 25, "10-11-2023");
        addBook(db, "Do Androids Dream of Electric Sheep?", null, "Un roman science fiction despre identitate și realitate, scris de Philip K. Dick", 7, "10-11-2023");
        addBook(db, "Moby-Dick", null, "Un roman despre obsesie și vânătoare de balene, scris de Herman Melville", 21, "10-11-2021");
        addBook(db, "The Picture of Dorian Gray", null, "Un roman despre vanitate și moralitate, scris de Oscar Wilde", 18, "10-11-2020");
        addBook(db, "The Adventures of Sherlock Holmes", null, "O colecție de povestiri despre celebrul detectiv Sherlock Holmes, scrise de Arthur Conan Doyle", 23, "10-11-2022");
        addBook(db, "At the Mountains of Madness", null, "O poveste cosmic-horror despre o expediție în Antarctica, scrisă de H.P. Lovecraft", 9, "11-11-2023");
        addBook(db, "Slaughterhouse-Five", null, "Un roman despre război și călătorii în timp, scris de Kurt Vonnegut", 14, "11-11-2021");

        /*addUser(db, "John Doe", "photo_url", "123456789", "john@example.com", "password");
        addUser(db, "Jane Smith", "photo_url", "987654321", "jane@example.com", "password");
        addUser(db, "Alice Johnson", "photo_url", "555555555", "alice@example.com", "password");
        addUser(db, "Bob Williams", "photo_url", "777777777", "bob@example.com", "password");
        addUser(db, "Eve Brown", "photo_url", "999999999", "eve@example.com", "password");
*/
//        addReview(db, 1, 1, 4.5, "Great book", "Enjoyed reading it", "2024-05-08");
//        addReview(db, 2, 2, 3.0, "Okay book", "Not bad", "2024-05-08");
//        addReview(db, 3, 3, 5.0, "Excellent book", "Highly recommended", "2024-05-08");
//        addReview(db, 1, 4, 2.5, "Disappointing", "Expected more", "2024-05-08");
//        addReview(db, 2, 5, 4.0, "Nice read", "Enjoyable", "2024-05-08");

//        addLoan(db, 1, 1, "2024-05-08", "2024-05-10", "2024-05-20");
//        addLoan(db, 2, 2, "2024-05-08", "2024-05-12", "2024-05-25");
//        addLoan(db, 3, 3, "2024-05-08", "2024-05-15", "2024-05-30");
//        addLoan(db, 4, 1, "2024-05-08", "2024-05-18", "2024-06-05");
//        addLoan(db, 5, 2, "2024-05-08", "2024-05-20", "2024-06-10");
    }

//    public static void addLoan(FirebaseFirestore db, int userId, int bookId, String requestDate, String startDate, String returnDate) {
//        // Create a map to hold the loan's data
//        Map<String, Object> loanData = new HashMap<>();
//        loanData.put("userId", userId);
//        loanData.put("bookId", bookId);
//        loanData.put("requestDate", requestDate);
//        loanData.put("startDate", startDate);
//        loanData.put("returnDate", returnDate);
//
//        db.collection("loan")
//                .add(loanData);
//    }

//    public static void addReview(FirebaseFirestore db, int bookId, int userId, double rating, String title, String comment, String date) {
//        // Create a map to hold the review's data
//        Map<String, Object> reviewData = new HashMap<>();
//        reviewData.put("bookId", bookId);
//        reviewData.put("userId", userId);
//        reviewData.put("rating", rating);
//        reviewData.put("title", title);
//        reviewData.put("comment", comment);
//        reviewData.put("date", date);
//
//        db.collection("reviews")
//                .add(reviewData);
//    }

    public static void addUser(FirebaseFirestore db, String userName, String photo, String phone, String email, String password) {
        // Create a map to hold the user's data
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", userName);
        userData.put("photo", photo);
        userData.put("phone", phone);
        userData.put("genres", null);
        userData.put("authors", null);
        userData.put("email", email);
        userData.put("password", password);

        // Add a new document to the "users" collection with the user's data
        db.collection("users")
                .add(userData);
    }

    public static void addBook(FirebaseFirestore db, String bookName, String photo, String description, int available, String dataPublicatie) {
        Map<String, Object> bookData = new HashMap<>();
        bookData.put("name", bookName);
        bookData.put("photo", photo);
        bookData.put("genres", null);
        bookData.put("authors", null);
        bookData.put("description", description);
        bookData.put("available", available);
        bookData.put("data", dataPublicatie);

        db.collection("books")
                .add(bookData);
    }

}
