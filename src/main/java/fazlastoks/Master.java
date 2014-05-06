package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Category;
import model.Product;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

import java.util.List;

@ViewScoped
@ManagedBean
public class Master implements Serializable {

	String searchText;

	List<Category> cats;

	public Master() {
		cats = Db.select(new Sql.Select().from("catcount").get(),
				Category.class);
		
		
	}

	public String search() {

		FaceUtils.log.fine("searchtext:" + searchText);

		return "urun-arama?faces-redirect=true&key=" + searchText;

	}

	public List<Category> getCats() {
		return cats;
	}

	public void setCats(List<Category> cats) {
		this.cats = cats;
	}



	public void Login() {

	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String test() {
		return "";
	}

	private static final long serialVersionUID = 4790738744214962785L;

}
