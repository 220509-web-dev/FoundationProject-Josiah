package dev.josiah.daos;

import dev.josiah.entities.*;

import java.sql.SQLException;
import java.util.List;

public interface AllDAO {
    User createUser(User user) throws SQLException;
    User getUserById(int id) throws SQLException;
    User getUserByUsername(String username) throws SQLException;
    List<User> getAllUsers() throws SQLException;
    void updateUser(User user) throws SQLException;
    void deleteUserById(int id) throws SQLException;

    void createUserInfo(UserPriv userp) throws SQLException;
    UserPriv getUserInfoById(int id) throws SQLException;
    List<UserPriv> getAllUserInfo() throws SQLException;
    void updateUserInfo(UserPriv userp) throws SQLException;
    void deleteUserInfoById(int id) throws SQLException;

    void createUserRole(Role role) throws SQLException;
    Role getUserRoleById(int id) throws SQLException;
    List<Role> getAllUserRole() throws SQLException;
    void updateUserRole(Role userp) throws SQLException;
    void deleteUserRoleById(int id) throws SQLException;

    Card createCard(Card card) throws SQLException; // auto-incrementing PKey
    Card getCardById(int id) throws SQLException;
    List<Card> getAllCard() throws SQLException;
    void updateCard(Card card) throws SQLException;
    void deleteCardById(int id) throws SQLException;

    void createRating(Rating rating) throws SQLException;
    List<Rating> getRatingsByCardId(int id) throws SQLException;
    List<Rating> getRatingsByUserId(int id) throws SQLException;
    List<Rating> getAllRating() throws SQLException;
    void updateRating(Rating rating) throws SQLException;
    void deleteRatingByIds(int card_id, int user_id) throws SQLException;

    Deck createDeck(Deck deck) throws SQLException; // auto-incrementing PKey
    Deck getDeckByDeckId(int id) throws SQLException;
    List<Deck> getDeckByUserId(int id) throws SQLException;
    List<Deck> getAllDeck() throws SQLException;
    void updateDeck(Deck deck) throws SQLException;
    void deleteDeckById(int id) throws SQLException;

    void addCardToCardDeck(Card card, Deck deck) throws SQLException;
    //void removeCardFromCardDeck(Card card, Deck deck) throws SQLException;
    List<Card> getCardsByDeckId(int id) throws SQLException;


//    CardDeck getCardDeckByDeckId(int id) throws SQLException;
//    List<CardDeck> getAllCardDeck() throws SQLException;
//    void updateCardDeck(CardDeck cardDeck) throws SQLException;
//    void deleteCardDeckById(int id) throws SQLException;
}

