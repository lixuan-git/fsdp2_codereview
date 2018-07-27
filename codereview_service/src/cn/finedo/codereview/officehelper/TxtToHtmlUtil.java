package cn.finedo.codereview.officehelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import cn.finedo.common.non.NonUtil;

/**      
* @Description: txt文本转html
* @company Finedo.cn
* @author zhusf@finedo.com   
* @date 2018年7月27日 上午9:46:08   
* @version v1.0 
*/ 
public class TxtToHtmlUtil {
	
	private static Logger logger = LogManager.getLogger();

	/**
	 * <p>Title: txt转Html</p>
	 * <p>Description: </p>
	 * @param SourcePath	源文件路径
	 * @param targetPath	转换文件路径
	 * @return
	 * @author Charlee
	 * @date 2018年6月14日
	 */
	public static boolean txtToHtml(String sourcePath, String targetPath) {
		File file = new File(sourcePath);
		if (file.isFile() && file.exists()) { // 判断文件是否存在
			//检查文件夹是否存在，不存在则创建
			File filePath = new File(targetPath);
			if (!filePath.isDirectory()) {
				filePath.mkdirs();
			}
		} else {
			logger.debug("找不到指定的文件");
			return false;
		}

		FileOutputStream fos = null;
		BufferedReader bufferedReader = null;
		BufferedWriter bw = null;
		InputStreamReader read = null;
		OutputStreamWriter osw = null;
		
		//截取路径、文件名
		File tempFile =new File(sourcePath.trim());
		String txtName = tempFile.getName();
		//html文件名
		String htmlFile = txtName.substring(0, txtName.indexOf(".")) + ".html";
				
		try {
			String encoding = "GBK";
			read = new InputStreamReader(new FileInputStream(file), encoding);
			// 考虑到编码格式
			bufferedReader = new BufferedReader(read);
			// 写文件
			fos = new FileOutputStream(new File(targetPath + File.separator + htmlFile));
			osw = new OutputStreamWriter(fos, "GBK");
			bw = new BufferedWriter(osw);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				bw.write(lineTxt + "</br>");
			}
		} catch (Exception e) {
			logger.error("读取文件内容出错",e);
			return false;
		} finally {
			try {
				if (NonUtil.isNotNon(bw)) {
					bw.close();
				}
				if (NonUtil.isNotNon(osw)) {
					osw.close();
				}
				if (NonUtil.isNotNon(fos)) {
					fos.close();
				}
				if (NonUtil.isNotNon(read)) {
					read.close();
				}
				if (NonUtil.isNotNon(bufferedReader)) {
					bufferedReader.close();
				}
			} catch (IOException ioe) {
				logger.error("IO流关闭异常", ioe);
			}
		}
		return true;
	}
}
