package service;

import model.*;

import java.util.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public final class ReservationService{

    private static ReservationService instance;

    // Set of rooms ensuring roomNumber cannot be duplicated
    private final Set<IRoom> roomSet = new HashSet<>();

    // Set of reservations ensuring room cannot be double booked
    private final Set<Reservation> reservationSet = new HashSet<>();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }
    // Adds new room to room set
    public void addRoom(final IRoom room) {
       roomSet.add(room);
       System.out.println("Room added");
    }

    // Retrieves an individual room from the room set and mentions if it is a free room
    public IRoom getARoom(String roomId) {
        for(IRoom room : roomSet) {
            if (room.getRoomNumber().equals(roomId)) {
                // Free room
                if (room.isFree()) {
                    System.out.println("This room is free!");
                }return room;
            }
        }
        return null;
    }

    // Adds a new reservation to the reservation set
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        final Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservationSet.add(reservation);
        System.out.println("Room reserved");
        return reservation;
    }



    // Returns all rooms
    public Collection<IRoom> getRoomList() {
        return roomSet;
    }

    // The criteria for determining whether a new reservation would overlap an existing one
    public static boolean resOverlaps(Reservation reservation, Date checkInDate, Date checkOutDate){
        return checkInDate.before(reservation.getCheckOutDate())
                && checkOutDate.after(reservation.getCheckInDate());
    }
    // Weeds out unavailable rooms or invalid date ranges and returns available rooms
    public Collection<IRoom> findRooms(final Date checkInDate, final Date checkOutDate) {
        ArrayList<IRoom> noneAvailable = new ArrayList<>();
        ArrayList<IRoom> available = new ArrayList<>();
        if (roomSet.isEmpty()) {
            System.out.println("No rooms exist.");
        }else if (checkInDate.before(new Date()) || checkOutDate.before(new Date())
                || checkInDate.equals(checkOutDate) || checkInDate.after(checkOutDate)){
            System.out.println("Not a valid date range");
        }else if (!reservationSet.isEmpty()) {
            for (Reservation reservation : reservationSet) {
                if (resOverlaps(reservation, checkInDate, checkOutDate)) {
                    noneAvailable.add(reservation.getRoom());
                }
            }
        }for (IRoom room : roomSet) {
            if (!noneAvailable.contains(room)) {
                available.add(room);
            }
        }return available;
    }

    // Find alt dates when requested are not available
    public Collection<IRoom> altRooms(final Date checkInDate, final Date checkOutDate) {
        return findRooms(addDays(checkInDate), addDays(checkOutDate));
    }

    // Adds number of days to requested dates
    public Date addDays(final Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar.getTime();
    }

    // Retrieves an individual customer's scheduled reservations
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> reservations = new ArrayList<>();
        for (Reservation reservation : reservationSet) {
            if (reservation.getCustomer().equals(customer)) {
                reservations.add(reservation);
                System.out.println(reservations);
            }
        }
        return reservations;
    }

    // Retrieves all reservations at the hotel
    public Collection<Reservation> printAllReservation(){
        return reservationSet;
    }
}