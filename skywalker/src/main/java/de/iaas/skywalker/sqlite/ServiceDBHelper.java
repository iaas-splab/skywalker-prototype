package de.iaas.skywalker.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDBHelper {

    private static String dbName = "grid.db";

    public ServiceDBHelper() {
        this.selectAllGrid();
    }

    //    public static void main(String[] args) {
//        ServiceDBHelper serviceDBHelper = new ServiceDBHelper();
////        serviceDBHelper.createNewDatabase(dbName);
////        serviceDBHelper.initDatabaseGrid(dbName);
////        serviceDBHelper.insert("objectstorage", "s3");
////        serviceDBHelper.insert("objectstorage", "blob");
////        serviceDBHelper.insert("objectstorage", "storage");
////
////        serviceDBHelper.insert("endpoint", "http");
////        serviceDBHelper.insert("endpoint", "http");
////        serviceDBHelper.insert("endpoint", "http");
////
////        serviceDBHelper.insert("schedule", "schedule");
////        serviceDBHelper.insert("schedule", "timer");
////        serviceDBHelper.insert("schedule", "schedule");
////
////        serviceDBHelper.insert("database", "dynamo");
////        serviceDBHelper.insert("database", "cosmosdb");
////        serviceDBHelper.insert("database", "cloudant");
////
////        serviceDBHelper.insert("pubsub", "sns");
////        serviceDBHelper.insert("pubsub", "eventgrid");
////        serviceDBHelper.insert("pubsub", "eventstreams");
////
////        serviceDBHelper.insert("eventstreaming", "kinsis");
////        serviceDBHelper.insert("eventstreaming", "eventhubs");
////        serviceDBHelper.insert("eventstreaming", "eventstreams");
////
////        serviceDBHelper.insert("messagequeueing", "sqs");
////        serviceDBHelper.insert("messagequeueing", "queue");
////        serviceDBHelper.insert("messagequeueing", "eventstreams");
//
////        serviceDBHelper.selectAllGrid();
//        List<String> list = serviceDBHelper.gridSelectForGRN("objectstorage");
//    }
//
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/db/" + this.dbName;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String grn, String prn) {
        String sql = "INSERT INTO grid(genericResourceName,providerResourceName) VALUES(?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grn);
            pstmt.setString(2, prn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initDatabaseGrid(String fileName) {
        String url = "jdbc:sqlite:sqlite/db/" + fileName;
        String sql = "CREATE TABLE IF NOT EXISTS grid (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    genericResourceName text NOT NULL,\n"
                + "    providerResourceName text NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:sqlite/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAllGrid(){
        String sql = "SELECT id, genericResourceName, providerResourceName FROM grid";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("genericResourceName") + "\t" +
                        rs.getString("providerResourceName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> gridSelectForGRN(String genericResourceType){
        List<String> resources = new ArrayList<>();
        String sql = "SELECT * FROM " + "grid" + " WHERE " + "genericResourceName" + " = " + "\"" + genericResourceType + "\"";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                resources.add(rs.getString("providerResourceName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resources;
    }

    public String gridSelectForPRN(String providerResourceName){
        String sql = "SELECT * FROM " + "grid" + " WHERE " + "providerResourceName" + " = " + "\"" + providerResourceName + "\"";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return rs.getString("genericResourceName");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void gridUpdate(int id, String grn, String prn) {
        String sql = "UPDATE grid SET genericResourceName = ? , "
                + "providerResourceName = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, grn);
            pstmt.setString(2, prn);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void gridDelete(int id) {
        String sql = "DELETE FROM grid WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the gridDelete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
