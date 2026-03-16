import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    private Map<String, Integer> roomAvailability;

    public BookMyStayApp() {
        roomAvailability = new HashMap<>();

        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void displayRoomDetails() {
        System.out.println("Room details displayed.");
    }

    public void searchAvailableRooms(
            BookMyStayApp inventory,
            BookMyStayApp singleRoom,
            BookMyStayApp doubleRoom,
            BookMyStayApp suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        if (availability.get("Single") > 0) {
            System.out.println("Single Room Available");
            singleRoom.displayRoomDetails();
        }

        if (availability.get("Double") > 0) {
            System.out.println("Double Room Available");
            doubleRoom.displayRoomDetails();
        }

        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room Available");
            suiteRoom.displayRoomDetails();
        }
    }

    public static void main(String[] args) {

        BookMyStayApp inventory = new BookMyStayApp();
        BookMyStayApp singleRoom = new BookMyStayApp();
        BookMyStayApp doubleRoom = new BookMyStayApp();
        BookMyStayApp suiteRoom = new BookMyStayApp();

        BookMyStayApp searchService = new BookMyStayApp();

        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}