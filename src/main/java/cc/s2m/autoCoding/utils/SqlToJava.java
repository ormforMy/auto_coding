package cc.s2m.autoCoding.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import cc.s2m.autoCoding.AutoCoding;

public class SqlToJava {
    
    public static String getJavaPropname(String tableName){
        if (!Strings.isNullOrEmpty(AutoCoding.DB_TABLE_PREFIX) &&
                tableName.startsWith(AutoCoding.DB_TABLE_PREFIX)) {
            tableName = tableName.substring(AutoCoding.DB_TABLE_PREFIX.length());
        }
        List<String> splitFlags = Lists.newArrayList("\\|", "_");
        for(String flag : splitFlags){
            String[] tableName_ = tableName.split(flag);
            StringBuilder strBuilder = new StringBuilder();
            for(int i = 0;i<tableName_.length ;i++){
                String tmp = tableName_[i];
                if(i > 0){
                    tmp = StringUtils.capitalize(tmp);
                }
                strBuilder.append(tmp);
            }
            tableName = strBuilder.toString();
        }
        tableName = StringUtils.uncapitalize(tableName);
        return tableName;
    }
}
