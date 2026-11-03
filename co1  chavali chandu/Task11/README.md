# Task 11 — Integrated Smart Parking System

**Aim:** Combine vehicle/user registration, ticket generation, slot
availability, vehicle exit and fee calculation.

`SmartParkingSystem.java` uses a fixed slot array, one active ticket per
occupied slot and a shared ticket counter. Exit is looked up by registration
number and releases the slot. The recorded sample policy charges two-wheelers
Rs 10/hour and cars Rs 30/hour, rounding up started hours. A student or staff
member with a valid monthly pass pays Rs 0; a visitor pays the calculated
vehicle fee.

```text
javac SmartParkingSystem.java
java SmartParkingSystem
```

The demonstration covers capacity, entry/exit, availability, monthly pass,
visitor fee, absent-vehicle exit and invalid duration. See Task 11 in
`../Assignment_Report.md` for the test plan and limitations.
