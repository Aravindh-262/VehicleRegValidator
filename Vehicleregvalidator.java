import java.util.*;
import java.util.regex.*;
import java.time.LocalDate;

public class VehicleRegValidator {
    
    public static void main(String[] args) {
        // Collect vehicle data
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter vehicle registration number: ");
        String regNumber = scanner.nextLine();
        
        System.out.println("Enter vehicle type (Car/Truck): ");
        String vehicleType = scanner.nextLine();
        
        System.out.println("Enter owner's name: ");
        String ownerName = scanner.nextLine();
        
        // Create owner object
        VehicleOwner owner = new VehicleOwner(ownerName, "123 Street Name", "123-456-7890");
        
        // Create vehicle object
        Vehicle vehicle = VehicleFactory.createVehicle(regNumber, vehicleType);
        
        if (vehicle.validateRegistration()) {
            System.out.println("Registration is valid.");
            
            // Vehicle with owner example
            VehicleWithOwner vehicleWithOwner = new VehicleWithOwner(regNumber, vehicleType, owner);
            System.out.println("Owner: " + vehicleWithOwner.getOwner().getOwnerName());
            
            // Expiry check (assume expiry date for demo)
            LocalDate expiryDate = LocalDate.of(2023, 12, 22);
            if (RegistrationExpiryChecker.isRegistrationExpired(expiryDate)) {
                System.out.println("Registration has expired.");
            } else {
                System.out.println("Registration is valid.");
            }
        } else {
            System.out.println("Invalid registration number.");
        }
    }

    // Basic Validation: Checks if the registration matches a simple pattern
    public static boolean isValid(String regNumber) {
        if (regNumber == null || regNumber.isEmpty()) {
            return false;
        }
        
        // Example pattern for a registration (might differ based on the country)
        String pattern = "^[A-Z]{2}\\d{2}\\s?[A-Z]{2}\\d{3}$";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(regNumber);

        return m.matches();
    }
}

abstract class Vehicle {
    protected String regNumber;
    protected String vehicleType;
    
    public Vehicle(String regNumber, String vehicleType) {
        this.regNumber = regNumber;
        this.vehicleType = vehicleType;
    }

    public abstract boolean validateRegistration();
}

class Car extends Vehicle {
    public Car(String regNumber) {
        super(regNumber, "Car");
    }

    @Override
    public boolean validateRegistration() {
        // Example for car specific registration validation
        return regNumber.matches("^[A-Z]{2}\\d{2}\\s?[A-Z]{2}\\d{3}$");
    }
}

class Truck extends Vehicle {
    public Truck(String regNumber) {
        super(regNumber, "Truck");
    }

    @Override
    public boolean validateRegistration() {
        // Example for truck specific registration validation
        return regNumber.matches("^[A-Z]{2}\\d{2}\\s?[A-Z]{3}\\d{2}$");
    }
}

class VehicleFactory {
    public static Vehicle createVehicle(String regNumber, String vehicleType) {
        if (vehicleType.equalsIgnoreCase("Car")) {
            return new Car(regNumber);
        } else if (vehicleType.equalsIgnoreCase("Truck")) {
            return new Truck(regNumber);
        } else {
            throw new IllegalArgumentException("Unsupported vehicle type");
        }
    }
}

class VehicleOwner {
    private String ownerName;
    private String address;
    private String contactNumber;
    
    public VehicleOwner(String ownerName, String address, String contactNumber) {
        this.ownerName = ownerName;
        this.address = address;
        this.contactNumber = contactNumber;
    }

    // Getters
    public String getOwnerName() {
        return ownerName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}

class VehicleWithOwner extends Vehicle {
    private VehicleOwner owner;

    public VehicleWithOwner(String regNumber, String vehicleType, VehicleOwner owner) {
        super(regNumber, vehicleType);
        this.owner = owner;
    }

    @Override
    public boolean validateRegistration() {
        // Include owner details validation (example)
        return regNumber.matches("^[A-Z]{2}\\d{2}\\s?[A-Z]{2}\\d{3}$");
    }

    public VehicleOwner getOwner() {
        return owner;
    }
}

class RegistrationExpiryChecker {
    public static boolean isRegistrationExpired(LocalDate expiryDate) {
        return LocalDate.now().isAfter(expiryDate);
    }
}
