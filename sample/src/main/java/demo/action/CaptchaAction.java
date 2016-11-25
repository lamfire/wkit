package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.action.StreamAction;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

import java.io.OutputStream;

@ACTION
public class CaptchaAction extends com.lamfire.wkit.action.CaptchaAction {

    @MAPPING(path = "/cap")
    public void a(OutputStream output){
        super.execute(output);
    }
}
