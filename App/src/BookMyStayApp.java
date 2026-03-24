import java.util.*;

/**
 * CLASS - Reservation
 * Represents a booking
 */
class Reservation {
    private String reservationId;
    private String customerName;
    private String roomType;

    public Reservation(String reservationId, String customerName, String roomType) {
        this.reservationId = reservationId;
        this.customerName = customerName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * CLASS - BookingHistory
 */
class BookingHistory {

    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * CLASS - BookingReportService
 */
class BookingReportService {

    public void generateReport(BookingHistory history) {

        List<Reservation> reservations = history.getConfirmedReservations();

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("===== BOOKING REPORT =====");

        for (Reservation r : reservations) {
            System.out.println("Reservation ID: " + r.getReservationId());
            System.out.println("Customer Name : " + r.getCustomerName());
            System.out.println("Room Type     : " + r.getRoomType());
            System.out.println("--------------------------");
        }

        System.out.println("Total Bookings: " + reservations.size());
    }
}

/**
 * MAIN CLASS - BookMyStayApp
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Adding sample reservations
        history.addReservation(new Reservation("R101", "Rahul", "Deluxe"));
        history.addReservation(new Reservation("R102", "Aisha", "Suite"));
        history.addReservation(new Reservation("R103", "Karan", "Standard"));

        // Generate report
        reportService.generateReport(history);
    }
}