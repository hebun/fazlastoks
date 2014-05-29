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
import freela.util.Sql.Update;

@ViewScoped
@ManagedBean
public class Categories extends CrudBase implements Serializable {

	List<Category> cats;
	Category cat;

	public Category getCat() {
		return cat;
	}

	public void setCat(Category cat) {
		this.cat = cat;
	}

	public Categories() {
		this.table = "category";
		this.cat = new Category();
		cats = Db.select(new Sql.Select().from(table).get(), Category.class);

		initColumns();

	}

	public String updateRow(Category c) {

		try {
			Update update = new Sql.Update(table).add("cname", c.getCname());

			if (c.getDescription() != null)
				update.add("description", c.getDescription());

			if (c.getKeywords() != null)
				update.add("keywords", c.getKeywords());
			if (c.getTitle() != null)
				update.add("title", c.getTitle());
			update.where("id", c.getId());

			if (Db.update(update.get()) > 0) {
				super.success("Kategori Guncellendi.");
			} else {
				super.errorOccured();
			}
			this.editRowId = "0";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public void addCat() {

		try {
			cat.setId(Db.insert(new Sql.Insert(table)
					.add("cname", cat.getCname())
					.add("description", cat.getDescription())
					.add("keywords", cat.getKeywords())
					.add("title", cat.getTitle()).get()));

			cats.add(cat);
			hasMessage = true;
			messageType = "alert_success";
			message = "Yeni kategori başarıyla eklendi.";
			this.cat = new Category();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
