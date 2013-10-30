/**
 * Copyright (c) 2007 神州泰岳服务管理事业部应用组
 * All rights reserved.
 *
 * 文件名称: PageBean.java
 * 文件标示: PageBean.java
 * 摘   要:
 * 
 * 当前版本：
 * 作   者: yechanglun
 * 完成日期:
 */
package com.ultrapower.eoms.common.support;

import java.util.List;

public class PageBean<T> {

	private static int DEFAULT_PAGE_SIZE = 20;

	long rowCount = 0; // 记录总数

	int pageCount = 0;// 页总数

	int pageCur = 0;// 当前页数

	int pageSize = DEFAULT_PAGE_SIZE;// 页显示记录数

	int firstResult = 0;

    private List<T> list;

    private Class<T> entityClass;

	public long getRowCount() {
		return rowCount;
	}

	public void setRowCount(long rowCount) {
        this.rowCount = rowCount;
        if (pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageCount = (int) rowCount / this.pageSize;
        if (this.rowCount % this.pageSize != 0) {
            pageCount++;
        }
        if (this.pageCur == 0 && this.rowCount > 0) {
            this.pageCur = 1;
        }
        //解决第二次查询页数少于当前页数时的问题
        if (pageCur > pageCount) {
            pageCur = pageCount;
        }
    }

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageCur() {
		return pageCur;
	}

	public void setPageCur(int pageCur) {
		this.pageCur = pageCur;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstResult() {
		this.firstResult = (this.pageCur - 1) * this.pageSize;
		if (this.firstResult < 0) {
			this.firstResult = 0;
		}
		return firstResult;
	}

	public int getPrevious() {
		return this.pageCur > 1 ? this.pageCur - 1 : 0;
	}

	public int getNext() {
		return this.pageCur < this.pageCount ? this.pageCur + 1
				: this.pageCount;
	}

	public int getLast() {
		return this.pageCount > 0 ? this.pageCount : 0;
	}

	public int getFirst() {
		return this.pageCount > 0 ? 1 : 0;
	}

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

}
