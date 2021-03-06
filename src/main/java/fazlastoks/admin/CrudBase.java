package fazlastoks.admin;

import java.util.List;
import java.util.Map;

import model.ColumnModel;
import freela.util.Db;
import freela.util.Sql;

public class CrudBase {
	String table;
	boolean hasMessage;
	String messageType;
	String message;
	String newCat;
	String editRowId = "0";
	protected List<Map<String, String>> data;
	protected Map<String, String> record;

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public Map<String, String> getRecord() {
		return record;
	}

	public void setRecord(Map<String, String> record) {
		this.record = record;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getEditRowId() {
		return editRowId;
	}

	public void setEditRowId(String editRowId) {
		this.editRowId = editRowId;
	}

	List<ColumnModel> columns;

	public String editRow(String id) {
		editRowId = id;
		return null;
	}

	public CrudBase() {

		// initColumns();
	}

	public void initColumns() {
		columns = Db.select(new Sql.Select("header,name").from("gridfield")
				.where("tableName=", this.table).and("state=", "0").get(),
				ColumnModel.class);

	}

	public void inform(String type, String message) {
		hasMessage = true;
		messageType = "alert_" + type;
		this.message = message;
	}

	public void warn(String message) {
		this.inform("warning", message);
	}

	public void error(String message) {
		this.inform("error", message);
	}

	public void info(String message) {
		this.inform("info", message);
	}

	public void success(String message) {
		this.inform("success", message);
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

	public void errorOccured() {
		this.error("Hata Oluştu.");

	}

}