# Continuous Integration Server - Assignment 2

## Overview
This Java-based Continuous Integration (CI) server is designed to encapsulate the core functionalities of CI processes, catering to essential CI features. The server can be configured to listen for webhooks from Github, to automatically test a repository each time a new push event occurs.

Developed as a part of the DD2480 Software Engineering Fundamentals course at KTH Royal Institute of Technology, this project emphasizes educational purposes and the practical application of CI principles.

## Features
- **Compilation:** Automated compilation of the project to ensure code integrity.
- **Testing:** Execution of automated tests to maintain code quality.
- **Notification:** Notification mechanisms to inform about the CI process outcomes.

## Installation and Usage
```bash
# Clone the repository
git clone https://github.com/NicoleWij/Group_5_Assignment_2

# Compile


# Run


```

## Testing
The testing is implemented with Junit 5. To test you can either use the Testing feature in VS code, and then run all Java tests with Junit Jupiter. For testing using the terminal follow the instructions below.

```bash
# Compile TEST 


# Run TEST

```

## Commit Status 
- We update the commit status' using the Git Status API with the four possible values (`success`, `failure`, `error` and `pending`) accordingly to the situation.
- A test can be made by modifying any file in the `assessment` branch and making a commit, then, the corresponding status should appear under the "checks" section with the outcome result.


## Statement of Contributions
Note that all group members have worked together on all features in the assignment by using the "Code with me" feature in intelliJ.

- **Maja Larsson**: Active in general design and implementation for core feature 1 and 2. Contributed to documentation of README and wrote the documentation for property semat (Checklist for team).
- **Martin Catheland**: Active in general design and implementation for all core features. Created the skeleton for `utils` including `GitUtilites.java`. Implemented testing of the server. 
- **Nicole Wijkman**: Active in general design and implementation for core feature 1 and 2. Primarily involved in the development of core feature 1, designing and writing the code for the cloning and compilation features of the CI. Contributed to documentation of README and general documentation. 
- **Simon Hocker**: Active in general design and implementation for all core features. Primarily involved in the development of core feature 1, designing and writing the code for the cloning and compilation features of the CI. Contributed to general documentation throughout the assignment.
- **Serhan Çakmak**: Active in general design and implementation of all core features. Implemented the fundamentals of `GithubStatusUpdater.java`. Created the skeleton for the Maven project structure. 

## About This Project
This project was developed as part of an assignment for DD2480 Software Engineering Fundamentals at KTH Royal Institute of Technology and Science. It is meant for educational purposes by the members of Group 5.

## Roadmap
While there are no planned updates, potential future improvements could focus on performance optimization and expanding test coverage.

## Compatibility and Reliability
- Backward compatibility is a priority. All changes adhere to semantic versioning.
- Extensive testing ensures reliability and correctness of the algorithm.

## Keep it Simple
Our approach is based on simplicity and clarity, avoiding over-complication in both code and documentation.
