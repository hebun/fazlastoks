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
import javax.servlet.http.Part;

import freela.util.Sql.Select;
import model.Product;

public class FaceUtils {
	public static final Logger log = Logger.getLogger("FazlaStoklar");
	public static String uploadDir;
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

		log.setLevel(Level.ALL);

	}

	public static String getFilename(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String filename = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return filename.substring(filename.lastIndexOf('/') + 1)
						.substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

	public static <T> T getObjectFromGETParam(String param, Class<T> type,
			String table) {

		String string = getGET(param);
		if (string != null)
			return getObjectById(type, table, string);
		else {
			try {
				return type.newInstance();
			} catch (InstantiationException e) {
				log.warning(e.getMessage());
			} catch (IllegalAccessException e) {
				log.warning(e.getMessage());
			}
		}
		return null;

	}

	public static <T> T getObjectById(Class<T> type, String table, String string) {
		Sql.Select sql = (Select) new Sql.Select().from(table)
				.where("id=", string).prepare();

		List<T> li = Db.preparedSelect(sql.get(), sql.params(), type);
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

	public static String getGET(String param) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext context = facesContext.getExternalContext();
		String string = context.getRequestParameterMap().get(param);
		return string;
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
