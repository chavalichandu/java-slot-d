# Task 7 — Method Overriding

**Aim:** Specialise inherited behavior and implement value-based equality.

`TwoWheeler` and `Car` override `ratePerHour()`. The `@Override` annotation
lets the compiler check the method signature. `Vehicle.equals()` compares
normalised registration numbers, and `hashCode()` uses the same value so the
two methods remain consistent.

See Task 7 in `../Assignment_Report.md` for overriding rules and comparison
with a type-based `switch`.
