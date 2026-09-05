/*
 * PROBLEM 4 - Intermediate
 * MovieBookingProfile JavaBean & OTP Property
 *
 * A fully JavaBean-compliant class: public no-arg constructor, a
 * convenience constructor chained via this(...), getX()/setX() (and
 * isX() for booleans) for ordinary properties, plus a true write-only
 * otp property - settable but never retrievable in any form.
 */
public class Problem4_MovieBookingProfileJavaBean {

    static class MovieBookingProfile {
        private String name;
        private boolean confirmed;

        // otp is intentionally write-only: no getter, no field exposed,
        // no toString()/equals() that would leak it either.
        private String otp;

        /** Public no-argument constructor, required for JavaBean compliance. */
        public MovieBookingProfile() {
        }

        /** Convenience constructor: chains to the no-arg constructor. */
        public MovieBookingProfile(String name) {
            this();
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        // Boolean property uses isX(), not getX(), per JavaBean convention.
        public boolean isConfirmed() {
            return confirmed;
        }

        public void setConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
        }

        /**
         * Sets the OTP. There is deliberately no corresponding getter
         * anywhere on this class - once set, it cannot be read back.
         */
        public void setOtp(String otp) {
            this.otp = otp;
        }

        // Note: no getOtp(), no public accessor, and toString() below
        // deliberately omits otp so it can't leak through logging either.
        @Override
        public String toString() {
            return "MovieBookingProfile{name='" + name + "', confirmed=" + confirmed + "}";
        }
    }

    // ------------------------------------------------------------------
    // Demo / manual test driver
    // ------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println(new MovieBookingProfile("Rahul Dev").getName());
        // Expected: Rahul Dev

        MovieBookingProfile p = new MovieBookingProfile("Rahul Dev");
        p.setConfirmed(true);
        System.out.println(p.isConfirmed());
        // Expected: true

        p.setOtp("4471");
        // No method anywhere on the class can retrieve this value again.
        System.out.println(p);
        // Expected: MovieBookingProfile{name='Rahul Dev', confirmed=true}
    }
}
