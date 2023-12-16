package File.Database;

import java.sql.*;
public class DB_Class {
    public Connection connection;

    public DB_Class() {
        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/", "root", "");
        }catch (SQLException connectException){
            System.out.println("Unable to connect to the database. " + connectException.getMessage());
            System.exit(1);
        }
        createDataBase();
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost/ccats", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createDataBase(){
        try{
            Statement statement = connection.createStatement();
            String command = "CREATE DATABASE IF NOT EXISTS ccats";
            statement.execute(command);
            System.out.println("Database has been created successfully.");
        }catch (SQLException e){
            System.out.println("Error creating database: " + e.getMessage());
        }

        try{
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/ccats", "root", "");
            Statement states = connection.createStatement();

            try{// For Table admin
                String command = "CREATE TABLE IF NOT EXISTS admin (" +
                        "username VARCHAR(224)," +
                        "password VARCHAR(224)," +
                        "position VARCHAR(224)," +
                        "agentID int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "age int(3))";
                states.executeUpdate(command);
                System.out.println("Table 'admin' has been created successfully.");
            }catch (SQLException e){
                System.out.println("Error creating table 'admin': " + e.getMessage());
            }

            try{// For Table agentInfo
                String command = "CREATE TABLE IF NOT EXISTS agentInfo (" +
                        "agentID int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "agentFullName VARCHAR(224)," +
                        "agentAddress VARCHAR(224)," +
                        "agentAge int(3)," +
                        "agentGender VARCHAR(224)," +
                        "agentStatus VARCHAR(224)," +
                        "dateHire date,"+
                        "agentPassword VARCHAR(224))"+
                        "Auto_Increment = 1000";
                states.executeUpdate(command);
                System.out.println("Table 'agentInfo' has been created successfully.");
            }catch (SQLException e){
                System.out.println("Error creating table 'agentInfo': " + e.getMessage());
            }

            try{// For Table companyInfo
                String command = "CREATE TABLE IF NOT EXISTS companyInfo (" +
                        "companyID int(4) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "companyName VARCHAR(224)," +
                        "companyService VARCHAR(224)," +
                        "companyNo VARCHAR(224))"+
                        "Auto_Increment = 7000";
                states.executeUpdate(command);
                System.out.println("Table 'companyInfo' has been created successfully.");
            }catch (SQLException e){
                System.out.println("Error creating table 'companyInfo': " + e.getMessage());
            }

            try {
                String command = "CREATE TABLE IF NOT EXISTS callHistory (" +
                        "callDate DATE," +
                        "agentIDs INT," +
                        "companyNames VARCHAR(224)," +
                        "companyServices VARCHAR(224)," +
                        "ticket INT(5) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "callDuration VARCHAR(224)," +
                        "FOREIGN KEY (agentIDs) REFERENCES agentInfo (agentID)," +
                        "FOREIGN KEY (companyNames) REFERENCES companyInfo (companyName), " +
                        "FOREIGN KEY (companyServices) REFERENCES companyInfo (companyService))" +
                        "Auto_Increment = 10000";

                states.executeUpdate(command);
                System.out.println("Table 'callHistory' has been created successfully.");
            } catch (SQLException e) {
                System.out.println("Error creating table 'callHistory': " + e.getMessage());
            }

            try {
                String command = "CREATE TABLE IF NOT EXISTS clientinfo (" +
                        "client_ID int(5) NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                        "client_fullname VARCHAR(224)," +
                        "client_phoneNum VARCHAR(13)," +
                        "client_Age VARCHAR(4)," +
                        "client_address VARCHAR(224)," +
                        "client_gender VARCHAR(224)," +
                        "client_Username VARCHAR(50)," +
                        "cLient_Password VARCHAR(50))"+
                        "Auto_Increment = 9000";

                states.executeUpdate(command);
                System.out.println("Table 'clientinfo' has been created successfully.");
            } catch (SQLException e) {
                System.out.println("Error creating table 'clientinfo': " + e.getMessage());
            }

            try {
                String command = "CREATE TABLE IF NOT EXISTS inbound_calls (" +
                        "called_in Time," +
                        "callDates DATE," +
                        "companyNames VARCHAR(224)," +
                        "companyServices VARCHAR(224)," +
                        "client_IDs int(5)," +
                        "client_fullnames VARCHAR(224)," +
                        "client_phoneNums VARCHAR(13)," +
                        "FOREIGN KEY (client_IDs) REFERENCES clientinfo (client_ID)," +
                        "FOREIGN KEY (client_fullnames) REFERENCES clientinfo (client_fullname)," +
                        "FOREIGN KEY (client_phoneNums) REFERENCES clientinfo (client_phoneNum)," +
                        "FOREIGN KEY (companyNames) REFERENCES companyInfo (companyName), " +
                        "FOREIGN KEY (callDates) REFERENCES callHistory (callDate), " +
                        "FOREIGN KEY (companyServices) REFERENCES companyInfo (companyService))";

                states.executeUpdate(command);
                System.out.println("Table 'inbound_call' has been created successfully.");
            } catch (SQLException e) {
                System.out.println("Error creating table 'inbound_call': " + e.getMessage());
            }

        }catch (SQLException connectException){
            System.out.println("Unable to connect to the database. " + connectException.getMessage());
            System.exit(1);
        }
    }

    // Method to create an admin account
    public void createPresetAdminAccount() {
        try {
            String username = "admin";
            String password = "123qwe";
            String position = "Administrator";
            int age = 22;


            if (isUsernameAlreadyExists(username)) {
                System.out.println("Admin account already exists in the database.");
                return;
            }

            String insertQuery = "INSERT INTO admin (username, password, position, age) VALUES (?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, position);
            preparedStatement.setInt(4, age);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Admin account created successfully.");
            } else {
                System.out.println("Failed to create admin account.");
            }

            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error creating admin account: " + e.getMessage());
        }
    }

    private boolean isUsernameAlreadyExists(String username) {
        try {
            String query = "SELECT * FROM admin WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                return true;
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error checking if username exists: " + e.getMessage());
        }
        return false;
    }








}
