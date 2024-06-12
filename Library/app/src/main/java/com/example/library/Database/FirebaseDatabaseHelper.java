package com.example.library.Database;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.library.Models.Chat;
import com.example.library.Models.DB.Author;
import com.example.library.Models.DB.Book;
import com.example.library.Models.DB.Genre;
import com.example.library.Models.DB.Message;
import com.example.library.Models.DB.Review;
import com.example.library.Models.DB.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.Query;
//import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseDatabaseHelper {
    private FirebaseFirestore db;

    public FirebaseDatabaseHelper(){
        db = FirebaseFirestore.getInstance();
    }

    public Task<String> addNewUser(String name, String number, String email, String password) {
        Map<String, Object> newUser = new HashMap<>();
        newUser.put("name", name);
        newUser.put("number", number);
        newUser.put("email", email);
        newUser.put("password", password);

        return db.collection("users")
                .add(newUser)
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return Tasks.forResult(task.getResult().getId());
                    } else {
                        Exception e = task.getException();
                        System.err.println("Error adding user: " + e.getMessage());
                        return Tasks.forException(e);
                    }
                });
    }

    public Task<String> authentificateUser(String userMail, String password) {
        return db.collection("users")
                .whereEqualTo("email", userMail)
                .whereEqualTo("password", password)
                .limit(1)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null && !task.getResult().isEmpty()) {
                        return task.getResult().getDocuments().get(0).getId();
                    } else {
                        return null;
                    }
                });
    }

    public Task<Boolean> isPasswordOk(String id, String password) {
        return db.collection("users")
                .document(id)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String realPassword = documentSnapshot.getString("password");
                        return realPassword != null && realPassword.equals(password);
                    } else {
                        return false;
                    }
                });
    }

    public Task<Void> changePassword(String id, String password) {
        return db.collection("users")
                .document(id)
                .update("password", password);
    }

    public Task<Void> changeEmail(String id, String email) {
        return db.collection("users")
                .document(id)
                .update("email", email);
    }

    public Task<Void> changePhoneNumber(String id, String phone){
        return db.collection("users")
                .document(id)
                .update("number", phone);
    }

    public Task<Void> changeName(String id, String name){
        return db.collection("users")
                .document(id)
                .update("name", name);
    }

    public Task<Boolean> uniqueEmailRegister(String email) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("users")
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            QuerySnapshot querySnapshot = task.getResult();
                            boolean isUnique = querySnapshot.isEmpty(); // Set isUnique based on query result
                            taskCompletionSource.setResult(isUnique); // Resolve the task with the result
                        } else {
                            taskCompletionSource.setException(task.getException()); // Propagate the exception
                        }
                    }
                });

        return taskCompletionSource.getTask();
    }


    public Task<Void> addPreferencesGenre(String userId, String genre) {
        DocumentReference userRef = db.collection("users").document(userId);
        return userRef.update("preferredGenres", FieldValue.arrayUnion(genre));
    }

    public Task<Void> addPreferencesAuthor(String userId, String author) {
        DocumentReference userRef = db.collection("users").document(userId);
        return userRef.update("preferredAuthors", FieldValue.arrayUnion(author));
    }

    public Task<List<Book>> getNewBooks() {
        return db.collection("books")
                .orderBy("data", Query.Direction.DESCENDING)
                .get()
                .onSuccessTask(querySnapshot -> {
                    List<Book> booksList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Book book = new Book();
                        book.setId(document.getId());
                        book.setName(document.getString("name"));
                        Long available = document.getLong("available");
                        if (available != null) {
                            book.setDisponible(available.intValue());
                        }
                        List<String> genres = (List<String>) document.get("genres");
                        if (genres != null) {
                            book.setGenres(genres);
                        } else {
                            book.setGenres(Collections.emptyList());
                        }
                        List<String> authors = (List<String>) document.get("authors");
                        if (authors != null) {
                            book.setAuthors(authors);
                        } else {
                            book.setAuthors(Collections.emptyList());
                        }
                        book.setDescription(document.getString("description"));
                        book.setDateRelease(document.getString("dateRelease"));
                        booksList.add(book);
                    }
                    return Tasks.forResult(booksList);
                });
    }


    public Task<List<Book>> getPopularBooks() {
        return db.collection("loans")
                .get()
                .continueWithTask(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        throw task.getException();
                    }

                    // Count the occurrences of each bookId
                    Map<String, Integer> bookIdCounts = new HashMap<>();
                    for (DocumentSnapshot loanDoc : task.getResult().getDocuments()) {
                        String bookId = loanDoc.getString("bookId");
                        bookIdCounts.put(bookId, bookIdCounts.getOrDefault(bookId, 0) + 1);
                    }

                    // Sort the bookIds by their counts in descending order
                    List<String> sortedBookIds = new ArrayList<>(bookIdCounts.keySet());
                    sortedBookIds.sort((id1, id2) -> bookIdCounts.get(id2) - bookIdCounts.get(id1));

                    // Create a list of book retrieval tasks in sorted order
                    List<Task<Book>> bookTasks = new ArrayList<>();
                    for (String bookId : sortedBookIds) {
                        Task<Book> bookTask = this.getBookById(bookId);
                        bookTasks.add(bookTask);
                    }

                    // Wait for all book retrieval tasks to complete and return the result
                    return Tasks.whenAllSuccess(bookTasks);
                });
    }





    @RequiresApi(api = Build.VERSION_CODES.O)
    public void borrowBook(String idBook, String idUser) {
        LocalDate currentDate = LocalDate.now();
        DocumentReference bookRef = db.collection("books").document(idBook);
        DocumentReference loanRef = db.collection("loans").document();

        db.runTransaction((Transaction.Function<Void>) transaction -> {
            // Get book document
            DocumentSnapshot bookSnapshot = transaction.get(bookRef);
            if (bookSnapshot.exists()) {
                int availableBooks = bookSnapshot.getLong("available").intValue();
                if (availableBooks > 0) {
                    // Update book availability
                    transaction.update(bookRef, "available", availableBooks - 1);

                    Map<String, Object> loanData = new HashMap<>();
                    loanData.put("userId", idUser);
                    loanData.put("bookId", idBook);
                    loanData.put("requestDate", currentDate.toString());
                    loanData.put("startDate", currentDate.toString());
                    loanData.put("returnDate", null);

                    transaction.set(loanRef, loanData);
                } else {
                    transaction.update(bookRef, "available", availableBooks - 1);

                    Map<String, Object> loanData = new HashMap<>();
                    loanData.put("userId", idUser);
                    loanData.put("bookId", idBook);
                    loanData.put("requestDate", currentDate.toString());
                    loanData.put("startDate", null);
                    loanData.put("returnDate", null);

                    transaction.set(loanRef, loanData);
                }
            } else {
                throw new FirebaseFirestoreException("Book not found",
                        FirebaseFirestoreException.Code.NOT_FOUND);
            }
            return null;
        }).addOnSuccessListener(aVoid -> {
            // Transaction success
            System.out.println("Book borrowed successfully");
        }).addOnFailureListener(e -> {
            // Transaction failure
            e.printStackTrace();
        });
    }

    public Task<List<Book>> getBorrowedBooks(String idUser) {
        return db.collection("loans")
                .whereEqualTo("userId", idUser)
                .whereEqualTo("returnDate", null)
                .get()
                .continueWithTask(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        throw task.getException();
                    }

                    List<Task<Book>> bookTasks = new ArrayList<>();

                    for (DocumentSnapshot loanDoc : task.getResult().getDocuments()) {
                        String bookId = loanDoc.getString("bookId");
                        Task<Book> bookTask = this.getBookById(bookId);
                        bookTasks.add(bookTask);
                    }

                    return Tasks.whenAllSuccess(bookTasks);
                });
    }

    public Task<List<Book>> getAllHistory(String idUser) {
        return db.collection("loans")
                .whereEqualTo("userId", idUser)
                .get()
                .continueWithTask(task -> {
                    if (!task.isSuccessful() || task.getResult() == null) {
                        throw task.getException();
                    }

                    List<Task<Book>> bookTasks = new ArrayList<>();

                    for (DocumentSnapshot loanDoc : task.getResult().getDocuments()) {
                        String bookId = loanDoc.getString("bookId");
                        Task<Book> bookTask = this.getBookById(bookId);
                        bookTasks.add(bookTask);
                    }

                    return Tasks.whenAllSuccess(bookTasks);
                });
    }

    public void getUserById(String userId, final FirestoreCallback<User> callback) {
        if (userId == null) {
            // Handle the case where userId is null
            callback.onComplete(null);
            return;
        }
        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                User user = new User();
                user.setId(document.getId());
                user.setName(document.getString("name"));
                user.setEmail(document.getString("email"));
                user.setNumber(document.getString("number"));
                user.setPhoto(document.getString("photo"));
                callback.onComplete(user);
            } else {
                callback.onComplete(null); // Handle the error or null case
            }
        });
    }

    public Task<Boolean> isBookBorrowed(String idUser, String idBook) {
        return db.collection("loans")
                .whereEqualTo("userId", idUser)
                .whereEqualTo("bookId", idBook)
                .whereEqualTo("returnDate", null)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        return !querySnapshot.isEmpty(); // Book is borrowed if query result is not empty
                    }
                    return false; // Error occurred or no result found
                });
    }

    public Task<Float> getRatingOfBook(String idBook) {
        return db.collection("reviews")
                .whereEqualTo("bookId", idBook)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        int count = querySnapshot.size();
                        if (count == 0) {
                            return 0f; // No reviews found, return 0
                        }

                        float totalRating = 0f;
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            Double rating = doc.getDouble("rating");
                            if (rating != null) {
                                totalRating += rating.floatValue();
                            }
                        }
                        return totalRating / count; // Calculate and return average rating
                    }
                    return 0f; // Error occurred or no result found
                });
    }

    public Task<List<Integer>> getNumberOfRatings(String idBook) {
        return db.collection("reviews")
                .whereEqualTo("bookId", idBook)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        QuerySnapshot querySnapshot = task.getResult();
                        List<Integer> list = new ArrayList<>();
                        int number = querySnapshot.size();
                        for (int i = 0; i < 6; i++) {
                            list.add(0);
                        }
                        int one = 0, two = 0, three = 0, four = 0, five = 0;
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            double rating = doc.getDouble("rating");
                            if (rating <= 1.0) {
                                one++;
                            } else if (rating <= 2.0) {
                                two++;
                            } else if (rating <= 3.0) {
                                three++;
                            } else if (rating <= 4.0) {
                                four++;
                            } else if (rating <= 5.0) {
                                five++;
                            }
                        }
                        list.set(0, number);
                        list.set(1, (int) ((double) one / number * 100));
                        list.set(2, (int) ((double) two / number * 100));
                        list.set(3, (int) ((double) three / number * 100));
                        list.set(4, (int) ((double) four / number * 100));
                        list.set(5, (int) ((double) five / number * 100));
                        return list;
                    }
                    return null; // Error occurred or no result found
                });
    }

    public Task<List<Review>> getAllReviewsOfBook(String idBook) {
        return db.collection("reviews")
                .whereEqualTo("bookId", idBook)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Review> reviews = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Review review = new Review();
                            review.setId_book(doc.getString("bookId"));
                            review.setId_user(doc.getString("userId")); // Assuming the field name is "userId"
                            review.setReviewTitle(doc.getString("title")); // Assuming the field name is "reviewTitle"
                            review.setDate(doc.getString("date"));
                            review.setRating(doc.getDouble("rating"));
                            review.setID(doc.getId());
                            review.setReviewText(doc.getString("comment"));

                            reviews.add(review);
                        }
                        return reviews;
                    } else {
                        // Log the error if the task was not successful
                        if (task.getException() != null) {
                            Log.e("getAllReviewsOfBook", "Error getting documents: ", task.getException());
                        }
                        // Return an empty list instead of null
                        return new ArrayList<>();
                    }
                });
    }

    public Task<List<Review>> getAllReviewsOfUser(String idUser) {
        return db.collection("reviews")
                .whereEqualTo("userId", idUser)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Review> reviews = new ArrayList<>();
                        for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                            Review review = new Review();
                            review.setId_book(doc.getString("bookId"));
                            review.setId_user(doc.getString("userId"));
                            review.setReviewTitle(doc.getString("title"));
                            review.setDate(doc.getString("date"));
                            review.setRating(doc.getDouble("rating"));
                            review.setID(doc.getId());
                            review.setReviewText(doc.getString("comment"));
                            reviews.add(review);
                        }
                        return reviews;
                    }
                    return null;
                });
    }

    public Task<DocumentReference> addReviewForABook(Review review) {
        Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("bookId", review.getId_book());
        reviewData.put("userId", review.getId_user());
        reviewData.put("rating", review.getRating());
        reviewData.put("title", review.getReviewTitle());
        reviewData.put("comment", review.getReviewText());
        reviewData.put("date", review.getDate());

        return db.collection("reviews")
                .add(reviewData)
                .addOnSuccessListener(documentReference -> {
                    System.out.println("Review added successfully: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    e.printStackTrace();
                });
    }

    public Task<Book> getBookById(String idBook) {
        return db.collection("books")
                .document(idBook)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot document = task.getResult();
                        Book book = new Book();
                        book.setId(document.getId());
                        book.setName(document.getString("name"));
                        Long available = document.getLong("available");
                        if (available != null) {
                            book.setDisponible(available.intValue());
                        }
                        List<String> genres = (List<String>) document.get("genres");
                        if (genres != null) {
                            book.setGenres(genres);
                        } else {
                            book.setGenres(Collections.emptyList());
                        }
                        List<String> authors = (List<String>) document.get("authors");
                        if (authors != null) {
                            book.setAuthors(authors);
                        } else {
                            book.setAuthors(Collections.emptyList());
                        }
                        book.setDescription(document.getString("description"));
                        book.setDateRelease(document.getString("dateRelease"));
                        return book;
                    } else {
                        throw task.getException() != null ? task.getException() : new Exception("Error retrieving book");
                    }
                });
    }


    private void getLastMessageForConversation(String conversationId, String userId, final FirestoreCallback<Message> callback) {
        db.collection("messages")
                .whereEqualTo("chatId", conversationId)
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            DocumentSnapshot document = querySnapshot.getDocuments().get(0);
                            QueryDocumentSnapshot queryDocument = (QueryDocumentSnapshot) document;

                            Message message = new Message();
                            message.setMessage(queryDocument.getString("text"));
                            message.setId(queryDocument.getString("chatId"));
                            message.setId_user(queryDocument.getString("userId"));
                            Date date = queryDocument.getDate("date");
                            if (date != null) {
                                message.setDate(date.toString());
                            }
                            callback.onComplete(message);
                        } else {
                            callback.onComplete(null); // No messages found
                        }
                    } else {
                        callback.onComplete(null); // Handle the error
                    }
                });
    }



    public void getChats(String idUser, final FirestoreCallback<List<Chat>> callback) {
        List<Chat> chatList = new ArrayList<>();

        db.collection("chats")
                .whereEqualTo("user1Id", idUser)
                .get()
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task1.getResult()) {
                            Chat chat = new Chat();
                            chat.setId(document.getId());
                            String user1Id = document.getString("user1Id");
                            String user2Id = document.getString("user2Id");

                            String otherUserId = user1Id.equals(idUser) ? user2Id : user1Id;
                            getUserById(otherUserId, user -> {
                                if (user != null) {
                                    chat.setName(user.getName());
                                    getLastMessageForConversation(chat.getId(), idUser, message -> {
                                        if (message != null) {
                                            chat.setMessage(message.getMessage());
                                            chat.setDate(message.getDate());
                                            chat.setLastMessageMine(message.getId_user().equals(idUser));
                                        }
                                        chatList.add(chat);
                                        callback.onComplete(chatList);
                                    });
                                }
                            });
                        }
                    } else {
                        callback.onComplete(chatList); // or handle the error
                    }
                });

        db.collection("chats")
                .whereEqualTo("user2Id", idUser)
                .get()
                .addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task2.getResult()) {
                            Chat chat = new Chat();
                            chat.setId(document.getId());
                            String user1Id = document.getString("user1Id");
                            String user2Id = document.getString("user2Id");

                            String otherUserId = user1Id.equals(idUser) ? user2Id : user1Id;
                            getUserById(otherUserId, user -> {
                                if (user != null) {
                                    chat.setName(user.getName());
                                    getLastMessageForConversation(chat.getId(), idUser, message -> {
                                        if (message != null) {
                                            chat.setMessage(message.getMessage());
                                            chat.setDate(message.getDate());
                                            chat.setLastMessageMine(message.getId_user().equals(idUser));
                                        }
                                        chatList.add(chat);
                                        callback.onComplete(chatList);
                                    });
                                }
                            });
                        }
                    } else {
                        callback.onComplete(chatList); // or handle the error
                    }
                });
    }


    public Task<List<Message>> getAllMessages(String chatId) {
        return db.collection("messages")
                .whereEqualTo("chatId", chatId)
                .orderBy("date", Query.Direction.ASCENDING)
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        List<Message> messages = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Message message = new Message();
                            message.setMessage(document.getString("text"));
                            message.setId(document.getString("chatId"));
                            message.setId_user(document.getString("userId"));
                            Date date = document.getDate("date");
                            if(date!=null) {
                                message.setDate(date.toString());
                            }
                            messages.add(message);
                        }
                        return messages;
                    } else {
                        return Collections.emptyList();
                    }
                });
    }

    public Task<Void> addMessage(String chatId, String userId, String text) {
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("userId", userId);
        messageData.put("text", text);
        messageData.put("chatId", chatId);
        messageData.put("date", FieldValue.serverTimestamp());

        return db.collection("messages")
                .add(messageData)
                .continueWith(task -> null);
    }

    public Task<Chat> addChat(String idUser1, String idUser2) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> chatData = new HashMap<>();
        chatData.put("user1Id", idUser1);
        chatData.put("user2Id", idUser2);

        return firestore.collection("chats")
                .add(chatData)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        DocumentReference documentReference = task.getResult();
                        String chatId = documentReference.getId();
                        Chat chat = new Chat();
                        chat.setId(chatId);
                        return chat;
                    } else {
                        throw task.getException();
                    }
                });
    }


    public Task<List<Book>> getAllBooks() {
        return db.collection("books")
                .get()
                .onSuccessTask(querySnapshot -> {
                    List<Book> booksList = new ArrayList<>();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Book book = new Book();
                        book.setId(document.getId());
                        book.setName(document.getString("name"));
                        Long available = document.getLong("available");
                        if (available != null) {
                            book.setDisponible(available.intValue());
                        }
                        List<String> genres = (List<String>) document.get("genres");
                        if (genres != null) {
                            book.setGenres(genres);
                        } else {
                            book.setGenres(Collections.emptyList());
                        }
                        List<String> authors = (List<String>) document.get("authors");
                        if (authors != null) {
                            book.setAuthors(authors);
                        } else {
                            book.setAuthors(Collections.emptyList());
                        }
                        book.setDescription(document.getString("description"));
                        book.setDateRelease(document.getString("dateRelease"));
                        booksList.add(book);
                    }
                    return Tasks.forResult(booksList);
                });
    }

    public Task<List<Book>> getBooksSearch(String query) {
        String[] keywords = query.toLowerCase().split("\\s+");
        List<Book> list = new ArrayList<>();

        Task<QuerySnapshot> queryTask = db.collection("books").get();

        return queryTask.continueWith(task -> {
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    for (String keyword : keywords) {
                        String name = document.getString("name");
                        if (name != null && name.toLowerCase().contains(keyword)) {
//                            System.out.println(name);
                            Book book = new Book();
                            book.setId(document.getId());
                            book.setName(document.getString("name"));
                            Long available = document.getLong("available");
                            if (available != null) {
                                book.setDisponible(available.intValue());
                            }
                            List<String> genres = (List<String>) document.get("genres");
                            if (genres != null) {
                                book.setGenres(genres);
                            } else {
                                book.setGenres(Collections.emptyList());
                            }
                            List<String> authors = (List<String>) document.get("authors");
                            if (authors != null) {
                                book.setAuthors(authors);
                            } else {
                                book.setAuthors(Collections.emptyList());
                            }
                            book.setDescription(document.getString("description"));
                            book.setDateRelease(document.getString("dateRelease"));
                            list.add(book);
                        }
                    }
                }
                return list;
            }
            return list;
        });
    }

    public Task<List<User>> getUsersSearch(String query) {
        String[] keywords = query.toLowerCase().split("\\s+");
        List<User> list = new ArrayList<>();

        Task<QuerySnapshot> queryTask = db.collection("users").get();

        return queryTask.continueWith(task -> {
            QuerySnapshot queryDocumentSnapshots = task.getResult();
            if (queryDocumentSnapshots != null) {
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    for (String keyword : keywords) {
                        String name = document.getString("name");
                        String email = document.getString("email");
                        if ((name != null && name.toLowerCase().contains(keyword)) || (email !=null && email.toLowerCase().contains(keyword))) {
                            System.out.println(name);
                            User user= new User();
                            user.setId(document.getId());
                            user.setEmail(email);
                            user.setName(name);
                            user.setNumber(document.getString("phone"));
                            List<String> genres = (List<String>) document.get("genres");
                            if (genres != null) {
                                user.setGenres(genres);
                            } else {
                                user.setGenres(Collections.emptyList());
                            }
                            List<String> authors = (List<String>) document.get("authors");
                            if (authors != null) {
                                user.setAuthors(authors);
                            } else {
                                user.setAuthors(Collections.emptyList());
                            }
                            list.add(user);
                        }
                    }
                }
                return list;
            }
            return list;
        });
    }

    public void getAuthorById(String authorId, final FirestoreCallback<Author> callback) {
        if (authorId == null) {
            // Log or handle the case where authorId is null
            callback.onComplete(null);
            return;
        }

        db.collection("authors")
                .document(authorId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Author author = new Author();
                            author.setName(document.getString("name"));
                            author.setId(document.getId());
                            callback.onComplete(author);
                        } else {
                            callback.onComplete(null);
                            Log.e(TAG, "Error getting author with ID(not found): " + authorId, task.getException());
                        }
                    } else {
                        callback.onComplete(null);
                        Log.e(TAG, "Error getting author with ID: " + authorId, task.getException());
                    }
                });
    }

    public void getGenreById(String genreId, final FirestoreCallback<Genre> callback) {
        if(genreId==null){
            callback.onComplete(null);
            return;
        }
        db.collection("genres")
                .document(genreId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Genre genre = new Genre();
                            genre.setId(document.getId());
                            genre.setName(document.getString("name"));
                            callback.onComplete(genre);
                        } else {
                            callback.onComplete(null); // Genre not found
                        }
                    } else {
                        callback.onComplete(null);
                    }
                });
    }


    public void getPreferencesGenre(String userId, final FirestoreCallback<List<Genre>> callback) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> genreIds = (List<String>) document.get("preferredGenres");                            for(String d: genreIds){
                                System.out.println(d);
                            }
                            if (genreIds != null && !genreIds.isEmpty()) {
                                getGenresByIds(genreIds, callback);
                            } else {
                                callback.onComplete(new ArrayList<>()); // No preference genres
                            }
                        } else {
                            callback.onComplete(new ArrayList<>()); // User document not found
                        }
                    } else {
                        callback.onComplete(null);
                    }
                });
    }

    private void getGenresByIds(List<String> genreIds, final FirestoreCallback<List<Genre>> callback) {
        List<Genre> genres = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(genreIds.size());

        for (String genreId : genreIds) {
            getGenreById(genreId, new FirestoreCallback<Genre>() {
                @Override
                public void onComplete(Genre genre) {
                    if (genre != null) {
                        genres.add(genre);
                    }
                    if (count.decrementAndGet() == 0) {
                        callback.onComplete(genres);
                    }
                }

            });
        }
    }

    public void getPreferencesAuthors(String userId, final FirestoreCallback<List<Author>> callback) {
        db.collection("users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            List<String> authorsIds = (List<String>) document.get("preferredAuthors");
                            if (authorsIds != null && !authorsIds.isEmpty()) {
                                getAuthorsByIds(authorsIds, callback);
                            } else {
                                callback.onComplete(new ArrayList<>());
                            }
                        } else {
                            callback.onComplete(new ArrayList<>());
                        }
                    } else {
                        callback.onComplete(null);
                    }
                });
    }

    public void getAuthorsByIds(List<String> authorIds, final FirestoreCallback<List<Author>> callback) {
        List<Author> authors = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(authorIds.size());

        for (String authorId : authorIds) {
            getAuthorById(authorId, new FirestoreCallback<Author>() {
                @Override
                public void onComplete(Author author) {
                    if (author != null) {
                        authors.add(author);
                    }
                    if (count.decrementAndGet() == 0) {
                        callback.onComplete(authors);
                    }
                }

            });
        }
    }

    public void deletePreferences(String id) {
        db.collection("users")
                .document(id)
                .update("preferredGenres", null);
        db.collection("users")
                .document(id)
                .update("preferredAuthors", null);
    }

    public Task<List<Author>> getAllAuthors() {
        TaskCompletionSource<List<Author>> tcs = new TaskCompletionSource<>();
        db.collection("authors")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Author> authors = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        Author author = new Author();
                        author.setName(document.getString("name"));
                        author.setId(document.getId());
                        authors.add(author);
                    }
                    tcs.setResult(authors); // Indicate success with the list of genres
                })
                .addOnFailureListener(e -> {
                    tcs.setException(e); // Indicate failure
                });
        return tcs.getTask();
    }
    public Task<List<Genre>> getAllGenres() {
//        TaskCompletionSource<List<Genre>> tcs = new TaskCompletionSource<>();
        return db.collection("genres")
                .get()
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        List<Genre> genres = new ArrayList<>();
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            Genre genre = new Genre();
                            genre.setName(document.getString("name"));
                            genre.setId(document.getId());
                            genres.add(genre);
                        }
                        return genres; // Indicate success with the list of genres
                    } else {
                        return null; // Indicate failure
                    }
                });
//        return tcs.getTask();
    }


}
