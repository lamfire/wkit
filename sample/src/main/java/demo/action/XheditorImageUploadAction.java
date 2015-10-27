package demo.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.lamfire.code.UUIDGen;
import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.FileUtils;
import com.lamfire.utils.FilenameUtils;
import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.MultiPartFile;
import com.lamfire.wkit.action.StreamAction;

public class XheditorImageUploadAction extends StreamAction{
	final static Logger LOGGER = Logger.getLogger(XheditorImageUploadAction.class);
	private MultiPartFile file;
	
	@Override
	public void execute(OutputStream out) {
		String url = "";
		if(file != null){
			String dir = ActionContext.getActionContext().getServletContext().getRealPath("/upload");
			String host = ActionContext.getActionContext().getHttpServletRequest().getRequestURL().toString();
			host = host.substring(0, host.indexOf(ActionContext.getActionContext().getHttpServletRequest().getContextPath()));
			url = host+ ActionContext.getActionContext().getHttpServletRequest().getContextPath() +"/upload/";
			String filename = UUIDGen.getTimeAndNodeID() +"."+ FilenameUtils.getExtension(file.getFileName());
			url+= filename;
			LOGGER.info(url);
			LOGGER.info(dir);
			try {
				FileUtils.makeDirs(dir);
				file.saveAs(new File(dir +"/" + filename));
			} catch (IOException e) {
				LOGGER.error(e);
			}
			
			JSON js = new JSON();
			js.put("url", url);
			
			write(out,js.toString().getBytes());
			return;
		}
		
		JSON js = new JSON();
		js.put("error", "no found upload's file.");
		
		write(out,js.toString().getBytes());
	}

	public void setFile(MultiPartFile file) {
		this.file = file;
	}

	
}
