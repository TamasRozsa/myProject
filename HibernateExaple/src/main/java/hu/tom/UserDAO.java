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
	public Optional<User> get(long userId) {
		return Optional.ofNullable(entityManager.find(User.class, userId));
	}

	@Override
	public Optional<User> get(String name) {
		TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u WHERE u.name=:UserName",
				User.class);
		typedQuery.setParameter("UserName", name);
		return typedQuery.getResultList().stream().findFirst(); // TODO return ONLY first record !!!
	}

	@Override
	public List<User> getAll() {
		TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u ", User.class);
		return typedQuery.getResultList();
	}

	@Override
	public void save(User t) {
		executeTransaction(e -> e.persist(t));
	}

	@Override
	public void update(long userId, User t) {
		t.setId(userId);
		executeTransaction(e -> e.merge(t));
	}

	@Override
	public void delete(long userId) {
		User user = entityManager.find(User.class, userId);
		if (user != null) {
			executeTransaction(e -> e.remove(user));
		}
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
