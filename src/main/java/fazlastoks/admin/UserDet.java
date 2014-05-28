package fazlastoks.admin;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql.Update;

;

@ViewScoped
@ManagedBean
public class UserDet extends CrudBase implements Serializable {

	public UserDet() {
		this.table = "user";
		this.record = FaceUtils.getRecordFromGET("id", this.table);
		super.initColumns();

	}

	public String updateUser() {
		Update update = new Update(this.table);
		for (Map.Entry<String, String> col : this.record.entrySet()) {
			if (!col.getKey().equals("id"))
				update.add(col.getKey(), col.getValue());
		}
		update.where("id", record.get("id"));
		Db.update(update.get());
		super.success("Kullanıcı Güncellendi.");
		return null;

	}

	private static final long serialVersionUID = 1L;

}
