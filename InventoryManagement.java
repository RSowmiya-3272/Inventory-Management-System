package javaProgram;

import java.sql.*;
import java.util.Scanner;

public class InventoryManagement {

    static final String URL = "jdbc:mysql://localhost:3306/inventorydb";
    static final String USER = "root";
    static final String PASS = "2006";

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        Connection con = DriverManager.getConnection(URL, USER, PASS);

        while (true) {

            System.out.println("\n===== INVENTORY MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Quantity");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    sc.nextLine();

                    System.out.print("Enter Product Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    String insertQuery =
                            "INSERT INTO products(name, quantity, price) VALUES(?,?,?)";

                    PreparedStatement insertStmt =
                            con.prepareStatement(insertQuery);

                    insertStmt.setString(1, name);
                    insertStmt.setInt(2, quantity);
                    insertStmt.setDouble(3, price);

                    insertStmt.executeUpdate();

                    System.out.println("Product Added Successfully!");

                    break;

                case 2:

                    String selectQuery = "SELECT * FROM products";

                    Statement stmt = con.createStatement();

                    ResultSet rs = stmt.executeQuery(selectQuery);

                    System.out.println("\nID\t\tNAME\t\tQTY\t\tPRICE");

                    while (rs.next()) {

                        System.out.println(
                                rs.getInt("id") + "\t" +
                                rs.getString("name") + "\t" +
                                rs.getInt("quantity") + "\t" +
                                rs.getDouble("price")
                        );
                    }

                    break;

                case 3:

                    System.out.print("Enter Product ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Quantity: ");
                    int newQty = sc.nextInt();

                    String updateQuery =
                            "UPDATE products SET quantity=? WHERE id=?";

                    PreparedStatement updateStmt =
                            con.prepareStatement(updateQuery);

                    updateStmt.setInt(1, newQty);
                    updateStmt.setInt(2, updateId);

                    int rowsUpdated = updateStmt.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("Quantity Updated!");
                    } else {
                        System.out.println("Product Not Found!");
                    }

                    break;

                case 4:

                    System.out.print("Enter Product ID to Delete: ");
                    int deleteId = sc.nextInt();

                    String deleteQuery =
                            "DELETE FROM products WHERE id=?";

                    PreparedStatement deleteStmt =
                            con.prepareStatement(deleteQuery);

                    deleteStmt.setInt(1, deleteId);

                    int rowsDeleted = deleteStmt.executeUpdate();

                    if (rowsDeleted > 0) {
                        System.out.println("Product Deleted!");
                    } else {
                        System.out.println("Product Not Found!");
                    }

                    break;

                case 5:

                    System.out.println("Exiting...");
                    con.close();
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}