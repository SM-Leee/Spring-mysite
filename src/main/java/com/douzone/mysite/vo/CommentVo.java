package com.douzone.mysite.vo;

public class CommentVo {
	private Long no;
	private String comment;
	private Long board_no;
	private Long user_no;
	private String user_name;
	private int order_no;
	private int group_no;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getBoard_no() {
		return board_no;
	}
	public void setBoard_no(Long board_no) {
		this.board_no = board_no;
	}
	public Long getUser_no() {
		return user_no;
	}
	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getGroup_no() {
		return group_no;
	}
	public void setGroup_no(int group_no) {
		this.group_no = group_no;
	}
	@Override
	public String toString() {
		return "CommentVo [no=" + no + ", comment=" + comment + ", board_no=" + board_no + ", user_no=" + user_no
				+ ", user_name=" + user_name + ", order_no=" + order_no + ", group_no=" + group_no + "]";
	}

	
	
	
	
}
