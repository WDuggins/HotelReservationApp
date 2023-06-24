package model;

import java.util.Date;


public class Reservation {
    final Customer customer;
    final IRoom room;
    final Date checkInDate;
    final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
    }
    public final Customer getCustomer(){
        return customer;
    }
    public final IRoom getRoom(){
        return room;
    }
    public final Date getCheckInDate(){
        return checkInDate;
    }
    public final Date getCheckOutDate(){
        return checkOutDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}