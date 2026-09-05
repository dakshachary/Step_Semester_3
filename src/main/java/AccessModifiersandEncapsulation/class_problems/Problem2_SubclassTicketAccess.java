/*
 * PROBLEM 2 - Basic -> Intermediate
 * Subclass Ticket Access
 *
 * Extends AccessChecker to correctly handle the cross-package protected
 * access rule when inheritance is involved:
 *   - SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE: accessing the protected member
 *     through a variable declared as the SUBCLASS's own type -> allowed
 *     for protected.
 *   - SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE: accessing it through a
 *     variable declared as the PARENT class's type -> denied, because
 *     Java's cross-package protected rule goes by the declared
 *     (compile-time) type of the reference, not the runtime object.
 */
public class Problem2_SubclassTicketAccess {

    // ------------------------------------------------------------------
    // MovieTicket (parent, imagine this lives in package com.cinehub.core)
    // ------------------------------------------------------------------
    static class MovieTicket {
        private String seatNumber;
        String screenId;
        protected double ticketPrice;
        public String movieTitle;

        public MovieTicket(double ticketPrice) {
            this.ticketPrice = ticketPrice;
        }
    }

    // ------------------------------------------------------------------
    // PremiumMovieTicket (subclass, imagine this lives in a different
    // package, e.g. com.cinehub.loyalty, and reads the inherited
    // protected ticketPrice)
    // ------------------------------------------------------------------
    static class PremiumMovieTicket extends MovieTicket {
        public PremiumMovieTicket(double ticketPrice) {
            super(ticketPrice);
        }

        double readOwnTypePrice() {
            // Accessing protected ticketPrice through 'this' / own type
            // reference -> ALLOWED.
            PremiumMovieTicket self = this;
            return self.ticketPrice;
        }

        double readParentTypePrice(MovieTicket parentRef) {
            // Accessing protected ticketPrice through a MovieTicket-typed
            // reference, even though the object is really a
            // PremiumMovieTicket -> DENIED by Java's cross-package
            // protected rule (would not compile in real Java from a
            // truly different package). Represented here conceptually.
            // return parentRef.ticketPrice; // <-- would NOT compile
            return -1; // placeholder to represent "denied" in this simulation
        }
    }

    // ------------------------------------------------------------------
    // AccessChecker (extended to 5 contexts)
    // ------------------------------------------------------------------
    static class AccessChecker {

        static String classifyAccess(String fieldModifier, String accessorContext) {
            boolean allowed;

            switch (fieldModifier) {
                case "private":
                    allowed = accessorContext.equals("SAME_CLASS");
                    break;

                case "default":
                    allowed = accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE");
                    break;

                case "protected":
                    allowed = accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE")
                            // Cross-package protected access is allowed
                            // ONLY when reached through a reference typed
                            // as the subclass itself...
                            || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE");
                            // ...but NOT when reached through a reference
                            // typed as the parent class, even inside the
                            // subclass's own code.
                    break;

                case "public":
                    allowed = true;
                    break;

                default:
                    allowed = false;
            }

            return allowed ? "ALLOWED" : "DENIED";
        }

        static String summarizeBatch(String[][] attempts) {
            int allowedCount = 0;
            int deniedCount = 0;

            for (String[] attempt : attempts) {
                if (classifyAccess(attempt[0], attempt[1]).equals("ALLOWED")) {
                    allowedCount++;
                } else {
                    deniedCount++;
                }
            }

            return "Allowed: " + allowedCount + " | Denied: " + deniedCount;
        }
    }

    // ------------------------------------------------------------------
    // Demo / manual test driver
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(AccessChecker.classifyAccess(
                "protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        // Expected: ALLOWED

        System.out.println(AccessChecker.classifyAccess(
                "protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        // Expected: DENIED

        // private and default stay DENIED for both new contexts too
        System.out.println(AccessChecker.classifyAccess(
                "private", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        // Expected: DENIED
        System.out.println(AccessChecker.classifyAccess(
                "default", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));
        // Expected: DENIED
    }
}
