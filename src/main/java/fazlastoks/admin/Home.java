package fazlastoks.admin;

import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import freela.util.Db;
import freela.util.FaceUtils;
import freela.util.Sql;

@ViewScoped
@ManagedBean
public class Home implements Serializable {

	Map<String, String> stats;
	Logger log = FaceUtils.log;

	public Home() {
		String pcount = new Sql.Select("count(*)").from("product").get();
		String ucount = new Sql.Select("count(*)").from("user").get();
		String tcount = new Sql.Select("count(*)").from("talep").as("t")
				.innerJoin("product").as("p").on("p.id", "t.productid")
				.where("state=", 0).get();

		String all = new Sql.Select("(" + pcount + ") as pcount,(" + ucount
				+ ") as ucount,(" + tcount + ") as tcount").get();

		stats = Db.selectTable(all).get(0);

		// memUse();
	}

	private void memUse() {
		MemoryMXBean memoryMxBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage memoryUsage = memoryMxBean.getHeapMemoryUsage();
		log.info(memoryMxBean.getHeapMemoryUsage().toString());
	}

	public Map<String, String> getStats() {
		return stats;
	}

	public void setStats(Map<String, String> stats) {
		this.stats = stats;
	}

	private static final long serialVersionUID = -5139031444628021650L;

}
