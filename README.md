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

- `/` User login page, currently used as an admin page
- `/signup` signup form, adds user to the database if username is not taken
- `/update-username` Update username
- `/home` The home page of all tasks
- `/home/newTask` Create new task, and add to the `/home` page
- `/home/archive` List of archived tasks, can be unarchived
- More, but not mentioned (don't have their own page in the UI)


- `/categories` List of categories
- `/categories/new` Make new category, now working because we don't have userId

