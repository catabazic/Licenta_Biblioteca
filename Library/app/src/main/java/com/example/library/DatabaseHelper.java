package com.example.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
}
