package cn.com.ultrapower.eoms.user.comm.hibernatesession;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html}.
 */
public class HibernateSessionFactory {

	/** Holds a single instance of Sessionss */
	public static ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();

	/** The single instance of hibernate SessionFactory */
	public static org.hibernate.SessionFactory sessionFactory;

	/**
	 * 
	 * 使用Spring注入sessionFactory
	 */
	public void setHibernateSessionFactory(SessionFactory _sessionFactory) {
		System.out.println("基础组Hibernate SessionFactory 加载成功");
		sessionFactory = _sessionFactory;
	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */

	public static Session currentSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		if (session == null) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}

	public static void main(String[] args) {
		HibernateSessionFactory.currentSession();
	}
}
