package cn.com.ultrapower.eoms.common.basedao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateFactory
{
	 /** 
     * Location of hibernate.cfg.xml file.
     * NOTICE: Location should be on the classpath as Hibernate uses
     * #resourceAsStream style lookup for its configuration file. That
     * is place the config file in a Java package - the default location
     * is the default Java package.<br><br>
     * Examples: <br>
     * <code>CONFIG_FILE_LOCATION = "/hibernate.conf.xml". 
     * CONFIG_FILE_LOCATION = "/com/foo/bar/myhiberstuff.conf.xml".</code> 
     */
    public static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
    
	/** Holds a single instance of Session */
    public static final ThreadLocal threadLocal = new ThreadLocal();

    /** The single instance of hibernate configuration */
    public static final Configuration cfg = new Configuration();

    /** The single instance of hibernate SessionFactory */
    public static org.hibernate.SessionFactory sessionFactory;
    
    public HibernateFactory()
    {
    	
    }
	public static void setsessionFactory(org.hibernate.SessionFactory parmsessionFactory) {
		sessionFactory = parmsessionFactory;
	}
    public synchronized static SessionFactory getsessionFactory() throws HibernateException {
        if (sessionFactory == null)
        {
            try
            {
                cfg.configure(CONFIG_FILE_LOCATION);
                sessionFactory 						= cfg.buildSessionFactory();
            }
            catch (Exception e)
            {
                System.err.println("%%%% Error Creating SessionFactory %%%%");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}