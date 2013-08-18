package com.joco.playground.model;

import com.joco.playground.model.base.BaseToJsonStringObject;

public class BookCount extends BaseToJsonStringObject {
	private int books;
	public int getBooks() {
		return books;
	}
	public void setBooks(int books) {
		this.books = books;
	}
}