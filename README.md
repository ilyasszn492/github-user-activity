# Task Tracker CLI

**Project Page:** [GitHub Repo](https://github.com/ilyasszn492/github-user-activity)

**Task Tracker** is a command-line tool to track and manage your tasks. You can add, update, delete tasks, mark them as in-progress or done, and list tasks by status. Tasks are stored in a JSON file in the project directory.

## Features

- Add, update, and delete tasks
- Mark tasks as **in-progress** or **done**
- List all tasks or filter by status: todo, in-progress, done

## Task Properties

Each task has:

- **id**: Unique identifier
- **description**: Task description
- **status**: `todo`, `in-progress`, `done`
- **createdAt**: Task creation timestamp
- **updatedAt**: Last update timestamp

## Usage Examples

```bash
# Add a task
task-cli add "Buy groceries"

# Update a task
task-cli update 1 "Buy groceries and cook dinner"

# Delete a task
task-cli delete 1

# Mark status
task-cli mark-in-progress 1
task-cli mark-done 1

# List tasks
task-cli list          # all tasks
task-cli list todo     # todo tasks
task-cli list in-progress
task-cli list done

this is the project url you guys on roadmap.sh : https://roadmap.sh/projects/task-tracker, a great site ofc that helps throughout your journy, this is 100% not correct grammer i didn't sleep 2 days ago whatever...
