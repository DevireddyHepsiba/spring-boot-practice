package hibernate.dao;

import hibernate.entity.Role;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * DAO providing basic CRUD operations for User and role management helpers.
 */
public class UserDao {
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public User save(User user) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			session.persist(user);
			tx.commit();
			return user;
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	public Optional<User> findById(Long id) {
		try (Session session = sessionFactory.openSession()) {
			return Optional.ofNullable(session.get(User.class, id));
		}
	}

	public List<User> findAll() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from User", User.class).list();
		}
	}

	public void update(User user) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			session.merge(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	public void delete(Long id) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			User user = session.get(User.class, id);
			if (user != null) session.remove(user);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	public void addRoleToUser(Long userId, Role role) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			User user = session.get(User.class, userId);
			if (user != null) {
				// Ensure we attach or find a managed Role instance in this session
				Role managedRole = null;
				if (role.getId() != null) {
					managedRole = session.get(Role.class, role.getId());
				} else {
					List<Role> found = session.createQuery("from Role r where r.name = :name", Role.class)
						.setParameter("name", role.getName())
						.list();
					if (!found.isEmpty()) {
						managedRole = found.get(0);
					} else {
						// role is new - persist and use it
						session.persist(role);
						managedRole = role;
					}
				}

				user.addRole(managedRole);
				session.merge(user);
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}

	public void removeRoleFromUser(Long userId, Role role) {
		Transaction tx = null;
		try (Session session = sessionFactory.openSession()) {
			tx = session.beginTransaction();
			User user = session.get(User.class, userId);
			if (user != null) {
				Role managedRole = null;
				if (role.getId() != null) {
					managedRole = session.get(Role.class, role.getId());
				} else {
					List<Role> found = session.createQuery("from Role r where r.name = :name", Role.class)
						.setParameter("name", role.getName())
						.list();
					if (!found.isEmpty()) {
						managedRole = found.get(0);
					}
				}
				if (managedRole != null) {
					user.removeRole(managedRole);
					session.merge(user);
				}
			}
			tx.commit();
		} catch (Exception e) {
			if (tx != null) tx.rollback();
			throw e;
		}
	}
}
