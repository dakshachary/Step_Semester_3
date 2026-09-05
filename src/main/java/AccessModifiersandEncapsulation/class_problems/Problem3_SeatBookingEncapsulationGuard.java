/*
 * PROBLEM 3 - Intermediate
 * Seat Booking Encapsulation Guard
 *
 * CineScreen keeps seatsTotal and seatsAvailable fully private. The
 * count can only ever move through validated methods, and the class
 * can never be constructed with a nonsensical seat count.
 */
public class Problem3_SeatBookingEncapsulationGuard {

    static class CineScreen {
        private final int seatsTotal;
        private int seatsAvailable;

        CineScreen(int seatsTotal) {
            if (seatsTotal <= 0) {
                throw new IllegalArgumentException(
                        "seatsTotal must be positive, got: " + seatsTotal);
            }
            this.seatsTotal = seatsTotal;
            this.seatsAvailable = seatsTotal;
        }

        /**
         * Books a single seat. Silently does nothing if no seats remain,
         * so the count can never go negative.
         */
        void bookSeat() {
            if (seatsAvailable <= 0) {
                return; // silently reject - no seat available to take
            }
            seatsAvailable--;
        }

        /**
         * Cancels a single booking. Silently does nothing if the screen
         * is already back to full capacity, so the count can never
         * exceed seatsTotal.
         */
        void cancelBooking() {
            if (seatsAvailable >= seatsTotal) {
                return; // silently reject - nothing genuine to undo
            }
            seatsAvailable++;
        }

        int getSeatsAvailable() {
            return seatsAvailable;
        }
    }

    // ------------------------------------------------------------------
    // Demo / manual test driver
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        // Construction rejected for zero/negative seatsTotal
        try {
            CineScreen bad = new CineScreen(0);
            System.out.println("Should not reach here");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected: " + e.getMessage());
        }

        // Booking beyond capacity is silently rejected
        CineScreen c = new CineScreen(2);
        c.bookSeat();
        c.bookSeat();
        c.bookSeat(); // no seat left -> silently rejected
        System.out.println(c.getSeatsAvailable());
        // Expected: 0

        // Cancelling beyond full capacity is silently rejected
        c.cancelBooking();
        c.cancelBooking();
        c.cancelBooking(); // already full -> silently rejected
        System.out.println(c.getSeatsAvailable());
        // Expected: 2
    }
}
