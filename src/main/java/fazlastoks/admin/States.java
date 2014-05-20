package fazlastoks.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.Category;
import model.ColumnModel;
import model.State;
import freela.util.Db;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "ps")
public class States extends CrudBase implements Serializable {
	List<State> states;

	public States() {
		this.table = "state";
		states = Db.select(new Sql.Select().from(table).get(), State.class);
		initColumns();
	}

	public String updateRow(State s) {

		if (Db.update(new Sql.Update(table).add("sname", s.getSname())
				.where("id", s.getId()).get()) > 0) {
			super.success("Durum Guncellendi.");
		} else {
			super.errorOccured();
		}
		this.editRowId = "0";
		return null;

	}
	public void addState() {
		State cat = new State(newCat);

		cat.setId(Db.insert(new Sql.Insert(table).add("sname", newCat).get()));
		states.add(cat);
		hasMessage = true;
		messageType = "alert_success";
		message = "Yeni kayıt başarıyla eklendi.";
	}

	public void delete(State cat) {
		states.remove(cat);
		Db.delete(new Sql.Delete(table).where("id=", cat.getId()).get());
		hasMessage = true;
		messageType = "alert_success";
		message = "Kayıt başarıyla silindi.";
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> cats) {
		this.states = cats;
	}

	private static final long serialVersionUID = -474092066100939384L;
}
