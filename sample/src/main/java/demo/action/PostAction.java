package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.Parameters;
import com.lamfire.wkit.action.ActionSupport;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;

import java.io.OutputStream;

@ACTION
public class PostAction extends ActionSupport {

    @MAPPING(path = "/post",permissions = "ADMIN")
    public void post(Parameters params, OutputStream out){
        JSON json = new JSON();
        json.put("status",200);
        json.put("title",params.get("title"));
        json.put("name",params.get("name"));
        write(out,json.toBytes());
    }
}
