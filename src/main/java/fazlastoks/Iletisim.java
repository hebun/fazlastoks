package fazlastoks;

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
import model.Talep;

@ViewScoped
@ManagedBean
public class Iletisim implements Serializable {
	Talep talep;

	public Talep getTalep() {
		return talep;
	}

	public void setTalep(Talep talep) {
		this.talep = talep;
	}

	public Iletisim() {
		talep = new Talep();
	}

	public String sendMessage() {

		List<Map<String, String>> table = Db.selectTable(new Sql.Select()
				.from("mailcontent").where("name", "iletisim").get());

		String mc = table.get(0).get("content");

		mc = mc.replaceAll("#Name#", talep.getUname())
				.replaceAll("#email#", talep.getEmail())
				.replaceAll("#Content#", mc);

		try {
			DoMail.postMail(new String[] { "info@nethizmet.net",
					"ismettung@gmail.com" }, "Fazlastoklar bilgi",
					talep.getNotes(), DoMail.emailFromAddress);
		} catch (MessagingException e) {
			FaceUtils.addError("Mesaj gönderilirken hata oluştu.");
			FaceUtils.log.warning(e.getMessage());
			return null;
		}
		FaceUtils.addError("Mesajınız İletildi.");
		return null;

	}

	private static final long serialVersionUID = 1L;

}
