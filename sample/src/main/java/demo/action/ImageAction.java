package demo.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.lamfire.utils.FileUtils;
import com.lamfire.wkit.ActionContext;
import com.lamfire.wkit.ServletUtils;
import com.lamfire.wkit.action.StreamAction;

public class ImageAction extends StreamAction{

	@Override
	public void execute(OutputStream output) {
		String path = ServletUtils.getApplicationPath(ActionContext.getActionContext().getServletContext()) + "/res/images/loading.gif";
		try {
			byte[] bytes = FileUtils.readFileToByteArray(new File(path));
			write(output, bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
