package dev.josiah.daos;

import dev.josiah.entities.*;
import dev.josiah.utils.ConnectionFactory;

import java.sql.*;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

//drop table if exists ratings;
//        drop table if exists card_deck;
//        drop table if exists cards;
//        drop table if exists decks;
//        drop table if exists userp;
//        drop table if exists users;
//        drop table if exists roles;

public class AllDaoPostgres implements AllDAO {
    final private String sn = "notecard.";  // Schema name
    final private String t0 = "ratings";
    final private String t1 = "card_deck";
    final private String t2 = "cards";
    final private String t3 = "decks";
    final private String t4 = "userp";
    final private String t5 = "users";
    final private String t6 = "roles";
    final private String[] t = {
            sn+t0,
            sn+t1,
            sn+t2,
            sn+t3,
            sn+t4,
            sn+t5,
            sn+t6
    };


    // ratings table
    final private String t0c0 = "card_id";
    final private String t0c1 = "user_id";
    final private String t0c2 = "seeagain";
    final private String t0c3 = "rating";
    final private String t0c4 = "creationdate";
    final private String t0c5 = "creationtime";
    final private String[] t0c = {t0c0,t0c1,t0c2,t0c3,t0c4,t0c5};

    // card_deck table
    final private String t1c0 = "card_id";
    final private String t1c1 = "deck_id";
    final private String[] t1c = {t1c0,t1c1};

    // cards table
    final private String t2c0 = "id";
    final private String t2c1 = "html_q";
    final private String t2c2 = "html_a";
    final private String[] t2c = {t2c0,t2c1,t2c2};

    // decks table
    final private String t3c0 = "deck_id";
    final private String t3c1 = "owner_id";
    final private String t3c2 = "deckname";
    final private String[] t3c = {t3c0,t3c1,t3c2};

    // userp table
    final private String t4c0 = "id";
    final private String t4c1 = "password";
    final private String[] t4c = {t4c0,t4c1};

    // users table
    final private String t5c0 = "id";
    final private String t5c1 = "username";
    final private String t5c2 = "fname";
    final private String t5c3 = "lname";
    final private String t5c4 = "creationdate";
    final private String t5c5 = "creationtime";
    final private String[] t5c = {t5c0,t5c1,t5c2,t5c3,t5c4,t5c5};

    // roles table
    final private String t6c0 = "role_id";
    final private String t6c1 = "title";
    final private String[] t6c = {t6c0,t6c1};

    @Override
    public User createUser(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+st+" values (default";
            for (int i = 1; i<c.length;i++) { sql += ",?"; }
            sql += ");";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.setString(4, user.getAddress1());
            ps.setString(5, user.getAddress2());
            ps.setString(6, user.getCity());
            ps.setString(7, user.getState());
            ps.setString(8, user.getPostalcode());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt(c[0]);

            user.setUser_id(generatedID);
            return user;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        //return null;
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
