package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.ColumnModel;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Update;

;

@ViewScoped
@ManagedBean
public class UserDet extends CrudBase implements Serializable {

	
	
	private List<ColumnModel> allColumns;

	public UserDet() {
		this.table = "user";
		this.record = FaceUtils.getRecordFromGET("id", this.table);
		super.initColumns();
		
		allColumns= Db.select(new Sql.Select("header,name").from("gridfield")
				.where("tableName=", this.table).get(),
				ColumnModel.class);

	}

	public List<ColumnModel> getAllColumns() {
		return allColumns;
	}

	public void setAllColumns(List<ColumnModel> allColumns) {
		this.allColumns = allColumns;
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
	public String banUser() {
		Update update = new Update(this.table).add("state","PENDING");
		
		update.where("id", record.get("id"));
		Db.update(update.get());
		super.success("Kullanıcı Banlandı.");
		return null;

	}
	private static final long serialVersionUID = 1L;

}
