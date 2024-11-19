# Hugbúnaðarverkefni 1 (Software project 1)

- Ásgerður Júlía
- Freydís Xuan
- Hermann Ingi
- Vilborg

Task manager website

[Síðan okkar](https://hugboverkefni1.onrender.com)


## Síður:

- `/` home page, lists the tasks, and has navbar to logout, settings and home
- `/login` login page, opens if no current user is logged in, else it's linked in the nav?
- `/signup` signup form, adds user to the database if username is not taken
- `/settings` update all user attributes, and delete account
-
- `/newTask` Create new task, and add to the `/home` page
- `/archive` List of archived tasks, can be unarchived
- `/categories` List of each users categories, and where they can create new categories
- `/admin` Overview page of all user accounts (only implemented for testing purposes)

## Database

PostgreSQL hosted on render.com


## Implemented funtionalities

1. **Task Management System**: Basic task creation, editing and deletion functionality. Ability to organize tasks into categories (e.g. work, personal) and set priorities. Due date assignment and visual indicators for deadlines.
2. **Searching and filtering**: Search function to locate specific tasks, notes or checklists. Filtering options by due date, priority, tags or categories.
3. **Create account**: Easy account creation process with email and password registration. Basic account management options e.g. abilities to reset passwords and update profile information.


1. **Track task progress**:
   A user works on a task and updates its status from "Not Started" to "In Progress." The system reflects the change, adjusting the visual indicators on the task list. Upon completion, the user marks the task as "Completed" and the system updates the dashboard to show the task as finished.
2. **Pomodoro clock**:
   The user starts a timer for a predefined focus session. The application begins the countdown and shows the remaining time on the screen. When the time is up, the app notifies the user and prompts them to take a short break before the next focus session begins. The timer resets automatically for the next cycle.
3. **Set reminders** (did not do):
   A user selects a task and sets a reminder with a specific date and time. The system schedules a notification and alerts the user when the reminder time approaches. The notification pops up on the user’s device. The user closes the pop-up after reception.
4. **Archive completed tasks**:
   A user reviews their completed tasks and decides to archive them. The system	moves the task for an archive section, which removes them from the main task list. The archived tasks are stored for future reference but do not clutter the active task space.
5. **Add notes to tasks**:
   A user clicks on a task to expand it and adds additional notes or sub-tasks. The system saves the notes attaching them to the task. When the task is reopened the user can view, edit or update the notes as needed.
6. **Create weekly planner** (did not do): 
   A user opens the weekly planner view and drags tasks into specific days of the week. The system updates the planner to reflect assigned tasks for each day. The user reviews their week and the planner displays an organized view of all scheduled activities.
7. **Delete task**:
   A user selects a task from their list that is no longer needed and chooses the delete option. The system confirms the action to prevent accidental deletions. Once confirmed the task is then permanently removed from the task list. The system updates the user’s dashboard reflecting the changes.
8. **User management**:
   Authorised admin can access stored user data. Admins can view detailed user profiles and see a statistical overview of the platform, such as user engagement, account status and activity history.


## Því ég kann ekki á git

- Pull changes from Master:
```
git fetch origin
git checkout <local branch>  # Switch to the branch where you want to make changes
git pull origin Master  # Pull changes from the 'Master' branch into 'local branch'
```

- Commita á remote branch:

```
git add .
git commit -m "Your commit message"
git push <remote branch> # (origin <branch-name>)
```