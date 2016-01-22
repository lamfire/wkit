package com.lamfire.wkit.paging;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;

public class PagingTag extends TagSupport {
    private static final Logger LOGGER = Logger.getLogger(PagingTag.class);
	private static final long serialVersionUID = 3086267641490020107L;
	private static final String TAG_FORMAT = "<a href='javascript:%s(%d);' class='%s'>%s</a>";
	private Paging paging;
	private String callback;
	private String[] titles = new String[]{"First","Prev","Next","Last"};

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}
	
	private String getTagHtml(long index,String text){
		if(paging.getPageIndex() == index){
			return String.format(TAG_FORMAT, callback,index,"act",text);
		}
		return String.format(TAG_FORMAT, callback,index,"",text);
	}
	
	private String getFirstTag(){
		return String.format(TAG_FORMAT, callback,0,"",titles[0]);
	}
	
	private String getPrevTag(){
		int index = 0;
		if(paging.getPageIndex() > 0){
			index = paging.getPageIndex() -1;
		}
		return String.format(TAG_FORMAT, callback,index,"",titles[1]);
	}
	
	private String getNextTag(){
		int index = paging.getPageIndex();
		if(paging.getPageIndex() < (paging.getPageCount() -1)){
			index = paging.getPageIndex() + 1;
		}
		return String.format(TAG_FORMAT, callback,index,"",titles[2]);
	}
	
	private String getLastTag(){
		return String.format(TAG_FORMAT, callback,paging.getPageCount() -1,"",titles[3]);
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if(paging.getPageCount() <= 1){
                if(LOGGER.isDebugEnabled()){
                    LOGGER.warn("paging total count = " + paging.getPageCount() +",skip write paging tag.");
                }
				return EVAL_PAGE;
			}
			
			JspWriter writer = this.pageContext.getOut();
			
			//首页
			writer.println(getFirstTag());
			
			//上一页
			writer.println(getPrevTag());
			

			//中间页
			long from = 0;
			if(paging.getPageCount() - paging.getPageIndex() > 5){
				from = paging.getPageIndex() - 5;
			}else{
				from = paging.getPageCount() - 10;
			}
			if(from < 0){
				from = 0 ;
			}

			long to = from + 10;
			if (to > (paging.getPageCount())) {
				to = paging.getPageCount();
			}
			
			for (long i = (from); i < to; i++) {
				String item = getTagHtml(i,String.valueOf(i+1));
				writer.println(item);
			}
			
			//下一页
			writer.println(getNextTag());
			

			//末页
			writer.println(getLastTag());
			writer.println("<span>"+ (paging.getPageIndex()+1) + "/"+paging.getPageCount()+"(" + paging.getTotal()+")</span>");
		} catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
		}
		return EVAL_PAGE;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	public void setTitles(String titles){
		if(StringUtils.isBlank(titles)){
			return;
		}
		String[] ts = StringUtils.split(titles, '|');
		int len = 4;
		if(ts.length < 4){
			len = ts.length;
		}
		for(int i=0;i<len;i++){
			this.titles[i] = ts[i];
		}
	}
}
