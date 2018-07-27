package cn.finedo.codereview.officehelper;
//package cn.finedo.codereview.ut;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
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
//
///*****************************************
// * <p>Title: word转html工具类</p>
// * <p>Description: </p>
// * <p>Company: 合肥非度信息技术有限公司</p> 
// * @author  Charlee
// * @date    2018年6月14日上午10:12:43
// * @version 1.0
// *****************************************/
//public class PoiWordToHtmlUtil1 {
//    
//    private static Logger logger = LogManager.getLogger();
//    
//    /**
//     * <p>Title: Word转Html</p>
//     * <p>Description: </p>
//     * @param SourcePath    源文件路径
//     * @param targetPath    转换文件路径
//     * @return
//     * @author Charlee
//     * @date 2018年6月14日
//     */
//    public static boolean wordToHtml(String sourcePath,String targetPath) {
//        // 判断文件是否存在
//        File wordFile = new File(sourcePath);
//        if (!(wordFile.isFile()) && !(wordFile.exists())) {
//            logger.debug("找不到指定的文件");
//            return false;
//        }
//        
//        //截取文件名
//        File tempFile =new File(sourcePath.trim());
//        String wordName = tempFile.getName();   //doc文件名
//        String htmlName = wordName.substring(0, wordName.indexOf(".")) + ".html";   //html文件名
//        final String picturesDir = targetPath + File.separator + "image" + File.separator;  //图片存放路径
//        
//        File htmlFile = new File(targetPath + File.separator + htmlName);
//        InputStream in = null;
//        OutputStream out = null;
//        String ext = FileUtils.GetFileExt(sourcePath);
//        try {
//            if (ext.equals("doc")) {
//                //原word文档
//                in = new FileInputStream(new File(sourcePath));
//                
//                HWPFDocument wordDocument = new HWPFDocument(in);
//                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
//                //设置图片存放的位置
//                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
//                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
//                        File imgPath = new File(picturesDir);
//                        if(!imgPath.exists()){//图片目录不存在则创建
//                            imgPath.mkdirs();
//                        }
//                        File file = new File(picturesDir + suggestedName);
//                        OutputStream os = null;
//                        try {
//                            os = new FileOutputStream(file);
//                            os.write(content);
//                        } catch (Exception e) {
//                            logger.error("文件转换失败",e);
//                        } finally {
//                            try {
//                                if (NonUtil.isNotNon(os)) {
//                                    os.close();
//                                }
//                            } catch (IOException ioe) {
//                                logger.error("IO流关闭异常", ioe);
//                            }
//                        }
//                        String prefix = picturesDir.substring(picturesDir.indexOf("/codereview_service"));
//                        //图片在html文件上的路径 相对路径
//                        //return "image/" + suggestedName;
//                        return prefix + suggestedName;
//                    }
//                });
//                //解析word文档
//                wordToHtmlConverter.processDocument(wordDocument);
//                Document htmlDocument = wordToHtmlConverter.getDocument();
//                
//                //生成html文件上级文件夹
//                File folder = new File(targetPath);
//                if(!folder.exists()){
//                    folder.mkdirs();
//                }
//                
//                //生成html文件地址
//                out = new FileOutputStream(htmlFile);
//                
//                DOMSource domSource = new DOMSource(htmlDocument);
//                StreamResult streamResult = new StreamResult(out);
//                
//                TransformerFactory factory = TransformerFactory.newInstance();
//                Transformer serializer = factory.newTransformer();
//                serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
//                serializer.setOutputProperty(OutputKeys.METHOD, "html");
//                serializer.transform(domSource, streamResult);
//            } else if (ext.equals("docx")) {
//                // 1) 加载word文档生成 XWPFDocument对象 
//                in = new FileInputStream(wordFile); 
//                XWPFDocument document = new XWPFDocument(in); 
//                
//                // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录) 
//                File imgFolder = new File(picturesDir);
//                XHTMLOptions options = XHTMLOptions.create();
//                options.setExtractor(new FileImageExtractor(imgFolder));
//              //添加服务器路径
//                String prefix = picturesDir.substring(picturesDir.indexOf("/codereview_service"));
//                
//                //html中图片的路径 相对路径 
//                //options.URIResolver(new BasicURIResolver("image"));
//                options.URIResolver(new BasicURIResolver(prefix));
//                options.setIgnoreStylesIfUnused(false);
//                options.setFragment(true);
//                // 3) 将 XWPFDocument转换成XHTML
//                //生成html文件上级文件夹
//                File folder = new File(targetPath);
//                if(!folder.exists()){ 
//                    folder.mkdirs();
//                }
//                out = new FileOutputStream(htmlFile);
//                XHTMLConverter.getInstance().convert(document, out, options);
//            }
//        } catch (Exception e) {
//            logger.error("文件转换失败",e);
//            return false;
//        } finally {
//            try {
//                if (NonUtil.isNotNon(in)) {
//                    in.close();
//                }
//                if (NonUtil.isNotNon(out)) {
//                    out.close();
//                }
//            } catch (IOException ioe) {
//                logger.error("IO流关闭异常", ioe);
//            }
//        }
//        return true;
//    }
//}
//
