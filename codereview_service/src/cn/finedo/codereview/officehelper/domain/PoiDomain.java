package cn.finedo.codereview.officehelper.domain;

import cn.finedo.common.domain.BaseDomain;

public class PoiDomain extends BaseDomain {

	private static final long serialVersionUID = 1L;
	private String sourcePath;		//源文件路径
	private String picturesPath;	//图片路径
	private String targetPath;		//转换文件路径
	
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getPicturesPath() {
		return picturesPath;
	}
	public void setPicturesPath(String picturesPath) {
		this.picturesPath = picturesPath;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
}
