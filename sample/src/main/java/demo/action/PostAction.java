package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.action.ActionResult;
import com.lamfire.wkit.action.StreamAction;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;
import com.lamfire.wkit.anno.PARAM;

import java.io.OutputStream;

@ACTION
public class PostAction extends StreamAction {

    @MAPPING(path = "/post",permissions = "ADMIN")
    public void post(@PARAM(value = "title") String title, @PARAM(value = "name")String name,OutputStream out){
        JSON json = new JSON();
        json.put("status",200);
        json.put("title",title);
        json.put("name",name);

        write(out,json.toBytes());
    }
}
