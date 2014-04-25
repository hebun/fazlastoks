package fazlastoks.admin;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Home implements Serializable {

	Map<String,String> stats;
	
	public Home(){
		String pcount = new Sql.Select("count(*)").from("product").get();
		String ucount = new Sql.Select("count(*)").from("user").get();
		String tcount = new Sql.Select("count(*)").from("talep").where("state=", 1).get();

		String all = new Sql.Select("("+pcount + ") as pcount,(" + ucount
				+ ") as ucount,("+tcount+") as tcount").get();

		stats=Db.selectTable(all).get(0);
	}
	
	public Map<String, String> getStats() {
		return stats;
	}

	public void setStats(Map<String, String> stats) {
		this.stats = stats;
	}

	private static final long serialVersionUID = -5139031444628021650L;

}
