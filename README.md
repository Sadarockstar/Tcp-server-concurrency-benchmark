# TCP Server Concurrency Benchmark (Java)

## Overview

This project demonstrates the performance difference between **single-threaded** and **multi-threaded** TCP servers implemented in Java using the `ServerSocket` and `Socket` APIs.

The servers were tested under heavy load using **Apache JMeter** to measure key performance metrics such as **throughput**, **latency**, and **error rate**.

The goal of this experiment is to understand how **concurrency improves server scalability and responsiveness** when handling multiple client connections.

---

## Features

* Implementation of a **Single-Threaded TCP Server**
* Implementation of a **Multi-Threaded TCP Server**
* Load testing using **Apache JMeter**
* Performance comparison under **60,000 requests**
* Analysis of **throughput, latency, and error rate**

---

## Technologies Used

* **Java**
* **Java Networking (ServerSocket, Socket)**
* **Multithreading**
* **Apache JMeter**
* **TCP Protocol**

---

## Project Structure

```
tcp-server-concurrency-benchmark
│
├── single-threaded-server
│   └── Server.java
│
├── multi-threaded-server
│   └── Server.java
│
├── jmeter-test-plan
│   └── tcp_test.jmx
│
├── results
│   ├── single-thread-summary.png
│   └── multi-thread-summary.png
│
└── README.md
```

---

## Server Architecture

### Single-Threaded Server

The server processes **one client at a time**.
All other clients must wait until the current request is finished.

```
Client1 → processed
Client2 → waiting
Client3 → waiting
```

### Multi-Threaded Server

Each client connection is handled by a **separate thread**, allowing concurrent processing.

```
Client1 → Thread1
Client2 → Thread2
Client3 → Thread3
```

---

## Load Testing Setup

Load testing was performed using **Apache JMeter**.

Test configuration:

| Parameter      | Value      |
| -------------- | ---------- |
| Total Requests | 60,000     |
| Test Duration  | 60 seconds |
| Protocol       | TCP        |
| Server Port    | 8010       |

---

## Performance Results

### Single-Threaded Server

| Metric                | Value                  |
| --------------------- | ---------------------- |
| Requests              | 60,004                 |
| Average Response Time | 2 ms                   |
| Max Response Time     | 225 ms                 |
| Error Rate            | 0.91%                  |
| Throughput            | **537.6 requests/sec** |

---

### Multi-Threaded Server

| Metric                | Value                   |
| --------------------- | ----------------------- |
| Requests              | 60,000                  |
| Average Response Time | 1 ms                    |
| Max Response Time     | 128 ms                  |
| Error Rate            | 0.01%                   |
| Throughput            | **1000.3 requests/sec** |

---

## Performance Comparison

| Metric      | Single Thread | Multi Thread |
| ----------- | ------------- | ------------ |
| Throughput  | 537 req/sec   | 1000 req/sec |
| Avg Latency | 2 ms          | 1 ms         |
| Error Rate  | 0.91%         | 0.01%        |

**Throughput improvement: ~86%**

This demonstrates how **concurrent request handling significantly improves server scalability and reliability**.

---

## How to Run

### Compile the Server

```
javac Server.java
```

### Run the Server

```
java Server
```

The server will start listening on port:

```
8010
```

---

## Running Load Tests

1. Install **Apache JMeter**
2. Open the provided `.jmx` test plan
3. Start the server
4. Run the test in JMeter
5. View results in the **Summary Report**

---

## Learning Outcomes

This project demonstrates:

* TCP socket programming in Java
* Server architecture design
* Multithreading and concurrency
* Load testing using Apache JMeter
* Performance analysis of network servers

---

## Future Improvements

* Implement **Thread Pool server (ExecutorService)**
* Implement **Non-blocking I/O (Java NIO)**
* Add **connection pooling**
* Perform **higher concurrency stress tests**

---

## Author

**Sadananda Behera**

Computer Science Engineering Student
