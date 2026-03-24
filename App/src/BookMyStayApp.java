import java.util.*;

/**
 * Reservation class
 */
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

/**
 * Thread-safe Booking Queue
 */
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll(); // returns null if empty
    }
}

/**
 * Room Inventory
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 1);
    }

    public boolean allocate(String roomType) {
        if (rooms.containsKey(roomType) && rooms.get(roomType) > 0) {
            rooms.put(roomType, rooms.get(roomType) - 1);
            return true;
        }
        return false;
    }

    public void display() {
        System.out.println("Inventory: " + rooms);
    }
}

/**
 * Allocation Service
 */
class RoomAllocationService {
    public void allocateRoom(Reservation r, RoomInventory inventory) {
        boolean success = inventory.allocate(r.roomType);

        if (success) {
            System.out.println(Thread.currentThread().getName() +
                    " booked " + r.roomType + " for " + r.guestName);
        } else {
            System.out.println(Thread.currentThread().getName() +
                    " FAILED booking for " + r.guestName);
        }
    }
}

/**
 * Concurrent Booking Processor
 */
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {
        while (true) {

            Reservation reservation;

            // Thread-safe queue access
            synchronized (bookingQueue) {
                reservation = bookingQueue.getNextRequest();
            }

            if (reservation == null) break;

            // Thread-safe inventory update
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

/**
 * MAIN CLASS - BookMyStayApp
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService service = new RoomAllocationService();

        // Add booking requests
        queue.addRequest(new Reservation("Rahul", "Standard"));
        queue.addRequest(new Reservation("Aisha", "Standard"));
        queue.addRequest(new Reservation("Karan", "Standard"));
        queue.addRequest(new Reservation("Neha", "Deluxe"));

        // Create threads
        Thread t1 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service), "T1");
        Thread t2 = new Thread(new ConcurrentBookingProcessor(queue, inventory, service), "T2");

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }

        System.out.println("\nFinal Inventory:");
        inventory.display();
    }
}