package fazlastoks;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import model.Product;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@RequestScoped
@ManagedBean
public class Search implements Serializable {

	private List<Product> list;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	public Search() {
		
	
	}

	public void init() {
		String sql = new Sql.Select().from("product").where("pname like ", "%"+key+"%").
				or("content like ", "%"+key+"%").get();
		
		list=Db.select(sql,Product.class);
		
		FaceUtils.log.info(sql);

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