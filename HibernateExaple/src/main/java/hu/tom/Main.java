package hu.tom;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Main {

	private static Dao<User> userDao = new UserDAO();

	public static void main(String[] args) {

		/* Add few user records in database */
		saveUser(new User("Evan Holyfield", "12@34"));
		saveUser(new User("Papp Laci", "34@56"));
		saveUser(new User("Mike Tyson", "45@67"));

		/* Find user with Id */
		User user1 = getUser(2L);
		/* Find user with Name */
		User user2 = getUser("Mike Tyson");

		System.out.println("-Name: " + user1.getName() + " -Password: " + user1.getPassword());
		System.out.println();
		System.out.println("-Name: " + user2.getName() + " -Password: " + user2.getPassword());
		System.out.println();

		/* List down all the user */
		List<User> allUser = getAllUsers();

		for (Iterator<User> iterator = allUser.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			System.out.println("-Name: " + user.getName() + " -Password: " + user.getPassword());
		}

		/* Update users records */
		updateUser(3, new User("Muhammad Ali", "23@45"));

		/* Delete an user from the database */
		deleteUser(1);

		/* List down all the user again */
		List<User> allUser2 = getAllUsers();

		for (Iterator<User> iterator = allUser2.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			System.out.println("-Name: " + user.getName() + " -Password: " + user.getPassword());
		}
	}

	public static User getUser(long id) {
		Optional<User> user = userDao.get(id);
		return user.orElseGet(() -> new User("non-existing user", "no-record"));
	}

	public static User getUser(String name) {
		Optional<User> user = userDao.get(name);
		return user.orElseGet(() -> new User("non-existing user", "no-record"));
	}

	public static List<User> getAllUsers() {
		return userDao.getAll();
	}

	public static void updateUser(long id, User t) {
		userDao.update(id, t);
	}

	public static void saveUser(User t) {
		userDao.save(t);
	}

	public static void deleteUser(long id) {
		userDao.delete(id);
	}

}
