package cc.s2m.autoCoding;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import cc.s2m.autoCoding.utils.SqlToJava;
import cc.s2m.autoCoding.utils.TablesColums;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class MakingControllerFiles {

    public static void done(String tableName) throws IOException, TemplateException {
        String pojoName = StringUtils.capitalize(SqlToJava.getJavaPropname(tableName));
        
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("pojoName", pojoName);
        root.put("pojoName_small", SqlToJava.getJavaPropname(tableName));
        root.put("packagePath", AutoCoding.basePackage + "." + AutoCoding.projectName);
        root.put("columsList", new TablesColums(AutoCoding.jdbcTemplate).getTableColumsInfo(tableName));
        root.put("pk_colum_bean", AutoCoding.pk_colum_bean);

        File configFile = new File(AutoCoding.templeFilesPath);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(configFile);
        
        Template template = cfg.getTemplate("controller.html","utf-8");
        StringWriter out = new StringWriter();
        template.process(root, out);
        
        FileUtils.write(new File(AutoCoding.pathAndName
                + File.separatorChar + AutoCoding.projectName + "-web"
        		+ File.separatorChar + "src"
        		+ File.separatorChar + "main"
        		+ File.separatorChar + "java"
        		+ File.separatorChar + AutoCoding.basePackage.replace('.', File.separatorChar)
        		+ File.separatorChar + AutoCoding.projectName
        		+ File.separatorChar + "controller"
        		+ File.separatorChar + "admin"
        		+ File.separatorChar + pojoName + "Controller.java"), 
                out.toString(),"UTF-8", false);
    }
}
