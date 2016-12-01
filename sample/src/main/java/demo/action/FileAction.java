package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.MultiPartFile;
import com.lamfire.wkit.Parameters;
import com.lamfire.wkit.action.ActionSupport;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

import java.io.OutputStream;

@ACTION
public class FileAction extends ActionSupport {

    @MAPPING(path = "/file",permissions = "ADMIN")
    public void file(@PARAM(value = "file")MultiPartFile file,OutputStream out){
        write(out,file.getBytes());
    }
}
