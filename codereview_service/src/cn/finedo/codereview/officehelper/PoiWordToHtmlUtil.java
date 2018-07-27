package cn.finedo.codereview.officehelper;
//package cn.finedo.codereview.ut;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.apache.poi.hwpf.HWPFDocument;
//import org.apache.poi.hwpf.converter.PicturesManager;
//import org.apache.poi.hwpf.converter.WordToHtmlConverter;
//import org.apache.poi.hwpf.usermodel.PictureType;
//import org.apache.poi.xwpf.converter.core.BasicURIResolver;
//import org.apache.poi.xwpf.converter.core.FileImageExtractor;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.w3c.dom.Document;
//
//import cn.finedo.common.non.NonUtil;
//
///*****************************************
// * <p>Title: Word转Html工具类</p>
// * <p>Description: </p>
// * <p>Company: 合肥非度信息技术有限公司</p> 
// * @author	Charlee
// * @date	2018年6月14日下午3:31:40
// * @version 1.0
// *****************************************/
//public class PoiWordToHtmlUtil {
//	
//	//private static Logger logger = LogManager.getLogger();
//	
//	private static final String ENCODING = "GB2312";// UTF-8  GB2312
//
//	/**
//	 * <p>Title: Word转Html</p>
//	 * <p>Description: </p>
//	 * @param SourcePath	源文件路径
//	 * @param targetPath	转换文件路径
//	 * @return
//	 * @author Charlee
//	 * @date 2018年6月14日
//	 */
//	public static boolean wordToHtml(String sourcePath, final String targetPath){
//		// 判断文件是否存在
//		File sourcefile = new File(sourcePath);
//		if (!(sourcefile.isFile()) && !(sourcefile.exists())) {
//			//logger.debug("找不到指定的文件");
//			return false;
//		}
//		
//		//截取路径、文件名
//		File tempFile =new File(sourcePath.trim());
//		String wordName = tempFile.getName();
//		String fileName = wordName.substring(0, wordName.indexOf("."));	//文件名
//		final String picturesPath = targetPath + File.separator + "image";	//图片存放路径
//		File picturesDir = new File(picturesPath);
//		
//		if (!picturesDir.isDirectory()) {
//			picturesDir.mkdirs();
//		}
//		String ext = FileUtils.GetFileExt(sourcePath);
//		String content = null;
//		ByteArrayOutputStream out = null;
//		ByteArrayOutputStream baos = null;
//		try {
//			if (ext.equals("doc")) {
//				HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(sourcePath));
//				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
//				wordToHtmlConverter.setPicturesManager(new PicturesManager() {
//					@Override
//					public String savePicture(byte[] content, PictureType pictureType, String suggestedName,float widthInches, float heightInches) {
//						File file = new File(picturesPath + File.separator + suggestedName);
//						FileOutputStream fos = null;
//						try {
//							fos = new FileOutputStream(file);
//							fos.write(content);
//						} catch (Exception e) {
//							//logger.error("文件转换失败",e);
//						} finally {
//							try {
//								if (NonUtil.isNotNon(fos)) {
//									fos.close();
//								}
//							} catch (IOException ioe) {
//								//logger.error("IO流关闭异常", ioe);
//							}
//						}
//						
//						
//						return "image/" + suggestedName;
//					}
//				});
//				wordToHtmlConverter.processDocument(wordDocument);
//				Document htmlDocument = wordToHtmlConverter.getDocument();
//				out = new ByteArrayOutputStream();
//				DOMSource domSource = new DOMSource(htmlDocument);
//				StreamResult streamResult = new StreamResult(out);
//
//				TransformerFactory tf = TransformerFactory.newInstance();
//				Transformer serializer = tf.newTransformer();
//				serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
//				serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//				serializer.setOutputProperty(OutputKeys.METHOD, "html");
//				serializer.transform(domSource, streamResult);
//				FileUtils.writeFile(new String(out.toByteArray()), targetPath + File.separator + fileName + ".html");
//				content = out.toString();
//			} else if (ext.equals("docx")) {
//				// 1) 加载word文档生成 XWPFDocument对象
//				InputStream in = new FileInputStream(new File(sourcePath));
//				XWPFDocument document = new XWPFDocument(in);
//				// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
//				XHTMLOptions options = XHTMLOptions.create();
//				options.setExtractor(new FileImageExtractor(picturesDir));
//				options.URIResolver(new BasicURIResolver("image"));
//				// 3) 将 XWPFDocument转换成XHTML
//				baos = new ByteArrayOutputStream();
//				XHTMLConverter.getInstance().convert(document, baos, options);
//				content = baos.toString();
//				FileUtils.writeFile(content, targetPath + File.separator + fileName + ".html");
//			}
//		} catch (Exception e) {
//			//logger.error("文件转换失败",e);
//			return false;
//		} finally {
//			try {
//				if (NonUtil.isNotNon(out)) {
//					out.close();
//				}
//				if (NonUtil.isNotNon(baos)) {
//					baos.close();
//				}
//			} catch (IOException ioe) {
//				//logger.error("IO流关闭异常", ioe);
//			}
//		}
//		return true;
//	}
//	
//	   public static boolean mywordToHtml(String sourcePath, final String targetPath){
//	        // 判断文件是否存在
//	        File sourcefile = new File(sourcePath);
//	        if (!(sourcefile.isFile()) && !(sourcefile.exists())) {
//	            //logger.debug("找不到指定的文件");
//	            return false;
//	        }
//	        
//	        //截取路径、文件名
//	        File tempFile =new File(sourcePath.trim());
//	        String wordName = tempFile.getName();
//	        String fileName = wordName.substring(0, wordName.indexOf(".")); //文件名
//	        final String picturesPath = targetPath + File.separator + "image";  //图片存放路径
//	        File picturesDir = new File(picturesPath);
//	        
//	        if (!picturesDir.isDirectory()) {
//	            picturesDir.mkdirs();
//	        }
//	        String ext = FileUtils.GetFileExt(sourcePath);
//	        String content = null;
//	        ByteArrayOutputStream out = null;
//	        ByteArrayOutputStream baos = null;
//	        try {
//	            if (ext.equals("doc")) {
//	                HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(sourcePath));
//	                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
//	                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
//	                    @Override
//	                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName,float widthInches, float heightInches) {
//	                        File file = new File(picturesPath + File.separator + suggestedName);
//	                        FileOutputStream fos = null;
//	                        try {
//	                            fos = new FileOutputStream(file);
//	                            fos.write(content);
//	                        } catch (Exception e) {
//	                            //logger.error("文件转换失败",e);
//	                        } finally {
//	                            try {
//	                                if (NonUtil.isNotNon(fos)) {
//	                                    fos.close();
//	                                }
//	                            } catch (IOException ioe) {
//	                                //logger.error("IO流关闭异常", ioe);
//	                            }
//	                        }
//	                        
//	                        
//	                        return "image/" + suggestedName;
//	                    }
//	                });
//	                wordToHtmlConverter.processDocument(wordDocument);
//	                Document htmlDocument = wordToHtmlConverter.getDocument();
//	                out = new ByteArrayOutputStream();
//	                DOMSource domSource = new DOMSource(htmlDocument);
//	                StreamResult streamResult = new StreamResult(out);
//
//	                TransformerFactory tf = TransformerFactory.newInstance();
//	                Transformer serializer = tf.newTransformer();
//	                serializer.setOutputProperty(OutputKeys.ENCODING, ENCODING);
//	                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//	                serializer.setOutputProperty(OutputKeys.METHOD, "html");
//	                serializer.transform(domSource, streamResult);
//	                FileUtils.writeFile(new String(out.toByteArray()), targetPath + File.separator + fileName + ".html");
//	                content = out.toString();
//	            } else if (ext.equals("docx")) {
//	                // 1) 加载word文档生成 XWPFDocument对象
//	                InputStream in = new FileInputStream(new File(sourcePath));
//	                XWPFDocument document = new XWPFDocument(in);
//	                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
//	                XHTMLOptions options = XHTMLOptions.create();
//	                options.setExtractor(new FileImageExtractor(picturesDir));
//	                options.URIResolver(new BasicURIResolver("image"));
//	                // 3) 将 XWPFDocument转换成XHTML
//	                baos = new ByteArrayOutputStream();
//	                XHTMLConverter.getInstance().convert(document, baos, options);
//	                content = baos.toString();
//	                FileUtils.writeFile(content, targetPath + File.separator + fileName + ".html");
//	            }
//	        } catch (Exception e) {
//	            //logger.error("文件转换失败",e);
//	            return false;
//	        } finally {
//	            try {
//	                if (NonUtil.isNotNon(out)) {
//	                    out.close();
//	                }
//	                if (NonUtil.isNotNon(baos)) {
//	                    baos.close();
//	                }
//	            } catch (IOException ioe) {
//	                //logger.error("IO流关闭异常", ioe);
//	            }
//	        }
//	        return true;
//	    }
//}
