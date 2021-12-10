package hu.tom;

import java.util.Iterator;
import java.util.List;

public class Main {


	public static void main(String[] args) {

		ManageUser manageUser = new ManageUser();

		/* Add few user records in database */
		Integer userId1 = manageUser.addUser("Muhammad", "Ali");
		Integer userId2 = manageUser.addUser("Papp", "Laci");
		Integer userId3 = manageUser.addUser("Mike", "Tyson");

		/* Find user with Id */
		User oneUser = manageUser.findUserById(userId1);
		System.out.print("First Name: " + oneUser.getFirstName());
		System.out.println("  Last Name: " + oneUser.getLastName());
		System.out.println();

		/* List down all the user */
		List<User> allUser = manageUser.findAllUser();
		/* ...and print */
		for (Iterator<User> iterator = allUser.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			System.out.print("First Name: " + user.getFirstName());
			System.out.print("  Last Name: " + user.getLastName());
			System.out.println();
		}

		/* Update users records */
		manageUser.updateUser(userId3, "Evan", "Holyfield");

		/* Delete an user from the database */
		manageUser.deleteUser(userId2);

		/* List down all the user again */
		List<User> allUser2 = manageUser.findAllUser();
		/* ...and print */
		for (Iterator<User> iterator = allUser2.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			System.out.print("First Name: " + user.getFirstName());
			System.out.print("  Last Name: " + user.getLastName());
			System.out.println();
		}
	}

}
