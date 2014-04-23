package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import model.Product;

@RequestScoped
@ManagedBean
public class Search implements Serializable {

	private List<Product> list;
	private transient Session ss;
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	public Search() {
		System.out.println("products consturctor called");

	}

	@SuppressWarnings("unchecked")
	public void init() {
		this.ss = FaceUtils.openHibernateSession();

		ss.getTransaction().begin();
		String value = "%" + key + "%";
		list = ss
				.createCriteria(Product.class)
				.add(Restrictions.disjunction()
						.add(Restrictions.like("pname", value))
						.add(Restrictions.like("content", value))).list();

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