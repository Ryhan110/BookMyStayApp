import java.util.*;

/**
 * CLASS - RoomAllocationService
 * Handles reservation confirmation and room allocation.
 */
class RoomAllocationService {

    // Stores all allocated room IDs (to avoid duplicates)
    private Set<String> allocatedRoomIds;

    // Stores assigned room IDs by room type
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    /**
     * Allocates a room if available
     */
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();

        if (!inventory.isAvailable(roomType)) {
            System.out.println("❌ No rooms available for type: " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        // Update tracking
        allocatedRoomIds.add(roomId);

        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        // Update inventory immediately
        inventory.bookRoom(roomType);

        System.out.println("✅ Room allocated: " + roomId + " for " + reservation.getGuestName());
    }

    /**
     * Generates unique room ID
     */
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + (int)(Math.random() * 1000);
        } while (allocatedRoomIds.contains(roomId));

        return roomId;
    }
}

/**
 * Reservation Class
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * Room Inventory Class
 */
class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String type, int count) {
        inventory.put(type, count);
    }

    public boolean isAvailable(String type) {
        return inventory.getOrDefault(type, 0) > 0;
    }

    public void bookRoom(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("\n📊 Current Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

/**
 * MAIN CLASS
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Setup inventory
        inventory.addRoomType("Deluxe", 2);
        inventory.addRoomType("Standard", 1);

        // FIFO booking queue
        Queue<Reservation> bookings = new LinkedList<>();

        bookings.add(new Reservation("Ryhan", "Deluxe"));
        bookings.add(new Reservation("Aman", "Deluxe"));
        bookings.add(new Reservation("Sara", "Standard"));
        bookings.add(new Reservation("John", "Standard")); // should fail

        // Process bookings
        while (!bookings.isEmpty()) {
            Reservation r = bookings.poll();
            service.allocateRoom(r, inventory);
        }

        // Show final inventory
        inventory.displayInventory();
    }
}