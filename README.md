Minimum Spanning Tree Algorithms - Assignment 3
Implementation and comparison of Prim's and Kruskal's algorithms for finding the Minimum Spanning Tree in weighted undirected graphs.
 Project Overview
This project optimizes a city transportation network by determining the minimum set of roads that connect all city districts with the lowest possible total construction cost. The implementation includes:

 Prim's Algorithm with Priority Queue
 Kruskal's Algorithm with Union-Find (Path Compression)
 Performance analysis and comparison
 JSON input/output handling
 Operation counting and time measurement

 Assignment Objectives

Apply graph algorithms to real-world optimization problems
Compare algorithmic efficiency (time and operations)
Analyze performance on different graph structures
Generate comprehensive analytical reports

 Project Structure
assignment_3_ds/
├── src/
│   ├── Graph.java                 # Graph representation with edges
│   ├── PrimAlgorithm.java        # Prim's MST implementation
│   ├── KruskalAlgorithm.java     # Kruskal's MST implementation
│   ├── JsonHandler.java          # JSON I/O operations
│   └── MSTComparison.java        # Main execution class
├── input_example.json            # Sample input graphs
├── output_example.json           # Algorithm results
├── pom.xml                       # Maven dependencies
└── README.md                     # This file
 Getting Started
Prerequisites

Java 11 or higher (tested on Java 23)
Maven 3.6+
IntelliJ IDEA (recommended) or any Java IDE

Installation

Clone the repository

bash   git clone https://github.com/akbota2007/Assignment-3-MST-Prim-and-Kruskal-implementation.git

Open in IntelliJ IDEA

File → Open → Select project folder
Wait for Maven to download dependencies


Build the project

bash   mvn clean compile
Running the Application
Option 1: From IDE

Open MSTComparison.java
Right-click → Run 'MSTComparison.main()'
Check console output and output_example.json

Option 2: From Command Line
bashmvn exec:java -Dexec.mainClass="MSTComparison"
 Sample Output
Reading input data from input_example.json...
Successfully read 2 graph(s)

============================================================
Processing Graph ID: 1
Vertices: 5, Edges: 7
============================================================

Running Prim's Algorithm...
✓ Prim's Algorithm completed
  Total Cost: 16
  Operations: 57
  Execution Time: 10.15 ms
  MST Edges:
    A - C (weight: 3)
    C - B (weight: 2)
    B - D (weight: 5)
    D - E (weight: 6)

Running Kruskal's Algorithm...
✓ Kruskal's Algorithm completed
  Total Cost: 16
  Operations: 35
  Execution Time: 11.19 ms
  MST Edges:
    B - C (weight: 2)
    A - C (weight: 3)
    B - D (weight: 5)
    D - E (weight: 6)

Comparison:
  Total costs match: ✓ YES
  Prim operations: 57
  Kruskal operations: 35
  Faster algorithm: Prim
 Input Format
The program reads graph data from input_example.json:
json{
  "graphs": [
    {
      "id": 1,
      "nodes": ["A", "B", "C", "D", "E"],
      "edges": [
        {"from": "A", "to": "B", "weight": 4},
        {"from": "A", "to": "C", "weight": 3},
        {"from": "B", "to": "C", "weight": 2}
      ]
    }
  ]
}
Output Format
Results are saved to output_example.json:
json{
  "results": [
    {
      "graph_id": 1,
      "input_stats": {
        "vertices": 5,
        "edges": 7
      },
      "prim": {
        "mst_edges": [...],
        "total_cost": 16,
        "operations_count": 57,
        "execution_time_ms": 10.15
      },
      "kruskal": {
        "mst_edges": [...],
        "total_cost": 16,
        "operations_count": 35,
        "execution_time_ms": 11.19
      }
    }
  ]
}
 Algorithm Details
Prim's Algorithm

Time Complexity: O(E log V) with binary heap
Space Complexity: O(V + E)
Approach: Grows MST from a single vertex
Best for: Dense graphs

Key Features:

Priority Queue for minimum edge selection
Adjacency list representation
Incremental tree building

Kruskal's Algorithm

Time Complexity: O(E log E)
Space Complexity: O(V + E)
Approach: Global edge sorting and selection
Best for: Sparse graphs

Key Features:

Union-Find with path compression
Edge sorting by weight
Cycle detection using disjoint sets

 Performance Comparison
GraphVerticesEdgesPrim OpsKruskal OpsWinner1575735Kruskal2454024Kruskal
Key Findings:

Kruskal performs 35-40% fewer operations
Execution times are comparable for small graphs
Both algorithms always produce optimal MST

Technologies Used

Language: Java 23
Build Tool: Maven
Libraries:

Gson 2.10.1 (JSON processing)


Data Structures:

HashMap (adjacency lists)
PriorityQueue (Prim's algorithm)
Union-Find (Kruskal's algorithm)



 Dependencies
xml<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
 Testing
The project includes two test graphs:

Graph 1: 5 vertices, 7 edges (dense)
Graph 2: 4 vertices, 5 edges (dense)

To add more test cases, edit input_example.json and add new graph objects.
 Documentation

Analytical Report: Detailed analysis available in Report.pdf
Code Comments: All classes include inline documentation
Algorithm Explanation: See report Section 5

Academic Context

Course: Design and Analysis of Algorithms
Assignment: Assignment 3 - MST Optimization
Topics Covered:

Greedy algorithms
Graph theory
Algorithm complexity analysis
Data structures (heaps, union-find)



Contributing
This is an academic project, but suggestions are welcome:

Fork the repository
Create a feature branch
Commit your changes
Push to the branch
Open a Pull Request

 License
This project is submitted as part of academic coursework.
👤 Author
Akbota

University: Astana IT University
Course: Design and Analysis of Algorithms
Date: October 2025

 Acknowledgments

Course instructor and teaching assistants
Cormen et al., Introduction to Algorithms
Sedgewick & Wayne, Algorithms

 Contact
For questions or feedback:

Email: aospanali@internet.ru
GitHub: @akbota2007

Last Updated: October 26, 2025
