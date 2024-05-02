package com.example.library.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.library.Models.Book;
import com.example.library.Models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "biblioteca.db";
    private static final int DATABASE_VERSION = 1;

    // Tabelele și coloanele
    private static final String TABLE_USER = "utilizatori";
    private static final String COLUMN_USER_ID = "ID";
    private static final String COLUMN_USER_NAME = "nume_utilizator";
    private static final String COLUMN_USER_PHOTO = "photo";
    private static final String COLUMN_USER_PHONE = "nr_telefon";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "parola";

    private static final String TABLE_BOOK = "carte";
    private static final String COLUMN_BOOK_ID = "ID";
    private static final String COLUMN_BOOK_NAME = "nume";
    private static final String COLUMN_BOOK_AUTHOR_ID = "id_autor";
    private static final String COLUMN_BOOK_GENRE_ID = "id_genre";
    private static final String COLUMN_BOOK_PHOTO = "photo";
    private static final String COLUMN_BOOK_DESCRIPTION = "descriere";
    private static final String COLUMN_BOOK_AVAILABLE = "disponibile";

    private static final String TABLE_GENRE = "genre";
    private static final String COLUMN_GENRE_ID = "ID";
    private static final String COLUMN_GENRE_NAME = "genre";

    private static final String TABLE_AUTHOR = "autor";
    private static final String COLUMN_AUTHOR_ID = "ID";
    private static final String COLUMN_AUTHOR_NAME = "autor";

    private static final String TABLE_REVIEW = "review";
    private static final String COLUMN_REVIEW_BOOK_ID = "id_carte";
    private static final String COLUMN_REVIEW_USER_ID = "id_user";
    private static final String COLUMN_REVIEW_RATING = "nota";
    private static final String COLUMN_REVIEW_COMMENT = "comentariu";
    private static final String COLUMN_REVIEW_DATE = "data";

    private static final String TABLE_LOAN = "imprumuturi";
    private static final String COLUMN_LOAN_USER_ID = "id_user";
    private static final String COLUMN_LOAN_BOOK_ID = "id_carte";
    private static final String COLUMN_LOAN_REQUEST_DATE = "data_cerere";
    private static final String COLUMN_LOAN_START_DATE = "data_inceput";
    private static final String COLUMN_LOAN_RETURN_DATE = "data_intoarcere";

    private static final String TABLE_CONVERSATION = "conversatii";
    private static final String COLUMN_CONVERSATION_ID = "ID";
    private static final String COLUMN_CONVERSATION_USER1_ID = "id_user1";
    private static final String COLUMN_CONVERSATION_USER2_ID = "id_user2";

    private static final String TABLE_MESSAGE = "mesaje";
    private static final String COLUMN_MESSAGE_CONVERSATION_ID = "id_conversatie";
    private static final String COLUMN_MESSAGE_USER_ID = "id_user";
    private static final String COLUMN_MESSAGE_CONTENT = "continut_mesaj";
    private static final String COLUMN_MESSAGE_DATE = "data_trimis";

    private static final String TABLE_USER_PREFERENCES_GENRE = "UserPreferencesGenre";
    private static final String COLUMN_USER_PREFERENCES_GENRE_USER_ID = "id_user";
    private static final String COLUMN_USER_PREFERENCES_GENRE_GENRE_ID = "id_genre";

    private static final String TABLE_USER_PREFERENCES_AUTHOR = "UserPreferencesAuthor";
    private static final String COLUMN_USER_PREFERENCES_AUTHOR_USER_ID = "id_user";
    private static final String COLUMN_USER_PREFERENCES_AUTHOR_AUTHOR_ID = "id_autor";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_PHOTO + " TEXT, " +
                COLUMN_USER_PHONE + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTableQuery);


        String createGenreTableQuery = "CREATE TABLE " + TABLE_GENRE + " (" +
                COLUMN_GENRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GENRE_NAME + " TEXT)";
        db.execSQL(createGenreTableQuery);

        String createAuthorTableQuery = "CREATE TABLE " + TABLE_AUTHOR + " (" +
                COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AUTHOR_NAME + " TEXT)";
        db.execSQL(createAuthorTableQuery);

        String createBookTableQuery = "CREATE TABLE " + TABLE_BOOK + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_NAME + " TEXT, " +
                COLUMN_BOOK_AUTHOR_ID + " INTEGER NOT NULL REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + "), " +
                COLUMN_BOOK_GENRE_ID + " INTEGER NOT NULL REFERENCES " + TABLE_GENRE + "(" + COLUMN_GENRE_ID + "), " +
                COLUMN_BOOK_PHOTO + " TEXT, " +
                COLUMN_BOOK_DESCRIPTION + " TEXT, " +
                COLUMN_BOOK_AVAILABLE + " INTEGER)";
        db.execSQL(createBookTableQuery);

        String createReviewTableQuery = "CREATE TABLE " + TABLE_REVIEW + " (" +
                COLUMN_REVIEW_BOOK_ID + " INTEGER, " +
                COLUMN_REVIEW_USER_ID + " INTEGER, " +
                COLUMN_REVIEW_RATING + " INTEGER, " +
                COLUMN_REVIEW_COMMENT + " TEXT, " +
                COLUMN_REVIEW_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_REVIEW_BOOK_ID + ") REFERENCES " + TABLE_BOOK + "(" + COLUMN_BOOK_ID + "), " +
                "FOREIGN KEY(" + COLUMN_REVIEW_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createReviewTableQuery);

        String createLoanTableQuery = "CREATE TABLE " + TABLE_LOAN + " (" +
                COLUMN_LOAN_USER_ID + " INTEGER, " +
                COLUMN_LOAN_BOOK_ID + " INTEGER, " +
                COLUMN_LOAN_REQUEST_DATE + " TEXT, " +
                COLUMN_LOAN_START_DATE + " TEXT, " +
                COLUMN_LOAN_RETURN_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_LOAN_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_LOAN_BOOK_ID + ") REFERENCES " + TABLE_BOOK + "(" + COLUMN_BOOK_ID + "))";
        db.execSQL(createLoanTableQuery);

        String createConversationTableQuery = "CREATE TABLE " + TABLE_CONVERSATION + " (" +
                COLUMN_CONVERSATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONVERSATION_USER1_ID + " INTEGER, " +
                COLUMN_CONVERSATION_USER2_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_CONVERSATION_USER1_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_CONVERSATION_USER2_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createConversationTableQuery);

        String createMessageTableQuery = "CREATE TABLE " + TABLE_MESSAGE + " (" +
                COLUMN_MESSAGE_CONVERSATION_ID + " INTEGER, " +
                COLUMN_MESSAGE_USER_ID + " INTEGER, " +
                COLUMN_MESSAGE_CONTENT + " TEXT, " +
                COLUMN_MESSAGE_DATE + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_MESSAGE_CONVERSATION_ID + ") REFERENCES " + TABLE_CONVERSATION + "(" + COLUMN_CONVERSATION_ID + "), " +
                "FOREIGN KEY(" + COLUMN_MESSAGE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(createMessageTableQuery);

        String createUserPreferencesGenreTableQuery = "CREATE TABLE " + TABLE_USER_PREFERENCES_GENRE + " (" +
                COLUMN_USER_PREFERENCES_GENRE_USER_ID + " INTEGER, " +
                COLUMN_USER_PREFERENCES_GENRE_GENRE_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_PREFERENCES_GENRE_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_USER_PREFERENCES_GENRE_GENRE_ID + ") REFERENCES " + TABLE_GENRE + "(" + COLUMN_GENRE_ID + "))";
        db.execSQL(createUserPreferencesGenreTableQuery);

        String createUserPreferencesAuthorTableQuery = "CREATE TABLE " + TABLE_USER_PREFERENCES_AUTHOR + " (" +
                COLUMN_USER_PREFERENCES_AUTHOR_USER_ID + " INTEGER, " +
                COLUMN_USER_PREFERENCES_AUTHOR_AUTHOR_ID + " INTEGER, " +
                "FOREIGN KEY(" + COLUMN_USER_PREFERENCES_AUTHOR_USER_ID + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_USER_PREFERENCES_AUTHOR_AUTHOR_ID + ") REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + "))";
        db.execSQL(createUserPreferencesAuthorTableQuery);
        System.out.println("Suntem in onCrate din dbhelper");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PREFERENCES_GENRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_PREFERENCES_AUTHOR);
        onCreate(db);
    }

    public void insertMockData(){
        System.out.println("Suntem in insertMockData din dbhelper");
        SQLiteDatabase db = this.getWritableDatabase();
        this.onUpgrade(db,0,1);
        db.execSQL("INSERT INTO Autor (autor) VALUES " +
                "('Agatha Christie')," +
                "('Terry Pratchett')," +
                "('J.R.R. Tolkien')," +
                "('Jane Austen')," +
                "('Leo Tolstoy')," +
                "('Charles Dickens')," +
                "('Fyodor Dostoevsky')," +
                "('Mark Twain')," +
                "('Ernest Hemingway')," +
                "('Virginia Woolf')," +
                "('Haruki Murakami')," +
                "('Gabriel Garcia Marquez')," +
                "('Margaret Atwood')," +
                "('George R.R. Martin')," +
                "('Philip K. Dick')," +
                "('Herman Melville')," +
                "('Oscar Wilde')," +
                "('Arthur Conan Doyle')," +
                "('H.P. Lovecraft')," +
                "('Kurt Vonnegut')");

        // Adăugare date mock pentru genuri
        db.execSQL("INSERT INTO Genre (genre) VALUES " +
                "('Mystery')," +
                "('Comedy')," +
                "('Fantasy')," +
                "('Romance')," +
                "('Classic')," +
                "('Science Fiction')," +
                "('Horror')," +
                "('Adventure')," +
                "('Thriller')," +
                "('Historical Fiction')," +
                "('Dystopian')," +
                "('Biography')," +
                "('Young Adult')," +
                "('Satire')," +
                "('Poetry')," +
                "('Crime')," +
                "('Philosophy')," +
                "('Self-Help')," +
                "('Travel')," +
                "('Western')");

        // Adăugare date mock pentru cărți
        db.execSQL("INSERT INTO Carte (nume, id_autor, id_genre, photo, descriere, disponibile) VALUES " +
                "('Murder on the Orient Express', 1, 1, NULL, 'Unul dintre cele mai cunoscute romane de mister scrise de Agatha Christie', 12)," +
                "('Guards! Guards!', 2, 2, NULL, 'O carte din seria Discworld, plină de umor, scrisă de Terry Pratchett', 18)," +
                "('The Hobbit', 3, 3, NULL, 'O aventură epică despre un hobbit ce pornește într-o călătorie extraordinară, scrisă de J.R.R. Tolkien', 20)," +
                "('Pride and Prejudice', 4, 4, NULL, 'Un roman clasic despre dragoste și prejudecăți în societatea engleză din secolul al XIX-lea, scris de Jane Austen', 15)," +
                "('War and Peace', 5, 5, NULL, 'Un roman monumental despre război și pace în Rusia, scris de Lev Tolstoi', 10)," +
                "('Great Expectations', 6, 6, NULL, 'O poveste clasică despre aspirații și deziluzii, scrisă de Charles Dickens', 14)," +
                "('Crime and Punishment', 7, 7, NULL, 'Un roman psihologic despre crima și consecințele sale, scris de Fyodor Dostoevsky', 16)," +
                "('The Adventures of Tom Sawyer', 8, 8, NULL, 'O aventură clasică despre un băiat în Mississippi, scrisă de Mark Twain', 22)," +
                "('The Old Man and the Sea', 9, 9, NULL, 'O poveste despre un bătrân pescar și lupta sa cu un pește gigant, scrisă de Ernest Hemingway', 19)," +
                "('To the Lighthouse', 10, 10, NULL, 'Un roman modernist despre viața și conștiința unei familii, scris de Virginia Woolf', 11)," +
                "('Norwegian Wood', 11, 11, NULL, 'O poveste despre dragoste și pierdere în Tokyo, scrisă de Haruki Murakami', 17)," +
                "('One Hundred Years of Solitude', 12, 12, NULL, 'Un roman magic-realism despre familia Buendía, scris de Gabriel Garcia Marquez', 13)," +
                "('The Handmaid''s Tale', 13, 13, NULL, 'O distopie despre o societate totalitaristă și rolul femeilor, scrisă de Margaret Atwood', 8)," +
                "('A Game of Thrones', 14, 3, NULL, 'Primul roman din seria A Song of Ice and Fire, scrisă de George R.R. Martin', 25)," +
                "('Do Androids Dream of Electric Sheep?', 15, 6, NULL, 'Un roman science fiction despre identitate și realitate, scris de Philip K. Dick', 7)," +
                "('Moby-Dick', 16, 14, NULL, 'Un roman despre obsesie și vânătoare de balene, scris de Herman Melville', 21)," +
                "('The Picture of Dorian Gray', 17, 4, NULL, 'Un roman despre vanitate și moralitate, scris de Oscar Wilde', 18)," +
                "('The Adventures of Sherlock Holmes', 18, 1, NULL, 'O colecție de povestiri despre celebrul detectiv Sherlock Holmes, scrise de Arthur Conan Doyle', 23)," +
                "('At the Mountains of Madness', 19, 7, NULL, 'O poveste cosmic-horror despre o expediție în Antarctica, scrisă de H.P. Lovecraft', 9)," +
                "('Slaughterhouse-Five', 20, 8, NULL, 'Un roman despre război și călătorii în timp, scris de Kurt Vonnegut', 14)");
        db.close();
    }

    @SuppressLint("Range")
    public int authenticateUser(String userMail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_USER_EMAIL + " = ?" +
                " AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userMail, password});
        int userId=-1;
        if(cursor!=null && cursor.moveToFirst()){
            userId= cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
            cursor.close();

        }
        return userId;
    }

    public boolean uniqueEmailRegister(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_USER_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        boolean isValid=cursor.getCount()==0;
        cursor.close();
        return isValid;
    }

    public int addNewUser(String name, String number, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, number);
        values.put(COLUMN_USER_EMAIL,email);
        values.put(COLUMN_USER_PASSWORD,password);
        db.insert(TABLE_USER, null, values);
        db.close();
        return this.authenticateUser(email,password);
    }

    @SuppressLint("Range")
    public List<Book> getPopularBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK;
        Cursor cursor = db.rawQuery(query, null);

        List<Book> booksList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                book.setName(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME)));
                String queryAuthor = "SELECT * FROM " + TABLE_AUTHOR+" WHERE "
                        + COLUMN_AUTHOR_ID + "=?";
                Cursor cursorAuthor = db.rawQuery(queryAuthor, new String[]{cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR_ID))});
                if (cursorAuthor.moveToFirst()) {
                    // Cursor has at least one row, so it's safe to retrieve data
                    book.setAuthor(cursorAuthor.getString(cursorAuthor.getColumnIndex(COLUMN_AUTHOR_NAME)));
                } else {
                    // Cursor is empty, handle this case accordingly (e.g., set a default value for the author)
                    book.setAuthor("Unknown");
                }
                cursorAuthor.close();
                String queryGenre = "SELECT * FROM " + TABLE_GENRE+" WHERE "
                        + COLUMN_GENRE_ID + "=?";
                Cursor cursorGenre = db.rawQuery(queryGenre, new String[]{cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_GENRE_ID))});

                if (cursorGenre.moveToFirst()) {
                    // Cursor has at least one row, so it's safe to retrieve data
                    book.setGenre(cursorGenre.getString(cursorGenre.getColumnIndex(COLUMN_GENRE_NAME)));
                } else {
                    // Cursor is empty, handle this case accordingly (e.g., set a default value for the author)
                    book.setGenre("Unknown");
                }
                cursorGenre.close();

                book.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PHOTO)));
                book.setDisponible(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_AVAILABLE)));
                book.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));
                booksList.add(book);

            } while (cursor.moveToNext());
            cursor.close();
        }
        return booksList;
    }

    @SuppressLint("Range")
    public List<Book> getNewBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOK + " ORDER BY " + COLUMN_BOOK_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        List<Book> booksList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                book.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID)));
                book.setName(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME)));
                String queryAuthor = "SELECT * FROM " + TABLE_AUTHOR+" WHERE "
                        + COLUMN_AUTHOR_ID + "=?";
                Cursor cursorAuthor = db.rawQuery(queryAuthor, new String[]{cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_AUTHOR_ID))});
                if (cursorAuthor.moveToFirst()) {
                    // Cursor has at least one row, so it's safe to retrieve data
                    book.setAuthor(cursorAuthor.getString(cursorAuthor.getColumnIndex(COLUMN_AUTHOR_NAME)));
                } else {
                    // Cursor is empty, handle this case accordingly (e.g., set a default value for the author)
                    book.setAuthor("Unknown");
                }
                cursorAuthor.close();
                String queryGenre = "SELECT * FROM " + TABLE_GENRE+" WHERE "
                        + COLUMN_GENRE_ID + "=?";
                Cursor cursorGenre = db.rawQuery(queryGenre, new String[]{cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_GENRE_ID))});

                if (cursorGenre.moveToFirst()) {
                    // Cursor has at least one row, so it's safe to retrieve data
                    book.setGenre(cursorGenre.getString(cursorGenre.getColumnIndex(COLUMN_GENRE_NAME)));
                } else {
                    // Cursor is empty, handle this case accordingly (e.g., set a default value for the author)
                    book.setGenre("Unknown");
                }
                cursorGenre.close();

                book.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PHOTO)));
                book.setDisponible(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_AVAILABLE)));
                book.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));
                booksList.add(book);
            }
            cursor.close();
        }
        return booksList;
    }

    @SuppressLint("Range")
    public Book getBookByNameAndAuthor(String name, String author){
        Book book = new Book();
        int authorId=-1;

        SQLiteDatabase db = this.getReadableDatabase();
        String queryAuthor = "SELECT * FROM " + TABLE_AUTHOR + " WHERE " +
                COLUMN_AUTHOR_NAME + "=?";
        Cursor cursorAuthor = db.rawQuery(queryAuthor, new String[]{author});
        if (cursorAuthor.moveToFirst()) {
            authorId= cursorAuthor.getInt(cursorAuthor.getColumnIndex(COLUMN_AUTHOR_ID));
        }
        cursorAuthor.close();

        String query = "SELECT * FROM " + TABLE_BOOK + " WHERE " +
                COLUMN_BOOK_NAME + " =? AND " + COLUMN_AUTHOR_ID +
                "=?";
        Cursor cursor = db.rawQuery(query, new String[]{name, String.valueOf(authorId)});
        if (cursor != null && cursor.moveToFirst()) {
            book.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID)));
            book.setName(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME)));
            book.setAuthor(author);
            String queryGenre = "SELECT * FROM " + TABLE_GENRE+" WHERE "
                    + COLUMN_GENRE_ID + "=?";
            Cursor cursorGenre = db.rawQuery(queryGenre, new String[]{cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_GENRE_ID))});

            if (cursorGenre.moveToFirst()) {
                // Cursor has at least one row, so it's safe to retrieve data
                book.setGenre(cursorGenre.getString(cursorGenre.getColumnIndex(COLUMN_GENRE_NAME)));
            } else {
                // Cursor is empty, handle this case accordingly (e.g., set a default value for the author)
                book.setGenre("Unknown");
            }
            cursorGenre.close();

            book.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PHOTO)));
            book.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));
            book.setDisponible(cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_AVAILABLE)));
            cursor.close();
        }

        return book;
    }

    @SuppressLint("Range")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void borrowBook(int idBook, int idUser){
        LocalDate currentDate = LocalDate.now();

        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COLUMN_LOAN_USER_ID, idUser);
            values.put(COLUMN_LOAN_BOOK_ID, idBook);
            values.put(COLUMN_LOAN_REQUEST_DATE, currentDate.toString());

            String query = "SELECT * FROM " + TABLE_BOOK +
                    " WHERE " + COLUMN_BOOK_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(idBook)});

            if(cursor != null && cursor.moveToFirst()){
                int availableBooks = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_AVAILABLE));
                if(availableBooks > 0){
                    values.put(COLUMN_LOAN_START_DATE, currentDate.toString());

                    ContentValues updateValues = new ContentValues();
                    updateValues.put(COLUMN_BOOK_AVAILABLE, availableBooks - 1);
                    String whereClause = COLUMN_BOOK_ID + "=?";
                    String[] whereArgs = {String.valueOf(idBook)};
                    db.beginTransaction();
                    try {
                        db.update(TABLE_BOOK, updateValues, whereClause, whereArgs);
                        db.insert(TABLE_LOAN, null, values);
                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }
                }
            }
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }


    @SuppressLint("Range")
    public List<Book> getBorrowedBooks(int idUser){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOAN +
                " WHERE " + COLUMN_LOAN_USER_ID + " =? AND "
                + COLUMN_LOAN_RETURN_DATE + " IS NULL" ;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser)});

        List<Book> booksList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                if(cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_START_DATE))!=null){
                    book.setState("Disponibil din " + cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_START_DATE)));
                }else{
                    book.setState("Rezervat pe " + cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_REQUEST_DATE)));
                }

                String queryBook = "SELECT * FROM " + TABLE_BOOK +
                        " WHERE " + COLUMN_BOOK_ID + "=?";
                Cursor cursorBook = db.rawQuery(queryBook, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_LOAN_BOOK_ID)))});
                if(cursorBook!=null && cursorBook.moveToFirst()) {
                    System.out.println("YEEEEEEEEEEEEES");
                    book.setId(cursorBook.getInt(cursorBook.getColumnIndex(COLUMN_BOOK_ID)));
                    book.setName(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_NAME)));
                    String queryAuthor = "SELECT * FROM " + TABLE_AUTHOR + " WHERE "
                            + COLUMN_AUTHOR_ID + "=?";
                    Cursor cursorAuthor = db.rawQuery(queryAuthor, new String[]{cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_AUTHOR_ID))});
                    if (cursorAuthor.moveToFirst()) {
                        book.setAuthor(cursorAuthor.getString(cursorAuthor.getColumnIndex(COLUMN_AUTHOR_NAME)));
                    } else {
                        book.setAuthor("Unknown");
                    }
                    cursorAuthor.close();
                    String queryGenre = "SELECT * FROM " + TABLE_GENRE + " WHERE "
                            + COLUMN_GENRE_ID + "=?";
                    Cursor cursorGenre = db.rawQuery(queryGenre, new String[]{cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_GENRE_ID))});

                    if (cursorGenre.moveToFirst()) {
                        book.setGenre(cursorGenre.getString(cursorGenre.getColumnIndex(COLUMN_GENRE_NAME)));
                    } else {
                        book.setGenre("Unknown");
                    }
                    cursorGenre.close();

                    book.setImage(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_PHOTO)));
                    book.setDisponible(cursorBook.getInt(cursorBook.getColumnIndex(COLUMN_BOOK_AVAILABLE)));
                    book.setDescription(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));

                    cursorBook.close();
                }
                booksList.add(book);
            }
            cursor.close();
        }
        return booksList;
    }

    @SuppressLint("Range")
    public List<Book> getAllHistory(int idUser){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOAN +
                " WHERE " + COLUMN_LOAN_USER_ID + " =? " ;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser)});

        List<Book> booksList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();
                if(cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_START_DATE))!=null){
                    book.setState("Disponibil din " + cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_START_DATE)));
                }else{
                    book.setState("Rezervat pe " + cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_REQUEST_DATE)));
                }

                String queryBook = "SELECT * FROM " + TABLE_BOOK +
                        " WHERE " + COLUMN_BOOK_ID + "=?";
                Cursor cursorBook = db.rawQuery(queryBook, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_LOAN_BOOK_ID)))});
                if(cursorBook!=null && cursorBook.moveToFirst()) {
                    System.out.println("YEEEEEEEEEEEEES");
                    book.setId(cursorBook.getInt(cursorBook.getColumnIndex(COLUMN_BOOK_ID)));
                    book.setName(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_NAME)));
                    String queryAuthor = "SELECT * FROM " + TABLE_AUTHOR + " WHERE "
                            + COLUMN_AUTHOR_ID + "=?";
                    Cursor cursorAuthor = db.rawQuery(queryAuthor, new String[]{cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_AUTHOR_ID))});
                    if (cursorAuthor.moveToFirst()) {
                        book.setAuthor(cursorAuthor.getString(cursorAuthor.getColumnIndex(COLUMN_AUTHOR_NAME)));
                    } else {
                        book.setAuthor("Unknown");
                    }
                    cursorAuthor.close();
                    String queryGenre = "SELECT * FROM " + TABLE_GENRE + " WHERE "
                            + COLUMN_GENRE_ID + "=?";
                    Cursor cursorGenre = db.rawQuery(queryGenre, new String[]{cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_GENRE_ID))});

                    if (cursorGenre.moveToFirst()) {
                        book.setGenre(cursorGenre.getString(cursorGenre.getColumnIndex(COLUMN_GENRE_NAME)));
                    } else {
                        book.setGenre("Unknown");
                    }
                    cursorGenre.close();

                    book.setImage(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_PHOTO)));
                    book.setDisponible(cursorBook.getInt(cursorBook.getColumnIndex(COLUMN_BOOK_AVAILABLE)));
                    book.setDescription(cursorBook.getString(cursorBook.getColumnIndex(COLUMN_BOOK_DESCRIPTION)));

                    cursorBook.close();
                }
                booksList.add(book);
            }
            cursor.close();
        }
        return booksList;
    }


    @SuppressLint("Range")
    public User getUserById(int id){
        User user=new User();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_USER_ID + " =? " ;
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if(cursor!=null && cursor.moveToFirst()){
            user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
            user.setNumber(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHONE)));
            user.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PHOTO)));
            cursor.close();
        }
        return user;
    }

    public boolean isBookBorrowed(int idUser, int idBook){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_LOAN +
                " WHERE " + COLUMN_LOAN_USER_ID + " = ? AND " +
                COLUMN_LOAN_BOOK_ID + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUser), String.valueOf(idBook)});
        boolean isValid=cursor.getCount()>0;
        System.out.println(isValid);
        cursor.close();
        return isValid;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public void booksReturn(int idUser) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate twoWeeksAgo = currentDate.minusWeeks(2);
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_LOAN +
                    " WHERE " + COLUMN_LOAN_USER_ID + "=? AND " +
                    COLUMN_LOAN_START_DATE + "=?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(idUser), twoWeeksAgo.toString()});
            if (cursor != null && cursor.moveToFirst()) {
                db.beginTransaction();
                do {
                    ContentValues updateValues = new ContentValues();
                    updateValues.put(COLUMN_LOAN_RETURN_DATE, currentDate.toString());
                    String whereClause = COLUMN_LOAN_BOOK_ID + "=? AND " + COLUMN_LOAN_USER_ID + "=?";
                    String[] whereArgs = {cursor.getString(cursor.getColumnIndex(COLUMN_LOAN_BOOK_ID)), String.valueOf(idUser)};
                    db.update(TABLE_LOAN, updateValues, whereClause, whereArgs);
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exception properly
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void anulateRezervationOfBook(int idUser, int idBook){
        SQLiteDatabase db = null;
        try {
            db.beginTransaction();
            LocalDate currentDate = LocalDate.now();
            ContentValues updateValues = new ContentValues();
            updateValues.put(COLUMN_LOAN_RETURN_DATE, currentDate.toString());
            String whereClause = COLUMN_LOAN_BOOK_ID + "=? AND " + COLUMN_LOAN_USER_ID + "=?";
            String[] whereArgs = {String.valueOf(idBook), String.valueOf(idUser)};
            db.update(TABLE_LOAN, updateValues, whereClause, whereArgs);
            db.setTransactionSuccessful();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public void bookIsAvailable() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_LOAN +
                    " WHERE " + COLUMN_LOAN_START_DATE + " IS NULL AND " +
                    COLUMN_LOAN_RETURN_DATE + " IS NOT NULL";
            cursor = db.rawQuery(query, null);
            if (cursor != null && cursor.moveToFirst()) {
                db.beginTransaction();
                do {
                    String queryBook = "SELECT * FROM " + TABLE_BOOK +
                            " WHERE " + COLUMN_BOOK_ID + "=? AND " +
                            COLUMN_BOOK_AVAILABLE + ">0 ";
                    Cursor cursorBook = db.rawQuery(queryBook, new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_LOAN_BOOK_ID)))});
                    if (cursorBook != null && cursorBook.moveToFirst()) {
                        this.borrowBook(cursor.getInt(cursor.getColumnIndex(COLUMN_LOAN_BOOK_ID)), cursor.getInt(cursor.getColumnIndex(COLUMN_LOAN_USER_ID)));
                    }
                    if (cursorBook != null) {
                        cursorBook.close();
                    }
                } while (cursor.moveToNext());
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }


}
