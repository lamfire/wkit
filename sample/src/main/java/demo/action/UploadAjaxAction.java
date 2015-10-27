package demo.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.lamfire.logger.Logger;
import com.lamfire.utils.FileUtils;
import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.MultiPartFile;
import com.lamfire.wkit.ServletUtils;
import com.lamfire.wkit.action.StreamAction;

public class UploadAjaxAction extends StreamAction{
	private static final Logger LOGGER = Logger.getLogger(UploadAjaxAction.class);
	private MultiPartFile file;
	
	private String url;
	
	@Override
	public void execute(OutputStream output) {
		String savePath = ServletUtils.getApplicationPath(ActionContext.getActionContext().getServletContext());
		String filename = file.getFileName();
		try {
			File saveTo = new File(savePath +"/upload/" + filename);
			FileUtils.makeParentDirs(saveTo);
			
			LOGGER.info("[save]:" + saveTo.getAbsolutePath());
			file.saveAs(saveTo);
			
			url = "upload/" + filename;
			this.write(output, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public MultiPartFile getFile() {
		return file;
	}


	public void setFile(MultiPartFile file) {
		this.file = file;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

}
