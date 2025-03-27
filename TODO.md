# API routes

## 👤 User Routes

- [x] ``POST /login`` — Log in user and return a JWT token
- [x] ``POST /signup`` — Register a new user with username, email, and password
- [x] ``PATCH /update`` — Update current user’s username, email, or password
- [x] ``POST /upload-pic`` — Upload and update user’s profile picture (multipart/form-data, key = `image`)
- [x] ``DELETE /delete/me`` — Delete the currently authenticated user
- [x] ``GET /admin`` — Return all users (open for now, intended for admin use)


## ✅ Task Routes

- [x] ``GET /tasks`` — Get all tasks for the logged-in user (optionally filter with query params like `?favorite=true`)
- [x] ``GET /tasks/{id}`` — Get a specific task by its ID
- [ ] ``POST /tasks`` — Create a new task (currently returns a placeholder)
- [ ] ``PATCH /tasks/{id}`` — Update a task by ID (currently returns a placeholder)
- [ ] ``DELETE /tasks/{id}`` — Delete a task by ID (currently returns a placeholder)

#### ⭐ Favorite-related

- [x] ``PUT /tasks/{id}/favorite`` — Mark a task as favorite
- [x] ``PUT /tasks/{id}/unfavorite`` — Unmark a task as favorite

#### 🗃 Archive-related

- [x] ``GET /tasks/archives`` — Get all archived tasks (currently returns a placeholder)
- [x] ``PUT /tasks/{id}/archive`` — Archive a task
- [x] ``PUT /tasks/{id}/unarchive`` — Unarchive a task

---

## 🗂 Category Routes

- [x] ``GET /categories`` — Get all categories for the logged-in user
- [x] ``GET /categories/{id}`` — Get a specific category by ID (returns message placeholder)
- [ ] ``POST /categories`` — Create a new category (returns message placeholder)
- [ ] ``PATCH /categories/{id}`` — Update a category by ID (returns message placeholder)
- [ ] ``DELETE /categories/{id}`` — Delete a category by ID (returns message placeholder)

