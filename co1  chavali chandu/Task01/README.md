# Task 1 — Understand the Problem

**Aim:** Identify the system inputs, outputs, business rules and candidate
objects before selecting an implementation.

**Analysis:** A vehicle has a registration and category; a parking slot has a
floor, number and occupancy state; a ticket associates a vehicle and user with
an active slot; and the facility allocates and releases slots. Students and
staff with a valid monthly pass pay no parking fee. Visitors pay the applicable
parking fee.

**Design result:** Separate responsibilities into Vehicle, ParkingSlot,
ParkingTicket, ParkingFacility and User. Keep registration numbers and ticket
numbers unique among active or issued records, and do not allocate an occupied
slot.

See Task 1 in `../Assignment_Report.md` for the detailed analysis and object
versus parallel-array comparison.
