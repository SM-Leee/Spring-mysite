package com.douzone.mysite.vo;

public class BoardVo {
	
	private Long no;
	private String title;
	private String contents;
	private String write_date;
	private int hit;
	private int group_no;
	private int order_no;
	private int depth;
	private Long user_no;
	private Long max_no;
	private String user_name;
	private int count;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWrite_date() {
		return write_date;
	}
	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getGroup_no() {
		return group_no;
	}
	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Long getUser_no() {
		return user_no;
	}
	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}
	public Long getMax_no() {
		return max_no;
	}
	public void setMax_no(Long max_no) {
		this.max_no = max_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", contents=" + contents + ", write_date=" + write_date
				+ ", hit=" + hit + ", group_no=" + group_no + ", order_no=" + order_no + ", depth=" + depth
				+ ", user_no=" + user_no + ", max_no=" + max_no + ", user_name=" + user_name + ", count=" + count + "]";
	}
	

}
