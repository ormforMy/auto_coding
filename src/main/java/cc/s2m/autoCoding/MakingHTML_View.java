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

public class MakingHTML_View {
    
    public static void done(String tableName) throws IOException, TemplateException {
        String pojoName_small = SqlToJava.getJavaPropname(tableName);
        String pojoName = StringUtils.capitalize(pojoName_small);
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("pojoName", pojoName);
        root.put("pojoName_small", SqlToJava.getJavaPropname(tableName));
        root.put("packagePath", AutoCoding.basePackage + "." + AutoCoding.projectName);
        root.put("columsList", new TablesColums(AutoCoding.jdbcTemplate).getTableColumsInfo(tableName));
        root.put("pk_colum_bean", AutoCoding.pk_colum_bean);
        root.put("adminTitleStr", AutoCoding.adminTitleStr);

        File configFile = new File(AutoCoding.templeFilesPath);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setDirectoryForTemplateLoading(configFile);
        
        Template template = cfg.getTemplate("view.html","utf-8");
        StringWriter out = new StringWriter();
        template.process(root, out);
        
        FileUtils.write(new File(AutoCoding.pathAndName
                + File.separatorChar + AutoCoding.projectName + "-web"
        		+ File.separatorChar + "src"
        		+ File.separatorChar + "main"
        		+ File.separatorChar + "webapp"
        		+ File.separatorChar + "WEB-INF"
        		+ File.separatorChar + "views"
        		+ File.separatorChar + "admin"
        		+ File.separatorChar + pojoName_small + "_view.html"), 
                out.toString(),"UTF-8", false);
    }
}
