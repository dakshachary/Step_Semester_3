import java.util.Arrays;

/*
 * PROBLEM 5 - Intermediate -> Advanced
 * Immutable Booking Receipt & Nightly Settlement
 *
 * BookingReceipt is genuinely immutable: final class, final fields,
 * and seatNumbers is defensively copied both on the way in (constructor)
 * and on the way out (getter). "Changes" produce a brand-new object via
 * a with-style method. GroupBookingReceipt extends it, and the nightly
 * settlement processor uses instanceof to treat groups differently from
 * individual receipts, while never throwing on a null batch entry.
 */
public class Problem5_ImmutableBookingReceiptSettlement {

    // ------------------------------------------------------------------
    // BookingReceipt: immutable base class
    // ------------------------------------------------------------------
    static class BookingReceipt {
        private final String bookingId;
        private final String[] seatNumbers;

        public BookingReceipt(String bookingId, String[] seatNumbers) {
            this.bookingId = bookingId;
            // Defensive copy IN: mutating the caller's array afterward
            // must not affect this receipt.
            this.seatNumbers = (seatNumbers == null)
                    ? new String[0]
                    : Arrays.copyOf(seatNumbers, seatNumbers.length);
        }

        public String getBookingId() {
            return bookingId;
        }

        public String[] getSeatNumbers() {
            // Defensive copy OUT: mutating the returned array must not
            // affect this receipt's internal state.
            return Arrays.copyOf(seatNumbers, seatNumbers.length);
        }

        /**
         * "Wither" method: returns a brand-new BookingReceipt with one
         * seat updated, leaving this receipt untouched.
         */
        public BookingReceipt withUpdatedSeat(int index, String newSeat) {
            String[] updated = Arrays.copyOf(seatNumbers, seatNumbers.length);
            updated[index] = newSeat;
            return new BookingReceipt(bookingId, updated);
        }
    }

    // NOTE: The problem states "the class itself final". Since
    // GroupBookingReceipt below extends BookingReceipt, in a real
    // single-file submission you would make BookingReceipt final and
    // instead have GroupBookingReceipt compose a BookingReceipt
    // internally (favoring composition over inheritance) to satisfy
    // both "final class" and "GroupBookingReceipt extends it"
    // requirements without contradiction. Here, GroupBookingReceipt
    // is modeled as a subclass for clarity of the instanceof dispatch
    // requirement; mark BookingReceipt final only if your assignment
    // does not require GroupBookingReceipt to subclass it directly.

    // ------------------------------------------------------------------
    // GroupBookingReceipt: adds a groupSize on top of BookingReceipt
    // ------------------------------------------------------------------
    static class GroupBookingReceipt extends BookingReceipt {
        private final int groupSize;

        public GroupBookingReceipt(String bookingId, String[] seatNumbers, int groupSize) {
            super(bookingId, seatNumbers);
            this.groupSize = groupSize;
        }

        public int getGroupSize() {
            return groupSize;
        }
    }

    // ------------------------------------------------------------------
    // Nightly settlement processor
    // ------------------------------------------------------------------
    static class SettlementProcessor {

        static String processNightlySettlement(BookingReceipt[] receipts) {
            int processed = 0;
            int nullSkipped = 0;
            int groupCount = 0;
            int individualCount = 0;

            if (receipts != null) {
                for (BookingReceipt receipt : receipts) {
                    if (receipt == null) {
                        nullSkipped++;
                        continue;
                    }
                    processed++;
                    // instanceof-based dispatch: group vs individual
                    if (receipt instanceof GroupBookingReceipt) {
                        groupCount++;
                    } else {
                        individualCount++;
                    }
                }
            }

            return processed + " processed | " + nullSkipped + " null skipped | "
                    + groupCount + " group | " + individualCount + " individual";
        }
    }

    // Expose the static method at the outer level for convenience,
    // matching the required function signature.
    static String processNightlySettlement(BookingReceipt[] receipts) {
        return SettlementProcessor.processNightlySettlement(receipts);
    }

    // ------------------------------------------------------------------
    // Demo / manual test driver
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        // Defensive copy IN test
        BookingReceipt b = new BookingReceipt("CH-1001", new String[]{"A1", "A2"});
        String[] seats = b.getSeatNumbers();
        seats[0] = "X"; // mutate the returned array
        System.out.println(b.getSeatNumbers()[0]);
        // Expected: A1 (unaffected by external mutation)

        // Wither pattern test
        BookingReceipt updated = b.withUpdatedSeat(1, "A3");
        System.out.println(Arrays.toString(b.getSeatNumbers()));
        // Expected: [A1, A2]
        System.out.println(Arrays.toString(updated.getSeatNumbers()));
        // Expected: [A1, A3]

        // Nightly settlement test
        BookingReceipt[] batch = {
                new GroupBookingReceipt("CH-2002", new String[]{"B1", "B2"}, 2),
                null,
                new BookingReceipt("CH-3003", new String[]{"C1"})
        };
        System.out.println(processNightlySettlement(batch));
        // Expected: 2 processed | 1 null skipped | 1 group | 1 individual
    }
}
