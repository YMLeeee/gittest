package com.min.edu.dtos;

import java.io.Serializable;



public class RowNum_DTO implements Serializable {

	
	private static final long serialVersionUID = -2431382776265030318L;
	private int pageList; // 출력할 페이지 번호 갯수
	private int index; //출력한 페이지의 번호
	private int pageNum; // 출력할 페이지 시작번호
	private int listNum; // 출력할 리스트의 갯수
	private int total; // 리스트의 총 갯수
	
	{
		pageList = 5;
		index = 0;
		pageNum = 1;
		listNum = 5;
	}

	public RowNum_DTO() {
	}

	public RowNum_DTO(String index, String pageNum, String listNum) {
		if(index != null) {
			this.index = Integer.parseInt(index);
		}
		if(pageNum != null) {
			this.pageNum = Integer.parseInt(pageNum);
		}
		if(listNum != null) {
			this.listNum = Integer.parseInt(listNum);
		}
	}
	
	// 1 ~ 5 , 6 ~10
	public int getStart() {
		return index * listNum +1; // 0*5 + 1 = >1
	}
	
	public int getLast() {
		return (index*listNum) + listNum; // (0*5) + 5 => 5
	}
	
	
	public int getCount() { // index 6 pageNum 1, listNum 5, total 35
		// 전체 갯수 - 출력리스트*(시작번호) => 91 - 5*(1-1) => 91
		
		// 10 = 35 - 5*(6-1)
		int temp = total - listNum*(pageNum-1);
		// 91/5 = 18
		// 2 = 10 / 5
		int min = temp/listNum;
		int count = 0;
		
		// 91%5 = 1 => min = 19
		if(temp%listNum !=0) {
			min++;
		}
		
		// 10
		if(temp <= listNum) {
			count = pageNum;
		}
		// 2 <= 5
		else if(min <= pageList) {
			count = min + pageNum -1;
		}else {
			count = pageList+pageNum-1;
		}
		System.out.println("count="+count);
		return count;
	}

	public int getPageList() {
		return pageList;
	}

	public void setPageList(int pageList) {
		this.pageList = pageList;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getListNum() {
		return listNum;
	}

	public void setListNum(int listNum) {
		this.listNum = listNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "RowNum_DTO [pageList=" + pageList + ", index=" + index + ", pageNum=" + pageNum + ", listNum=" + listNum
				+ ", total=" + total + "]";
	}
	
	
	
	
	
	
}









