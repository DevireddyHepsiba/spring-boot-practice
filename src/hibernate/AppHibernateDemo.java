package hibernate;

import hibernate.dao.UserDao;
import hibernate.entity.Role;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;

import java.util.List;

/**
 * Simple demo to exercise Hibernate CRUD and role management.
 */
public class AppHibernateDemo {
	public static void main(String[] args) {
		UserDao userDao = new UserDao();

	System.err.println("\n--- Creating roles and users ---");
		Role admin = new Role("ADMIN");
		Role userRole = new Role("USER");

		User alice = new User("Alice", "alice@example.com");
		alice.addRole(admin);

		User bob = new User("Bob", "bob@example.com");
		bob.addRole(userRole);

		// Persist users (roles will be persisted because of cascade)
		userDao.save(alice);
		userDao.save(bob);

	System.err.println("\n--- All users after insert ---");
		List<User> all = userDao.findAll();
		all.forEach(System.out::println);

		// Update example
	System.err.println("\n--- Updating Bob's email ---");
		bob.setEmail("bob.new@example.com");
		userDao.update(bob);
		userDao.findById(bob.getId()).ifPresent(System.out::println);

		// Add role to existing user
	System.err.println("\n--- Adding ADMIN role to Bob ---");
		userDao.addRoleToUser(bob.getId(), admin);
		userDao.findById(bob.getId()).ifPresent(u -> {
			System.out.println("Roles for " + u.getName() + ": " + u.getRoles());
		});

		// Delete example
	System.err.println("\n--- Deleting Alice ---");
		userDao.delete(alice.getId());

	System.err.println("\n--- Final users ---");
		userDao.findAll().forEach(System.out::println);

		// Shutdown Hibernate
		HibernateUtil.shutdown();
	}
}
