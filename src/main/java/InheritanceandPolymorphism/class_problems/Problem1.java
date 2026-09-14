public class Problem1 {

    static class LibraryMember {
        protected String memberId;
        protected int borrowLimit;
        protected int booksBorrowed;

        public LibraryMember(String memberId, int borrowLimit) {
            if (memberId == null || memberId.trim().length() < 4) {
                throw new IllegalArgumentException("Invalid memberId: " + memberId);
            }
            this.memberId = memberId;
            this.borrowLimit = borrowLimit;
            this.booksBorrowed = 0;
        }

        void borrowBook() {
            if (booksBorrowed < borrowLimit) {
                booksBorrowed++;
            }
        }

        int getBooksBorrowed() {
            return booksBorrowed;
        }

        static String enrollBatch(String[] memberIds, int borrowLimit) {
            int enrolled = 0;
            int rejected = 0;
            for (String id : memberIds) {
                try {
                    new LibraryMember(id, borrowLimit);
                    enrolled++;
                } catch (IllegalArgumentException e) {
                    rejected++;
                }
            }
            return "Enrolled: " + enrolled + " | Rejected: " + rejected;
        }
    }

    static class StudentMember extends LibraryMember {
        private String course;

        public StudentMember(String memberId, int borrowLimit, String course) {
            super(memberId, borrowLimit);
            this.course = course;
        }
    }

    public static void main(String[] args) {
        try {
            new LibraryMember("LB1", 3);
            System.out.println("construction succeeded");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        StudentMember s = new StudentMember("STU10", 3, "CSE");
        s.borrowBook();
        s.borrowBook();
        System.out.println(s.getBooksBorrowed());

        System.out.println(LibraryMember.enrollBatch(
                new String[]{"STU1", "LB1", "STU2", " ", "STU3"}, 3));
    }
}
