/*
 * PROBLEM 1 - Basic
 * Movie Ticket Field Visibility Checker
 *
 * CineHub's booking engine has a MovieTicket class with fields at every
 * access level. AccessChecker classifies a single access attempt as
 * ALLOWED or DENIED purely from Java's real access rules for
 * SAME_CLASS, SAME_PACKAGE, and DIFFERENT_PACKAGE.
 */
public class Problem1_MovieTicketFieldVisibilityChecker {

    // ------------------------------------------------------------------
    // MovieTicket: one field at each access level, chosen by who
    // genuinely needs to reach it.
    // ------------------------------------------------------------------
    static class MovieTicket {
        // seatNumber: internal booking detail, only this class should
        // ever touch it directly -> private
        private String seatNumber;

        // screenId: used by other booking classes in the same package
        // (e.g. a Screen scheduler) but not outside it -> default (package-private)
        String screenId;

        // ticketPrice: needs to be read/adjusted by subclasses such as
        // PremiumMovieTicket, even from another package -> protected
        protected double ticketPrice;

        // movieTitle: safe to expose to anyone, e.g. UI/receipts -> public
        public String movieTitle;

        public MovieTicket(String seatNumber, String screenId,
                            double ticketPrice, String movieTitle) {
            this.seatNumber = seatNumber;
            this.screenId = screenId;
            this.ticketPrice = ticketPrice;
            this.movieTitle = movieTitle;
        }
    }

    // ------------------------------------------------------------------
    // AccessChecker
    // ------------------------------------------------------------------
    static class AccessChecker {

        /**
         * Classifies whether a field with the given modifier can be
         * accessed from the given context, per real Java access rules.
         */
        static String classifyAccess(String fieldModifier, String accessorContext) {
            boolean allowed;

            switch (fieldModifier) {
                case "private":
                    // Only reachable from inside the same class.
                    allowed = accessorContext.equals("SAME_CLASS");
                    break;

                case "default":
                    // Reachable from the same class or same package only.
                    allowed = accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE");
                    break;

                case "protected":
                    // Same class, same package always allowed.
                    // Different package only allowed via subclass access
                    // (handled fully in Problem 2); from a plain
                    // different-package context it's denied.
                    allowed = accessorContext.equals("SAME_CLASS")
                            || accessorContext.equals("SAME_PACKAGE");
                    break;

                case "public":
                    // Reachable from anywhere.
                    allowed = true;
                    break;

                default:
                    allowed = false;
            }

            return allowed ? "ALLOWED" : "DENIED";
        }

        /**
         * Runs classifyAccess over a batch of {fieldModifier, accessorContext}
         * pairs and summarizes the results.
         */
        static String summarizeBatch(String[][] attempts) {
            int allowedCount = 0;
            int deniedCount = 0;

            for (String[] attempt : attempts) {
                String result = classifyAccess(attempt[0], attempt[1]);
                if (result.equals("ALLOWED")) {
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
        System.out.println(AccessChecker.classifyAccess("private", "SAME_CLASS"));
        // Expected: ALLOWED

        System.out.println(AccessChecker.classifyAccess("protected", "DIFFERENT_PACKAGE"));
        // Expected: DENIED

        String[][] batch = {
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(AccessChecker.summarizeBatch(batch));
        // Expected: Allowed: 2 | Denied: 1
    }
}
