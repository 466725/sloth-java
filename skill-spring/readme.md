# skill-spring

A collection of Java learning examples covering core Java concepts, practical tutorials, and common interview problems. This module is part of the [sloth-java](../readme.md) multi-module workspace.

## Overview

The `skill-spring` module is organized into three main sections:

| Directory | Purpose |
|-----------|---------|
| [`concepts/`](concepts/) | Core Java language concepts with illustrative examples |
| [`tutorial/`](tutorial/) | Hands-on tutorials for libraries, tools, and frameworks |
| [`AzureQueries/`](AzureQueries/) | Sample Azure Monitor KQL queries |

## Concepts

Fundamental Java topics demonstrated with concise, runnable examples:

| Topic | Files | Description |
|-------|-------|-------------|
| **Algorithms** | `algorithm/` | Sorting (Quick, Insertion, Selection), Binary Search, Binary Tree Traversal |
| **Annotations** | `annotations/` | Custom annotations, Lombok `@Getter`/`@Setter` demo |
| **Backtracking** | `backtrack/` | Character subsets, Generate Parentheses |
| **Deep Copy** | `deepcopy/` | Shallow vs deep copy patterns with Java objects |
| **Dynamic Programming** | `dynamicprogramming/` | Climbing Stairs, Coin Change, Maximum Subarray, Minimum Path Sum, Unique Paths |
| **Generics** | `generics/` | Type parameters, bounded types, wildcard usage |
| **Polymorphism** | `polymorphism/` | Inheritance, method overriding, `Comparable` interface |
| **Recursion** | `recursion/` | Factorial, Fibonacci |
| **Reflection** | `reflection/` | Inspecting classes, methods, and fields at runtime |

## Tutorials

Practical, hands-on examples for commonly used libraries and tools:

### Java Basics (`tutorial/basics/`)
Entry-level Java examples: data types, arrays, HashMap, HashSet, Strings, loops, inner classes, static blocks, and the `+` operator.

### TestNG (`tutorial/testng/`)
TestNG framework features demonstrated one by one:
- Simple tests, Data Providers, Factory methods
- Dependent tests, Parallel execution, Groups
- Expected exceptions, Timeouts, Disabling tests

### AssertJ (`tutorial/assertj/`)
Fluent assertion library examples for writing readable test assertions.

### Dependency Injection (`tutorial/injection/`)
Google Guice-based dependency injection: modules, bindings, and injector patterns.

### Interview Problems (`tutorial/interview/`)
Common coding interview questions with solutions:
- **Arrays:** Two Sum, Contains Duplicate, Move Zeroes, Merge Sorted Array, Product of Array Except Self, Subarray Sum Equals K, Top K Frequent Elements, Kth Largest Element
- **Strings:** Longest Common Prefix, Longest Palindromic Substring, Longest Substring Without Repeating Characters, Valid Anagram
- **Linked Lists:** Reverse Linked List, Merge Two Sorted Lists, Remove Nth Node From End, Add Two Numbers
- **Trees & Graphs:** Number of Islands
- **Dynamic Programming:** House Robber, Jump Game, Trapping Rain Water, Container With Most Water
- **Binary Search:** Find Peak Element, Search in Sorted Array
- **Sorting & Intervals:** Merge Intervals, Meeting Rooms II
- **Other:** Valid Parentheses, Majority Element, Group Anagrams, Zero Sum Triplets, Palindrome Number

### JavaFaker (`tutorial/javafaker/`)
Generating realistic fake data for testing purposes.

### JSON (`tutorial/json/`)
Parsing and creating JSON with the `json-simple` library.

### MySQL (`tutorial/mysql/`)
Basic MySQL operations: creating tables, listing tables via JDBC.

### CSV (`tutorial/csv/`)
Reading and writing CSV files programmatically.

### Synchronization (`tutorial/synchronization/`)
Java concurrency examples: thread synchronization, `wait()`/`notify()`, and thread-safe patterns.

## AzureQueries

Sample KQL (Kusto Query Language) queries for Azure Application Insights monitoring:

| File | Description |
|------|-------------|
| `requests.txt` | Query request telemetry |
| `alerts.txt` | Query alert configurations |
| `page-views.txt` | Query page view analytics |
| `performanceCounters.txt` | Query performance counter metrics |

## Tech Stack

- **Java 25**
- **Maven** (build tool)
- **TestNG 7.10.2** (testing framework)
- **AssertJ 3.27.3** (fluent assertions)
- **Selenium 4.35.0** (browser automation)
- **MySQL Connector 9.2.0** (database connectivity)
- **Google Guice 7.0.0** (dependency injection)
- **JavaFaker 1.0.2** (fake data generation)
- **json-simple 1.1.1** (JSON parsing)
- **ExtentReports 2.41.2** (test reporting)
- **Log4j 1.2.17** (logging)

## Quick Start

### Prerequisites

- JDK 25+
- Maven 3.9+

### Build

From the repository root:

```bash
mvn -pl skill-spring clean compile
```

### Run Examples

Each `.java` file contains a `main()` method or test methods and can be run directly from your IDE or via Maven:

```bash
# Run a specific class
mvn -pl skill-spring exec:java -Dexec.mainClass="algorithm.ArrayQuickSort"

# Run TestNG tests
mvn -pl skill-spring test
```

## Project Structure

```
skill-spring/
├── pom.xml                  # Maven build configuration
├── concepts/                # Core Java concepts
│   ├── algorithm/           # Sorting, searching, tree traversal
│   ├── annotations/         # Custom annotations, Lombok
│   ├── backtrack/           # Backtracking algorithms
│   ├── deepcopy/            # Object copying patterns
│   ├── dynamicprogramming/  # DP algorithm examples
│   ├── generics/            # Java generics
│   ├── polymorphism/        # Inheritance & interfaces
│   ├── recursion/           # Recursive algorithms
│   └── reflection/          # Runtime reflection API
├── tutorial/                # Hands-on tutorials
│   ├── basics/              # Java fundamentals
│   ├── testng/              # TestNG framework
│   ├── assertj/             # AssertJ assertions
│   ├── injection/           # Guice dependency injection
│   ├── interview/           # Coding interview problems
│   ├── javafaker/           # Fake data generation
│   ├── json/                # JSON parsing
│   ├── mysql/               # MySQL JDBC examples
│   ├── csv/                 # CSV file handling
│   └── synchronization/     # Concurrency & threading
└── AzureQueries/            # Azure KQL query examples
    ├── requests.txt
    ├── alerts.txt
    ├── page-views.txt
    └── performanceCounters.txt
```

## Adding New Examples

1. **Concepts:** Place new concept examples under `concepts/<topic>/` with a descriptive class name.
2. **Tutorials:** Add tutorial files under `tutorial/<topic>/`. Include a `main()` method for standalone execution or use TestNG annotations for test-based examples.
3. **Dependencies:** If a new example requires a library, add it to `pom.xml` under `<dependencies>`.

Both `tutorial/` and `concepts/` are registered as source directories in the Maven build, so new `.java` files are compiled automatically.
