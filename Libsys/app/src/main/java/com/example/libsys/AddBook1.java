package com.example.libsys;

public class AddBook1 {
    long bookId;
    String bookName;
    String bookAuthor;
    int bookCount;
    int remBookCount;

    public AddBook1() {
    }

    public AddBook1(long bookId, String bookName, String bookAuthor, int bookCount, int remBookCount){
        this.bookId=bookId;
        this.bookName=bookName;
        this.bookAuthor=bookAuthor;
        this.bookCount=bookCount;
        this.remBookCount=remBookCount;
    }

    public long getBookId() {
        return bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getRemBookCount() {
        return remBookCount;
    }
}

