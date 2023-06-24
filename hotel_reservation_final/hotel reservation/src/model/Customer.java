package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    // Customer constructor containing email address pattern and regex
    public Customer(String email, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        validE(email);
    }

    public static boolean validE(String email){
        final String emailRegex = "^(.+)@(.+).(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);
        boolean valid = true;
        try {
            if(!pattern.matcher(email).matches()){
                throw new IllegalArgumentException();
            }
        }catch(IllegalArgumentException e){
            System.out.println("Invalid email address");
            valid = false;
        }return valid;
    }

    public final String getFirstName(){
        return firstName;
    }
    public final String getLastName(){
        return lastName;
    }
    public final String getEmail(){
        return email;
    }

    @Override
    public String toString(){
        return "Name: " + firstName + " " + lastName + "\nEmail: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}