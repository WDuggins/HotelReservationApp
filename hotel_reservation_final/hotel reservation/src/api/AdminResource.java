package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.TreeMap;

public class AdminResource {
    private static AdminResource instance;
    private static final CustomerService customerService = CustomerService.getInstance();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private AdminResource(){
    }
    public static AdminResource getInstance(){
        if(instance == null){
            instance = new AdminResource();
        }
        return instance;
    }
    public final Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void addRoom(final IRoom room){
        reservationService.addRoom(room);
    }
    public Collection<IRoom> getAllRooms(){
        return reservationService.getRoomList();
    }
    public TreeMap<String, Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    public Collection<Reservation> displayAllReservations(){
        return reservationService.printAllReservation();
    }
}
