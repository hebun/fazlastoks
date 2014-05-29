package fazlastoks;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.mail.MessagingException;

import freela.util.DoMail;
import freela.util.FaceUtils;
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

		try {
			DoMail.postMail(talep.getEmail(), "Fazlastoklar bilgi",
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
