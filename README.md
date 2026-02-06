# iP Submission - Level-10 Enhancement

This PR adds several improvements to the Snorax chatbot application.

## Features Added

**Major Changes:**
- Text UI improvements
- Parser enhancements with better error handling
- `find` command implementation for searching tasks

**Technical Details:**
1. Refactored command parsing logic
2. Added comprehensive error messages
3. Implemented search functionality
4. Updated storage format

### Code Example

Here's how the new `find` command works:

```java
public class FindCommand extends Command {
    private String keyword;
    
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        return ui.showFindResults(tasks.find(keyword));
    }
}
