import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    private Map<String, Integer> roomAvailability;

    public BookMyStayApp() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single Room", 10);
        roomAvailability.put("Double Room", 7);
        roomAvailability.put("Suite Room", 3);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }

    public static void main(String[] args) {

        BookMyStayApp inventory = new BookMyStayApp();

        System.out.println("Current Room Availability:");

        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }

        System.out.println("\nUpdating availability for Single Room...\n");

        inventory.updateAvailability("Single Room", 8);

        System.out.println("Updated Room Availability:");

        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}`