package com.shd.core.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分頁模組
 * 
 * @author Grandy
 * 
 */
public class Pagination<T extends Serializable> {

	/**
	 * 1. 從 Dao 取得是存放 Persistence Bean.<br>
	 * 2. 從 Service 取得是存放 View Bean.
	 */
	private List<T> resultList;

	/**
	 * 每頁筆數
	 */
	private int pageSize;

	/**
	 * 目前頁數, 起使值為 1
	 */
	private int currentPage;

	/**
	 * 總筆數
	 */
	private int totalRow;

	/**
	 * constructor
	 * 
	 * @param results
	 * @param page
	 * @param pageSize
	 * @param totalResults
	 */
	public Pagination(List<T> results, int page, int pageSize, int totalRow) {
		this.resultList = results;
		this.currentPage = page;
		this.pageSize = pageSize;
		this.totalRow = totalRow;
	}

	/**
	 * 取得目前頁的資料.<br>
	 * 
	 * @return 從 Dao 取得, 是 Persistenc Bean. 從 Service 取得是 View Bean
	 */
	public List<T> getCurrentList() {
		// 如果是最後一頁, 筆數只有3筆, 那麼我們只會取得3筆資料
		return (List<T>) (getHasNextPage() ? resultList.subList(0, pageSize) : resultList);
	}

	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 取得目前頁的起始 number, 如: 每頁有10筆, 那麼第二頁的第一筆資料的 number=11
	 * 
	 * @return
	 */
	public int getCurrentPageFirstNumber() {
		return (currentPage - 1) * pageSize + 1;
	}

	/**
	 * 取得目前頁的結束 number, 如: 每頁有 10 筆, 那麼第二頁的最後一筆資料的 number= 20
	 * 
	 * @return
	 */
	public int getCurrentPageLastNumber() {
		int fullPage = getCurrentPageFirstNumber() + pageSize - 1;

		return totalRow < fullPage ? totalRow : fullPage;
	}

	/**
	 * 是否有下一頁
	 * 
	 * (為了搭配 jstl, 要用 getXXXX)
	 * 
	 * @return true, 有下一頁
	 */
	public boolean getHasNextPage() {
		// 假設 pageSize =10 , 那麼系統會抓取 ( pageSize + 1 ) 筆資料, 如果傳回 11 筆資料,
		// 那麼表示有下一頁.
		return resultList.size() > pageSize;
	}

	/**
	 * 是否有前一頁, 系統的頁碼起始值為 1
	 * 
	 * (為了搭配 jstl, 要用 getXXXX)
	 * 
	 * @return true, 有前一頁
	 */
	public boolean getHasPreviousPage() {
		return currentPage > 1;
	}

	/**
	 * 是否為第一頁
	 * 
	 * @return true, 目前頁碼為第一頁
	 */
	public boolean getIsFirstPage() {
		// 系統頁碼的起始值為 1
		return currentPage == 1;
	}

	/**
	 * 是否為最後一頁
	 * 
	 * @return true, 目前頁碼為最後一頁
	 */
	public boolean getIsLastPage() {
		return currentPage >= getTotalPage();
	}

	/**
	 * 取得每頁筆數
	 * 
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 不是給頁面使用, 是給 service 使用
	 * 
	 * @return the resultList
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * 取得總頁數
	 * 
	 * @return
	 */
	public int getTotalPage() {
		if (totalRow <= 0)
			return 0;

		return (totalRow - 1) / pageSize + 1;
	}

	/**
	 * 取得總筆數
	 * 
	 * @return the totalResults
	 */
	public int getTotalRow() {
		return totalRow;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
