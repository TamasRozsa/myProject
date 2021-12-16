package hu.tom;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class UserDAO implements Dao<User> {

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("UsersDB");
	private EntityManager entityManager = getEntityManager();

	@Override
	public Optional<User> get(long id) {
		return Optional.ofNullable(entityManager.find(User.class, id));
	}

	@Override
	public Optional<User> get(String name) {
		TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.name=:UserName",
				User.class);
		typedQuery.setParameter("UserName", name);
		return Optional.ofNullable(typedQuery.getSingleResult());
	}

	@Override
	public List<User> getAll() {
		TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u ", User.class);
		return typedQuery.getResultList();
	}

	@Override
	public void save(User t) {
		executeTransaction(entityManager -> entityManager.persist(t));
	}

	@Override
	public void update(long userId, User t) {

		User modifiedUser = t;
		modifiedUser.setId(userId);
		executeTransaction(entityManager -> entityManager.merge(modifiedUser));
	}

	@Override
	public void delete(long userId) {
		User user = entityManager.find(User.class, userId);
		entityManager.remove(user);
	}
	
	private EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
		
	}

	private void executeTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = entityManager.getTransaction();
		try {
			tx.begin();
			action.accept(entityManager);
			tx.commit();
		} catch (RuntimeException e) {
			tx.rollback();
			throw e;
		}
	}

}
