package dev.josiah.daos;

import dev.josiah.entities.*;
import dev.josiah.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dev.josiah.complaintDepartment.ProblemScribe.Complain;

public class AllDaoPostgres implements AllDAO {
    final private String sn = "notecard.";  // Schema name

    //table names
    final private String t0 = "ratings";
    final private String t1 = "card_deck";
    final private String t2 = "cards";
    final private String t3 = "decks";
    final private String t4 = "userp";
    final private String t5 = "users";
    final private String t6 = "roles";
    final private String[] t = {
            sn+t0, // ratings   is t[0]
            sn+t1, // card_deck is t[1]
            sn+t2, // cards     is t[2]
            sn+t3, // decks     is t[3]
            sn+t4, // userp     is t[4]
            sn+t5, // users     is t[5]
            sn+t6  // roles     is t[6]
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
            String sql = "insert into "+t[5]+" values (default";
            for (int i = 1; i<t5c.length-2;i++) { sql += ",?"; }
            sql += ",default,default);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt(t5c[0]);

            user.setId(generatedID);
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
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[5]+" where "+t5c[0]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                User user = new User();
                user.setId(id);
                user.setUsername(       rs.getString(t5c[1]));
                user.setFname(          rs.getString(t5c[2]));
                user.setLname(          rs.getString(t5c[3]));
                user.setCreationdate(   rs.getString(t5c[4]));
                user.setCreationtime(   rs.getString(t5c[5]));
                return user;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String username) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            username = username.toLowerCase(); // DB has case-insensitive uniqueness constraint
            String sql = "select * from "+t[5]+" where lower("+t5c[1]+") = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(             rs.getInt(   t5c[0]));
                user.setUsername(       rs.getString(t5c[1]));
                user.setFname(          rs.getString(t5c[2]));
                user.setLname(          rs.getString(t5c[3]));
                user.setCreationdate(   rs.getString(t5c[4]));
                user.setCreationtime(   rs.getString(t5c[5]));
                return user;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[5]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setId(             rs.getInt(   t5c[0]));
                user.setUsername(       rs.getString(t5c[1]));
                user.setFname(          rs.getString(t5c[2]));
                user.setLname(          rs.getString(t5c[3]));
                user.setCreationdate(   rs.getString(t5c[4]));
                user.setCreationtime(   rs.getString(t5c[5]));
                users.add(user);
            }
            return users;
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
    public void updateUser(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[5]+" set ";
            for (int i=1; i<t5c.length-3;i++) {sql += t5c[i]+" = ?,";}
            sql += t5c[t5c.length-3]+" = ? where "+t5c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void deleteUserById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[5]+" where "+t5c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void createUserInfo(UserPriv userp) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[4]+" values (";
            for (int i = 0; i<t4c.length-1;i++) { sql += "?,"; }
            sql += "?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userp.getId());
            ps.setString(2, userp.getPassword());
            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public UserPriv getUserInfoById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[4]+" where "+t4c[0]+" = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                UserPriv userp = new UserPriv();
                userp.setId(   rs.getInt(t4c[0]));
                userp.setPassword( rs.getString(t4c[1]));
                return userp;
            }
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<UserPriv> getAllUserInfo() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[4]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<UserPriv> userps = new ArrayList<UserPriv>();
            boolean results = false;
            while (rs.next()) {
                results = true;
                UserPriv userp = new UserPriv();
                userp.setId(       rs.getInt(   t4c[0]));
                userp.setPassword( rs.getString(t4c[1]));
                userps.add(userp);
            }
            if (results) return userps;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public void updateUserInfo(UserPriv userp) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[4]+" set ";
            for (int i = 1; i< t4c.length-1; i++) {sql += t4c[i]+" = ?,";}
            sql += t4c[t4c.length-1]+" = ? where "+ t4c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString( 1, userp.getPassword());
            ps.setInt(    2, userp.getId());
            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void deleteUserInfoById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[4]+" where "+ t4c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void createUserRole(Role role) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[6]+" values (";
            for (int i = 1; i<t6c.length-1;i++) { sql += "?,"; }
            sql += "?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, role.getRole_id());
            ps.setString(2, role.getTitle());
            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public Role getUserRoleById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[6]+" where "+t6c[0]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                Role role = new Role();
                role.setRole_id(id);
                role.setTitle(   rs.getString(t6c[1]));
                return role;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<Role> getAllUserRole() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[6]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Role> roles = new ArrayList<Role>();
            while (rs.next()) {
                Role role = new Role();
                role.setRole_id(    rs.getInt(   t6c[0]));
                role.setTitle(      rs.getString(t6c[1]));
                roles.add(role);
            }
            return roles;
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
    public void updateUserRole(Role role) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[6]+" set ";
            for (int i=1; i<t6c.length-1;i++) {sql += t6c[i]+" = ?,";}
            sql += t6c[t6c.length-1]+" = ? where "+t6c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, role.getRole_id());
            ps.setString(2, role.getTitle());
            ps.execute();

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
    public void deleteUserRoleById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[6]+" where "+t6c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public Card createCard(Card card) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[2]+" values (default";
            for (int i = 1; i<t2c.length;i++) { sql += ",?"; }
            sql += ");";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, card.getHtml_q());
            ps.setString(2, card.getHtml_a());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt(t2c[0]);

            card.setId(generatedID);
            return card;

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public Card getCardById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[2]+" where "+t2c[0]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                Card card = new Card();
                card.setId(id);
                card.setHtml_q( rs.getString(t2c[1]));
                card.setHtml_a( rs.getString(t2c[2]));

                return card;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<Card> getAllCard() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[2]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Card> cards = new ArrayList<Card>();
            while (rs.next()) {
                Card card = new Card();
                card.setId(     rs.getInt(   t2c[0]));
                card.setHtml_q( rs.getString(t2c[1]));
                card.setHtml_a( rs.getString(t2c[2]));
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void updateCard(Card card) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[2]+" set ";
            for (int i=1; i<t2c.length-1;i++) {sql += t2c[i]+" = ?,";}
            sql += t2c[t2c.length-1]+" = ? where "+t2c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, card.getHtml_q());
            ps.setString(2, card.getHtml_a());

            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void deleteCardById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[2]+" where "+t2c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void createRating(Rating rating) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[0]+" values (";
            for (int i = 1; i<t0c.length-3;i++) { sql += "?,"; }
            sql += "cast(? as real), default, default);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, rating.getCard_id());
            ps.setInt(2, rating.getUser_id());
            ps.setBoolean(3, rating.getSeeagain());
            ps.setDouble(4, rating.getRating());
            ps.execute();

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
    public List<Rating> getRatingsByCardId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[0]+" where "+t0c[0]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Rating> ratings = new ArrayList<Rating>();
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setCard_id(        rs.getInt(     t0c[0]));
                rating.setUser_id(        rs.getInt(     t0c[1]));
                rating.setSeeagain(       rs.getBoolean( t0c[2]));
                rating.setRating(         rs.getDouble(  t0c[3]));
                rating.setCreationdate(   rs.getString(  t0c[4]));
                rating.setCreationtime(   rs.getString(  t0c[5]));
                ratings.add(rating);
            }
            return ratings;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public List<Rating> getRatingsByUserId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[0]+" where "+t0c[1]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Rating> ratings = new ArrayList<Rating>();
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setCard_id(        rs.getInt(     t0c[0]));
                rating.setUser_id(        rs.getInt(     t0c[1]));
                rating.setSeeagain(       rs.getBoolean( t0c[2]));
                rating.setRating(         rs.getDouble(  t0c[3]));
                rating.setCreationdate(   rs.getString(  t0c[4]));
                rating.setCreationtime(   rs.getString(  t0c[5]));
                ratings.add(rating);
            }
            return ratings;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public List<Rating> getAllRating() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[0]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Rating> ratings = new ArrayList<Rating>();
            while (rs.next()) {
                Rating rating = new Rating();
                rating.setCard_id(        rs.getInt(     t0c[0]));
                rating.setUser_id(        rs.getInt(     t0c[1]));
                rating.setSeeagain(       rs.getBoolean( t0c[2]));
                rating.setRating(         rs.getDouble(  t0c[3]));
                rating.setCreationdate(   rs.getString(  t0c[4]));
                rating.setCreationtime(   rs.getString(  t0c[5]));
                ratings.add(rating);
            }
            return ratings;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void updateRating(Rating rating) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[0]+" set ";
            for (int i=0; i<t0c.length-2;i++) {sql += t0c[i]+" = ?,";}
            sql += t0c[t0c.length-2]+" = ? where "+t0c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, rating.getCard_id());
            ps.setInt(2, rating.getUser_id());
            ps.setBoolean(3, rating.getSeeagain());
            ps.setDouble(4, rating.getRating());

            ps.execute();

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void deleteRatingByIds(int card_id, int user_id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[0]+" where "+t0c[0]+"=? and "+t0c[1]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, card_id);
            ps.setInt(2, user_id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public Deck createDeck(Deck deck) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[3]+" values (default";
            for (int i = 1; i<t3c.length;i++) { sql += ",?"; }
            sql += ");";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, deck.getOwner_id());
            ps.setString(2, deck.getDeckname());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int generatedID = rs.getInt(t3c[0]);

            deck.setDeck_id(generatedID);
            return deck;
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
    public Deck getDeckByDeckId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[3]+" where "+t3c[0]+" = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            // Get first record
            if (rs.next()) {
                Deck deck = new Deck();
                deck.setDeck_id(id);
                deck.setOwner_id( rs.getInt(t3c[1]));
                deck.setDeckname( rs.getString(t3c[2]));
                return deck;
            }

        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
        return null;
    }

    @Override
    public List<Deck> getDeckByUserId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[3]+" where "+t3c[1]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id); // indexed from 1 rather than 0
            ResultSet rs = ps.executeQuery();

            List<Deck> decks = new ArrayList<Deck>();
            while (rs.next()) {
                Deck deck = new Deck();
                deck.setDeck_id(    rs.getInt(    t3c[0]));
                deck.setOwner_id(   rs.getInt(    t3c[1]));
                deck.setDeckname(   rs.getString( t3c[2]));
                decks.add(deck);
            }
            return decks;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public List<Deck> getAllDeck() throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[3]+";";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Deck> decks = new ArrayList<Deck>();
            while (rs.next()) {
                Deck deck = new Deck();
                deck.setDeck_id(    rs.getInt(    t3c[0]));
                deck.setOwner_id(   rs.getInt(    t3c[1]));
                deck.setDeckname(   rs.getString( t3c[2]));
                decks.add(deck);
            }
            return decks;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void updateDeck(Deck deck) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "update "+t[3]+" set ";
            for (int i=1; i<t3c.length-1;i++) {sql += t3c[i]+" = ?,";}
            sql += t3c[t3c.length-1]+" = ? where "+t3c[0]+"=?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, deck.getOwner_id());
            ps.setString(2, deck.getDeckname());
            ps.setInt(3, deck.getDeck_id());
            ps.execute();

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
    public void deleteDeckById(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "delete from "+t[3]+" where "+t3c[0]+" = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public void addCardToCardDeck(Card card, Deck deck) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "insert into "+t[1]+" values (?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, card.getId());
            ps.setInt(2, deck.getDeck_id());

            ps.execute();
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }

    @Override
    public List<Card> getCardsByDeckId(int id) throws SQLException {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from "+t[2]+" where "+t2c[0]+" in "+
                    "(select "+t1c[0]+" from "+t[1]+" where "+t1c[1]+" = ?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            List<Card> cards = new ArrayList<Card>();
            while (rs.next()) {
                Card card = new Card();
                card.setId(rs.getInt(t2c[0]));
                card.setHtml_q(rs.getString(t2c[1]));
                card.setHtml_a(rs.getString(t2c[2]));
                cards.add(card);
            }
            return cards;
        } catch (SQLException e) {
            Complain(e);
            throw new SQLException(e);
        } catch (Throwable t) {
            Complain(t);
            throw new RuntimeException(t);
        }
    }
}






















