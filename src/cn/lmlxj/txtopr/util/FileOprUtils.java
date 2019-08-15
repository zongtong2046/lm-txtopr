package cn.lmlxj.txtopr.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.lmlxj.txtopr.exception.TxtOprException;

public class FileOprUtils {
	private static final String ENCODE_UTF8 = "utf-8";
	private static final String NEW_LINE = "\r\n";
	private static final String KEY_CTRL_D = "[ctrl+d]";
	public static final int OPR_FIRST = 0x01 << 0;
	public static final int OPR_SRCH = 0x01 << 1;
	public static final int OPR_REPLACE = 0x01 << 2;
	public static final int OPR_FLAG_REG = 0x01 << 3;
	public static final int OPR_FLAG_CASE = 0x01 << 4;
	
	/**
	 * scanFile.
	 */
    public static ArrayList<String> scanFile(Pattern filePattern, String path, String strSrch, 
    																			String strReplace, 
    																			int oprFlag) {
    	ArrayList<String> list = new ArrayList<String>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                	if(list.size() > 0 && (oprFlag & OPR_FIRST) == OPR_FIRST) return list;
                	String absPath = file2.getAbsolutePath();
                    if (file2.isDirectory()) {
                        list.addAll(scanFile(filePattern, absPath, strSrch, strReplace, oprFlag));
                    } else {
                    	if(filePattern==null || filePattern.matcher(absPath).find()) {
                    		if (findAndReplaceText(absPath, oprFlag, strSrch, strReplace)) {
                    			// System.out.println("add.., " + strSrch + " " + oprFlag);
                    			list.add(absPath);
                    		}
                    	}
                    }
                }
            }
        } else {
        	throw new TxtOprException(path + ", 不存在");
        }
        return list;
    }
    
    /**
     * TODO 
     * @param oprFlag, 1-正则, 2-大小写, 3-正则+大小写
     * @return
     */
    public static boolean findAndReplaceText(String path, int oprFlag, String src, String tgt) {
    	boolean flag = false;
		try { 
			File filename = new File(path); // 要读取以上路径的input。txt文件
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), ENCODE_UTF8); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line = "";
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if ((oprFlag & OPR_FLAG_REG) == OPR_FLAG_REG) {	// reg match
					
				} else {
					if (line.contains(src)) {
						flag = true;
						if ((oprFlag & OPR_REPLACE) == OPR_REPLACE && !KEY_CTRL_D.equalsIgnoreCase(tgt)) {
							line = line.replace(src, tgt);
						}
					}
				}
				if (!(flag && KEY_CTRL_D.equalsIgnoreCase(tgt))) {
					sb.append(line).append(NEW_LINE);
				}
			}
			
			try {
				br.close();
			} catch (Exception e) {
				//TODO
			}
			
			if (flag && (oprFlag & OPR_REPLACE) == OPR_REPLACE) {	//找到，并且是替换操作
				File writename = new File(path);
				writename.createNewFile(); // 创建新文件
				BufferedWriter out = new BufferedWriter(new FileWriter(writename));
				//
				if (sb.length() > 0) sb.delete(sb.length() - NEW_LINE.length(), sb.length());
				out.write(sb.toString());
				out.flush(); 	// 把缓存区内容压入文件
				out.close(); 	// 最后记得关闭文件
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
    
	public static void deleteFile(String delpath) throws IOException {
		File file = new File(delpath);
		if (!file.isDirectory()) {
			file.delete();
		} else if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(String.valueOf(delpath) + "\\" + filelist[i]);
				if (!delfile.isDirectory()) {
					delfile.delete();
				} else if (delfile.isDirectory()) {
					deleteFile(String.valueOf(delpath) + "\\" + filelist[i]);
				}
			}
			file.delete();
		}
	}
	
	public static String getFileContent(File file) {
		return getFileContent(file, NEW_LINE);
	}

	public static String getFileContent(File file, String lineChar) {
		StringBuilder sb = new StringBuilder();
		if (lineChar == null) lineChar = NEW_LINE;
		try {
			String line = "";
			BufferedReader in = new BufferedReader(new FileReader(file));
			while ((line = in.readLine()) != null) {
				sb.append(line).append(lineChar);
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		if (sb.length() > 0) sb.delete(sb.length() - lineChar.length(), sb.length());
		return sb.toString();
	}
}
