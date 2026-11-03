# Task 9 — Abstraction

**Aim:** Separate common vehicle state from specialised behavior and
represent optional capabilities.

`Vehicle` is abstract because a general vehicle does not have one concrete
hourly rate. `Chargeable` is an interface because only some vehicles support
charging. `ElectricCar` can extend `Car` and implement `Chargeable`.

See Task 9 in `../Assignment_Report.md` for the abstract-class/interface
comparison and alternatives.
