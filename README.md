# Horse Race Simulator

A Java Swing desktop game that simulates horse races lane by lane, with per-horse statistics and a simple betting system.

## Overview

This began as first-year Object-Oriented Programming coursework at Queen Mary University of London (April 2024). The module provided starter `Horse` and `Race` classes for a three-lane race printed to the terminal. Building on those, I:

- made the race support any number of lanes;
- added a Swing interface for creating, customising and removing horses;
- added a statistics module that keeps each horse's race history;
- added betting on the outcome.

In November 2026 I revisited the project. I fixed several logic bugs, moved the betting rules out of the GUI, made the race engine testable, and added a JUnit test suite and a Maven build. The commit history shows both stages.

## Features

- Add any number of horses, each with a name, a chess-piece symbol and a starting confidence from 0 to 1.
- Set the track length (5 to 20) and watch the race animate frame by frame.
- Per-horse statistics: wins, losses, win rate, average end-of-race confidence, fall rate, and a match-by-match history.
- Betting: one bet per race, from £1 to £10,000 and never more than your balance (you start with £75,000). A winning bet pays twice the stake. If you remove the horse you bet on, the stake is refunded.

## How a race works

Every frame, each horse that is still standing:

- moves forward one step with probability equal to its **confidence**;
- falls with probability **0.1 × confidence²**. A fallen horse stays down for the rest of the race.

The first horse or horses to reach the end of the track win. A win raises a horse's confidence by 0.1, and a fall lowers it by 0.1, so form carries over between races. If every horse falls, there is no winner and the race counts as a loss for all of them.

## Design

| Class | Responsibility |
|---|---|
| `Race` | Lanes, the race loop, finishing and winners, and betting rules. It has no Swing dependency. |
| `Horse` | Name, symbol, confidence (clamped to 0 to 1), position and fallen state. |
| `Statistics`, `RacePerformance` | Per-horse results and the summary figures derived from them. |
| `RaceOutput` | Interface for where the race is drawn. |
| `TextAreaOutput` | Draws into the GUI's text area. |
| `RaceGUI` | The Swing window. It reads input, delegates to `Race` and displays the results. |
| `Main` | Entry point. |

Some design decisions:

- **The engine doesn't depend on the GUI.** `Race` writes to a `RaceOutput` instead of a Swing component, and it takes its `Random` and frame delay as constructor arguments. Tests can therefore run a race instantly with a predictable outcome.
- **Threading.** The race runs on a background thread so the window stays responsive. Each frame reaches the text area through `SwingUtilities.invokeLater`, so all Swing updates happen on the event dispatch thread. The controls are disabled until the race finishes, so horses and bets can't change mid-race.
- **Rules live in the model.** Bet validation and payouts are in `Race`, not in button handlers, so the tests cover them.

## Requirements

- JDK 17 or later
- Maven 3.9 or later, for building and testing

## Build and run

```bash
mvn package
java -jar target/horse-race-simulator-1.0.0.jar
```

Without Maven:

```bash
javac -d out src/main/java/horserace/*.java
java -cp out horserace.Main
```

The chess-piece symbols need a font that includes them. Most desktop systems have one.

## Tests

```bash
mvn test
```

The suite has 23 JUnit 5 tests across `Horse`, `Statistics` and `Race`. A fixed-value `Random` makes race outcomes deterministic, so the tests can check exact results:

- winners and ties
- the confidence change after a race
- races where every horse falls
- bet payouts, refunds and validation
- lane renumbering when a horse is removed

The Swing interface isn't covered by automated tests.

## Limitations

- **No saved state.** Balance and statistics last only while the window is open.
- **Falling at the finish still counts.** A horse that falls on the step that takes it across the line is still a finisher, because the starter code checks distance only when deciding who has finished.
- **GUI testing is manual.**

## Attribution and licence

`Horse` and `Race` started from starter classes provided by the module; the original `Race` header credits McFarewell. Everything else was built on top of that starter code.

No open-source licence is granted, because the starter code isn't mine to license. The repository is published to show the work.

## Author

Muhammad Hussain ([mh-husssain](https://github.com/mh-husssain))
