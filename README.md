# Continuous Integration Server - Assignment 2

## Overview
This Java-based Continuous Integration (CI) server is designed to encapsulate the core functionalities of CI processes, catering to essential CI features. The server can be configured to listen for webhooks from Github, to automatically test a repository each time a new push event occurs.

Developed as a part of the DD2480 Software Engineering Fundamentals course at KTH Royal Institute of Technology, this project emphasizes educational purposes and the practical application of CI principles.

## Features
- **Compilation:** Automated compilation of the project to ensure code integrity.
- **Testing:** Execution of automated tests to maintain code quality.
- **Notification:** Notification mechanisms to inform about the CI process outcomes.

## Configuration

### Requirements for development
- Java 17
- Maven 4.0
- GitHub Account

### Setup
Enviroment variable that has to be available to the server:
- **GITHUB_AUTH**: GitHub access token

#### Running the Project

```bash
# Compile
mvn clean package

# Run
mvn test
```
## Core CI feature #1 - Compilation
Compilation has been implemented in the method `compileProject` in `GitUtilities`. The method executes the Maven compile command through a process, waits for that process to complete, and then checks the process's exit code. A return value of true indicates a successful compilation, while false indicates a failure or an exception during the compilation process.

## Core CI feature #2 - Testing
The test execution has been implemented in the method `testProject` in `GitUtilities`. The method executes the Maven test command through a process, waits for that process to complete, and then checks the process's exit code. A return value of true indicates that all tests passed, while false indicates a failure or an exception during the testing process.

The testing of the actual server is implemented with Junit 5 and run by using the in-built testing feature in preferred development environment.

## Core CI feature #3 - Notification 
- We update the commit status' using the Git Status API with the four possible values (`success`, `failure`, `error` and `pending`) accordingly to the situation.

- A test can be made by modifying any file in the `assessment` branch and making a commit, then, the corresponding status should appear under the "checks" section with the outcome result.

## Statement of Contributions
Note that all group members have worked together on all features in the assignment by using the "Code with me" feature in intelliJ.

- **Maja Larsson**: Active in general design and implementation for core feature 1 and 2. Contributed to documentation of README and wrote the documentation for property semat (Checklist for team). Implemented testing of the server. 

- **Martin Catheland**: Active in general design and implementation for all core features. Created the skeleton for `utils` including `GitUtilites.java`. Implemented testing of the server. Implemented the CI feature for keeping the history of the past builds. 

- **Nicole Wijkman**: Active in general design and implementation for core feature 1 and 2. Primarily involved in the development of core feature 1, designing and writing the code for the cloning and compilation features of the CI. Contributed to documentation of README and general documentation. Implemented testing of the server. 

- **Simon Hocker**: Active in general design and implementation for all core features. Primarily involved in the development of core feature 1, designing and writing the code for the cloning and compilation features of the CI. Contributed to general documentation throughout the assignment. Implemented testing of the server. 

- **Serhan Çakmak**: Active in general design and implementation of all core features. Implemented the fundamentals of `GithubStatusUpdater.java`. Created the skeleton for the Maven project structure. Implemented testing of the server. 

## Argument for P+
*Property: most of the recent 25 commits (typically 90%) are linked to an issue describing the feature / commit.*

Currently the requirements regarding commits tracing to issues is fulfilled.

*Property (CI feature): the CI server keeps the history of the past builds. This history persists even if the server is rebooted. Each build is given a unique URL, that is accessible to get the build information (commit identifier, build date, build logs). One URL exists to list all builds.*

Currently the requirements regarding that the CI server keeps the history of the past builds is implemented. 

## About This Project
This project was developed as part of an assignment for DD2480 Software Engineering Fundamentals at KTH Royal Institute of Technology and Science. It is meant for educational purposes by the members of Group 5.