package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionDB;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        this.sessionFactory = getSessionDB();
    }

    @Override
    public void createUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS \"user\" " +
                    "(id bigserial primary key, name varchar(255), last_name varchar(255), age SMALLINT);" ;

            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS \"user\"";
            session.createNativeQuery(sql).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);

            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            String sql = "SELECT * FROM \"user\"";
            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            userList = query.getResultList();

            session.getTransaction().commit();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            String sql = "DELETE FROM \"user\"";
            NativeQuery query = session.createNativeQuery(sql);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }
}
