import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;

public class TestMenu {
    public static void main(String[] args) {
        ArrayList<Villain> villainArrayList = new ArrayList<>();
        ArrayList<StandUser> standUserArrayList = new ArrayList<>();

        Scanner userInput = new Scanner(System.in);
        boolean stop = false;

        while(!stop) {
            System.out.println("\n== Storage Menu ==");

            System.out.println(
                    """
                    \\/Please Choose an Option\\/
                    
                    1. Create new StandUser(s).
                    2. Create new Villain(s).
                    3. Save Stand Users to Database.
                    4. Read Stand Users From Database.
                    5. Write Villains to holdingCell.txt.
                    6. Read Villains From holdingCell.txt.
                    7. Exit
                    
                    /\\Please Choose an Option/\\
                    """
            );

            System.out.print("Your Choice: ");
            int userChoice = userInput.nextInt();
            userInput.nextLine();


            switch(userChoice) {
                case 1:
                    boolean endChoice = false;

                    while(!endChoice) {
                        System.out.print("\nEnter Stand user's first name: ");
                        String firstName = userInput.nextLine();

                        System.out.print("\nEnter Stand user's last name: ");
                        String lastName = userInput.nextLine();

                        System.out.print("\nEnter Stand's name: ");
                        String standName = userInput.nextLine();

                        System.out.print("\nEnter Stand user's age: ");
                        int age = userInput.nextInt();
                        userInput.nextLine();

                        System.out.print("\nEnter the part the Stand user is from (3-9): ");
                        int fromPart = userInput.nextInt();
                        userInput.nextLine();

                        StandUser createdUser = new StandUser(firstName, lastName, standName, age, fromPart);

                        standUserArrayList.add(createdUser);
                        System.out.println("Stand User successfully created.");

                        System.out.print("\nMake another Stand User? (y\\n): ");
                        endChoice = userInput.nextLine().equalsIgnoreCase("n");
                    }
                    break;
                case 2:
                    boolean endVChoice = false;

                    while(!endVChoice) {
                        System.out.print("\nEnter Villain's first name: ");
                        String firstName = userInput.nextLine();

                        System.out.print("\nEnter Villain's last name: ");
                        String lastName = userInput.nextLine();

                        System.out.print("\nEnter Villain's Stand's name: ");
                        String standName = userInput.nextLine();

                        System.out.print("\nEnter Villain's age: ");
                        int age = userInput.nextInt();
                        userInput.nextLine();

                        System.out.print("\nEnter the part the Villain is from (3-9): ");
                        int fromPart = userInput.nextInt();
                        userInput.nextLine();

                        System.out.print("\nEnter Villain's occupation: ");
                        String occupation = userInput.nextLine();

                        System.out.print("\nEnter the group the Villain is associated with: ");
                        String group = userInput.nextLine();

                        System.out.print("\nEnter Villain's reason for being one: ");
                        String reason = userInput.nextLine();

                        Villain createdVillain = new Villain(firstName, lastName, standName, age, fromPart, occupation, group, reason);

                        villainArrayList.add(createdVillain);
                        System.out.println("Villain successfully created.");

                        System.out.print("\nMake another Villain? (y\\n): ");
                        endVChoice = userInput.nextLine().equalsIgnoreCase("n");
                    }
                    break;
                case 3:
                    try {
                        System.out.print("\n\nConnecting to Database...");

                        Connection connection = null;
                        Class.forName("org.postgresql.Driver");

                        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java");

                        System.out.print("\rSuccessfully Connected to Database.");

                        System.out.print("\rEnsuring Table's Existence...");

                        PreparedStatement sqlCreateTable = connection.prepareStatement(
                                """
                                CREATE TABLE IF NOT EXISTS stand_users(
                                id SERIAL,
                                first_name TEXT NOT NULL,
                                last_name TEXT,
                                stand_name TEXT,
                                age INTEGER,
                                from_part INTEGER
                                );
                                """
                        );
                        sqlCreateTable.execute();
                        sqlCreateTable.close();

                        System.out.print("\rTable Successfully Created or Already Exists.");

                        System.out.print("\rInserting Data...");

                        PreparedStatement sqlInsertInto = connection.prepareStatement("""
                                INSERT INTO stand_users(first_name, last_name, stand_name, age, from_part) VALUES(?, ?, ?, ?, ?);
                                """);

                        for (StandUser su : standUserArrayList) {
                            sqlInsertInto.setString(1, su.getFirstName());
                            sqlInsertInto.setString(2, su.getLastName());
                            sqlInsertInto.setString(3, su.getStandName());
                            sqlInsertInto.setInt(4, su.getAge());
                            sqlInsertInto.setInt(5, su.getFromPart());

                            sqlInsertInto.addBatch();
                        }
                        sqlInsertInto.executeBatch();
                        sqlInsertInto.close();

                        System.out.print("\rAll Data Successfully Inserted.");

                        connection.close();

                        System.out.print("\nPress Enter to Return to Menu: ");
                        userInput.nextLine();
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                case 4:
                    try {
                        System.out.print("\n\nConnecting to Database...");

                        Connection connection = null;
                        Class.forName("org.postgresql.Driver");

                        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/java");

                        System.out.print("\rSuccessfully Connected to Database.");

                        System.out.print("\rGetting Stand Users in Database...");
                        PreparedStatement sqlSelectFrom = connection.prepareStatement("""
                                SELECT * FROM stand_users;
                                """);

                        ResultSet dbStandUsers = sqlSelectFrom.executeQuery();

                        while (dbStandUsers.next()) {
                            System.out.printf("""
                                    \n---
                                    ID: %d
                                    first_name: %s
                                    last_name: %s
                                    stand_name: %s
                                    age: %d
                                    from_part: %d
                                    ---
                                    """, dbStandUsers.getInt(1), dbStandUsers.getString(2), dbStandUsers.getString(3), dbStandUsers.getString(4), dbStandUsers.getInt(5), dbStandUsers.getInt(6));
                        }
                        sqlSelectFrom.close();

                        System.out.print("\nPress Enter to Return to Menu: ");
                        userInput.nextLine();

                        connection.close();
                        break;
                    } catch (SQLException e) {
                        e.printStackTrace();
                        break;
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        break;
                    }
                case 5:
                    try {
                        FileOutputStream fileOutput = new FileOutputStream("src/holdingCell.txt");
                        ObjectOutputStream objectFileOutput = new ObjectOutputStream(fileOutput);

                        for (Villain v : villainArrayList) {
                            objectFileOutput.writeObject(v);
                        }

                        objectFileOutput.close();
                        fileOutput.close();

                        System.out.println("Successfully Saved to holdingCell.txt.");

                        System.out.print("\nPress Enter to Return to Menu: ");
                        userInput.nextLine();
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                case 6:
                    try {
                        FileInputStream fileInput = new FileInputStream("src/holdingCell.txt");
                        ObjectInputStream objectFileInput = new ObjectInputStream(fileInput);

                        ArrayList<Villain> villainReadList = new ArrayList<>();
                        Object temp = null;

                        while(true) {
                            try {
                                temp = objectFileInput.readObject();
                            } catch (EOFException e) {
                                break;
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                            villainReadList.add((Villain) temp);
                        }

                        System.out.println("-- Villains Found in holdingCell.txt --");
                        System.out.println(villainReadList);

                        System.out.print("\nPress Enter to Return to Menu: ");
                        userInput.nextLine();
                        break;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                case 7:
                    System.out.println("Thank you for using my program!\nHave an excellent day :)");
                    userInput.close();
                    stop = true;
                    break;
                default:
                    System.out.print("\n   What you have entered is not an available option. Press Enter to Return to Menu: ");
                    userInput.nextLine();
            }
        }
    }
}