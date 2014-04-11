// default package
// Generated Apr 11, 2014 10:35:08 PM by Hibernate Tools 4.0.0
package model;
import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Productphoto.
 * @see .Productphoto
 * @author Hibernate Tools
 */
public class ProductphotoHome {

	private static final Log log = LogFactory.getLog(ProductphotoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Productphoto transientInstance) {
		log.debug("persisting Productphoto instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Productphoto instance) {
		log.debug("attaching dirty Productphoto instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Productphoto instance) {
		log.debug("attaching clean Productphoto instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Productphoto persistentInstance) {
		log.debug("deleting Productphoto instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Productphoto merge(Productphoto detachedInstance) {
		log.debug("merging Productphoto instance");
		try {
			Productphoto result = (Productphoto) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Productphoto findById(java.lang.Integer id) {
		log.debug("getting Productphoto instance with id: " + id);
		try {
			Productphoto instance = (Productphoto) sessionFactory
					.getCurrentSession().get("Productphoto", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Productphoto> findByExample(Productphoto instance) {
		log.debug("finding Productphoto instance by example");
		try {
			List<Productphoto> results = (List<Productphoto>) sessionFactory
					.getCurrentSession().createCriteria("Productphoto")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
