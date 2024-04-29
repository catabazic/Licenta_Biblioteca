package com.example.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.PreparedStatement;

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

        String createBookTableQuery = "CREATE TABLE " + TABLE_BOOK + " (" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_NAME + " TEXT, " +
                COLUMN_BOOK_AUTHOR_ID + " INTEGER, " +
                COLUMN_BOOK_GENRE_ID + " INTEGER, " +
                COLUMN_BOOK_PHOTO + " TEXT, " +
                COLUMN_BOOK_DESCRIPTION + " TEXT, " +
                COLUMN_BOOK_AVAILABLE + " INTEGER)";
        db.execSQL(createBookTableQuery);

        String createGenreTableQuery = "CREATE TABLE " + TABLE_GENRE + " (" +
                COLUMN_GENRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GENRE_NAME + " TEXT)";
        db.execSQL(createGenreTableQuery);

        String createAuthorTableQuery = "CREATE TABLE " + TABLE_AUTHOR + " (" +
                COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AUTHOR_NAME + " TEXT)";
        db.execSQL(createAuthorTableQuery);

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
        db.close();
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
        SQLiteDatabase db = this.getReadableDatabase();
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
    }

    public boolean authenticateUser(String userMail, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER +
                " WHERE " + COLUMN_USER_EMAIL + " = ?" +
                " AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userMail, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
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

    public void addNewUser(String name, String number, String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PHONE, number);
        values.put(COLUMN_USER_EMAIL,email);
        values.put(COLUMN_USER_PASSWORD,password);
        db.insert(TABLE_USER, null, values);
        db.close();
    }


}
