package cc.s2m.autoCoding;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import cc.s2m.autoCoding.utils.SqlToJava;
import cc.s2m.autoCoding.utils.TablesColums;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MakingMybatisFiles {
	private File configFile = new File(AutoCoding.templeFilesPath);
	private String name;
	private String basePackage;
	private JdbcTemplate jdbcTemplate;
	private String pathAndName;
	
	public MakingMybatisFiles(JdbcTemplate jdbcTemplate,String basePackage,String name){
		this.jdbcTemplate = jdbcTemplate;
		this.name = name;
		this.basePackage = basePackage;
		this.pathAndName = AutoCoding.path + File.separatorChar + name;
	}
    
    public void done(String tableName) throws IOException, TemplateException {
        String pojoName = StringUtils.capitalize(SqlToJava.getJavaPropname(tableName));
        
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("tableName", tableName);
        root.put("pojoName", pojoName);
        root.put("packagePath", basePackage + "." + name);
        root.put("columsList", new TablesColums(jdbcTemplate).getTableColumsInfo(tableName));
        root.put("pk_colum_bean", AutoCoding.pk_colum_bean);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(configFile);
        
        Template template = cfg.getTemplate("mybatis.html","utf-8");
        StringWriter out = new StringWriter();
        template.process(root, out);
        
        FileUtils.write(new File(pathAndName
                + File.separatorChar + AutoCoding.projectName + "-core"
        		+ File.separatorChar + "src"
        		+ File.separatorChar + "main"
        		+ File.separatorChar + "resources"
        		+ File.separatorChar + "mybatis"
        		+ File.separatorChar + "base"
        		+ File.separatorChar + pojoName + "Mapper.xml"), 
                out.toString(),"UTF-8", false);
        
        File mybatisExtFile = new File(pathAndName
                + File.separatorChar + AutoCoding.projectName + "-core"
                + File.separatorChar + "src"
                + File.separatorChar + "main"
                + File.separatorChar + "resources"
                + File.separatorChar + "mybatis"
                + File.separatorChar + "ext"
                + File.separatorChar + pojoName + "Mapper.xml");
        if(!mybatisExtFile.exists()){
            template = cfg.getTemplate("mybatis-ext.html","utf-8");
            out = new StringWriter();
            template.process(root, out);
            
            FileUtils.write(mybatisExtFile, 
                    out.toString(),"UTF-8", false);
        }
    }
}
