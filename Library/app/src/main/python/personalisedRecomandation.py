# import pandas as pd
# from sklearn.metrics.pairwise import cosine_similarity
#
#
# def load_data():
#     # Exemplu de date statice pentru demonstrație
#     ratings_data = {'user_id': [1, 1, 2, 2, 3, 3], 'book_id': [101, 102, 101, 103, 102, 104],
#                     'rating': [5, 3, 4, 5, 4, 2]}
#     books_data = {'book_id': [101, 102, 103, 104],
#                   'title': ['Book A', 'Book B', 'Book C', 'Book D']}
#     ratings = pd.DataFrame(ratings_data)
#     books = pd.DataFrame(books_data)
#     return ratings, books
#
#
# def create_recommendations(user_id):
#     ratings, books = load_data()
#     ratings_matrix = ratings.pivot(index='user_id', columns='book_id', values='rating')
#     ratings_matrix.fillna(0, inplace=True)
#
#     book_similarity = cosine_similarity(ratings_matrix.T)
#     book_similarity_df = pd.DataFrame(book_similarity, index=ratings_matrix.columns,
#                                       columns=ratings_matrix.columns)
#
#     user_ratings = ratings_matrix.loc[user_id]
#     rated_books = user_ratings[user_ratings > 0].index.tolist()
#
#     similar_books = pd.Series(dtype='float64')
#     for book in rated_books:
#         similar_books = similar_books.append(book_similarity_df[book])
#
#     similar_books = similar_books.groupby(similar_books.index).mean()
#     similar_books = similar_books.drop(rated_books)
#     similar_books = similar_books.sort_values(ascending=False)
#
#     recommended_books = similar_books.head(5).index.tolist()
#     recommendations = books[books['book_id'].isin(recommended_books)].to_dict('records')
#     return recommendations
