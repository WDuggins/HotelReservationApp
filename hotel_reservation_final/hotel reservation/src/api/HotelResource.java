package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static HotelResource instance;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private HotelResource(){
    }
    public static HotelResource getInstance(){
        if(instance == null){
            instance = new HotelResource();
        }
        return instance;
    }
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }
    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(email, firstName, lastName);
    }
    public IRoom getRoom(String roomId){
        return reservationService.getARoom(roomId);
    }
    public Reservation bookARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }
    public void getCustomersReservations(String email) {
        reservationService.getCustomersReservation(getCustomer(email));
    }
    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        return reservationService.findRooms(checkInDate, checkOutDate);
    }
    public Collection<IRoom> altRooms(Date checkInDate, Date checkOutDate) {
        return reservationService.altRooms(checkInDate, checkOutDate);
    }
    public Date addDays(final Date date) {

        return reservationService.addDays(date);
    }
}