# API partur af verkefni

## Yfirlit yfir routes í API, tilbúnar og ekki tilbúnar

- UserController
- [x] `POST /login`
- [x] `POST /signup`
- [x] `GET /admin`
- [x] `PATCH /update` 
- [x] `DELETE /admin/delete/{id}` Virkar, en ætti að vera bara fyrir admin en nn ekki því þá þurfum við að laga database og svoleiðis
- [x] `DELETE /delete/me`

- TaskController
- [x] `GET /tasks`
- [ ] `POST /tasks`
- [ ] `PATCH /tasks/{taskid}`
- [ ] `DELTETE /tasks/{taskid}`
- [ ] `GET /task/{taskId}`
- [ ] `POST /favorites/add/{taskId}`
- [ ] `POST /favorites/remove/{taskId}`
- [ ] `GET /archive`
- [ ] `POST /archive/{taskId}`
- [ ] `POST /archive/unarhive/{taskId}`

- CategoryController
- [x] `GET /categories`
- [ ] `POST /categories`
- [ ] `PATCH /categories/{catId}`
- [ ] `DELTETE /categories/{catId}`
- [ ] `GET /categories/{catId}`
