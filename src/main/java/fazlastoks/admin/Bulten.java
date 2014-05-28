package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;

import freela.util.Db;
import freela.util.DoMail;
import freela.util.FaceUtils;
import freela.util.Sql;
import freela.util.Sql.Delete;

@ViewScoped
@ManagedBean
public class Bulten extends CrudBase implements Serializable {
	String bultenMessage;
	private String subject;

	public String getBultenMessage() {
		return bultenMessage;
	}

	public void setBultenMessage(String bultenMessage) {
		this.bultenMessage = bultenMessage;
	}

	public String updateRow(Map<String, String> rec) {

		int delete = Db.update(new Sql.Update(this.table).add("email",rec.get("email")).where("id",
				rec.get("id")).get());
		if (delete > 0) {
			
			super.info("Kayıt Güncellendi.");
		} else {
			super.errorOccured();
		}
		return null;
	}

	public String sendEmails() {

		for (Map<String, String> email : emails) {
			try {
				DoMail.postMail(email.get("email"), subject, bultenMessage,
						DoMail.emailFromAddress);
			} catch (MessagingException e) {
				FaceUtils.log.warning(e.getMessage());
			}
		}
		super.info("Bulten mesajı yollandı.");
		return null;

	}

	public Bulten() {
		this.table = "bulten";
		emails = Db.selectTable(new Sql.Select().from(this.table).get());
		this.initColumns();
	}

	public String delete(Map<String, String> record) {
		int delete = Db.delete(new Delete(this.table).where("id",
				record.get("id")).get());
		if (delete > 0) {
			this.emails.remove(record);
			super.info("Kayit Silindi");
		} else {
			super.errorOccured();
		}
		return null;
	}

	public List<Map<String, String>> getEmails() {
		return emails;
	}

	public void setEmails(List<Map<String, String>> emails) {
		this.emails = emails;
	}

	private static final long serialVersionUID = 1L;
	private List<Map<String, String>> emails;

}
