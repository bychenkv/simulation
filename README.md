# Simulation

A simple step-by-step simulation of a 2D world written in Java.

## 🎯 Overview

This simulation creates a 2D grid-based world where:
- **Herbivores** 🐇 search for and consume grass
- **Predators** 🐺 hunt herbivores
- **Resources** 🌱 (grass) spawn dynamically on each iteration
- **Static objects** 🪨 (rocks) / 🌳 (trees) block movement

Each creature uses **BFS pathfinding algorithm** to navigate toward target resources.

The simulation runs in real-time with a terminal-based UI that allows
- pausing / resuming
- stopping 
- map scrolling

## 🚀 Getting started

### Prerequisites

- **Java 22** or higher
- **Maven 3.8** or higher
- Terminal with UTF-8 support (for emoji rendering)

### Installation

1. **Clone repository**

    ```bash
      git clone https://github.com/bychenkv/hangman.git
      cd simulation
    ```

2. **Build & run project**

    ```bash
      mvn clean package
      mvn exec:java
    ```

## 🎬 Demo

[![asciicast](https://asciinema.org/a/D7Sje7ZP8qesAB1F.svg)](https://asciinema.org/a/D7Sje7ZP8qesAB1F)