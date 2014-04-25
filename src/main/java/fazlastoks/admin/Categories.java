package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Category;
import freela.util.Db;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Categories implements Serializable {

	boolean hasMessage;
	String messageType;
	String message;
	String newCat;
	List<Category> cats;

	public void addCat() {

		Category cat = new Category(newCat);

		cat.setId(Db.insert(new Sql.Insert("category").add("cname", newCat)
				.get()));

		cats.add(cat);
		hasMessage = true;
		messageType = "alert_success";
		message = "Yeni kategori başarıyla eklendi.";
	}

	public List<Category> getCats() {
		return cats;
	}

	public void setCats(List<Category> cats) {
		this.cats = cats;
	}

	public Categories() {
		cats = Db.select(new Sql.Select().from("category").get(),
				Category.class);
	}

	public void delete(Category cat) {
		cats.remove(cat);
		Db.delete(new Sql.Delete("category").where("id=", cat.getId()).get());
		hasMessage = true;
		messageType = "alert_success";
		message = "Kategori başarıyla silindi.";
	}

	public boolean isHasMessage() {
		return hasMessage;
	}

	public String getNewCat() {
		return newCat;
	}

	public void setNewCat(String newCat) {
		this.newCat = newCat;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setHasMessage(boolean hasMessage) {
		this.hasMessage = hasMessage;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return message;
	}

	private static final long serialVersionUID = -474092066100939384L;
}
