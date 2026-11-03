# Task 6 — Inheritance Hierarchy

**Aim:** Reuse common vehicle state and specialise vehicle behavior.

Task 8 models `Vehicle` as an abstract superclass with `TwoWheeler` and `Car`
subclasses; `ElectricCar` extends `Car`. This demonstrates hierarchical and
multilevel inheritance. Subclass constructors call `super(regNo)` to
initialise the shared registration number.

See Task 6 in `../Assignment_Report.md` for the hierarchy, constructor trace
and composition alternative.
