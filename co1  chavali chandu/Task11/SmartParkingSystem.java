import java.util.Locale;

enum UserCategory {
    STUDENT,
    STAFF,
    VISITOR
}

enum VehicleCategory {
    TWO_WHEELER,
    CAR
}

class ParkingUser {
    private final String name;
    private final UserCategory category;
    private final boolean validMonthlyPass;

    ParkingUser(String name, UserCategory category,
            boolean validMonthlyPass) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A user name is required.");
        }
        if (category == null) {
            throw new IllegalArgumentException("A user category is required.");
        }
        if (validMonthlyPass && category == UserCategory.VISITOR) {
            throw new IllegalArgumentException(
                    "Monthly passes are for students or staff.");
        }
        this.name = name.trim();
        this.category = category;
        this.validMonthlyPass = validMonthlyPass;
    }

    public String getName() {
        return name;
    }

    public UserCategory getCategory() {
        return category;
    }

    public boolean hasValidMonthlyPass() {
        return validMonthlyPass;
    }
}

class ParkingVehicle {
    private final String regNo;
    private final VehicleCategory category;

    ParkingVehicle(String regNo, VehicleCategory category) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        if (category == null) {
            throw new IllegalArgumentException(
                    "A vehicle category is required.");
        }
        this.regNo = regNo.trim().toUpperCase(Locale.ROOT);
        this.category = category;
    }

    public String getRegNo() {
        return regNo;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }
        if (minutes == 0) {
            return 0;
        }
        long hours = (minutes + 59L) / 60L;
        double hourlyRate = category == VehicleCategory.TWO_WHEELER
                ? 10.0 : 30.0;
        return hours * hourlyRate;
    }
}

class ParkingTicket {
    private static long nextNumber = 1;

    private final long ticketNumber;
    private final ParkingVehicle vehicle;
    private final ParkingUser user;
    private final int slotNumber;
    private double finalFee;
    private boolean active;

    ParkingTicket(ParkingVehicle vehicle, ParkingUser user, int slotNumber) {
        this.ticketNumber = nextTicketNumber();
        this.vehicle = vehicle;
        this.user = user;
        this.slotNumber = slotNumber;
        this.active = true;
    }

    private static synchronized long nextTicketNumber() {
        return nextNumber++;
    }

    public long getTicketNumber() {
        return ticketNumber;
    }

    public ParkingVehicle getVehicle() {
        return vehicle;
    }

    public ParkingUser getUser() {
        return user;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public boolean isActive() {
        return active;
    }

    public double getFinalFee() {
        if (active) {
            throw new IllegalStateException(
                    "The final fee is available after vehicle exit.");
        }
        return finalFee;
    }

    void completeExit(int durationMinutes) {
        if (!active) {
            throw new IllegalStateException("Ticket is already closed.");
        }
        if (durationMinutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }
        finalFee = user.hasValidMonthlyPass()
                ? 0 : vehicle.calculateFee(durationMinutes);
        active = false;
    }
}

class ParkingFacilitySimple {
    private final ParkingTicket[] tickets;

    ParkingFacilitySimple(int numberOfSlots) {
        if (numberOfSlots < 1) {
            throw new IllegalArgumentException(
                    "The facility must have at least one slot.");
        }
        tickets = new ParkingTicket[numberOfSlots];
    }

    public ParkingTicket park(ParkingVehicle vehicle, ParkingUser user) {
        if (vehicle == null || user == null) {
            throw new IllegalArgumentException(
                    "Vehicle and user details are required.");
        }
        if (findTicket(vehicle.getRegNo()) != null) {
            throw new IllegalArgumentException(
                    "This vehicle is already parked.");
        }

        for (int index = 0; index < tickets.length; index++) {
            if (tickets[index] == null) {
                tickets[index] = new ParkingTicket(
                        vehicle, user, index + 1);
                return tickets[index];
            }
        }
        return null;
    }

    public ParkingTicket exit(String regNo, int durationMinutes) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        if (durationMinutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }

        ParkingTicket ticket = findTicket(regNo.trim());
        if (ticket == null) {
            return null;
        }
        ticket.completeExit(durationMinutes);
        tickets[ticket.getSlotNumber() - 1] = null;
        return ticket;
    }

    public int getAvailableSlotCount() {
        int available = 0;
        for (ParkingTicket ticket : tickets) {
            if (ticket == null) {
                available++;
            }
        }
        return available;
    }

    public int getOccupiedSlotCount() {
        return tickets.length - getAvailableSlotCount();
    }

    public boolean isSlotOccupied(int slotNumber) {
        return slotNumber >= 1 && slotNumber <= tickets.length
                && tickets[slotNumber - 1] != null;
    }

    private ParkingTicket findTicket(String regNo) {
        for (ParkingTicket ticket : tickets) {
            if (ticket != null && ticket.getVehicle().getRegNo()
                    .equalsIgnoreCase(regNo)) {
                return ticket;
            }
        }
        return null;
    }
}

public class SmartParkingSystem {
    public static void main(String[] args) {
        ParkingFacilitySimple facility = new ParkingFacilitySimple(2);
        ParkingUser student = new ParkingUser(
                "Asha", UserCategory.STUDENT, true);
        ParkingUser visitor = new ParkingUser(
                "Ravi", UserCategory.VISITOR, false);

        ParkingTicket bikeTicket = facility.park(
                new ParkingVehicle("TN01AB1111",
                        VehicleCategory.TWO_WHEELER), student);
        ParkingTicket carTicket = facility.park(
                new ParkingVehicle("TN02CD2222",
                        VehicleCategory.CAR), visitor);
        try {
            facility.park(new ParkingVehicle("tn01ab1111",
                    VehicleCategory.TWO_WHEELER), visitor);
        } catch (IllegalArgumentException exception) {
            System.out.println("Duplicate active registration rejected.");
        }
        ParkingTicket fullFacilityTicket = facility.park(
                new ParkingVehicle("TN03EF3333",
                        VehicleCategory.CAR), visitor);

        System.out.println("Bike ticket: " + bikeTicket.getTicketNumber()
                + ", vehicle: " + bikeTicket.getVehicle().getRegNo()
                + ", slot: " + bikeTicket.getSlotNumber());
        System.out.println("Car ticket: " + carTicket.getTicketNumber()
                + ", vehicle: " + carTicket.getVehicle().getRegNo()
                + ", slot: " + carTicket.getSlotNumber());
        System.out.println("Third vehicle allocated: "
                + (fullFacilityTicket != null));
        System.out.println("Available/occupied slots: "
                + facility.getAvailableSlotCount() + "/"
                + facility.getOccupiedSlotCount());

        ParkingTicket bikeReceipt = facility.exit("TN01AB1111", 61);
        ParkingTicket carReceipt = facility.exit("TN02CD2222", 60);
        System.out.printf(Locale.ROOT, "Student pass fee (61 minutes): Rs %.2f%n",
                bikeReceipt.getFinalFee());
        System.out.printf(Locale.ROOT, "Visitor fee (60 minutes): Rs %.2f%n",
                carReceipt.getFinalFee());
        ParkingFacilitySimple staffFacility = new ParkingFacilitySimple(1);
        ParkingTicket staffTicket = staffFacility.park(
                new ParkingVehicle("TN04GH4444",
                        VehicleCategory.CAR),
                new ParkingUser("Dev", UserCategory.STAFF, true));
        ParkingTicket staffReceipt = staffFacility.exit("TN04GH4444", 0);
        System.out.printf(Locale.ROOT, "Staff pass fee (zero minutes): Rs %.2f%n",
                staffReceipt.getFinalFee());
        ParkingTicket boundaryTicket = facility.park(
                new ParkingVehicle("TN05IJ5555",
                        VehicleCategory.TWO_WHEELER), visitor);
        System.out.printf(Locale.ROOT, "Visitor fee (59 minutes): Rs %.2f%n",
                facility.exit("TN05IJ5555", 59).getFinalFee());
        double totalCollection = bikeReceipt.getFinalFee()
                + carReceipt.getFinalFee() + staffReceipt.getFinalFee()
                + boundaryTicket.getFinalFee();
        System.out.printf(Locale.ROOT, "Total collection: Rs %.2f%n",
                totalCollection);
        System.out.println("Available/occupied after exit: "
                + facility.getAvailableSlotCount() + "/"
                + facility.getOccupiedSlotCount());
        System.out.println("Exit for absent vehicle: "
                + (facility.exit("TN99ZZ9999", 30) != null));
        System.out.println("Staff and boundary ticket numbers: "
                + staffTicket.getTicketNumber() + " and "
                + boundaryTicket.getTicketNumber());

        try {
            facility.exit("TN02CD2222", -1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative parking duration rejected.");
        }
        try {
            new ParkingFacilitySimple(0);
        } catch (IllegalArgumentException exception) {
            System.out.println("Zero-slot facility rejected.");
        }
    }
}
