## Clinic Management System

Welcome to the Clinic Management System. Here is a guideline to help you get started to run the code.

> Requirements:
- `JRE / JDK`
- `IDE (IntelliJ, Eclipse, etc.)` 
- `Docker`

### Run the database from docker

Use the terminal and ``cd`` into ``...\Clinic-Management-System\database`` (location of **docker-compose.yml**), then run the following command:

```dockerfile
docker-compose up -d
```

### How to Run

The main function is in ``...\Clinic-Management-System\src\Menu\UserMenu``

Use your IDE to run the main script.

If you get an error that says "There is a problem with your database setup or configuration.", you need to add the library (in ``\lib``) to the modules of your program (IDE setting).

### Help

The database is empty by default.

You will need to register a doctor, as a large part of the functionality relies on having at least one doctor in the system.

You can register a receptionist, then login as the receptionist to add patients and book appointments.

Once you have a doctor and a receptionist account, most of the functionality will be available to you (except for user management).

Create an admin account to do user management functions.
