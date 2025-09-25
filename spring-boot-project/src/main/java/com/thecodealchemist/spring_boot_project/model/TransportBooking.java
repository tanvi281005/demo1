package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalDate;

public class TransportBooking {

    private int routeId;
    private boolean walletEnough;
    private String timeChosen;
    private LocalDate date;

    public TransportBooking() {}

    // getters and setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public boolean isWalletEnough() {
        return walletEnough;
    }

    public void setWalletEnough(boolean walletEnough) {
        this.walletEnough = walletEnough;
    }

    public String getTimeChosen() {
        return timeChosen;
    }

    public void setTimeChosen(String timeChosen) {
        this.timeChosen = timeChosen;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
