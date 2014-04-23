package fazlastoks;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;

import model.Product;

@ViewScoped
@ManagedBean
public class Products implements Serializable {

	private List<Product> list;
	private transient Session ss;

	@SuppressWarnings("unchecked")
	public Products() {
		this.ss = FaceUtils.openHibernateSession();
		System.out.println("products consturctor called");
		ss.getTransaction().begin();
		list = ss.createCriteria(Product.class).list();
		this.ss.getTransaction().commit();

	}

	public List<Product> getList() {
		
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public String delete(Product pro) {
		FaceUtils.log.finest("delete pro.id" + pro.getId());

		FaceUtils.hibernateDelete(this.ss, pro);
		list.remove(pro);
		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}