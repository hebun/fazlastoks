package freela.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class Sql {
	protected String fieldList;

	protected String tableName;
	protected boolean isBuilt = false;
	protected String currentSql = "";
	protected String orderColumn = null;
	protected String orderWay = "asc";
	protected int start = 0, count = 0;
	protected Map<String, Map.Entry<String, String>> where = new Hashtable<String, Map.Entry<String, String>>();
	protected boolean isPrepared = false;

	public abstract Sql prepare();

	public Sql whereEntry(String type, String key, final Object value) {

		char charAt = key.charAt(key.length() - 1);
		if (charAt != ' ' && charAt != '<' && charAt != '>' && charAt != '=') {
			key = key + "=";
		}
		final String fkey = key;
		if (where.containsKey(type))
			type = type + " ";
		where.put(type, new Map.Entry<String, String>() {

			@Override
			public String getKey() {
				return fkey;
			}

			@Override
			public String getValue() {
				return value.toString();
			}

			@Override
			public String setValue(String value) {

				return null;
			}
		});
		return this;
	}

	public Sql where(final String key, final Object value) {

		return whereEntry("where", key, value);
	}

	public Sql and(final String key, final String value) {
		if (where.size() == 0)
			throw new SqlBuilderExeption("cant use 'and' before 'where'");
		return whereEntry("and", key, value);
	}

	public Sql or(final String key, final String value) {
		if (where.size() == 0)
			throw new SqlBuilderExeption("cant use 'or' before 'where'");

		return whereEntry("or", key, value);
	}

	public Sql desc() {
		this.orderWay = "desc";
		return this;
	}

	public Sql limit(int start, int count) {
		this.start = start;
		this.count = count;
		return this;
	}

	public Sql limit(int count) {

		return this.limit(0, count);
	}

	public String getFollowings() {
		String ret = "";

		if (orderColumn != null) {
			ret += " order by " + orderColumn + " " + orderWay;
		}
		if (count != 0) {
			ret += " limit " + start + "," + count;
		}
		return ret;
	}

	public abstract String get();

	public static class Delete extends Sql {

		public Delete(String table) {
			this.tableName = table;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			if (where.size() == 0)
				throw new RuntimeException("cant use delete without where");
			StringBuilder builder = new StringBuilder("delete from  `");
			builder.append(this.tableName);
			builder.append("`");
			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ').append(en.getKey()).append(' ')
						.append(en.getValue().getKey()).append("'")
						.append(en.getValue().getValue()).append("' ");
			}

			this.currentSql = builder.toString();
			this.isBuilt = true;
			this.currentSql += super.getFollowings();
			return currentSql;
		}

		@Override
		public Delete prepare() {
			this.isPrepared = true;
			return this;
		}

	}

	public static class Update extends Sql {
		Map<String, Object> fields = new Hashtable<String, Object>();

		public Update(String table) {
			this.tableName = table;

		}

		public List<String> params() {
			List<String> ret = new ArrayList<>();
			for (Object string : fields.values()) {
				ret.add(string.toString());
			}
			return ret;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			if (where.size() == 0)
				throw new SqlBuilderExeption("cant use update without where");

			if (fields.size() <= 0) {
				throw new SqlBuilderExeption(
						"Sql.Update:there is no column to set  ");
			}

			StringBuilder builder = new StringBuilder("update `");
			builder.append(this.tableName);
			builder.append("` set ");
			for (Map.Entry<String, Object> en : fields.entrySet()) {
				builder.append(en.getKey()).append("=");

				if (isPrepared) {
					builder.append("?,");
				} else {

					builder.append("'").append(en.getValue().toString())
							.append("',");
				}
			}
			builder.deleteCharAt(builder.length() - 1);
			for (Map.Entry<String, Map.Entry<String, String>> en : where
					.entrySet()) {
				builder.append(' ').append(en.getKey()).append(' ')
						.append(en.getValue().getKey()).append("'")
						.append(en.getValue().getValue()).append("' ");
			}

			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

		public Update add(String key, Object value) {

			this.fields.put(key, value);
			return this;

		}

		@Override
		public Update prepare() {
			this.isPrepared = true;
			return this;
		}
	}

	public static class Insert extends Sql {
		Map<String, Object> fields = new Hashtable<String, Object>();

		public Insert(String table) {
			this.tableName = table;

		}

		public List<String> params() {
			List<String> ret = new ArrayList<>();
			for (Object string : fields.values()) {
				ret.add(string.toString());
			}
			return ret;

		}

		public Insert add(String key, Object value) {

			this.fields.put(key, value);
			return this;

		}

		@Override
		public String get() {
			if (isBuilt)
				return currentSql;
			StringBuilder builder = new StringBuilder("insert into `");
			builder.append(this.tableName);
			builder.append("`(");
			for (Map.Entry<String, Object> en : fields.entrySet()) {
				builder.append(en.getKey());
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(") values (");
			for (Map.Entry<String, Object> en : fields.entrySet()) {

				if (isPrepared) {
					builder.append("?,");

				} else {
					builder.append("'").append(en.getValue().toString())
							.append("',");
				}
			}
			builder.deleteCharAt(builder.length() - 1);
			builder.append(")");
			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

		@Override
		public Insert prepare() {
			this.isPrepared = true;
			return this;
		}

	}

	public static class Select extends Sql {

		String secondTable, thirdTable;
		String secondAlias, firstAlias, thirdAlias;
		boolean isJoin = false, isSecondJoin = false;

		String lastTable;
		String onKey, onValue, onKey2, onValue2;
		String joinType, secondJoinType;
		String groupBy;

		public Select(String params) {
			setFields(params);

		}

		public Select groupBy(String f) {
			this.groupBy = f;
			return this;
		}

		public Select join(String table) {
			if (isJoin) {
				isSecondJoin = true;
				thirdTable = table;
				secondJoinType = "join";
			} else {

				isJoin = true;
				secondTable = table;
				joinType = "join";

			}

			return this;
		}

		public Select innerJoin(String table) {

			if (isJoin) {
				isSecondJoin = true;
				thirdTable = table;
				secondJoinType = "inner join";
			} else {

				isJoin = true;
				secondTable = table;
				joinType = "inner join";
			}

			return this;
		}

		public Select rightJoin(String table) {
			if (isJoin) {
				isSecondJoin = true;
				thirdTable = table;
				secondJoinType = "right join";
			} else {

				isJoin = true;
				secondTable = table;
				joinType = "right join";
			}

			return this;
		}

		public Select leftJoin(String table) {
			if (isJoin) {
				isSecondJoin = true;
				thirdTable = table;
				secondJoinType = "left join";
			} else {

				isJoin = true;
				secondTable = table;
				joinType = "left join";
			}

			return this;
		}

		public Select on(String key, String value) {
			if (isSecondJoin) {
				onKey2 = key;
				onValue2 = value;
			} else {
				onKey = key;
				onValue = value;
			}
			return this;
		}

		public Select as(String alias) {

			if (secondTable == null) {
				if (tableName == null) {
					throw new SqlBuilderExeption("table name is null");
				}
				firstAlias = alias;
			} else {
				if (thirdTable == null) {
					secondAlias = alias;
				} else {
					thirdAlias = alias;
				}
			}
			return this;
		}

		public Select() {
			this("*");

		}

		public Select setFields(String params) {
			this.isBuilt = false;
			fieldList = params;

			return this;
		}

		public Select from(String table) {
			this.isBuilt = false;
			tableName = table;

			return this;
		}

		public List<String> params() {
			if (!isPrepared) {
				throw new SqlBuilderExeption("you didn't call prepare");
			}
			List<String> ret = new ArrayList<>();
			for (Map.Entry<String, String> entry : where.values()) {
				ret.add(entry.getValue());
			}
			return ret;

		}

		@Override
		public String get() {

			StringBuilder builder = new StringBuilder("select ");
			builder.append(fieldList);

			if (tableName != null && tableName != "") {

				builder.append(" from `").append(this.tableName).append("` ");

				if (firstAlias != null) {
					builder.append("as ").append(firstAlias);
				}
				if (isJoin) {
					builder.append(" " + joinType + " ").append(secondTable);
					if (secondAlias != null) {
						builder.append(" as ").append(secondAlias);
					}
					if (onKey == null || onValue == null) {
						throw new SqlBuilderExeption(
								"cant use join without on ");
					}
					builder.append(" on ").append(onKey).append("=")
							.append(onValue);
				}
				if (isSecondJoin) {
					builder.append(" " + secondJoinType + " ").append(
							thirdTable);
					if (thirdAlias != null) {
						builder.append(" as ").append(thirdAlias);
					}
					if (onKey2 == null || onValue2 == null) {
						throw new SqlBuilderExeption(
								"cant use join without on ");
					}
					builder.append(" on ").append(onKey2).append("=")
							.append(onValue2);
				}
				for (Map.Entry<String, Map.Entry<String, String>> en : where
						.entrySet()) {
					builder.append(' ').append(en.getKey()).append(' ')
							.append(en.getValue().getKey());
					if (isPrepared) {
						builder.append("? ");
					} else {
						builder.append("'").append(en.getValue().getValue())
								.append("' ");
					}
				}
				if (groupBy != null) {
					builder.append(" group by ").append(this.groupBy)
							.append("");
				}
			}
			this.currentSql = builder.toString();
			this.currentSql += super.getFollowings();
			this.isBuilt = true;
			return currentSql;
		}

		public Select order(String order) {

			this.orderColumn = order;
			return this;
		}

		@Override
		public Select prepare() {
			this.isPrepared = true;
			return this;
		}
	}

	@SuppressWarnings("serial")
	public class SqlBuilderExeption extends RuntimeException {

		public SqlBuilderExeption(String string) {
			super(string);
		}

	}
}
