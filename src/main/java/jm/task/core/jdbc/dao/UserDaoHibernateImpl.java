package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String sqlCreate = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(40) NOT NULL," +
            "lastName VARCHAR(40) NOT NULL," +
            "age TINYINT(3))";
    private static final String sqlDrop = "DROP TABLE IF EXISTS users ";
    public UserDaoHibernateImpl() {

    }
    static SessionFactory sessionFactory = Util.getSessionFactory();

    @Override
    public void createUsersTable() {
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery(sqlCreate).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery(sqlDrop).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
        try {
            User user = session.get(User.class, id);
            if (user != null)
                session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();

        try {
            users = session.createQuery("FROM User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction;
        Session session = sessionFactory.getCurrentSession();
        transaction = session.beginTransaction();
        try {
            session.createQuery("DELETE User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
