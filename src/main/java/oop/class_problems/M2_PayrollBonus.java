public class M2_PayrollBonus {

    static class Employee {
        private String empId;
        private double salary;

        public Employee(String empId, double salary) {
            this.empId = empId;   // resolves constructor param/field clash
            this.salary = salary;
        }

        void raiseSalary(double salary) {
            this.salary = this.salary + salary; // resolves method param/field clash
        }

        void printFinalSalary() {
            System.out.println(empId + " | Final Salary: Rs " + this.salary);
        }
    }

    public static void main(String[] args) {
        String[] empIds = {"E-101", "E-102", "E-103", "E-104"};
        double[] salaries = {40000, 55000, 62000, 48000};
        double bonus = 5000;

        Employee[] employees = new Employee[empIds.length];
        for (int i = 0; i < empIds.length; i++) {
            employees[i] = new Employee(empIds[i], salaries[i]);
        }

        for (Employee emp : employees) {
            emp.raiseSalary(bonus);
            emp.printFinalSalary();
        }
    }
}