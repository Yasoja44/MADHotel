package com.example.madapp;

public class BookRoom {

    private String bookingId;
    private String checkIn;
    private String checkOut;
    private String roomNo;
    private String adultNo;
    private String childNo;
    private String userId;

    public BookRoom() {

    }

    public BookRoom(String bookingId,String roomNo,String adultNo, String childNo, String checkIn, String checkOut, String userId) {
        this.bookingId = bookingId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.roomNo = roomNo;
        this.adultNo = adultNo;
        this.childNo = childNo;
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }


    public String getAdultNo() {
        return adultNo;
    }

    public void setAdultNo(String adultNo) {
        this.adultNo = adultNo;
    }

    public String getChildNo() {
        return childNo;
    }

    public void setChildNo(String childNo) {
        this.childNo = childNo;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public String getUserId() {
        return userId;
    }


}
