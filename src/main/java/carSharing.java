public class carSharing {

    public static void main(String[] args) {

        String dbName = "carsharing.db";

        if (args.length > 0 && args[0].equals("-databaseFileName")) {
            dbName = args[1];
        }
        Controller gui = new Controller(dbName);
        gui.run();
    }
}