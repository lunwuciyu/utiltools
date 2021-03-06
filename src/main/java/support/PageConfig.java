package support;

import util.SortInfo;


public class PageConfig {
	// 偏移位置
	private int offset;
	// 记录长度
	private int length;
	// 排序信息
	private SortInfo sortInfo;

	public PageConfig() {
		this.offset = 0;
		this.length = 0;
	}

	public PageConfig(int offset, int length, SortInfo sortInfo) {
		this.offset = offset;
		this.length = length;
		this.sortInfo = sortInfo;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public SortInfo getSortInfo() {
		return sortInfo;
	}

	public void setSortInfo(SortInfo sortInfo) {
		this.sortInfo = sortInfo;
	}

}
