package com.sygdsoft.model;

import java.util.List;

/**
 * Created by 舒展 on 2016-06-20.
 */
public class BookInput {
    private Book book;//订单信息
    private List<BookRoom> bookRoomList;//订房列表
    private List<BookRoomCategory> bookRoomCategoryList;//房类列表

    public BookInput() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<BookRoom> getBookRoomList() {
        return bookRoomList;
    }

    public void setBookRoomList(List<BookRoom> bookRoomList) {
        this.bookRoomList = bookRoomList;
    }

    public List<BookRoomCategory> getBookRoomCategoryList() {
        return bookRoomCategoryList;
    }

    public void setBookRoomCategoryList(List<BookRoomCategory> bookRoomCategoryList) {
        this.bookRoomCategoryList = bookRoomCategoryList;
    }
}
