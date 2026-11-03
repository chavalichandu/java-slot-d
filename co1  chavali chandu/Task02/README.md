# Task 2 — Select Data Types and Arrays

**Aim:** Choose suitable Java types and data structures for parking data.

Use `int` for slot indexes and durations in minutes, `String` for registration
numbers, `long` for elapsed/large integer values, and `double` for the sample
fee values. A jagged `String[][]` represents floors with different slot
counts; `null` means that a slot is free.

For positive durations, `(minutes + 59L) / 60L` rounds up to the number of
started hours without integer-division truncation or `int` overflow.

See Task 2 in `../Assignment_Report.md` for type selection, precision,
complexity and alternative representations.
