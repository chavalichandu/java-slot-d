# Campus Smart Parking System Using Java

**Student:** Chavali Chandu  
**Register number:** 192472332  
**Java compatibility:** JDK 8 or later

This assignment contains twelve task explanations and four standalone Java
programs. Tasks 1–2 and 4–7, 9–10 are design and Java-concept exercises;
their answers are in `Assignment_Report.md` and the corresponding task
readmes. Tasks 3, 8, 11 and 12 contain runnable programs.

## Project structure

- `Assignment_Report.md` — task-wise analysis, algorithms, test evidence,
  results, conclusion, references and viva questions.
- `Task03/ParkingFacility.java` — jagged-array allocation and release.
- `Task08/FeeDemo.java` — vehicle inheritance, fee calculation and charging
  interface.
- `Task11/SmartParkingSystem.java` — vehicle/user registration, tickets,
  slot availability, exits and monthly-pass fee handling.
- `Task12/DesignChallenge.java` — e-bike, delivery van, jagged floors and
  visitor one-day pass.
- `Task*/expected_output.txt` — console output captured from the corresponding
  program after compiling and running it.

The slot counts and fee examples in the demos are sample inputs/policies
already present in this assignment. They are not additional faculty rules.
Task 12's e-bike and delivery-van fee examples are retained from the existing
starter implementation because no separate official rate schedule was
provided.

## Compile and run

Open a terminal in the project folder. Compile and run each task separately:

```text
cd Task03
javac ParkingFacility.java
java ParkingFacility
```

Use the same commands with `Task08` / `FeeDemo.java` / `FeeDemo`,
`Task11` / `SmartParkingSystem.java` / `SmartParkingSystem`, or
`Task12` / `DesignChallenge.java` / `DesignChallenge`.

All programs use the default package, so each source file compiles on its own.
The development environment has JDK 8 installed, although `javac` is not
currently on its PATH. If `javac` is not recognised on your computer, install
a JDK (not only a JRE) and add its `bin` directory to PATH.

## Submission notes

- Check the student details and add the GitHub repository URL in
  `Assignment_Report.md`.
- Run the programs on your own computer and capture genuine screenshots of
  the terminal output if screenshots are required by your faculty.
- The `.txt` output files are actual captured console output; they are not
  screenshots.
- No Git repository was present in the supplied assignment folder.
