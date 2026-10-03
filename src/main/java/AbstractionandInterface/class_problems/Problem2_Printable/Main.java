public class Main {
    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {
        PackageBox p = new PackageBox("TRK-88");
        Invoice i = new Invoice("INV-42");
        printAll(new Printable[]{p, i});
    }
}
