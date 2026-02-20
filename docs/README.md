# 🐾 Snorax — Task Management Chatbot

> "The best way to get things done is to simply begin." — unknown

Snorax is a **desktop chatbot** for managing your tasks, built with _Java_ and _JavaFX_.

## Features
- Add `todo`, `deadline`, and `event` tasks
- Mark tasks as **done** or ~~not done~~
- Find tasks by keyword
- Auto-saves to local file storage
- Duplicate task detection

## How to Run
1. Ensure you have **Java 17** installed
2. Clone this repository
3. Run the following command:

```bash
./gradlew run
```

4. Type commands in the chat box and press **Send**

## Commands

| Command | Format |
|---|---|
| Add todo | `todo <description>` |
| Add deadline | `deadline <desc> /by <yyyy-MM-dd HHmm>` |
| Add event | `event <desc> /from <datetime> /to <datetime>` |
| Delete | `delete <index>` |
| Find | `find <keyword>` |

## Progress Checklist
- [x] Todo tasks
- [x] Deadline tasks
- [x] Event tasks
- [x] GUI with JavaFX
- [x] Persistent storage
- [ ] Recurring tasks

## Resources
- [JavaFX Documentation](https://openjfx.io/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
