package cn.finedo.codereview.officehelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import cn.finedo.common.non.NonUtil;

public class PdfToHtmlUtil {
	
	private static Logger logger = LogManager.getLogger();
	
	/**
	 * <p>Title: pdf转Html</p>
	 * <p>Description: </p>
	 * @param SourcePath	源文件路径
	 * @param targetPath	转换文件路径
	 * @return
	 * @author Charlee
	 * @date 2018年7月9日
	 */
	public static boolean pdfToHtml(String sourcePath, String targetPath) {
		
		// 编码方式
		String encoding = "UTF-8";
		// 文件输入流，生成文本文件
		Writer output = null;
		// 内存中存储的PDF Document
		PDDocument document = null;
		
		// 判断文件是否存在
		File sourcefile = new File(sourcePath);
		if (sourcefile.isFile() && sourcefile.exists()) {
			//检查文件夹是否存在，不存在则创建
			File filePath = new File(targetPath);
			if (!filePath.isDirectory()) {
				filePath.mkdirs();
			}
		} else {
			logger.debug("找不到指定的文件");
			return false;
		}
		
		//截取路径、文件名
		File tempFile =new File(sourcePath.trim());
		String pdfName = tempFile.getName();									//PDF文件名
		String textFile = pdfName.substring(0, pdfName.indexOf(".")) + ".txt";	//文本文件名
		
		try {
			try {
				// 首先当作一个URL来加载文件，如果得到异常再从本地系统装载文件
				URL url = new URL(sourcePath);
				document = PDDocument.load(url);
			} catch (MalformedURLException e) {
				// 如果作为URL装载得到异常则从文件系统装载
				document = PDDocument.load(sourcePath);
			}
			// 文件输入流，写入文件到textFile
			output = new OutputStreamWriter(new FileOutputStream(targetPath + File.separator + textFile), encoding);
			// PDFTextStripper来提取文本
			PDFTextStripper stripper = null;
			stripper = new PDFTextStripper();
			// 设置是否排序
			stripper.setSortByPosition(false);
			// 设置起始页
			stripper.setStartPage(1);
			// 设置结束页
			stripper.setEndPage(Integer.MAX_VALUE);
			// 调用PDFTextStripper的writeText提取并输出文本
			stripper.writeText(document, output);
			
			
		} catch(Exception e) {
			logger.error("pdf中转txt失败", e);
			return false;
		}finally {
			try {
				if (NonUtil.isNotNon(output)) {
					output.close();		// 关闭输出流
				}
				if (NonUtil.isNotNon(document)) {
					document.close();	// 关闭PDF Document
				}
			} catch (IOException ioe) {
				logger.error("IO流关闭异常", ioe);
			}
		}
		//调用txt转html方法
		new TxtToHtmlUtil().txtToHtml(targetPath + File.separator + textFile, targetPath);
		//删除txt文件
		File file =new File(targetPath + File.separator + textFile);
		file.delete();
		return true;
	}
}



