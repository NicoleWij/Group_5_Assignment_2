# Launch Interceptor Decision Algorithm

## Overview
This repository contains a Java implementation of the Launch Interceptor Decision Algorithm. The algorithm is designed to evaluate whether an interceptor should be launched based on various Launch Interceptor Conditions (LICs).

The core of the program revolves around the 'DECIDE()' function, which gives either "YES" or "NO" based on the fulfillment of various requirements. A "YES" result signifies the satisfaction of all 15 Launch Interpretor Conditions (LIC's), assigning boolean values "true" or "false" to the Conditions Met Vector (CMV). The Logical Connector Matrix (LCM) determines the simultaneous consideration of specific LIC's in a 15x15 matrix. The CMV, combined with the LCM, is stored in the Preliminary Unlocking Matrix (PUM), also a 15x15 matrix. Lastly, there are the Preliminary Unlocking Vector (PUV) and the Final Unlocking Vector (FUV). The PUV indicates the relevant LIC's for each launch determination, while the FUV is a 15-element vector requiring all elements to be "true" for launch approval, resulting in a "YES" output.

## Features
- **LIC Implementation**: A set of criteria evaluating whether an interceptor should be launched.
- **PUM Calculation**: Constructs a Preliminary Unlocking Matrix based on LICs' outcomes.
- **FUV Generation**: Generates a Final Unlocking Vector to decide on interceptor launch.

## Installation and Usage
Clone the repository and navigate to the project directory. Compile the `main.java.Decide.java` file, the classes in the `main.java.utils` package, and the classes in the `main.java.computations` package. Then run main.java.Decide.java.

```bash
# Clone the repository
git clone https://gits-15.sys.kth.se/majlar/Group-5_Assignment_1-

# Compile
javac /src/main.java.computations/*.java /src/main.java.utils/*.java /src/main.java.Decide.java

# Run
java main.java.Decide

```

## Testing 
The testing is implemented with Junit 5. To test you can either use the Testing feature in VS code, and then run all Java tests with Junit Jupiter. For testing using the terminal follow the instructions below, which is an example of how to test all LICs.

```bash
# Compile CMVTest 
javac -cp "./lib/junit-platform-console-standalone-1.10.1.jar" -d . src/main.java.utils/*.java src/main.java.computations/*.java src/main.java.Decide.java   

# Run CMVTest
java -cp "./lib/junit-platform-console-standalone-1.10.1.jar" org.junit.platform.console.ConsoleLauncher --class-path . --select-class tests.java.computations.CMVTest 
```

## Detailed Documentation
For comprehensive details on the algorithm and its implementation, refer to the [DECIDE Algorithm Specification](docs/decide.pdf) included in this repository.

## Statement of Contributions

- **Maja Larsson**: Created the code, documentation and test cases for LIC's 3, 8, 13. Helped with the LAUNCH. Furthermore, contributed to documentation of README and Essence for way of working. 

- **Martin Catheland**: Created the code, documentation and test cases for LIC's 2, 7 and 12. Implemented CMV and  main.java.Decide  shells, the internal classes of  `Utils.java` such that `Coordinate`, `Vector`, `Matrix<T>`  and `Parameters` record. Configured Junit 5 testing and created shells for the test classes. 

- **Nicole Wijkman**: Created the code, documentation and test cases for LIC's 1, 6, and 11. Developed helper functions `calculateCircumcircleRadius` and `distance`. Implemented and tested FUV. Managed project documentation by setting up and organizing the README.

- **Simon Hocker**: Created the code, documentation and test cases for LIC's 0, 5, and 10. Implemented shell for FUV and assisted further development of it, then reviewed testing it. Contributed to overall documentation. Helped implement original CMV shell and overall testing platform and the LAUNCH. 

- **Serhan Çakmak**: Implemented functions to check the correctness of  LIC’s 4, 9, 14 and also created unit tests for these functions. Created PUM shell, implemented the main functionality, and added tests. Also contributed to the documentation.

## About This Project
This project was developed as part of an assignment for DD2480 Software Engineering Fundamentals at KTH Royal Institute of Technology and Science. It is meant for educational purposes by the members of Group 5.

## Roadmap
While there are no planned updates, potential future improvements could focus on performance optimization and expanding test coverage.

## Compatibility and Reliability
- Backward compatibility is a priority. All changes adhere to semantic versioning.
- Extensive testing ensures reliability and correctness of the algorithm.

## Keep it Simple
Our approach is based on simplicity and clarity, avoiding over-complication in both code and documentation.

## Argument for P+
*Property: most of the recent 25 commits (typically 90%) are linked to an issue describing the feature / commit.*

Currently the requirements regarding commits tracing to issues is fulfilled.