package hu.tom;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageUser {

	private SessionFactory getSessionFactory() {
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");
		return cfg.buildSessionFactory();
	}

	/* Method to GET user by Id */
	public User findUserById(Integer userId) {
		Session session = getSessionFactory().openSession();
		User user = null;
		try {
			user = session.get(User.class, userId);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return user;
	}

	/* Method to READ all the user */
	public List<User> findAllUser() {
		Session session = getSessionFactory().openSession();
		List<User> users = new ArrayList<User>();
		try {
			users = session.createQuery("FROM User", User.class).getResultList();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return users;
	}

	/* Method to CREATE an user in the database */
	public Integer addUser(String fname, String lname) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;
		Integer userId = null;

		try {
			tx = session.beginTransaction();
			User user = new User();
			user.setFirstName(fname);
			user.setLastName(lname);
			userId = (Integer) session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return userId;
	}

	/* Method to UPDATE first name and last name for an user */
	public void updateUser(Integer userId, String fname, String lname) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = session.get(User.class, userId);
			user.setFirstName(fname);
			user.setLastName(lname);
			session.update(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	/* Method to DELETE an user from the records */
	public void deleteUser(Integer userId) {
		Session session = getSessionFactory().openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			User user = session.get(User.class, userId);
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

}
