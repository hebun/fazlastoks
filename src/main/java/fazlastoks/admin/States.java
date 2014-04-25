package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Category;
import model.State;
import freela.util.Db;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "ps")
public class States implements Serializable {

	final static String table = "state";
	boolean hasMessage;
	String messageType;
	String message;
	String newCat;
	List<State> states;

	public void addState() {
		State cat = new State(newCat);

		Db.insert(new Sql.Insert(table).add("sname", newCat).get());
		states.add(cat);
		hasMessage = true;
		messageType = "alert_success";
		message = "Yeni kategori başarıyla eklendi.";
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> cats) {
		this.states = cats;
	}

	public States() {
		states = Db.select(new Sql.Select().from(table).get(), State.class);
	}

	public void delete(State cat) {
		states.remove(cat);
		Db.delete(new Sql.Delete(table).where("id=", cat.getId()).get());
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
