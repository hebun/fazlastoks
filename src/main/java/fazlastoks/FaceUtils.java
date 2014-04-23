package fazlastoks;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

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
		consoleHandler.setLevel(Level.ALL);
		log.addHandler(consoleHandler);
		log.setLevel(Level.ALL);
		
	}

	public static Session openHibernateSession() {
		Configuration configuration = new Configuration();
		Configuration cfg = configuration.configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory factory = cfg.buildSessionFactory(builder.build());
		return factory.openSession();
	}

	public static Session getHibernateSession() {
		Configuration configuration = new Configuration();
		Configuration cfg = configuration.configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(cfg.getProperties());
		SessionFactory factory = cfg.buildSessionFactory(builder.build());
		return factory.getCurrentSession();
	}

	public static boolean hibernateSave(Session ss, Object obj) {
		try {

			ss.getTransaction().begin();

			ss.saveOrUpdate(obj);
			ss.getTransaction().commit();
			return true;
		} catch (Exception e) {
			ss.getTransaction().rollback();
			FaceUtils.log.severe(e.getMessage());
			return false;
		}
	}

	public static void addError(String msg) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				msg, "");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public static void redirectTo(String url) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context
				.getResponse();

		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			log.warning(e.getMessage());
			e.printStackTrace();
		}
	}

	public static boolean hibernateDelete(Session ss, Object obj) {
		try {

			ss.getTransaction().begin();

			ss.delete(obj);
			ss.getTransaction().commit();
			return true;
		} catch (Exception e) {
			ss.getTransaction().rollback();
			FaceUtils.log.severe(e.getMessage());
			return false;
		}

	}
}
