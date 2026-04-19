# Multi-threaded Parking System Simulation

## Overview
A Java-based concurrent simulation that models a parking lot system with multiple entrance gates and limited parking spots. The project demonstrates thread synchronization, resource management, and inter-process communication concepts fundamental to operating systems design. Each arriving car is represented as an independent thread, competing for access to a shared pool of parking spaces through controlled synchronization mechanisms.

## Features
- Simulates concurrent car arrivals through 3 independent entrance gates
- Manages a fixed pool of 4 shared parking spots using thread-safe allocation
- Implements synchronization primitives (semaphores/monitors) to prevent race conditions
- Logs detailed event traces, including gate ID, car ID, arrival timestamp, and assigned spot
- Reads configuration from input.txt and writes structured output to output.txt
- Maven-based build system for dependency management and project standardization

## Technologies Used
- Language: Java (100%)
- Build Tool: Apache Maven
- Concurrency: java.util.concurrent, synchronized blocks, wait/notify mechanisms
- I/O: File-based input/output handling
- Project Structure: Standard Maven directory layout (src/main/java)

## Setup and Usage
1. Clone the repository:
```bash
git clone https://github.com/z00xINe/Multi-threaded-Parking-System-Simulation.git
cd Multi-threaded-Parking-System-Simulation
```
2. Build the project using Maven:
```
mvn clean compile
```
3. Prepare the input file (input.txt) with car arrival events in the format:
```
GateID, CarID, ArrivalTime, PreferredSpot
```
4. Run the simulation:
```
mvn exec:java -Dexec.mainClass="org.os.ParkingSystem"
```
5. View results in output.txt, which contains timestamped logs of all parking events.

## Output Format

- Each line in the output file follows this structure:
```
Gate X, Car Y, Arrive Z, Parks W
```
- Where:
  - X: Entrance gate identifier (1-3)
  - Y: Unique car identifier
  - Z: Simulation time of arrival
  - W: Assigned parking spot number (1-4)
- A separator line (`=======`) marks the end of each simulation run.

## Concurrency Design

- Each car arrival is handled by a dedicated thread to simulate real-world parallelism
- Parking spot allocation is protected by critical sections to ensure mutual exclusion
- Thread coordination prevents deadlock and ensures fair access to limited resources
- The design reflects classic operating system problems: producer-consumer, resource allocation, and semaphore-based synchronization 

## Notes

- This project is intended for educational purposes to illustrate multithreading concepts in Java 
- Execution results may vary slightly due to non-deterministic thread scheduling
- Extendable to support car departure events, priority queues, or dynamic spot allocation
