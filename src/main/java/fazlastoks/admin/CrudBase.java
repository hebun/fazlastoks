package fazlastoks.admin;

import java.util.List;

import model.ColumnModel;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

public class CrudBase {
	String table;
	boolean hasMessage;
	String messageType;
	String message;
	String newCat;

	List<ColumnModel> columns;

	public CrudBase() {

		// initColumns();
	}

	public void initColumns() {
		columns = Db.select(new Sql.Select("header,name").from("gridfield")
				.where("tableName=", this.table).and("state=", "0").get(),
				ColumnModel.class);
		
		FaceUtils.log.info("initcolumns called");
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public String getTable() {
		return table;
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

	public void toggleRead(Object m) {
	}

}