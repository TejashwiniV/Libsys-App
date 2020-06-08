package com.example.libsys;

public class IssueBook1 {
    long issueId;
    int bookID;
    int studentID;
    String issueDate;
    String returnDate;
    int renewCount;

    public IssueBook1(){}

    public IssueBook1(long issueId,int bookID,int studentID,String issueDate,String returnDate,int renewCount){
        this.issueId=issueId;
        this.bookID=bookID;
        this.studentID=studentID;
        this.issueDate=issueDate;
        this.returnDate=returnDate;
        this.renewCount=renewCount;
    }
    public Long getIssueId(){return issueId;}
    public int getBookID(){return bookID;}
    public int getStudentID(){return studentID;}
    public String getIssueDate(){return issueDate;}
    public String getReturnDate(){return returnDate;}
    public int getRenewCount(){return renewCount;}
}
