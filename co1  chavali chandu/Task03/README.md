# Task 3 — Design the Control Logic

**Aim:** Allocate the first available slot and release an occupied slot.

`ParkingFacility.java` stores registrations in a jagged array. Allocation
scans floors and their slots in order and returns a zero-based `{floor, slot}`
pair, or `null` when full. Release checks both indexes and occupancy before
clearing the registration. Duplicate active registrations and blank
registrations are rejected.

```text
javac ParkingFacility.java
java ParkingFacility
```

The main demonstration covers allocation, full capacity, release, invalid
indexes, reuse and blank-registration validation. See Task 3 in
`../Assignment_Report.md` for pseudocode and complexity.
