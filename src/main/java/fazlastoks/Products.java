package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Product;

@ViewScoped
@ManagedBean
public class Products implements Serializable {

	private List<Product> list;


	public Products() {
	
	}

	public List<Product> getList() {
		
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public String delete(Product pro) {
		FaceUtils.log.finest("delete pro.id" + pro.getId());

	
		list.remove(pro);
		return null;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2018425860394584424L;

}