import java.util.Arrays;
import java.util.Locale;

public class ParkingFacility {
    private final String[][] slots;

    public ParkingFacility(int[] slotsPerFloor) {
        if (slotsPerFloor == null) {
            throw new IllegalArgumentException("Slot counts cannot be null.");
        }

        slots = new String[slotsPerFloor.length][];
        for (int floor = 0; floor < slotsPerFloor.length; floor++) {
            if (slotsPerFloor[floor] < 0) {
                throw new IllegalArgumentException(
                        "A floor cannot have a negative slot count.");
            }
            slots[floor] = new String[slotsPerFloor[floor]];
        }
    }

    public int[] allocate(String vehicleNo) {
        String registration = normalizeRegistration(vehicleNo);
        if (isVehicleParked(registration)) {
            throw new IllegalArgumentException(
                    "This vehicle is already parked.");
        }

        for (int floor = 0; floor < slots.length; floor++) {
            for (int slot = 0; slot < slots[floor].length; slot++) {
                if (slots[floor][slot] == null) {
                    slots[floor][slot] = registration;
                    return new int[] { floor, slot };
                }
            }
        }
        return null;
    }

    public boolean release(int floor, int slot) {
        if (floor < 0 || floor >= slots.length
                || slot < 0 || slot >= slots[floor].length
                || slots[floor][slot] == null) {
            return false;
        }
        slots[floor][slot] = null;
        return true;
    }

    public boolean isOccupied(int floor, int slot) {
        return floor >= 0 && floor < slots.length
                && slot >= 0 && slot < slots[floor].length
                && slots[floor][slot] != null;
    }

    public int getAvailableSlotCount() {
        int available = 0;
        for (String[] floor : slots) {
            for (String vehicleNo : floor) {
                if (vehicleNo == null) {
                    available++;
                }
            }
        }
        return available;
    }

    public int getOccupiedSlotCount() {
        int occupied = 0;
        for (String[] floor : slots) {
            for (String vehicleNo : floor) {
                if (vehicleNo != null) {
                    occupied++;
                }
            }
        }
        return occupied;
    }

    private boolean isVehicleParked(String registration) {
        for (String[] floor : slots) {
            for (String vehicleNo : floor) {
                if (registration.equals(vehicleNo)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String normalizeRegistration(String vehicleNo) {
        if (vehicleNo == null || vehicleNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        return vehicleNo.trim().toUpperCase(Locale.ROOT);
    }

    public static void main(String[] args) {
        ParkingFacility parking = new ParkingFacility(new int[] { 2, 1 });

        System.out.println(Arrays.toString(parking.allocate("TN01AB1111")));
        System.out.println(Arrays.toString(parking.allocate("TN02CD2222")));
        System.out.println(Arrays.toString(parking.allocate("TN03EF3333")));
        System.out.println("Full facility allocation: "
                + Arrays.toString(parking.allocate("TN04GH4444")));
        System.out.println("Available/occupied: "
                + parking.getAvailableSlotCount() + "/"
                + parking.getOccupiedSlotCount());
        System.out.println("Release valid slot: " + parking.release(0, 1));
        System.out.println("Release same slot again: "
                + parking.release(0, 1));
        System.out.println("Release invalid floor: "
                + parking.release(5, 0));
        System.out.println("Reallocated slot: "
                + Arrays.toString(parking.allocate("TN04GH4444")));

        try {
            parking.allocate("tn04gh4444");
        } catch (IllegalArgumentException exception) {
            System.out.println("Duplicate registration rejected.");
        }
        try {
            parking.allocate("  ");
        } catch (IllegalArgumentException exception) {
            System.out.println("Blank registration rejected.");
        }
        try {
            new ParkingFacility(new int[] { 2, -1 });
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative floor capacity rejected.");
        }
    }
}
