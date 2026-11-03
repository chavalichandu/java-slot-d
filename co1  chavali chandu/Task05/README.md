# Task 5 — Static Members and Object Life Cycle

**Aim:** Apply shared class-level state and explain object reachability.

The Task 11 ticket number counter is `static`, so every ticket shares one
sequence. A synchronized static counter method protects ticket-number
generation if calls are concurrent. After an exit, the facility removes the
ticket from its active-slot array; the returned ticket remains usable while a
reference exists and is eligible for garbage collection once unreachable.

See Task 5 in `../Assignment_Report.md` for static versus instance state and
Java's garbage-collection lifecycle.
