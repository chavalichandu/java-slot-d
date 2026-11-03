import java.util.Locale;

interface Chargeable {
    double chargingFee(int kWh);
}

abstract class Vehicle {
    private final String regNo;

    protected Vehicle(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "A vehicle registration number is required.");
        }
        this.regNo = regNo.trim().toUpperCase(Locale.ROOT);
    }

    public String getRegNo() {
        return regNo;
    }

    protected abstract double ratePerHour();

    public double calculateFee(int minutes) {
        if (minutes < 0) {
            throw new IllegalArgumentException(
                    "Parking duration cannot be negative.");
        }
        if (minutes == 0) {
            return 0;
        }
        long hours = (minutes + 59L) / 60L;
        return hours * ratePerHour();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vehicle)) {
            return false;
        }
        Vehicle vehicle = (Vehicle) other;
        return regNo.equals(vehicle.regNo);
    }

    @Override
    public int hashCode() {
        return regNo.hashCode();
    }
}

class TwoWheeler extends Vehicle {
    TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 10;
    }
}

class Car extends Vehicle {
    Car(String regNo) {
        super(regNo);
    }

    @Override
    protected double ratePerHour() {
        return 30;
    }
}

class ElectricCar extends Car implements Chargeable {
    ElectricCar(String regNo) {
        super(regNo);
    }

    @Override
    public double chargingFee(int kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException(
                    "Charging energy cannot be negative.");
        }
        return kWh * 12.0;
    }
}

public class FeeDemo {
    public static void main(String[] args) {
        Vehicle[] parkedVehicles = {
            new TwoWheeler("TN01A1"),
            new Car("TN02B2"),
            new ElectricCar("TN03C3")
        };

        for (Vehicle vehicle : parkedVehicles) {
            double fee = vehicle.calculateFee(130);
            if (vehicle instanceof Chargeable) {
                fee += ((Chargeable) vehicle).chargingFee(10);
            }
            System.out.printf(Locale.ROOT, "%s %s -> Rs %.2f%n",
                    vehicle.getClass().getSimpleName(),
                    vehicle.getRegNo(), fee);
        }

        System.out.println("Same registration compares equal: "
                + new Car("TN02B2").equals(
                        new ElectricCar("TN02B2")));
        System.out.println("Zero-minute fee: "
                + new TwoWheeler("TN04D4").calculateFee(0));
        System.out.println("59-minute fee: "
                + new TwoWheeler("TN06F6").calculateFee(59));
        System.out.println("60-minute fee: "
                + new TwoWheeler("TN07G7").calculateFee(60));

        try {
            new Car("TN05E5").calculateFee(-1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative duration rejected.");
        }
        try {
            ((Chargeable) parkedVehicles[2]).chargingFee(-1);
        } catch (IllegalArgumentException exception) {
            System.out.println("Negative charging energy rejected.");
        }
        System.out.println("Maximum int duration fee: "
                + new TwoWheeler("TN08H8").calculateFee(Integer.MAX_VALUE));
    }
}
