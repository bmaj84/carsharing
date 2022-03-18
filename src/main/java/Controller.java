import java.util.List;
import java.util.Scanner;

public class Controller {

    private final CompanyService service;
    private final CarService carService;
    private final CustomerService customerService;

    public Controller(String dbName) {
        this.service = new CompanyService(dbName);
        this.carService = new CarService(dbName);
        this.customerService = new CustomerService(dbName);
    }

    Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            printLoginMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1" :
                    mainMenu();
                    break;
                case "2" :
                    customerMenu();
                    break;
                case "3" :
                    createCustomer();
                    break;
                case "0" :
                    System.exit(9);
                default:
                    break;
            }

        }
    }
    private void mainMenu() {
        while (true) {
            printMainMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    return;
                case "1":
                    companyList();
                    break;
                case "2":
                    createCompany();
                    break;
                default:
                    break;

            }
        }
    }
    private void carMenu(int companyId) {

        while(true) {
            printCarMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1":
                    carList(companyId);
                    break;
                case "2":
                    addCar(companyId);
                    break;
                case "0":
                    return;
            }
        }
    }
    private void chooseCustomer(int customerId) {
        while (true) {
            printCustomerMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    return;
                case "1":
                    rentCar(customerId);
                    break;
                case "2":
                    returnCar(customerId);
                    break;
                case "3":
                    myRentedCar(customerId);
                    break;
            }
        }
    }

    private void customerMenu() {
        List<Customer> allCustomer = customerService.getAllCustomer();
        if (allCustomer.size() ==0) {
            System.out.println("The customer list is empty!");
            return;
        } else {
            System.out.println("Customer list:");
            for (int i = 0; i < allCustomer.size(); i++) {
                System.out.println(i + 1 +". " + allCustomer.get(i).getName());
            }
            System.out.println("0. Back\n");
        }
        String option = scanner.nextLine();

        if (!option.equals("0") ) {
            int customerId = allCustomer.get(Integer.parseInt(option)-1).getId();
            chooseCustomer(customerId);
        }
    }

    private void returnCar(int customerId) {
        if (!checkCustomerCar(customerId)) {
            System.out.println("You didn't rent a car!");
        } else{
            customerService.returnCar(customerId);
            System.out.println("You've returned a rented car!");
        }
    }

    private void myRentedCar(int customerId) {
        List<Company> companies = service.getAllCompany();
        List<Car> cars = customerService.getRentedCar(customerId);
        if (cars.size() == 0) {
            System.out.println("You didn't rent a car!");
        } else {
            String carName = cars.get(0).getName();
            String companyName = "";
            for (Company a: companies) {
                if (a.getId() == (cars.get(0).getCompany_id())) {
                    companyName = a.getName();
                }
            }
            System.out.println("\nYour rented car:\n" + carName + "\nCompany:\n" + companyName);
        }
    }

    private void rentCar(int customerId) {
        if (checkCustomerCar(customerId)) {
            System.out.println("You've already rented a car!");
        } else {
            List<Company> allCompany = service.getAllCompany();
            if (allCompany.size() == 0) {
                System.out.println("\nThe company list is empty!");
                return;
            } else {
                System.out.println("\nChoose the company:");
                for (int i = 0; i < allCompany.size(); i++) {
                    System.out.println(i + 1 + ". " + allCompany.get(i).getName());
                }
                System.out.println("0. Back");
                String option = scanner.nextLine();
                if (!option.equals("0")) {
                    choseCar(option, customerId);
                } else return;
            }
        }
    }

    private boolean checkCustomerCar(int customerId) {
        List <Customer> customers = customerService.checkCustomerCar(customerId);
        if (customers.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    private void choseCar(String option, int customerId) {
        List<Company> companies = service.getAllCompany();
        String name = companies.get(Integer.parseInt(option)-1).getName();
        int companyID = companies.get(Integer.parseInt(option)-1).getId();
        List<Car> cars = carService.getAllFreeCars(companyID);
        if (cars.size() ==0) {
            System.out.println("No available cars in the '" + name + "' company");
            return;
        } else {
            System.out.println("\nChoose a car:");
            for (int i = 0; i < cars.size(); i++) {
                System.out.println(i+1 + ". " + cars.get(i).getName());
            }
            System.out.println("0. Back");
        }
        String chooseCar = scanner.nextLine();
        if (chooseCar.equals("0")) {
            return;
        } else {
            int carId = cars.get(Integer.parseInt(chooseCar) - 1).getId();
            String carName = cars.get(Integer.parseInt(chooseCar)-1).getName();
            customerService.updateCarId(carId, customerId);
            System.out.println("You rented '" + carName + "'");
        }
    }

    private void createCustomer() {
        System.out.println("Enter the customer name:");
        String name = scanner.nextLine();
        customerService.addCustomer(name);
    }

    private void carList(int companyId) {
        List<Car> carList = carService.getAllCars(companyId);
        if (carList.size() == 0) {
            System.out.println("The car list is empty!");
            return;
        } else
            System.out.println("Car list:");
        for (int i = 0; i <carList.size();i ++){
            System.out.printf("%d. %s%n", i+1, carList.get(i).getName());
        }
    }
    private void createCompany() {
        System.out.println("\nEnter the company name");
        String name = scanner.nextLine();
        service.addCompany(name);
    }

    private void companyList() {
        List<Company> allCompany = service.getAllCompany();
        if (allCompany.size() == 0) {
            System.out.println("\nThe company list is empty!");
            return;
        } else {
            System.out.println("\nChoose the company:");
            for (int i = 0; i<allCompany.size(); i++) {
                System.out.println(i+1 + ". " + allCompany.get(i).getName());
            }
            System.out.println("0. Back");
            String option = scanner.nextLine();
            if ( !option.equals("0")) {
                choseCompany(option);
            } else return;
        }
    }

    private void choseCompany(String option) {

        String companyName;
        int companyId;
        List<Company> allCompany = service.getAllCompany();
        companyName = allCompany.get(Integer.parseInt(option)-1).getName();
        companyId = allCompany.get(Integer.parseInt(option)-1).getId();
        System.out.println("'" + companyName + "' company");
        carMenu(companyId);
    }

    private void addCar(int id) {
        System.out.println("Enter the car name:");
        String name = scanner.nextLine();
        carService.addCar(name, id);
    }

    private void printCarMenu() {
        System.out.println("\n1. Car list\n2. Create a car\n0. Back");

    }

    private void printLoginMenu() {
        System.out.println("1. Log in as a manager\n2. Log in as a customer\n3. Create a customer\n0. Exit");
    }

    private void printCustomerMenu() {
        System.out.println("1. Rent a car\n2. Return a rented car\n3. My rented car\n0. Back");
    }
    private void printMainMenu() {
        System.out.println("1. Company list\n2. Create a company\n0. Back");
    }
}