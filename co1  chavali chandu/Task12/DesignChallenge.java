import java.util.Locale;

abstract class NewVehicle {
    private final String regNo;

    NewVehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        this.regNo = regNo.trim().toUpperCase(Locale.ROOT);
    }

    public String getRegNo() {
        return regNo;
    }

    public abstract double calculateFee(int minutes, int entryHour);
}

class EBike extends NewVehicle {
    EBike(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, int entryHour) {
        validateDurationAndHour(minutes, entryHour);
        return 20;
    }

    private void validateDurationAndHour(int minutes, int entryHour) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }
        if (entryHour < 0 || entryHour > 23) {
            throw new IllegalArgumentException(
                    "Entry hour must be between 0 and 23.");
        }
    }
}

class DeliveryVan extends NewVehicle {
    DeliveryVan(String regNo) {
        super(regNo);
    }

    @Override
    public double calculateFee(int minutes, int entryHour) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }
        if (entryHour < 0 || entryHour > 23) {
            throw new IllegalArgumentException(
                    "Entry hour must be between 0 and 23.");
        }
        if (minutes == 0) {
            return 0;
        }

        long hours = (minutes + 59L) / 60L;
        int hourlyRate = entryHour >= 9 && entryHour < 17 ? 60 : 30;
        return hours * hourlyRate;
    }
}

class VisitorPass {
    private final String visitorName;
    private final boolean validForToday;

    VisitorPass(String visitorName, boolean validForToday) {
        if (visitorName == null || visitorName.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A visitor name is required.");
        }
        this.visitorName = visitorName.trim();
        this.validForToday = validForToday;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public boolean isValidForToday() {
        return validForToday;
    }

    public double calculateFee(NewVehicle vehicle,
            int minutes, int entryHour) {
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle details are required.");
        }
        double regularFee = vehicle.calculateFee(minutes, entryHour);
        return validForToday ? 0 : regularFee;
    }
}

class SimpleJaggedParking {
    private final NewVehicle[][] slots;

    SimpleJaggedParking(int[] slotsPerFloor) {
        if (slotsPerFloor == null) {
            throw new IllegalArgumentException(
                    "Slot counts cannot be null.");
        }
        slots = new NewVehicle[slotsPerFloor.length][];
        for (int floor = 0; floor < slotsPerFloor.length; floor++) {
            if (slotsPerFloor[floor] < 0) {
                throw new IllegalArgumentException(
                        "A floor cannot have a negative slot count.");
            }
            slots[floor] = new NewVehicle[slotsPerFloor[floor]];
        }
    }

    public int[] park(NewVehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException(
                    "Vehicle details are required.");
        }
        if (findVehicle(vehicle.getRegNo()) != null) {
            throw new IllegalArgumentException(
                    "This vehicle is already parked.");
        }

        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == null) {
                    slots[floor][slot] = vehicle;
                    return new int[] { floor + 1, slot + 1 };
                }
            }
        }
        return null;
    }

    public boolean release(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        NewVehicle vehicle = findVehicle(regNo.trim());
        if (vehicle == null) {
            return false;
        }
        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == vehicle) {
                    slots[floor][slot] = null;
                    return true;
                }
            }
        }
        return false;
    }

    public int getAvailableSlotCount() {
        int available = 0;
        for (NewVehicle[] floor : slots) {
            for (NewVehicle vehicle : floor) {
                if (vehicle == null) {
                    available++;
                }
            }
        }
        return available;
    }

    public int getOccupiedSlotCount() {
        int occupied = 0;
        for (NewVehicle[] floor : slots) {
            for (NewVehicle vehicle : floor) {
                if (vehicle != null) {
                    occupied++;
                }
            }
        }
        return occupied;
    }

    private NewVehicle findVehicle(String regNo) {
        for (NewVehicle[] floor : slots) {
            for (NewVehicle vehicle : floor) {
                if (vehicle != null
                        && vehicle.getRegNo().equalsIgnoreCase(regNo)) {
                    return vehicle;
                }
            }
        }
        return null;
    }
}

public class DesignChallenge {
    public static void main(String[] args) {
        SimpleJaggedParking parking =
                new SimpleJaggedParking(new int[] { 2, 3, 1, 2, 2 });
        EBike ebike = new EBike("TN10EB1234");
        DeliveryVan van = new DeliveryVan("TN20DV5678");
        EBike anotherBike = new EBike("TN30EB1234");

        int[] ebikeSlot = parking.park(ebike);
        int[] vanSlot = parking.park(van);
        int[] anotherBikeSlot = parking.park(anotherBike);
        System.out.println("E-bike parked at floor " + ebikeSlot[0]
                + ", slot " + ebikeSlot[1]);
        System.out.println("Delivery van parked at floor " + vanSlot[0]
                + ", slot " + vanSlot[1]);
        System.out.println("Next vehicle parked at floor "
                + anotherBikeSlot[0] + ", slot " + anotherBikeSlot[1]);
        System.out.println("Available/occupied slots: "
                + parking.getAvailableSlotCount() + "/"
                + parking.getOccupiedSlotCount());
        System.out.printf(Locale.ROOT, "E-bike flat fee: Rs %.2f%n",
                ebike.calculateFee(90, 10));
        System.out.printf(Locale.ROOT, "Delivery van fee at 10 AM: Rs %.2f%n",
                van.calculateFee(60, 10));

        VisitorPass validPass = new VisitorPass("Mira", true);
        System.out.printf(Locale.ROOT, "Valid one-day visitor pass fee: Rs %.2f%n",
                validPass.calculateFee(van, 60, 11));
        VisitorPass expiredPass = new VisitorPass("Noor", false);
        System.out.printf(Locale.ROOT, "No-pass visitor fee at 5 PM: Rs %.2f%n",
                expiredPass.calculateFee(van, 60, 17));
        System.out.println("Delivery van fee at 8 AM: "
                + van.calculateFee(60, 8));
        System.out.println("Delivery van fee at 9 AM: "
                + van.calculateFee(60, 9));
        System.out.println("Delivery van fee at 4 PM: "
                + van.calculateFee(60, 16));
        System.out.println("Release delivery van: "
                + parking.release(van.getRegNo()));
        System.out.println("Release same vehicle again: "
                + parking.release(van.getRegNo()));
        int[] reusedSlot = parking.park(new EBike("TN60EB1234"));
        System.out.println("Reused slot after release: floor "
                + reusedSlot[0] + ", slot " + reusedSlot[1]);

        SimpleJaggedParking oneSlot = new SimpleJaggedParking(new int[] { 1 });
        System.out.println("One-slot facility full: "
                + (oneSlot.park(new EBike("TN40EB1234")) != null
                        && oneSlot.park(new EBike("TN50EB1234")) == null));
        System.out.println("Zero-minute delivery fee: "
                + van.calculateFee(0, 10));
        try {
            van.calculateFee(60, 24);
        } catch (IllegalArgumentException exception) {
            System.out.println("Invalid entry hour rejected.");
        }
        try {
            van.calculateFee(-1, 10);
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative duration rejected.");
        }
        try {
            new SimpleJaggedParking(new int[] { -1 });
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative floor capacity rejected.");
        }
    }
}
