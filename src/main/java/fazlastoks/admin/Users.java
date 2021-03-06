package fazlastoks.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Users extends CrudBase implements Serializable {

	List<Map<String, String>> users;

	public List<Map<String, String>> getUsers() {
		return users;
	}

	public void setUsers(List<Map<String, String>> users) {
		this.users = users;
	}

	static int insCount = 0;

	public Users() {

		this.table = "user";
		String sql = new Sql.Select("u.*,count(p.id) as productCount")
				.from(table).as("u").leftJoin("product").as("p")
				.on("u.id", "p.userid").groupBy("u.id").get();
		FaceUtils.log.info(insCount++ + "");
		;
		users = Db.selectTable(sql);

		super.initColumns();

	}

	private static final long serialVersionUID = 6349455300004482792L;

}