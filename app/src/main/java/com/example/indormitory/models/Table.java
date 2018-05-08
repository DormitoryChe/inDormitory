package com.example.indormitory.models;

public class Table {
    private String id;
    private String state;
    private int position;
    private String reserveTo;
    private String busyFrom;
    private String busyBy;
    private String imagePath;

    public Table(String id, String state, int position, String reserveTo, String busyFrom, String busyBy, String imagePath) {
        this.id = id;
        this.state = state;
        this.position = position;
        this.reserveTo = reserveTo;
        this.busyFrom = busyFrom;
        this.busyBy = busyBy;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public int getPosition() {
        return position;
    }

    public String getReserveTo() {
        return reserveTo;
    }

    public String getBusyFrom() {
        return busyFrom;
    }

    public String getBusyBy() {
        return busyBy;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isFree() {
        return state.equals("free");
    }

    public boolean isReserved() {
        return state.equals("reserved");
    }

    public boolean isBusy() {
        return state.equals("busy");
    }

    public boolean isBlocked() {
        return state.equals("blocked");
    }
}
