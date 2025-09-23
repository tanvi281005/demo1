package com.thecodealchemist.spring_boot_project.model;

import java.time.LocalTime;
import java.time.LocalDate;

public class TransportBooking {
    private int serviceId;
    private int routeId;
    private boolean walletEnough;
    private LocalTime timeChosen;
    private LocalDate date;

    public TransportBooking() {}
    public TransportBooking(int serviceId, int routeId, boolean walletEnough, LocalTime timeChosen, LocalDate date) {
        this.serviceId = serviceId;
        this.routeId = routeId;
        this.walletEnough = walletEnough;
        this.timeChosen = timeChosen;
        this.date = date;
    }

    public int getServiceId() { return serviceId; }
    public void setServiceId(int serviceId) { this.serviceId = serviceId; }
    public int getRouteId() { return routeId; }
    public void setRouteId(int routeId) { this.routeId = routeId; }
    public boolean isWalletEnough() { return walletEnough; }
    public void setWalletEnough(boolean walletEnough) { this.walletEnough = walletEnough; }
    public LocalTime getTimeChosen() { return timeChosen; }
    public void setTimeChosen(LocalTime timeChosen) { this.timeChosen = timeChosen; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
