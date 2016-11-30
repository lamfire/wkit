package demo.action;

import com.lamfire.json.JSON;
import com.lamfire.wkit.Parameters;
import com.lamfire.wkit.action.ActionSupport;
import com.lamfire.wkit.anno.ACTION;
import com.lamfire.wkit.anno.MAPPING;

import java.io.OutputStream;

@ACTION(path = "/user",permissions = "ADMIN",singleton = true)
public class UserAction extends ActionSupport {

    @MAPPING(path = "/add")
    public void add(OutputStream out){
        JSON json = new JSON();
        json.put("status",200);
        json.put("name","user add");
        write(out,json.toBytes());
    }

    @MAPPING(path = "/del")
    public void del(OutputStream out){
        JSON json = new JSON();
        json.put("status",200);
        json.put("name","user del");
        write(out,json.toBytes());
    }
}
