public class M4_CollegeSetup {

    static class SrmStudent {
        private String name;
        private static String collegeName;
        private static int academicYear;

        // Runs exactly once, no matter how many students are created
        static {
            collegeName = "SRM Institute of Science and Technology";
            academicYear = 2026;
            System.out.println("College info loaded");
        }

        public SrmStudent(String name) {
            this.name = name;
            System.out.println("Student record created: " + name);
        }
    }

    public static void main(String[] args) {
        String[] names = {"Ravi", "Meera", "Karthik", "Divya", "Anitha"};

        SrmStudent[] students = new SrmStudent[names.length];
        for (int i = 0; i < names.length; i++) {
            students[i] = new SrmStudent(names[i]);
        }
    }
}