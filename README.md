# MovementAtlas

MovementAtlas is a Kotlin Multiplatform Mobile (KMM) application for generating dance movement sequences. It creates both solo and partner dance sequences by combining step units, tracking weight foot transitions to ensure valid movement patterns.

The project is structured to share business logic between Android and iOS platforms, with platform-specific UI implementations.

## What It Does

The app generates dance sequences by:
- Combining step units (movement patterns) into sequences
- Tracking weight foot transitions (LEFT/RIGHT) to ensure valid combinations
- Supporting both solo sequences (single dancer) and partner sequences (lead and follow)
- Generating sequences starting from different initial weight states

## Architecture

The project follows Clean Architecture with a KMM structure:

- **`core/`**: Shared multiplatform domain layer (platform-agnostic)
  - Entities: Steps, StepUnits, Sequences
  - Use cases: Sequence generation, compatibility checking
  - Repositories: Data access interfaces
  - Designed to be shared between Android and iOS

- **`app/`**: Android-specific presentation layer
  - UI: Jetpack Compose screens
  - ViewModels: State management
  - Mappers: Domain to UI model conversion
  - Platform-specific Android dependencies (Hilt, Compose, etc.)

## Key Domain Concepts

### Steps
Atomic weight transfer movements:
- `Forward`, `Backward`, `Side`, `InPlace`
- Always transfer weight to the opposite foot

### StepUnits
Compositional movement units that group 1-3 steps:
- **DistanceOne**: Single step
- **DistanceTwo**: First foot doesn't travel again (weight returns in place)
- **DistanceThree**: First foot travels twice

**Domain Invariant**: All StepUnits must end on the opposite foot from where they started.

### Sequences
Collections of StepUnits:
- **SoloSequence**: Sequence for a single dancer (role-agnostic)
- **PartnerSequence**: Sequence with synchronized lead and follow step units

### WeightFoot
Tracks which foot has weight (LEFT or RIGHT) to ensure valid transitions between step units.

### Dance Conventions
- **Lead**: Starts with weight on RIGHT foot (first step with LEFT)
- **Follow**: Starts with weight on LEFT foot (first step with RIGHT)

## Project Structure

```
MovementAtlas/
├── app/                    # Android presentation layer
│   └── src/main/kotlin/.../movementatlas/
│       ├── presentation/   # UI, ViewModels, UI Models
│       └── di/             # Dependency injection (Android-specific)
└── core/                   # Shared multiplatform domain layer
    └── src/commonMain/kotlin/.../movementatlas/
        └── domain/
            ├── entity/     # Domain entities (Step, StepUnit, Sequence, etc.)
            ├── usecase/    # Business logic use cases
            ├── repository/ # Repository interfaces
            └── data/       # Default providers (step units, sequences)
```

**Note**: The `core/` module is designed as a multiplatform module to share business logic between Android and iOS. Platform-specific implementations (e.g., iOS app) would be added as separate modules.
