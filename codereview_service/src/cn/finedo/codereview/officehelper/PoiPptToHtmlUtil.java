package cn.finedo.codereview.officehelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hslf.usermodel.HSLFShape;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hslf.usermodel.HSLFSlideShowImpl;
import org.apache.poi.hslf.usermodel.HSLFTextParagraph;
import org.apache.poi.hslf.usermodel.HSLFTextRun;
import org.apache.poi.hslf.usermodel.HSLFTextShape;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.finedo.common.non.NonUtil;

/*****************************************
 * <p>Title: PPT转Html工具类</p>
 * <p>Description: </p>
 * <p>Company: 合肥非度信息技术有限公司</p>
 * @author	Charlee
 * @date	2018年6月14日下午3:31:12
 * @version 1.0
 *****************************************/
public class PoiPptToHtmlUtil {
	
	private static Logger logger = LogManager.getLogger();

	/**
	 * <p>Title: ppt转Html</p>
	 * <p>Description: </p>
	 * @param SourcePath	源文件路径
	 * @param targetPath	转换文件路径
	 * @return
	 * @author Charlee
	 * @date 2018年6月14日
	 */
	public static boolean pptToHtml(String sourcePath, String targetPath) {
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
		
		File pptFile = new File(sourcePath);
		if (pptFile.exists()) {
			String type = FileUtils.GetFileExt(sourcePath);
			String pptFileName = pptFile.getName().substring(0, pptFile.getName().indexOf("."));
			try {
				if (type.equals("ppt")) {
					String htmlStr = toImage2003(sourcePath, targetPath,pptFileName);
					FileUtils.writeFile(htmlStr, targetPath  + File.separator + pptFileName+".html");
				} else if (type.equals("pptx")) {
					String htmlStr = toImage2007(sourcePath, targetPath, pptFileName);
					FileUtils.writeFile(htmlStr, targetPath + File.separator + pptFileName+".html");
				}
				
			} catch (Exception e) {
				logger.error("文件转换错误", e);
				return false;
			}
		} else {
			logger.debug("文件不存在");
		}
		return true;
	}

	public static String toImage2007(String sourcePath, String targetDir, String pptFileName) throws Exception {
		String htmlStr = "";
		FileInputStream is = new FileInputStream(sourcePath);
		XMLSlideShow ppt = new XMLSlideShow(is);
		is.close();
		FileUtils.createDir(targetDir);//创建html dir
		Dimension pgsize = ppt.getPageSize();

		StringBuffer sb = new StringBuffer();
		FileOutputStream out = null;
		try {
			for (int i = 0; i < ppt.getSlides().size(); i++) {
				// 防止中文乱码
				for (XSLFShape shape : ppt.getSlides().get(i).getShapes()) {
					if (shape instanceof XSLFTextShape) {
						XSLFTextShape tsh = (XSLFTextShape) shape;
						for (XSLFTextParagraph p : tsh) {
							for (XSLFTextRun r : p) {
								r.setFontFamily("宋体");
							}
						}
					}
				}
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();
				//清除绘图区域
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				ppt.getSlides().get(i).draw(graphics);
				String imageDir = targetDir + File.separator + pptFileName + File.separator;
				//添加服务器路径
                String prefix = imageDir.substring(imageDir.indexOf("/codereview_service"));
                
				FileUtils.createDir(imageDir);// 创建图像dir
				String imagePath = imageDir + pptFileName + "-" + (i + 1) + ".png";
				sb.append("<br>");
				sb.append("<img src=" + "\"" + prefix + pptFileName + "-" + (i + 1) + ".png" + "\"" + "/>");
				out = new FileOutputStream(imagePath);
				javax.imageio.ImageIO.write(img, "png", out);
				out.close();
			}
		} catch (Exception e) {
			logger.error("pptx格式转换失败",e);
		} finally {
			try {
				if (NonUtil.isNotNon(out)) {
					out.close();
				}
			} catch (IOException ioe) {
				logger.error("IO流关闭异常", ioe);
			}
		}
		htmlStr = sb.toString();

		return htmlStr;
	}

	public static String toImage2003(String sourcePath, String targetDir, String pptFileName) {
		String htmlStr = "";
		try {
			HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(sourcePath));
			FileUtils.createDir(targetDir);// 创建html dir
			Dimension pgsize = ppt.getPageSize();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ppt.getSlides().size(); i++) {
				// 防止中文乱码
				for (HSLFShape shape : ppt.getSlides().get(i).getShapes()) {
					if (shape instanceof HSLFTextShape) {
						HSLFTextShape tsh = (HSLFTextShape) shape;
						for (HSLFTextParagraph p : tsh) {
							for (HSLFTextRun r : p) {
								r.setFontFamily("宋体");
							}
						}
					}
				}
				BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();
				//清除绘图区域
				graphics.setPaint(Color.white);
				graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
				// render
				ppt.getSlides().get(i).draw(graphics);
				String imageDir = targetDir + "/" + pptFileName + "/";
				//添加服务器路径
				String prefix = imageDir.substring(imageDir.indexOf("/codereview_service"));
				//
				FileUtils.createDir(imageDir);//创建图像dir
				String imagePath = imageDir + pptFileName + "-" + (i + 1) + ".png";
				sb.append("<br>");
				//sb.append("<img src=" + "\"" + pptFileName + "/" + pptFileName + "-" + (i + 1) + ".png" + "\"" + "/>");
				sb.append("<img src=" + "\"" +prefix +  pptFileName + "-" + (i + 1) + ".png" + "\"" + "/>");
				FileOutputStream out = new FileOutputStream(imagePath);
				javax.imageio.ImageIO.write(img, "png", out);
				out.close();
			}
			htmlStr = sb.toString();
		} catch (Exception e) {
			logger.error("ppt格式转换失败",e);
		}
		return htmlStr;
	}

	/***
	 * 功能 :调整图片大小
	 * @param srcImgPath 原图片路径
	 * @param distImgPath 转换大小后图片路径
	 * @param width 转换后图片宽度
	 * @param height 转换后图片高度
	 */
	public static void resizeImage(String srcImgPath, String distImgPath, int width, int height) throws IOException {
		
		File srcFile = new File(srcImgPath);
		Image srcImg = ImageIO.read(srcFile);
		BufferedImage buffImg = null;
		buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
		
		ImageIO.write(buffImg, "JPEG", new File(distImgPath));
		
	}
}
