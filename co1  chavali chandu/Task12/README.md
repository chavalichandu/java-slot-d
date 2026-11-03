# Task 12 — Design Challenge

**Aim:** Extend the model for e-bikes, delivery vans, variable floor sizes
and one-day visitor passes.

`DesignChallenge.java` keeps vehicle-specific fee behavior in subclasses,
stores slots in a jagged array, supports release and availability queries,
and applies a valid one-day pass as a zero parking fee. The e-bike flat fee,
delivery-van day/off-peak rates and sample floor capacities are retained from
the supplied starter implementation. They are illustrative existing sample
values because the assignment materials do not give an official rate schedule
or required slot counts.

```text
javac DesignChallenge.java
java DesignChallenge
```

The demonstration checks parking, fee examples, pass handling, release and an
invalid entry hour. See Task 12 in `../Assignment_Report.md` for the impact
analysis and design defence.
