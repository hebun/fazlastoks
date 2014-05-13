package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import model.User;
import freela.util.BaseBean;
import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@SuppressWarnings("serial")
@ViewScoped
@ManagedBean(name="act")
public class Activation extends BaseBean implements Serializable {
	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Activation() {
		this.record = FaceUtils.getRecordFromGET("code", "activation");

		if (this.record.size() > 0) {

			// User user = FaceUtils.getObjectById(User.class, "user",
			// record.get("id"));

			Db.update(new Sql.Update("user").add("state", "ACTIVE")
					.where("id", this.record.get("userid")).and("state", "PENDING")
					.get());
			message = "Tebrikler Fazlastoklar.com   Üyeliğiniz Tamamlanmıştır. Şimdi Giriş Yapabilirsiniz.";

		} else {
			message = "Geçersiz Kod!";

		}

	}
}
