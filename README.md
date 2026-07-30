# Hashtag Tracking & Social Media Management System

A console-based Java application that simulates a simplified social media platform, focused on **user management, posts/announcements, and hashtag analytics**. Built as an academic project to demonstrate core Object-Oriented Programming (OOP) principles — encapsulation, inheritance, abstraction, and polymorphism — through a real-world style system.

## Features

- **User Management**
  - Register, log in, log out, and change password
  - Username validation (length, allowed characters, no consecutive dots)
  - Email format and uniqueness validation
  - Strong password enforcement (uppercase, lowercase, digit, special character, min. length)
  - View any user's profile
- **Posts**
  - Create, edit, delete, and view posts
  - Automatic hashtag extraction from post content
  - Post metadata (created-at / last-edited timestamps)
  - View a short summary or the full content of any post
  - View all posts made by a specific user
- **Announcements**
  - Create priority-tagged announcements (High / Medium / Low)
  - View all announcements made by the logged-in user
- **Hashtag Analytics**
  - Track frequency of every hashtag used across the platform
  - View the top-N trending hashtags
  - Search all posts containing a specific hashtag
  - View overall hashtag usage frequency
- **Reports & Insights**
  - Total posts and total unique hashtags tracked
  - Top-K most active users (ranked by hashtag usage)
  - Full system summary report combining all statistics

## Tech Stack

- **Language:** Java
- **IDE:** BlueJ
- **Paradigm:** Object-Oriented Programming
- **Data Structures:** `HashMap`, `HashSet`, `ArrayList` (used for O(1) lookups, uniqueness constraints, and dynamic collections)

## Project Architecture

The system is organized into modular packages, each with a single responsibility:

```
OOPS_HASHTAG/
├── MainMenu.java              # CLI entry point; routes user commands to modules
├── Menu.java                  # Interface defining the menu contract
├── UsersManagingSystem/
│   ├── User.java              # User entity (profile, posts, announcements)
│   └── UserManager.java       # Registration, auth, and validation logic
├── Posts/
│   ├── Content.java           # Abstract base class for postable content
│   ├── Postable.java          # Interface for displayable/editable content
│   ├── Post.java              # Standard post with hashtag extraction
│   └── Announcement.java      # Priority-based announcement post
├── HashtagManagingSystem/
│   ├── HashtagManager.java    # Abstract class: frequency tracking & trending logic
│   └── HashtagStats.java      # Concrete class: hashtag search across users
└── Analytics/
    └── Reports.java           # Aggregated system-wide statistics and reports
```

### OOP Concepts Demonstrated

| Concept | Where it's used |
|---|---|
| **Abstraction** | `Content` and `HashtagManager` are abstract classes exposing a contract without full implementation |
| **Inheritance** | `Post` and `Announcement` extend `Content`; `HashtagStats` extends `HashtagManager` |
| **Polymorphism** | Overloaded `display()` / `getSummary()` methods across `Post`; overridden `getSummary(String)` in `Post` and `Announcement` |
| **Encapsulation** | Private fields with controlled access via getters/setters (e.g., `User`, `Post`) |
| **Interfaces** | `Menu` defines the CLI contract; `Postable` defines shared post behavior |
| **Composition** | `MainMenu` composes `UserManager`, `HashtagStats`, and `Reports` to coordinate the system |

## How to Run

1. Clone/download the repository.
2. Open the project folder in **BlueJ** (project files include `.bluej` and `.ctxt` metadata), or compile manually:
   ```bash
   javac -d out $(find . -name "*.java")
   java -cp out MainMenu
   ```
3. Follow the on-screen prompts — type commands like `Register`, `Login`, `CreatePost`, `ViewTrending`, `Report`, etc. Type `Menu` at any time to see the full list of commands.

## Sample Commands

```
Register        -> create a new user account
Login            -> log in to an existing account
CreatePost       -> create a post (hashtags like #java are auto-detected)
ViewTrending     -> view the top N trending hashtags
Active           -> view the top K most active users
Hashtag          -> view all posts containing a given hashtag
Report           -> view the full system summary report
```

## Future Enhancements

- Persistent storage (file/database) instead of in-memory data
- REST API layer for web/mobile clients
- User-to-user interactions (likes, comments, follows)
- Unit tests (JUnit) for validation and analytics logic

## Author

Developed as an academic project to apply and demonstrate Object-Oriented Programming principles in Java.
