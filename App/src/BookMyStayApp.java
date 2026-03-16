import java.util.LinkedList;
import java.util.Queue;

public class BookMyStayApp {

    private String guestName;
    private String roomType;

    public BookMyStayApp(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    private Queue<BookMyStayApp> requestQueue;

    public BookMyStayApp() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(BookMyStayApp reservation) {
        requestQueue.offer(reservation);
    }

    public BookMyStayApp getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }

    public static void main(String[] args) {

        System.out.println("Booking Request Queue");

        BookMyStayApp bookingQueue = new BookMyStayApp();

        BookMyStayApp r1 = new BookMyStayApp("Abhi", "Single");
        BookMyStayApp r2 = new BookMyStayApp("Subha", "Double");
        BookMyStayApp r3 = new BookMyStayApp("Vanmathi", "Suite");

        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        while (bookingQueue.hasPendingRequests()) {

            BookMyStayApp reservation = bookingQueue.getNextRequest();

            System.out.println(
                    reservation.getGuestName() +
                            " requested " +
                            reservation.getRoomType()
            );
        }
    }
}