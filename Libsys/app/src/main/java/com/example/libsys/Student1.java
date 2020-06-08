package com.example.libsys;

public class Student1 {
    long studentId;
    String userId;
    String userEmail;
    int borrowedBooks;

    public Student1(){}

    public Student1(long studentId,String userId,String userEmail,int borrowedBooks){
        this.studentId=studentId;
        this.userId=userId;
        this.borrowedBooks=borrowedBooks;
        this.userEmail=userEmail;
    }

    public long getStudentId() { return studentId; }
    public String getUserId() { return userId; }
    public String getUserEmail() { return userEmail; }
    public int getBorrowedBooks() { return borrowedBooks; }
}
