package demo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lamfire.utils.RandomUtils;
import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;
import demo.Item;
import com.lamfire.wkit.paging.Paging;


public class PagingAction extends ServletAction implements Serializable{

	private static final long serialVersionUID = -1496054655706162361L;
	private static int total = 525;
	
	private int index = 0;

	public ActionForward execute() {
		return forward("/actions/paging.jsp");
	}

	public Paging getPaging(){
		List<Item> list = new ArrayList<Item>();
		
		int from = index * 10;
		int to = from + 10;
		if(to > total){
			to = total;
		}
		for(int i = from; i< to ;i++){
			Item item = new Item();
			item.setId(i);
			item.setAuthor("author" + i);
			item.setPrice(RandomUtils.nextInt(1000));
			item.setTitle("title_" + i);
			item.setStatus(RandomUtils.nextInt(2));
			list.add(item);
		}
		
		Paging paging = new Paging();
		paging.setItems(list);
		paging.setPageIndex(index);
		paging.setTotal(total);
		paging.setPageSize(10);
		
		return paging;
	}
	
	public void setIndex(int i){
		this.index = i;
	}
}
