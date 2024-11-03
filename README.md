# Hugbúnaðarverkefni 1

- Ásgerður Júlía
- Freydís Xuan
- Hermann Ingi
- Vilborg



## Því ég kann ekki á git

- Pull changes from Main: 
```
git fetch origin
git checkout <local branch>  # Switch to the branch where you want to make changes
git pull origin Main  # Pull changes from the 'Main' branch into 'local branch'
```

- Commita á remote branch:

```
git add .
git commit -m "Your commit message"
git push <remote branch> # (origin <branch-name>)
```


## Database
Núna: H2

Til að skoða local database:

1. Ræsa application
2. Fara inn á `localhost:8080/h2-console`
3. Filla inn upplýsingar
   1. Saved Settings: `Generic H2 (Embedded)`
   2. Setting Name: `Generic H2 (Embedded)`
   3. Driver Class: `org.h2.Driver`
   4. JDBC URL: `jdbc:h2:file:./database/dbfile`
   5. User Name: `sa`
   6. Password: ` `
4. Íta á Connect
5. Til að skoða gögn, skrifið SQL skipun í glugga, ýtið á run



Seinna: postgres



## Uppsetning Viðmóts: localhost:8080

** TODO- laga þetta **

- `/` home page, lists the tasks, and has navbar to logout, settings and home
- `/login` login page, opens if no current user is logged in, else it's linked in the nav?
- `/signup` signup form, adds user to the database if username is not taken
- `/settings` uppdate all user attributes, and delete account (not yet inmplemented)
-
- `/newTask` Create new task, and add to the `/home` page
- `/archive` List of archived tasks, can be unarchived
- More, but not mentioned (don't have their own page in the UI)





## Todo sem er ekki endpoints

- bæta við error message við invalid login eða signup
- css ehv, Er frekar fugly rn
- laga date, viljum hafa það optional