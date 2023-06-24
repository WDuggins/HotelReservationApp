package service;

import model.Customer;

import java.util.*;

public class CustomerService{

    private static CustomerService instance;
    private Map<String, Customer> mapOfCustomers = new HashMap<String, Customer>();
    private ArrayList<Customer> customerList = new ArrayList<Customer>();

    private CustomerService() {}

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService();
        }
        return instance;
    }

    // Adds a new customer to map collection
    public Customer addCustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(email, firstName, lastName);
        mapOfCustomers.put(email, customer);
        return customer;
    }

    // Retrieves an individual customer's information
    public Customer getCustomer(String email){
        if(!mapOfCustomers.containsKey(email)){
            System.out.println("Customer not found");
            return null;
        }
        else{
            return mapOfCustomers.get(email);
        }
    }

    // Retrieves and sorts entire customer list
    public TreeMap<String, Customer> getAllCustomers() {
        TreeMap<String, Customer> sortCustomers = new TreeMap<>();
        sortCustomers.putAll(mapOfCustomers);
        for (Map.Entry<String, Customer> entry : sortCustomers.entrySet()) {}
        return sortCustomers;
    }
}
