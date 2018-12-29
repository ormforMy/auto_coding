package cc.s2m.autoCoding.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cc.s2m.autoCoding.AutoCoding;


public class TablesColums {
	private JdbcTemplate jdbcTemplate;
	
	public TablesColums(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
    public Map<String,String> getTablesInfo(final String tableName) {
        Map<String,String> map = jdbcTemplate.execute(new ConnectionCallback<Map<String,String>>() {
            public Map<String,String> doInConnection(Connection connection) throws SQLException {
                ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, new String[]{"TABLE","VIEW"});
                Map<String,String> map = Maps.newHashMap();
                while(resultSet.next()){
                    String tableName = resultSet.getString("TABLE_NAME");
                    String remark = resultSet.getString("REMARKS");
                    map.put(tableName, remark);
                }
                return map;
            }
        });
        return map;
    }
    public List<TableColumBean> getTableColumsInfo(final String tableName) {
        List<TableColumBean> list = jdbcTemplate.execute(new ConnectionCallback<List<TableColumBean>>() {
            public List<TableColumBean> doInConnection(Connection connection) throws SQLException {
                ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, null);
                List<TableColumBean> list = Lists.newArrayList();
                while(resultSet.next()){
                    String name = resultSet.getString("COLUMN_NAME");
                    String type = resultSet.getString("TYPE_NAME");
                    boolean canNull = resultSet.getBoolean("NULLABLE");
                    boolean isAutoIncrement = resultSet.getBoolean("IS_AUTOINCREMENT");
                    System.out.println(name + " : " + type + " canNull: " + canNull + " isAutoIncrement: " + isAutoIncrement);
                    TableColumBean bean = new TableColumBean();
                    bean.setCanNull(canNull);
                    bean.setName(name);
                    bean.setJavaProp(SqlToJava.getJavaPropname(name));
                    bean.setJavaPropGet("get" + StringUtils.capitalize(bean.getJavaProp()));
                    bean.setJavaPropSet("set" + StringUtils.capitalize(bean.getJavaProp()));
                    bean.setType(type);
                    bean.setAutoIncrement(isAutoIncrement);
                    switch (bean.getType().toUpperCase()) {
                    case "INT":
                    case "INT UNSIGNED":
                    case "TINYINT":
                    case "TINYINT UNSIGNED":
                    case "INTEGER":
                        bean.setJavaType("java.lang.Integer");
                        break;
                    case "CHAR":
                    case "VARCHAR":
                    case "TEXT":
                        bean.setJavaType("java.lang.String");
                        break;
                    case "BIGINT":
                        bean.setJavaType("java.lang.Long");
                        break;
                    case "BIT":
                        bean.setJavaType("java.lang.Boolean");
                        break;
                    case "DATE":
                    case "DATETIME":
                    case "TIMESTAMP":
                        bean.setJavaType("java.util.Date");
                        break;
                    case "DECIMAL":
                    case "DOUBLE":
                    	bean.setJavaType("java.math.BigDecimal");
                    	break;
                    default:
                        throw new RuntimeException("unknow colum_type:" + bean.getType());
                    }
                    list.add(bean);
                }
                resultSet = connection.getMetaData().getPrimaryKeys(null, null, tableName);
                while(resultSet.next()){
                    String  primaryKey = resultSet.getString("COLUMN_NAME");
                    for(TableColumBean bean : list){
                        if(bean.getName().equalsIgnoreCase(primaryKey)){
                            bean.setKey(true);
                            AutoCoding.pk_colum_bean = bean;
                        }
                    }
                }
                return list;
            }
        });
        return list;
    }
}
