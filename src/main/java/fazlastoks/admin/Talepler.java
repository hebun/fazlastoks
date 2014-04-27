package fazlastoks.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.ColumnModel;
import model.Talep;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean(name = "talep")
public class Talepler extends CrudBase implements Serializable {

	List<Talep> taleps;

	public List<Talep> getTaleps() {
		return taleps;
	}

	public void setTaleps(List<Talep> taleps) {
		this.taleps = taleps;
	}

	public Talepler() {
		this.table = "talep";
		taleps = Db.select(new Sql.Select().from(table).order("id").desc()
				.get(), Talep.class);

		columns = Arrays.asList(new ColumnModel("Ad Soyad", "name"),
				new ColumnModel("E-mail", "email"), new ColumnModel("Gsm",
						"gsm"), new ColumnModel("Notlar", "notes"),
				new ColumnModel("Okundu mu?", "state"));

	}

	public void delete(Talep cat) {
		taleps.remove(cat);
		Db.delete(new Sql.Delete(table).where("id=", cat.getId()).get());
		hasMessage = true;
		messageType = "alert_success";
		message = "Kayıt başarıyla silindi.";
	}

	@Override
	public void toggleRead(Object t) {
		if (t instanceof Talep) {
			Talep sel = (Talep) t;
			sel.setState(1);
			Db.update(new Sql.Update(table).add("state", "1")
					.where("id=", sel.getId()).get());
		}
	}

	private static final long serialVersionUID = 1838121987108198351L;

}
