# Task 8 — Polymorphism and Fee Calculation

**Aim:** Demonstrate runtime dispatch through a vehicle superclass reference
and an optional charging interface.

`FeeDemo.java` places different subclasses in a `Vehicle[]`. Parking fees
round a positive duration up to a started hour. The existing sample rates are
Rs 10/hour for a two-wheeler, Rs 30/hour for a car, and Rs 12/kWh for charging
an electric car; these are code examples, not additional rates specified
elsewhere in the assignment. Negative durations and charging amounts are
rejected.

```text
javac FeeDemo.java
java FeeDemo
```

See Task 8 in `../Assignment_Report.md` for the dispatch trace and
compile-time versus runtime polymorphism.
