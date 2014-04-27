package fazlastoks.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Category;
import model.ColumnModel;
import freela.util.Db;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Categories extends CrudBase implements Serializable {

	List<Category> cats;

	public Categories() {
		this.table="category";
		cats = Db.select(new Sql.Select().from(table).get(),
				Category.class);
		columns = Arrays.asList(new ColumnModel("Kategori Ismi", "cname"));
	}

	public void addCat() {

		Category cat = new Category(newCat);

		cat.setId(Db.insert(new Sql.Insert(table).add("cname", newCat)
				.get()));

		cats.add(cat);
		hasMessage = true;
		messageType = "alert_success";
		message = "Yeni kategori başarıyla eklendi.";
	}

	public void delete(Category cat) {
		cats.remove(cat);
		Db.delete(new Sql.Delete(table).where("id=", cat.getId()).get());
		hasMessage = true;
		messageType = "alert_success";
		message = "Kategori başarıyla silindi.";
	}

	public List<Category> getCats() {
		return cats;
	}

	public void setCats(List<Category> cats) {
		this.cats = cats;
	}

	private static final long serialVersionUID = -474092066100939384L;
}
