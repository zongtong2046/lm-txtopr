package cn.lmlxj.txtopr.util;

import java.util.regex.Pattern;

public class TxtRegUtils {
    public static Pattern getFileRegPattern(String fileFilter) {
    	if (StringUtils.isEmpty(fileFilter)) {
    		return null;
    	}
    	String[] filters = fileFilter.split(",");
    	String pattern = "";
    	for(String filter : filters) {
    		if (! StringUtils.isEmpty(filter)) {
    			filter = filter.replace("*", "");
    			filter = filter.replace(".", "\\.");
    			filter = ".*" + filter;
    			pattern += (pattern.length() > 0 ? "|" : "") + filter;
    		}
    	}
    	if (pattern.length() == 0) return null;
    	pattern = "(" + pattern + ")$";
    	pattern = pattern.replace("?", ".");
    	return Pattern.compile(pattern);
    }
}
