package model;
import java.util.*;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType enumeration;
    private final boolean isFree;

    public Room(final String roomNumber, final Double price, final RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        isFree = price == 0.0;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }
    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }
    @Override
    public String toString() {
        return "Room number: " + roomNumber + "  Price: " + price + "  Room Type: " + enumeration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber) && enumeration == room.enumeration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, enumeration);
    }
}