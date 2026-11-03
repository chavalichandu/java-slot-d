
## Title Page

**Course:** CO1 — Java Programming Assignment  
**Project:** Campus Smart Parking System Using Java  
**Student name:** Chavali Chandu  
**Register number:** 192472332  
**GitHub repository:** [Add repository URL before submission]  
**Java version:** JDK 8 or later


## Abstract

This assignment applies Java fundamentals and object-oriented programming to
a campus parking scenario. The twelve tasks progress from identifying
entities and choosing data structures to implementing allocation, fees,
inheritance, polymorphism, abstraction and encapsulation. Four standalone
programs demonstrate jagged-array allocation, fee calculation, an integrated
parking workflow and a design extension for e-bikes, delivery vans and a
visitor pass. The programs validate invalid input and the examples exercise
capacity, release and fee boundaries.

## 1. Introduction

A campus parking facility must associate vehicles with users, allocate
available slots and track entry and exit. A ticket links a vehicle and user
to its allocated slot. The assignment uses small in-memory arrays so that
the Java concepts remain visible; data is not persisted after a program
closes.

The supplied project material does not include a separate faculty handout
with complete rate tables or a required facility capacity. Rates and slot
counts shown in this report are therefore identified as existing demonstration
values from the supplied source/report, not as additional institutional
rules.

## 2. Objectives

- Analyse the system in terms of classes, objects, inputs, outputs and rules.
- Select suitable primitive/reference types and arrays.
- Allocate and release parking slots, including variable-size floors.
- Demonstrate constructors, static members, inheritance, overriding and
  runtime polymorphism.
- Use an abstract class and an interface to represent common behavior and
  optional capability.
- Apply encapsulation and validate invalid values.
- Generate tickets, calculate fees, manage exits and apply the documented
  pass rules.
- Explain the design and test evidence in preparation for a viva.

## 3. Software and Hardware Requirements

### Software

- Java Development Kit (JDK) 8 or later, providing `javac` and `java`.
- A terminal/command prompt and a text editor or Java IDE.
- Operating system capable of running the installed JDK (the implementation
  was compiled with JDK 8 on Windows).

### Hardware

- A standard computer capable of running the selected JDK.
- No special hardware, database, network service or external library is
  required. Any minimum memory or CPU specification depends on the installed
  JDK and operating system.

## 4. Assignment-Wide Business Rules and Assumptions

1. A slot contains at most one vehicle; an occupied slot is not allocated.
2. A vehicle needs a non-blank registration number. A duplicate active
   registration is rejected.
3. A released slot becomes available for another vehicle.
4. A ticket number is shared across ticket instances in Task 11.
5. Students and staff with a valid monthly pass pay no parking fee. Visitors
   without a pass pay the applicable example vehicle fee.
6. A positive duration is billed by started hour in Tasks 8 and 11. For
   example, 61 minutes is two hours.
7. Task 12's e-bike flat fee, delivery-van day/off-peak hourly rates and
   sample floor capacities are preserved from the supplied starter code.
   They are example values, not an official rate schedule. A valid
   one-day-pass example yields zero parking fee.
8. Programs keep state in memory only. There is no database or permanent
   record storage.

## 5. Task-Wise Aim and Explanation

### Task 1 — Understand the Problem and Identify Objects

**Aim:** Identify inputs, outputs, rules and candidate classes.

Inputs include registration, vehicle/user category, parking duration and
available slots. Outputs include ticket/slot information, occupancy and fee.
`Vehicle` stores vehicle data, `ParkingSlot` represents a single slot,
`ParkingTicket` links an entry to a slot, `ParkingFacility` manages slots and
`User` stores user/pass information. Separate responsibilities keep the
design understandable. Objects keep related data together; parallel arrays
require every related array to be updated at the same index.

### Task 2 — Choose Data Types and Data Representation

**Aim:** Represent each value with an appropriate Java type.

| Data | Java type | Reason |
|---|---|---|
| Floor/slot index, duration | `int` | Whole-number counts |
| Registration number | `String` | Contains letters and digits |
| Large counter or elapsed quantity | `long` | Wider integer range |
| Fee | `double` | Example fees may have a fractional part |
| Per-floor slots | `String[][]` or `Vehicle[][]` | A jagged array supports different floor sizes |
| Pass state | `boolean` | Valid/invalid state |

An empty array element (`null`) indicates a free slot in the simple examples.
For a positive duration, `(minutes + 59L) / 60L` rounds up to started hours
without losing the remainder through integer division. A jagged array uses
O(total slots) storage; a full scan for allocation is O(total slots) in the
worst case. An `ArrayList` or `HashMap` could be used for a larger system, but
is unnecessary for these introductory exercises.

### Task 3 — Design the Control Logic

**Aim:** Allocate the first available slot and safely release an occupied one.

`ParkingFacility.java` scans each floor and slot in order. It returns the
zero-based floor/slot pair, or `null` when the facility is full. It rejects
blank or duplicate registrations, validates floor capacities and exposes
occupancy/availability counts. Release checks both indexes and the current
occupancy.

**Allocation algorithm**
```text
FOR each floor
    FOR each slot on that floor
        IF slot is empty
            store registration
            RETURN floor and slot
RETURN no slot available
```

### Task 4 — Classes, Objects and Constructors

**Aim:** Model system entities with classes and create valid object instances.

A class defines state and methods; an object is an instance created with
`new`. Parameterized constructors initialise required values and prevent
objects with missing registration numbers/categories. `this` refers to the
current object. A copy constructor is unnecessary for a ticket because a new
ticket should receive a new identity rather than copy an issued number.

### Task 5 — Static Members and Object Life Cycle

**Aim:** Share ticket numbering and explain object reachability.

The Task 11 counter is static so all tickets use one sequence. Its synchronized
method avoids two simultaneous calls receiving the same number. On exit, the
facility removes the ticket from its active-slot array; the returned ticket
can still be read while referenced. Java may reclaim an object after no
reachable references remain; the program does not control the exact
garbage-collection time.

### Task 6 — Design the Inheritance Hierarchy

**Aim:** Reuse common vehicle state and specialise vehicle behavior.

Task 8 defines abstract `Vehicle`, with `TwoWheeler` and `Car` subclasses;
`ElectricCar` extends `Car`. This demonstrates hierarchical and multilevel
inheritance. A subclass constructor calls `super(regNo)` to initialise the
shared registration field. New categories can override category-specific
behavior.

### Task 7 — Override and Specialise Behavior

**Aim:** Implement category-specific behavior and identity comparison.

`TwoWheeler` and `Car` override `ratePerHour()`. The `@Override` annotation
lets the compiler detect a signature mismatch. `Vehicle.equals()` compares
normalised registrations, while `hashCode()` uses the same value to preserve
the Java equality/hash-code contract.

### Task 8 — Apply Polymorphism

**Aim:** Demonstrate compile-time and runtime polymorphism.

Compile-time polymorphism commonly uses overloaded method signatures.
Runtime polymorphism occurs when a `Vehicle` variable refers to a subclass
object; a `Vehicle[]` can hold different vehicle types. The called overridden
rate method belongs to the actual object, not just the declared reference
type. `FeeDemo.java` also invokes the optional `Chargeable` capability for an
electric car.

### Task 9 — Introduce Abstraction

**Aim:** Describe common behavior and optional capability without requiring
one concrete implementation for every vehicle.

`Vehicle` is abstract because a general vehicle has no single category rate.
`Chargeable` is an interface because only some vehicles support charging.
An abstract class can hold instance state and constructors; an interface
defines a behavior contract that a class can implement alongside its
superclass.

### Task 10 — Encapsulation, Packages and Access Modifiers

**Aim:** Protect state and explain code organisation.

Fields are private and accessed through methods so callers cannot bypass
validation. Public methods form the supported interface; protected methods
are suitable for subclass behavior; package-private access is limited to the
same package. A larger application could separate `parking.model`,
`parking.service` and `parking.app`. The required standalone source files
remain in the default package so each documented one-file compile command
continues to work.

### Task 11 — Implement, Test and Defend the Design

**Aim:** Combine registration, tickets, slots, exits, fees and pass handling.

`SmartParkingSystem.java` uses a fixed-size ticket array. A successful entry
gets the first free slot and a ticket number. Exit by registration calculates
the fee, closes the ticket and releases its slot. Student/staff users with a
valid monthly pass pay zero; visitors are charged according to the existing
two-wheeler/car example rates (Rs 10/Rs 30 per started hour). The captured
demonstration totals Rs 40 from two charged visitor exits; the student and
staff pass examples contribute Rs 0.

### Task 12 — Re-evaluate and Adapt the Design

**Aim:** Extend the model for e-bikes, delivery vans, variable floor sizes and
one-day visitor passes.

`DesignChallenge.java` adds `EBike`, `DeliveryVan` and `VisitorPass`, and
stores vehicles in a jagged array. Vehicle-specific fee behavior is defined
in the relevant subclass. The supplied starter code used an e-bike flat fee
of Rs 20 and delivery-van rates of Rs 60/hour from 9:00 to before 17:00 and
Rs 30/hour otherwise. These values and the sample floor layout are preserved
as demonstration behavior only because no separate official schedule or
capacity was supplied. The design is extended rather than replacing the
original slot/vehicle concepts.

## 6. Algorithms and Procedure

### Fee calculation

```text
IF duration is negative
    reject input
ELSE IF duration is zero
    fee = 0
ELSE
    startedHours = (duration + 59) / 60
    fee = startedHours * vehicle hourly rate
IF valid pass applies
    fee = 0
```

Task 12 retains the starter program's e-bike flat fee and time-window rule
for delivery vans. The program validates a 0–23 entry hour.

### Integrated entry and exit

```text
ENTRY:
validate vehicle and user
reject duplicate active registration
find first free slot
if none, report full
create ticket and occupy slot

EXIT:
find active ticket by registration
if absent, report not parked
calculate fee, applying a valid monthly pass
close ticket and clear its slot
```

### Execution procedure

1. Open a terminal in a task folder.
2. Compile the listed `.java` source using `javac`.
3. Run its public class with `java`.
4. Compare the console output with the corresponding
   `TaskNN/expected_output.txt` file.
5. Use the invalid/boundary scenarios in the source demonstrations to verify
   validation, capacity and release behavior.

## 7. Java Source References

| Task | Source/reference |
|---|---|
| 1 | `Task01/README.md`; this report, Task 1 |
| 2 | `Task02/README.md`; this report, Task 2 |
| 3 | `Task03/ParkingFacility.java` |
| 4 | `Task04/README.md`; this report, Task 4 |
| 5 | `Task05/README.md`; `Task11/SmartParkingSystem.java` |
| 6 | `Task06/README.md`; `Task08/FeeDemo.java` |
| 7 | `Task07/README.md`; `Task08/FeeDemo.java` |
| 8 | `Task08/FeeDemo.java` |
| 9 | `Task09/README.md`; `Task08/FeeDemo.java` |
| 10 | `Task10/README.md`; this report, Task 10 |
| 11 | `Task11/SmartParkingSystem.java` |
| 12 | `Task12/DesignChallenge.java` |

## 8. Sample Input, Output and Test Evidence

These are deterministic console demonstrations rather than interactive
programs. The actual output captured from each compiled program is stored in
the corresponding `expected_output.txt` file.

| Program | Sample values used by its demonstration |
|---|---|
| Task 3 | Floor capacities `{2, 1}`; registrations `TN01AB1111` through `TN04GH4444` |
| Task 8 | Two-wheeler, car and electric car; 130-minute stay; 10 kWh charging example |
| Task 11 | Two slots; two-wheeler student with pass and car visitor without a pass |
| Task 12 | Floor capacities `{2, 3, 1, 2, 2}`; e-bike/van examples at 90/60 minutes |

The test cases include:

| Area | Case | Expected behavior |
|---|---|---|
| Task 3 allocation | Fill all configured slots | Further allocation returns `null` |
| Task 3 release | Release valid slot, then same slot again | First succeeds; second fails |
| Task 3 validation | Blank registration or invalid floor | Registration rejected; invalid release returns false |
| Task 8 fee boundary | 130 minutes; zero minutes | Three started hours; zero fee for zero duration |
| Task 8 validation | Negative duration/charging energy | `IllegalArgumentException` |
| Task 11 capacity | Park a third vehicle in a two-slot example | No ticket is allocated |
| Task 11 exit | Exit registered vehicle, then absent vehicle | Slot released; absent vehicle returns no ticket |
| Task 11 pass/fee | Student with valid pass, visitor for 60 minutes | Rs 0; car example Rs 30 |
| Task 11 collection | Two visitor exits and two valid monthly passes | Rs 40 total from visitor fees; pass fees are zero |
| Task 12 capacity | Jagged floor layout | Allocation scans across floor boundaries |
| Task 12 release | Release a van twice | First succeeds; second fails |
| Task 12 validation | Entry hour 24 | `IllegalArgumentException` |
| Task 12 fee boundary | Entry at 8, 9, 16 and 17 | Off-peak/day-window examples follow the retained sample rule |

All four programs were compiled and executed with JDK 8 (`javac`/`java`
1.8.0_202). The output files contain the actual console output from those
runs. No screenshot is included: the execution environment did not provide an
authentic desktop terminal capture, and no screenshot has been fabricated.

## 9. Results and Discussion

The four programs model allocation, release, fee calculation and selected
business rules using plain Java. Jagged arrays support floors with different
slot counts. Encapsulated classes keep vehicle, user and ticket data
together. Dynamic dispatch selects vehicle-specific rates, and the Task 11
ticket associates an entry with its slot and user. Input validation prevents
blank registrations, negative durations and invalid entry hours.

The design is intentionally in-memory and single-process. It does not provide
persistent storage, a live clock-based duration, concurrent gate control,
authentication or a configurable official fee table. Those features were
not specified in the supplied assignment materials.

## 10. Conclusion

The assignment demonstrates how core Java and object-oriented design can
represent a small parking workflow. The four programs make allocation,
release, ticketing, fee calculation, inheritance, polymorphism, abstraction
and pass handling executable. The design remains suitable for a student
assignment while identifying its limitations and distinguishing sample
values from official requirements.

## 11. References

1. Supplied project `README.md` and task-folder instructions.
2. Oracle, *Java Platform, Standard Edition 8 API Specification*,
   https://docs.oracle.com/javase/8/docs/api/
3. Oracle, *The Java Tutorials — Object-Oriented Programming Concepts*,
   https://docs.oracle.com/javase/tutorial/java/concepts/
4. Oracle, *The Java Tutorials — Classes and Objects*,
   https://docs.oracle.com/javase/tutorial/java/javaOO/

## 12. Viva Questions and Answers

### Classes, objects and constructors

1. **What is a class?** A class is a blueprint that groups data and methods.
2. **What is an object?** An object is a runtime instance of a class.
3. **Why use a parameterized constructor?** It initializes required fields
   when an object is created.
4. **What does `this` refer to?** It refers to the current object.
5. **What does `super` do in a constructor?** It invokes the parent
   constructor to initialise inherited state.

### Data types and arrays

1. **Why is a registration number a `String`?** It may contain letters and
   digits and is not used for arithmetic.
2. **Why use `int` for slot indexes?** Indexes are whole-number positions.
3. **What is a jagged array?** A two-dimensional array whose rows can have
   different lengths.
4. **How is a free slot represented here?** As a `null` element in the slot
   array.
5. **Why use `(minutes + 59L) / 60L`?** It rounds a positive duration up to
   whole started hours and uses `long` arithmetic to avoid `int` overflow.

### Static members and object life cycle

1. **What does `static` mean?** The member belongs to the class, not to an
   individual object.
2. **Why is ticket numbering static?** All tickets share one number sequence.
3. **What is the difference between static and instance fields?** A static
   field has one class-level value; an instance field has a value per object.
4. **When can an object be garbage collected?** When it is no longer
   reachable from live references.
5. **Does Java guarantee when garbage collection runs?** No; the JVM decides
   when to reclaim eligible objects.

### Inheritance

1. **Why did we use inheritance?** To reuse common vehicle state and
   specialise behavior in vehicle categories.
2. **What is a superclass?** A class whose state or behavior is inherited by
   subclasses.
3. **What does `extends` express?** A class-to-class inheritance
   relationship.
4. **What does `super(regNo)` do?** It passes the registration to the parent
   constructor.
5. **What is multilevel inheritance in this project?** `ElectricCar` extends
   `Car`, which extends `Vehicle`.

### Overriding and runtime polymorphism

1. **What is method overriding?** A subclass provides an implementation for
   an inherited method with a compatible signature.
2. **What does `@Override` do?** It asks the compiler to verify that the
   method actually overrides a parent method.
3. **What is runtime polymorphism?** The JVM selects an overridden method
   using the actual object's class at runtime.
4. **How does a `Vehicle[]` demonstrate polymorphism?** It stores different
   vehicle subclasses behind the same parent type.
5. **What is the benefit over a long type-check chain?** New subclasses can
   provide behavior without expanding a central chain of type checks.

### Abstract classes

1. **What is an abstract class?** A class that cannot be instantiated
   directly and may contain abstract methods.
2. **Why is `Vehicle` abstract?** A generic vehicle does not have one
   category-specific hourly rate.
3. **Can an abstract class have fields and constructors?** Yes; subclasses
   use them for shared state initialization.
4. **Can an abstract class contain implemented methods?** Yes, it can mix
   implemented and abstract methods.
5. **Can we create an object directly from an abstract class?** No; create an
   instance of a concrete subclass.

### Interfaces

1. **Why do we use interfaces?** They define capabilities that unrelated
   classes can implement.
2. **What capability does `Chargeable` represent?** The ability to calculate
   an electric charging fee.
3. **Can a Java class implement more than one interface?** Yes.
4. **Can a class extend two classes?** No; Java classes have single class
   inheritance.
5. **What is the difference between an abstract class and an interface?**
   An abstract class can hold instance state and constructors; an interface
   expresses a behavior contract and allows multiple implementation.

### Encapsulation, packages and access modifiers

1. **Why is encapsulation important?** It prevents callers from changing
   internal state without validation.
2. **Why are fields private?** Only the defining class can directly modify
   them.
3. **What is the role of a getter?** It provides controlled read access to a
   field.
4. **What does `protected` allow?** Access in the same package and from
   subclasses, subject to Java's access rules.
5. **Why use packages?** Packages organise related classes and help manage
   names and access.

### Exceptions and input validation

1. **Why validate constructor arguments?** To prevent invalid objects from
   entering the system.
2. **What does `IllegalArgumentException` indicate?** A caller supplied an
   invalid method or constructor argument.
3. **When does the slot allocator return `null`?** When no slot is available.
4. **Why does release return `false` for an invalid or empty slot?** No
   occupied slot was released.
5. **Why reject negative parking duration?** A negative duration cannot
   represent a valid stay and should not produce a fee.

### Parking allocation and fees

1. **How does parking slot allocation work?** Scan floors and slots in order
   and reserve the first free slot.
2. **How is a slot released?** Find the active vehicle/ticket and clear its
   slot after exit.
3. **How does the fee round a partial hour?** Any positive remainder starts
   another billable hour.
4. **How does a valid monthly pass affect Task 11?** A student or staff
   member with the pass pays zero parking fee.
5. **What is a limitation of the demonstration?** Slot and ticket data are
   held in memory and disappear when the program exits.
