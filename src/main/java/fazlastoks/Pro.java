package fazlastoks;

import java.io.Serializable;
import java.util.logging.Level;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Product;

import org.hibernate.Session;

@ViewScoped
@ManagedBean
public class Pro implements Serializable {

	private Product pro;
	private Session ss;

	public Product getPro() {
		return pro;
	}

	public void setPro(Product pro) {
		this.pro = pro;
	}

	public Pro() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

		this.ss = FaceUtils.openHibernateSession();
	}

	public String save() {
		FaceUtils.log.info("save called pro.id" + pro.getId());
		 
		return FaceUtils.hibernateSave(this.ss, this.pro) ? "urunlerim" : null;
	}

	

	public void preRenderView() {
		if (pro == null) {
			pro = new Product();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}