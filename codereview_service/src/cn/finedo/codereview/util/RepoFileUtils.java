package cn.finedo.codereview.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cn.finedo.codereview.officehelper.POIExcelToHtml2;
import cn.finedo.codereview.officehelper.POIWordToHtml2;
import cn.finedo.codereview.officehelper.PdfToHtmlUtil;
import cn.finedo.codereview.officehelper.PoiPptToHtmlUtil;


/**
 * @Description: 文件操作辅助类
 * @company Finedo.cn
 * @author zhusf@finedo.com
 * @date 2018年7月27日 上午9:06:41
 * @version v1.0
 */
public class RepoFileUtils {

    /**
     * 将文本文件中的内容读入到buffer中
     * 
     * @author zhusf
     * @param @param
     *            buffer
     * @param @param
     *            filePath
     * @param @throws
     *            IOException
     * @return void
     */
    public static void readToBuffer(StringBuffer buffer, String filePath)
        throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     * 
     * @author zhusf
     * @param @param
     *            filePath
     * @param @return
     * @param @throws
     *            IOException
     * @return String
     */
    public static String readFile(String filePath)
        throws IOException {
        StringBuffer sb = new StringBuffer();
        RepoFileUtils.readToBuffer(sb, filePath);
        return sb.toString();
    }

    /**
     * @author zhusf
     * @param @param
     *            path
     * @param @return
     * @return ArrayList<String>
     */
    public static ArrayList<String> getFiles(String path) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++ ) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
            if (tempList[i].isDirectory()) {}
        }
        return files;
    }

    /**
     * @author zhusf
     * @param @param
     *            sourcePath
     * @param @param
     *            targetPath
     * @return void
     */
    public static void officeToHtml(String sourcePath, final String targetPath) {
        if (sourcePath.endsWith(".doc") || sourcePath.endsWith(".docx")) {
            POIWordToHtml2.wordToHtml(sourcePath, targetPath);
        }
        if (sourcePath.endsWith(".xls") || sourcePath.endsWith(".xlsx")) {
            String mytargetPath = sourcePath.substring(0, sourcePath.lastIndexOf(".")).concat(".html");
            POIExcelToHtml2.excelToHtml(sourcePath, mytargetPath, true);
        }
        if (sourcePath.endsWith(".ppt") || sourcePath.endsWith(".pptx")) {
            PoiPptToHtmlUtil.pptToHtml(sourcePath, targetPath);
        }
        if (sourcePath.endsWith(".pdf")) {
            PdfToHtmlUtil.pdfToHtml(sourcePath, targetPath);
        }
    }
}
