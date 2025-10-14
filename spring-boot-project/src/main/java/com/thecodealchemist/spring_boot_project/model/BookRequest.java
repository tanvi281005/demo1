package com.thecodealchemist.spring_boot_project.model;

public class BookRequest {
     private int routeId;
    private String timeChosen; // HH:mm:ss

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getTimeChosen() {
        return timeChosen;
    }

    public void setTimeChosen(String timeChosen) {
        this.timeChosen = timeChosen;
    }
}
