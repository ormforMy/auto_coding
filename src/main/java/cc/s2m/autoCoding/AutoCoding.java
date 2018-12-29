package cc.s2m.autoCoding;

import java.io.IOException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.google.common.collect.Lists;

import cc.s2m.autoCoding.utils.TableColumBean;
import freemarker.template.TemplateException;

public class AutoCoding {
    public static String DB_TABLE_PREFIX = "";
	public static String templeFilesPath = "src/main/resources/";
	public static JdbcTemplate jdbcTemplate;
	public static String basePackage = "com.test.springboot";
	public static String projectName = "p2pnew";
	
	public static TableColumBean pk_colum_bean;
	public static String path = "E:/javaprojects";
	public static String pathAndName = "E:/javaprojects/p2pnew";
	public static String adminTitleStr = "xxx系统后台";
	
	public static void main(String[] args) throws IOException, TemplateException {
		AutoCoding a = new AutoCoding();
		DB_TABLE_PREFIX = "";// 表名前缀,无前缀请留空
		/*List<String> tables = Lists.newArrayList("ali_log","ocr_invoice","account_recharge"
				,"account","account_log");*/
		List<String> tables = Lists.newArrayList("activity_recharge_gift_money");
		for(String table:tables){
			a.createMybatis(table);
			a.createPOJO(table);
			a.createDao(table);
			a.createService(table);
			a.createContraller(table);
			/*a.createHtmlList(table);
			a.createHtmlView(table);
			a.createHtmlEdit(table);*/
		}
	}
	
	static {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		//dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&tinyInt1isBit=false");
        dataSource.setUsername("root");
        dataSource.setPassword("password");
        jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void createMybatis(String tableName) throws IOException, TemplateException{
		MakingMybatisFiles m = new MakingMybatisFiles(jdbcTemplate, basePackage, projectName);
		m.done(tableName);
	}
	
	public void createPOJO(String tableName) throws IOException, TemplateException{
		MakingPojoFiles.done(tableName);
	}
	
	public void createDao(String tableName) throws IOException, TemplateException{
		MakingDaoFiles.done(tableName);
	}
	
	public void createService(String tableName) throws IOException, TemplateException{
		MakingServiceFiles.done(tableName);
	}
	
	public void createContraller(String tableName) throws IOException, TemplateException{
		MakingControllerFiles.done(tableName);
	}
	
	public void createHtmlList(String tableName) throws IOException, TemplateException{
		MakingHTML_List.done(tableName);
	}
	
	public void createHtmlView(String tableName) throws IOException, TemplateException{
		MakingHTML_View.done(tableName);
	}
	
	public void createHtmlEdit(String tableName) throws IOException, TemplateException{
		MakingHTML_Edit.done(tableName);
	}
}
