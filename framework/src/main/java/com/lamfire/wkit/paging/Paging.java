package com.lamfire.wkit.paging;

import java.util.List;

public class Paging {
	private int pageIndex;
	private int pageSize;
	private long total;
	
	private List<? extends Object> items;
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<? extends Object> getItems() {
		return items;
	}
	public void setItems(List<? extends Object> items) {
		this.items = items;
	}
	
	public long getPageCount(){
		long count = total / pageSize;
		if(total % pageSize > 0){
			count+=1;
		}
		return count;
	}
}
