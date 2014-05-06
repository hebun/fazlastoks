package freela.util;

import java.io.IOException;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import model.Product;

public class FaceUtils {
	public static final Logger log = Logger.getLogger("FazlaStoklar");
	static {
		log.setUseParentHandlers(false);
		ConsoleHandler consoleHandler = new ConsoleHandler() {
			{
				setOutputStream(System.out);
			}
		};
		consoleHandler.setFormatter(new LogFormatter());
		consoleHandler.setLevel(Level.ALL);	
		
		if (log.getHandlers().length == 0) {
			log.addHandler(consoleHandler);
		}
	
		log.setLevel(Level.WARNING);

	}

	public static <T> T getObjectFromGETParam(String param, Class<T> type,
			String table) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		String string = context.getRequestParameterMap().get(param);

		List<T> li = Db.select(
				new Sql.Select().from(table).where("id=", string).get(),
				type);
		T ret = null;
		try {
			if (li.size() == 0) {

				ret = type.newInstance();
			} else {
				ret = li.get(0);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return ret;

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

}
