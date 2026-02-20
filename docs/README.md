# 🐾 Snorax User Guide

> *"The best way to get things done is to simply begin."*

**Snorax** is a desktop chatbot app for managing your tasks — fast, simple, and friendly.
Built with _Java_ and _JavaFX_, it works entirely from a chat interface.

---

## 🚀 Quick Start

1. Ensure you have **[Java 17](https://www.oracle.com/java/technologies/downloads/#java17)** installed
2. Download the latest `snorax.jar` from the [Releases](../../releases) page
3. Run the app:

```bash
java -jar snorax.jar
```

4. Type a command in the chat box and press **Send**
5. Your tasks are automatically saved to `data/snorax.txt`

---

## 📋 Features

- ✅ Add **todo**, **deadline**, and **event** tasks
- ✅ Mark tasks as done or undone
- ✅ Delete tasks
- ✅ Search tasks by keyword
- ✅ Sort tasks by date
- ✅ Duplicate task detection
- ✅ Auto-saves to local storage
- ✅ Persistent across sessions

---

## 💬 Commands

> **Note:** All commands are case-insensitive. Extra spaces are handled automatically.

---

### Add a Todo

A task with no date attached.

**Format:** `todo <description>`

**Example:**
```
todo read textbook
```
```
Got it. I've added this task:
  [T][ ] read textbook
Now you have 1 task(s) in the list.
```

---

### Add a Deadline

A task that must be done by a specific date and time.

**Format:** `deadline <description> /by <yyyy-MM-dd HHmm>`

**Example:**
```
deadline submit assignment /by 2025-03-15 2359
```
```
Got it. I've added this task:
  [D][ ] submit assignment (by: Mar 15 2025, 23:59)
Now you have 2 task(s) in the list.
```

> ⚠️ The date must be **real** (e.g., `2025-02-30` is not allowed).

---

### Add an Event

A task that happens over a time period.

**Format:** `event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>`

**Example:**
```
event team meeting /from 2025-03-10 1400 /to 2025-03-10 1600
```
```
Got it. I've added this task:
  [E][ ] team meeting (from: Mar 10 2025, 14:00 to: Mar 10 2025, 16:00)
Now you have 3 task(s) in the list.
```

> ⚠️ Start time must be **before** end time.

---

### List All Tasks

**Format:** `list`

**Example output:**
```
Here are the tasks in your list:
1. [T][ ] read textbook
2. [D][ ] submit assignment (by: Mar 15 2025, 23:59)
3. [E][ ] team meeting (from: Mar 10 2025, 14:00 to: Mar 10 2025, 16:00)
```

---

### Mark a Task as Done

**Format:** `mark <task number>`

**Example:** `mark 1`
```
Nice! I've marked this task as done:
  [T][X] read textbook
```

---

### Unmark a Task

**Format:** `unmark <task number>`

**Example:** `unmark 1`
```
OK, I've marked this task as not done yet:
  [T][ ] read textbook
```

---

### Delete a Task

**Format:** `delete <task number>`

**Example:** `delete 2`
```
Noted. I've removed this task:
  [D][ ] submit assignment (by: Mar 15 2025, 23:59)
Now you have 2 task(s) in the list.
```

---

### Find Tasks by Keyword

Searches task descriptions (case-insensitive).

**Format:** `find <keyword>`

**Example:** `find meeting`
```
Here are the matching tasks in your list:
1. [E][ ] team meeting (from: Mar 10 2025, 14:00 to: Mar 10 2025, 16:00)
```

---

### Sort Tasks

**Format:**

| Command | Effect |
|---|---|
| `sort` | Sort all tasks by type then date |
| `sort deadline` | Sort deadlines chronologically |
| `sort event` | Sort events chronologically |
| `sort all` | Sort all tasks |

---

### Exit

**Format:** `bye`

Snorax will say goodbye and the app will close.

---

## ⚠️ Error Handling

Snorax will tell you clearly what went wrong:

| Mistake | Example | Error message |
|---|---|---|
| Wrong date format | `deadline task /by 31-12-2025` | Invalid deadline date/time |
| Non-existent date | `deadline task /by 2025-02-30 1200` | Date does not exist |
| Start after end | `event e /from 2025-03-10 1600 /to 2025-03-10 1400` | Start must be before end |
| Duplicate task | Adding same task twice | Duplicate task already exists |
| Missing description | `todo` | Missing argument |
| Invalid task number | `delete 99` | Task does not exist |

---

## 📁 Data Storage

- Tasks are saved automatically to **`data/snorax.txt`**
- You do **not** need to save manually
- Do **not** edit `snorax.txt` manually — it may corrupt your data
- If the file is missing, Snorax will create a new one automatically

---

## 🐛 Known Limitations

- ~~Recurring tasks~~ not yet supported
- GUI tested on macOS; minor visual differences may appear on Windows/Linux

---

*Made with ❤️ by Ray*
