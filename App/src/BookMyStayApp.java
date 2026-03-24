import java.util.*;

/**
 * Custom Exception
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * RoomInventory (dummy implementation for validation)
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 0); // intentionally unavailable
    }

    public boolean isAvailable(String roomType) {
        return rooms.containsKey(roomType) && rooms.get(roomType) > 0;
    }
}

/**
 * BookingRequestQueue (dummy)
 */
class BookingRequestQueue {
    public void addRequest(String guestName, String roomType) {
        System.out.println("Booking request added for " + guestName + " (" + roomType + ")");
    }
}

/**
 * ReservationValidator
 */
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        if (roomType == null || roomType.trim().isEmpty()) {
            throw new InvalidBookingException("Room type must be selected.");
        }

        if (!inventory.isAvailable(roomType)) {
            throw new InvalidBookingException("Selected room type is not available.");
        }
    }
}

/**
 * MAIN CLASS - BookMyStayApp
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Booking Validation ===");

        Scanner scanner = new Scanner(System.in);

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Standard/Deluxe/Suite): ");
            String roomType = scanner.nextLine();

            // Validation
            validator.validate(guestName, roomType, inventory);

            // If valid → add booking
            bookingQueue.addRequest(guestName, roomType);
            System.out.println("Booking successful ✅");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed ❌: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}