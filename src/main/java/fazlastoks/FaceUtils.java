package fazlastoks;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class FaceUtils {
	public static final Logger log = Logger.getLogger("FazlaStoklar");
	static {
		log.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new LogFormatter());
		log.addHandler(consoleHandler);
	}

	public static Session openHibernateSession() {
		Configuration configuration = new Configuration();
		Configuration cfg = configuration.configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory factory = cfg.buildSessionFactory(builder.build());
		return factory.openSession();
	}

	public static boolean hibernateSave(Session ss, Object obj) {
		try {

			ss.getTransaction().begin();

			ss.save(obj);
			ss.getTransaction().commit();
			return true;
		} catch (Exception e) {
			ss.getTransaction().rollback();
			FaceUtils.log.severe(e.getMessage());
			return false;
		}
	}
}
