package freela.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Db {

	public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static String DB_URL = "jdbc:mysql://localhost:3306/fazlastoklar?useUnicode=true&characterEncoding=utf8";
	public static String USER = "root";
	public static String PASS = "2882";
	public static boolean started = false;
	static Connection conn = null;
	static Statement stmt = null;
	static int say = 0;
	public static boolean debug = false;
	public static boolean dumpTime = false;

	public static void start(String caller) throws ClassNotFoundException,
			SQLException {

		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		stmt = conn.createStatement();

		started = true;
	}

	public static void select(String sql, SelectCallback callback) {

		try {
			if (debug)
				FaceUtils.log.info(sql);
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			callback.callback(rs, columns);

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select");
			e.printStackTrace();
		} finally {
			close("");
		}

	}

	public static <T> List<T> select(String sql, Class<T> type) {
		long start = System.currentTimeMillis();

		try {

			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}
			List<T> list = new ArrayList<T>();

			while (rs.next()) {
				T obj = type.newInstance();
				for (Field f : type.getDeclaredFields()) {
					if (Modifier.isPrivate(f.getModifiers())) {
						String input = f.getName();
						input = input.substring(0, 1).toUpperCase()
								+ input.substring(1);
						Method met;
						Class<?> type2 = f.getType();
				
						
						try {
							Object object = rs.getObject(f.getName());

							met = type.getMethod("set" + input, type2);
							if (object != null)

								met.invoke(obj, object);

						} catch (NoSuchMethodException e) {
							FaceUtils.log.finer(e.getMessage());
							e.printStackTrace();
						} catch (Exception e) {
							FaceUtils.log.fine(e.toString());
							e.printStackTrace();
						}
					}
				}
				list.add(obj);
			}
			return list;
		} catch (SQLException se) {
			FaceUtils.log.warning(se.getMessage()+":"+ sql);
			se.printStackTrace();
			return new ArrayList<T>();
		} catch (Exception e) {
			System.out.println("e in select");
			e.printStackTrace();
			return new ArrayList<T>();
		} finally {
			if (debug)
				FaceUtils.log.info(sql + " time:"
						+ (System.currentTimeMillis() - start));
			close("");
		}

	}

	public static void select(String sql, SelectCallbackTable callback) {

		String[] columns = null;
		List<List<String>> data = null;
		try {
			if (debug)
				FaceUtils.log.info(sql);
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			data = new ArrayList<List<String>>();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (String col : columns) {
					String string = rs.getString(col);
					if (string == null)
						string = "null";
					row.add(string);
				}
				data.add(row);
			}

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select" + sql);
			e.printStackTrace();
		} finally {
			close("");
		}
		callback.callback(columns, data);
	}

	public static void select(String sql, SelectCallbackLoop callback) {

		try {
			if (debug)
				FaceUtils.log.info(sql);
			start("");
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();

			String[] columns = new String[colCount];

			for (int i = 0; i < colCount; i++) {
				columns[i] = new String(metaData.getColumnLabel(i + 1));
			}

			while (rs.next()) {

				Map<String, String> map = new LinkedHashMap<String, String>();

				for (String col : columns) {
					String string = rs.getString(col);
					if (string == null)
						string = "null";
					map.put(col, string);

				}
				callback.callback(map);

			}

		} catch (SQLException se) {
			System.out.println("se in select" + sql);
			se.printStackTrace();
		} catch (Exception e) {
			System.out.println("e in select" + sql);
			e.printStackTrace();
		} finally {
			close("");
		}

	}

	public static List<Map<String, String>> selectTable(String sql) {

		long start = System.currentTimeMillis();

		List<Map<String, String>> list = null;

		try {

			start("");
			ResultSet rs = stmt.executeQuery(sql);

			ResultSetMetaData data = rs.getMetaData();

			int colCount = data.getColumnCount();

			list = new ArrayList<Map<String, String>>();

			while (rs.next()) {

				Map<String, String> hash = new HashMap<String, String>();
				for (int i = 1; i <= colCount; i++) {

					String value = rs.getString(i) == null ? "NULL" : rs
							.getString(i);
					String columnLabel = data.getColumnLabel(i);
					if (hash.containsKey(columnLabel)) {
						columnLabel += "_1";
					}

					hash.put(columnLabel, value);
				}
			list.add(hash);

			}
		} catch (SQLException se) {

			se.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			close("");
		}// end try
		if (debug) {
			FaceUtils.log.info(sql + " time:"
					+ (System.currentTimeMillis() - start));
		}
		return list;
	}

	public static int insert(String sql) {

		try {
			if (debug)
				FaceUtils.log.info(sql);
			start("query not started");
			say++;
			if (say % 100 == 0)
				System.out.print(say + ".");

			int rs = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (rs == 0)
				return rs;
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return (int) generatedKeys.getLong(1);
			}
			return 0;
		} catch (SQLException se) {
			System.out.println("se in insert" + sql);
			se.printStackTrace();
			return 0;
		} catch (Exception e) {

			System.out.println("query ex");
			return 0;
		} finally {

			close("");

		}// end trys

	}

	public static int update(String sql) {
		return insert(sql);
	}

	public static int delete(String sql) {
		return insert(sql);
	}

	public static void close(String caller) {

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		started = false;

	}

	public static Map<String, String> callProcedure(String sql) {
		try {
			start("");
			Map<String, String> map = new HashMap<String, String>();

			CallableStatement callableStatement = conn.prepareCall("{" + sql
					+ "}");

			callableStatement.registerOutParameter("pcount", Types.INTEGER);
			callableStatement.registerOutParameter("ucount", Types.INTEGER);

			boolean hadResults = callableStatement.execute();

			while (hadResults) {
				ResultSet rs = callableStatement.getResultSet();

				// process result set

				hadResults = callableStatement.getMoreResults();
			}

			map.put("pcount", callableStatement.getInt("pcount") + "");
			map.put("ucount", callableStatement.getInt("ucount") + "");
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return new HashMap<String, String>();

	}

	public static void main(String[] args) {
		String genSql = "";
		try {
			long a = System.currentTimeMillis();
			start("");

			DatabaseMetaData data = conn.getMetaData();
			ResultSet res = data.getTables(null, null, null,
					new String[] { "TABLE" });

			List<String> sourceTables = new ArrayList<String>();

			while (res.next()) {
				System.out.println("   " + res.getString("TABLE_CAT") + ", "
						+ res.getString("TABLE_SCHEM") + ", "
						+ res.getString("TABLE_NAME") + ", "
						+ res.getString("TABLE_TYPE") + ", "
						+ res.getString("REMARKS"));
				sourceTables.add(res.getString("TABLE_NAME"));
			}
			long b = System.currentTimeMillis() - a;

			Connection remCon = DriverManager.getConnection(
					"jdbc:mysql://173.243.120.226:3306/freelaj_fazlastoklar",
					"freelaj_fazla", "2882jh");

			long c = System.currentTimeMillis() - a;

			DatabaseMetaData dataR = remCon.getMetaData();
			System.out.println("blbla");

			ResultSet resR = dataR.getTables(null, null, null,
					new String[] { "TABLE" });
			List<String> targetTables = new ArrayList<String>();

			long d = System.currentTimeMillis() - a;

			while (resR.next()) {
				System.out.println("   " + resR.getString("TABLE_CAT") + ", "
						+ resR.getString("TABLE_SCHEM") + ", "
						+ resR.getString("TABLE_NAME") + ", "
						+ resR.getString("TABLE_TYPE") + ", "
						+ resR.getString("REMARKS"));
				targetTables.add(resR.getString("TABLE_NAME"));
			}

			List<String> diffTables = new ArrayList<String>();

			for (String string : sourceTables) {
				if (targetTables.indexOf(string) >= 0) {
					ResultSet scols = data.getColumns(null, null, string, null);
					ResultSet tcols = dataR
							.getColumns(null, null, string, null);

					List<String> scollist = new ArrayList<String>();
					List<String> tcollist = new ArrayList<String>();

					while (tcols.next()) {
						String coldef = tcols.getString(4) + ":"
								+ tcols.getString(6) + ":" + tcols.getString(7);
						tcollist.add(coldef);
					}

					while (scols.next()) {
						String col = scols.getString(4) + ":"
								+ scols.getString(6) + ":" + scols.getString(7);

						String colname = scols.getString(4);
						if (!subContain(tcollist, colname)) {
							System.out.println("missing column:" + string + "."
									+ col);

							genSql += "\n alter table " + string
									+ " add column " + colname + " "
									+ scols.getString(6) + " ("
									+ scols.getString(7) + ");";

						} else if (tcollist.indexOf(col) < 0) {
							System.out.println("altered column:" + string + "."
									+ col);
							genSql += "\n alter table " + string + " modify  "
									+ colname + " " + scols.getString(6) + " ("
									+ scols.getString(7) + ");";
						} else {

						}
						scollist.add(col);
					}

				} else {
					System.out.println("missing table:" + string);

					ResultSet scols = data.getColumns(null, null, string, null);
					genSql += "\n create table " + string + "(\n";
					while (scols.next()) {
						genSql += "\n" + scols.getString(4) + " "
								+ scols.getString(6) + " ("
								+ scols.getString(7) + "),";

					}
					genSql += ")";

				}
			}
			long e = System.currentTimeMillis() - a;
			// System.out.println("Missing Tables:");

			for (String table : sourceTables) {
				ResultSet cols = data.getColumns(null, null, table, null);
				while (cols.next()) {
					// System.out.print(cols.getString(2) + " ");
					// System.out.print(cols.getString(3) + " ");
					// System.out.print(cols.getString(4) + " ");
					// System.out.print(cols.getString(6) + " ");
					// System.out.println(cols.getString(7));
				}

			}

			System.out.println("================");
			long f = System.currentTimeMillis() - a;

			System.out.println("\ngot local:" + (b) + " \nconnected to remote:"
					+ (c - b) + " \ngot remote:" + (d - c)
					+ " \nsearched diffs:" + (e - d) + "\n last is:" + (f - e)
					+ " ii:" + ii);
			System.out.println(genSql);
		} catch (ClassNotFoundException e) {
			System.out.println("blbla");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("blbla");
			e.printStackTrace();
		}

	}

	private static int ii = 0;

	private static boolean subContain(List<String> tcollist, String string) {

		for (String string1 : tcollist) {
			ii++;
			if (string1.split(":")[0].equals(string)) {
				return true;
			}
		}

		return false;
	}

	public static interface SelectCallback {
		public void callback(ResultSet rs, String[] columns)
				throws SQLException;

	}

	public static interface SelectCallbackLoop {

		public void callback(Map<String, String> map);
	}

	public static interface SelectCallbackTable {

		public void callback(String[] columns, List<List<String>> data);
	}

	public static List<Map<String, String>> preparedSelect(String sql,
			List<String> params) {
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			started = true;
			statement = conn.prepareStatement(sql);
			for (int i = 1; i <= params.size(); i++) {
				statement.setString(i, params.get(i - 1));
			}

			ResultSet rs = statement.executeQuery();

			ResultSetMetaData data = rs.getMetaData();

			int colCount = data.getColumnCount();

			ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();

			while (rs.next()) {

				Map<String, String> hash = new HashMap<String, String>();
				for (int i = 1; i <= colCount; i++) {

					String value = rs.getString(i) == null ? "NULL" : rs
							.getString(i);
					String columnLabel = data.getColumnLabel(i);
					if (hash.containsKey(columnLabel)) {
						columnLabel += "_1";
					}

					hash.put(columnLabel, value);
				}
				list.add(hash);

			}
			return list;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;

		}

	}

}
