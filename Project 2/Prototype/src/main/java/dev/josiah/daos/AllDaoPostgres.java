package dev.josiah.daos;

import dev.josiah.entities.*;

import java.sql.SQLException;
import java.util.List;

public class AllDaoPostgres implements AllDAO {

    @Override
    public User createUser(User user) throws SQLException {
        return null;
    }

    @Override
    public User getUserById(int id) throws SQLException {
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return null;
    }

    @Override
    public User updateUser(User user) throws SQLException {
        return null;
    }

    @Override
    public void deleteUserById(int id) throws SQLException {

    }

    @Override
    public void createUserInfo(UserPriv userp) throws SQLException {

    }

    @Override
    public UserPriv getUserInfoById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<UserPriv> getAllUserInfo() throws SQLException {
        return null;
    }

    @Override
    public UserPriv updateUserInfo(UserPriv userp) throws SQLException {
        return null;
    }

    @Override
    public void deleteUserInfoById(int id) throws SQLException {

    }

    @Override
    public void createUserRole(Role role) throws SQLException {

    }

    @Override
    public Role getUserRoleById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Role> getAllUserRole() throws SQLException {
        return null;
    }

    @Override
    public Role updateUserRole(Role userp) throws SQLException {
        return null;
    }

    @Override
    public void deleteUserRoleById(int id) throws SQLException {

    }

    @Override
    public void createCard(Card card) throws SQLException {

    }

    @Override
    public Card getCardById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Card> getAllCard() throws SQLException {
        return null;
    }

    @Override
    public Card updateCard(Card card) throws SQLException {
        return null;
    }

    @Override
    public void deleteCardById(int id) throws SQLException {

    }

    @Override
    public void createRating(Rating rating) throws SQLException {

    }

    @Override
    public Rating getRatingById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Rating> getAllRating() throws SQLException {
        return null;
    }

    @Override
    public Rating updateRating(Rating rating) throws SQLException {
        return null;
    }

    @Override
    public void deleteRatingById(int id) throws SQLException {

    }

    @Override
    public void createDeck(Deck deck) throws SQLException {

    }

    @Override
    public Deck getDeckById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Deck> getAllDeck() throws SQLException {
        return null;
    }

    @Override
    public Deck updateDeck(Deck deck) throws SQLException {
        return null;
    }

    @Override
    public void deleteDeckById(int id) throws SQLException {

    }

    @Override
    public void createCardDeck(CardDeck cardDeck) throws SQLException {

    }

    @Override
    public CardDeck getCardDeckById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<CardDeck> getAllCardDeck() throws SQLException {
        return null;
    }

    @Override
    public CardDeck updateCardDeck(CardDeck cardDeck) throws SQLException {
        return null;
    }

    @Override
    public void deleteCardDeckById(int id) throws SQLException {

    }
}
