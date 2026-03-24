import java.util.*;

/**
 * RoomInventory (simple version)
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 1);
    }

    public boolean bookRoom(String roomType) {
        if (rooms.containsKey(roomType) && rooms.get(roomType) > 0) {
            rooms.put(roomType, rooms.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public void releaseRoom(String roomType) {
        rooms.put(roomType, rooms.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("Current Inventory: " + rooms);
    }
}

/**
 * CancellationService
 */
class CancellationService {

    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    // Register confirmed booking
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    // Cancel booking + rollback
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid reservation ID ❌");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Restore inventory
        inventory.releaseRoom(roomType);

        // Track rollback (LIFO)
        releasedRoomIds.push(reservationId);

        // Remove booking
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled for ID: " + reservationId);
    }

    // Show rollback history
    public void showRollbackHistory() {
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancellations yet.");
            return;
        }

        System.out.println("Rollback History (Latest First):");
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("- " + releasedRoomIds.get(i));
        }
    }
}

/**
 * MAIN CLASS - BookMyStayApp
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // Simulate bookings
        String res1 = "R201";
        String res2 = "R202";

        if (inventory.bookRoom("Standard")) {
            cancellationService.registerBooking(res1, "Standard");
        }

        if (inventory.bookRoom("Deluxe")) {
            cancellationService.registerBooking(res2, "Deluxe");
        }

        System.out.println("After Booking:");
        inventory.displayInventory();

        // Cancel one booking
        cancellationService.cancelBooking(res1, inventory);

        System.out.println("\nAfter Cancellation:");
        inventory.displayInventory();

        // Show rollback history
        cancellationService.showRollbackHistory();
    }
}