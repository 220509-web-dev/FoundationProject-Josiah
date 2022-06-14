package dev.josiah.daos;

import dev.josiah.entities.*;

import java.sql.SQLException;
import java.util.List;

//drop table if exists ratings;
//        drop table if exists card_deck;
//        drop table if exists cards;
//        drop table if exists decks;
//        drop table if exists userp;
//        drop table if exists users;
//        drop table if exists roles;

public class AllDaoPostgres implements AllDAO {
    final private String sn = "notecard.";  // Schema name
    final private String t1 = "ratings";
    final private String t2 = "card_deck";
    final private String t3 = "cards";
    final private String t4 = "decks";
    final private String t5 = "userp";
    final private String t6 = "users";
    final private String t7 = "roles";
    final private String[] t = {
            sn+t1,
            sn+t2,
            sn+t3,
            sn+t4,
            sn+t5,
            sn+t6,
            sn+t7
    };


    // ratings table
    final private String t1c0 = "card_id";
    final private String t1c1 = "user_id";
    final private String t1c2 = "seeagain";
    final private String t1c3 = "rating";
    final private String t1c4 = "creationdate";
    final private String t1c5 = "creationtime";
    final private String[] t1c = {t1c0,t1c1,t1c2,t1c3,t1c4,t1c5};

    // card_deck table
    final private String t2c0 = "card_id";
    final private String t2c1 = "deck_id";
    final private String[] t2c = {t2c0,t2c1};

    // cards table
    final private String t3c0 = "id";
    final private String t3c1 = "html_q";
    final private String t3c2 = "html_a";
    final private String[] t3c = {t3c0,t3c1,t3c2};

    // decks table
    final private String t4c0 = "deck_id";
    final private String t4c1 = "owner_id";
    final private String t4c2 = "deckname";
    final private String[] t4c = {t4c0,t4c1,t4c2};

    // userp table
    final private String t5c0 = "id";
    final private String t5c1 = "password";
    final private String[] t5c = {t5c0,t5c1};

    // users table
    final private String t6c0 = "id";
    final private String t6c1 = "username";
    final private String t6c2 = "fname";
    final private String t6c3 = "lname";
    final private String t6c4 = "creationdate";
    final private String t6c5 = "creationtime";
    final private String[] t6c = {t6c0,t6c1,t6c2,t6c3,t6c4,t6c5};

    // roles table
    final private String t7c0 = "role_id";
    final private String t7c1 = "title";
    final private String[] t7c = {t7c0,t7c1};

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
