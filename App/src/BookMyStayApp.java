import java.io.*;
import java.util.*;

/**
 * RoomInventory (with getter/setter for persistence)
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Standard", 2);
        rooms.put("Deluxe", 1);
        rooms.put("Suite", 1);
    }

    public Map<String, Integer> getRooms() {
        return rooms;
    }

    public void setRooms(Map<String, Integer> rooms) {
        this.rooms = rooms;
    }

    public void display() {
        System.out.println("Inventory: " + rooms);
    }
}

/**
 * FilePersistenceService
 */
class FilePersistenceService {

    // Save inventory to file
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getRooms().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved to file ✅");

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from file
    public void loadInventory(RoomInventory inventory, String filePath) {

        Map<String, Integer> loadedData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");

                if (parts.length == 2) {
                    String roomType = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    loadedData.put(roomType, count);
                }
            }

            inventory.setRooms(loadedData);
            System.out.println("Inventory loaded from file ✅");

        } catch (IOException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }
}

/**
 * MAIN CLASS - BookMyStayApp
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistence = new FilePersistenceService();

        String filePath = "inventory.txt";

        System.out.println("Initial Inventory:");
        inventory.display();

        // Save to file
        persistence.saveInventory(inventory, filePath);

        // Simulate app restart (clear inventory)
        inventory.setRooms(new HashMap<>());
        System.out.println("\nAfter Clearing Inventory:");
        inventory.display();

        // Load from file
        persistence.loadInventory(inventory, filePath);

        System.out.println("\nAfter Recovery:");
        inventory.display();
    }
}