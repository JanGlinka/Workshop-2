package pl.coderslab;

import pl.coderslab.entity.User;

import java.util.Scanner;

public class MainDao {

    private static final String QUERY1 = "CREATE TABLE users\n" +
            "(\n" +
            "    id       int(11)      not null auto_increment primary key,\n" +
            "    email    varchar(100) not null unique,\n" +
            "    username varchar(255) not null,\n" +
            "    password varchar(60)  not null\n" +
            ")";

    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED_BOLD = "\u001B[31;1m";
    public static final String GREEN = "\u001B[32m";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        UserDao userDao = new UserDao();
        User newUser = new User();

        while (running) {
            System.out.println(YELLOW + "Welcome to the CRUD Application" + RESET);
            System.out.println(BLUE + "Please select an option: " + RESET);
            System.out.println("create");
            System.out.println("read");
            System.out.println("readall");
            System.out.println("update");
            System.out.println("delete");
            System.out.println("exit");

            String option = scanner.nextLine().trim().toLowerCase();

            switch (option) {
                case "create":
                    UserCreation(scanner, newUser, userDao);
                    break;

                case "read":
                    UserRead(scanner, userDao);
                    break;

                case "readall":
                    UserReadAll(userDao);
                    break;

                case "update":
                    UserUpdate(scanner, newUser, userDao);
                    break;

                case "delete":
                    UserDelete(scanner, userDao);
                    break;

                case "exit":
                    running = false;
                    System.out.println(CYAN + "Good Bye" + RESET);
                    break;

                default:
                    System.out.println(RED_BOLD + "Invalid option, please try again." + RESET);
            }
        }

        scanner.close();
    }

    private static void UserCreation(Scanner scanner, User newUser, UserDao userDao) {
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        User createdUser = userDao.create(newUser);
        if (createdUser != null) {
            System.out.println(BLUE + "User created with ID: " + createdUser.getId() + RESET);
        } else {
            System.out.println(RED_BOLD + "User creation failed." + RESET);
        }
    }

    private static void UserRead(Scanner scanner, UserDao userDao) {
        while (true) {
            System.out.println("Please enter the ID you want to search: ");
            if (scanner.hasNextInt()) {
                int userId = scanner.nextInt();
                scanner.nextLine();
                User user = userDao.delete(userId);

                if (user != null) {
                    System.out.println(GREEN + "User found: " + RESET + user.getUsername() + ", " + user.getEmail());
                } else {
                    System.out.println(RED_BOLD + "User not found." + RESET);
                }
                break;
            } else {
                System.out.println(RED_BOLD + "Invalid input. Please enter a valid integer." + RESET);
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static void UserUpdate(Scanner scanner, User newUser, UserDao userDao) {
        System.out.println("Please enter User ID to update");
        int userId = scanner.nextInt();
        scanner.nextLine(); //Consume int otherwise String username gets skipped

        System.out.println("Please enter your username:");
        String username = scanner.nextLine();
        System.out.println("Please enter your email:");
        String email = scanner.nextLine();
        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        newUser.setId(userId);
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        User updatedUser = userDao.update(newUser);
        if (updatedUser != null) {
            System.out.println(CYAN + "User updated: " + updatedUser.getUsername() + ", " + updatedUser.getEmail());
        } else {
            System.out.println(RED_BOLD + "User update failed, no such user" + RESET);
        }
    }

    private static void UserDelete(Scanner scanner, UserDao userDao) {
        while (true) {
            System.out.println("Please enter the ID you want to delete");
            if (scanner.hasNextInt()) {
                int userId = scanner.nextInt();
                scanner.nextLine();
                User user = userDao.delete(userId);

                if (user != null) {
                    System.out.println(RED_BOLD + "User with id " + userId + " has been deleted" + RESET);
                } else {
                    System.out.println(RED_BOLD + "User not found." + RESET);
                }
                break;
            } else {
                System.out.println(RED_BOLD + "Invalid input. Please enter a valid integer." + RESET);
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private static void UserReadAll(UserDao userDao) {
        User[] users = userDao.findAll();
        if (users != null && users.length > 0) {
            for (User user : users) {
                System.out.println(GREEN + "User ID: " + RESET + user.getId() +
                        GREEN + ", Username: " + RESET + user.getUsername() +
                        GREEN + ", Email: " + RESET + user.getEmail());
            }
        } else {
            System.out.println(RED_BOLD + "No users found." + RESET);
        }
    }

}
