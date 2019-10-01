package de.iaas.skywalker.sqlite;

import de.iaas.skywalker.models.MapEntryBundle;

import java.sql.*;
import java.util.*;

public class ServiceDBHelper {

    private static String dbName = "grid.db";

    public ServiceDBHelper() {
//        this.selectAllGrid();
    }

//        public static void main(String[] args) {
//        ServiceDBHelper serviceDBHelper = new ServiceDBHelper();
//////        serviceDBHelper.createNewDatabase(dbName);
//////        serviceDBHelper.initDatabaseGrid(dbName);
//        serviceDBHelper.insert("objectstorage", "s3", "aws");
//        serviceDBHelper.insert("objectstorage", "blob", "azure");
//        serviceDBHelper.insert("objectstorage", "storage", "ibm");
//
//        serviceDBHelper.insert("endpoint", "http", "aws");
//        serviceDBHelper.insert("endpoint", "http", "azure");
//        serviceDBHelper.insert("endpoint", "http", "ibm");
//
//        serviceDBHelper.insert("schedule", "schedule", "aws");
//        serviceDBHelper.insert("schedule", "timer", "azure");
//        serviceDBHelper.insert("schedule", "schedule", "ibm");
//
//        serviceDBHelper.insert("database", "dynamo", "aws");
//        serviceDBHelper.insert("database", "cosmosdb", "azure");
//        serviceDBHelper.insert("database", "cloudant", "ibm");
//
//        serviceDBHelper.insert("pubsub", "sns", "aws");
//        serviceDBHelper.insert("pubsub", "eventgrid", "azure");
//        serviceDBHelper.insert("pubsub", "eventstreams", "ibm");
//
//        serviceDBHelper.insert("eventstreaming", "kinsis", "aws");
//        serviceDBHelper.insert("eventstreaming", "eventhubs", "azure");
//        serviceDBHelper.insert("eventstreaming", "eventstreams", "ibm");
//
//        serviceDBHelper.insert("point2point", "sqs", "aws");
//        serviceDBHelper.insert("point2point", "queue", "azure");
//        serviceDBHelper.insert("point2point", "eventstreams", "ibm");
//
//        serviceDBHelper.selectAllGrid();
//        Map<String, String> list = serviceDBHelper.gridSelectForGRN("objectstorage");
//        System.out.println("done");
//    }

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

    public void insert(String grn, String prn, String pf) {
        String sql = "INSERT INTO grid(genericResourceName,providerResourceName, platformName) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grn);
            pstmt.setString(2, prn);
            pstmt.setString(3, pf);
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
        String sql = "SELECT id, genericResourceName, providerResourceName, providerName FROM grid";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("genericResourceName") + "\t" +
                        rs.getString("providerName") + "\t" +
                        rs.getString("providerResourceName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, String> gridSelectForGRN(String genericResourceType){
        Map<String, String> resources = new HashMap<>();
        String sql = "SELECT * FROM " + "grid" + " WHERE " + "genericResourceName" + " = " + "\"" + genericResourceType + "\"";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                resources.put(
                        rs.getString("providerResourceName"),
                        rs.getString("providerName")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return resources;
    }

    public MapEntryBundle<String, String> gridSelectForPRN(String providerResourceName){
        String sql = "SELECT * FROM " + "grid" + " WHERE " + "providerResourceName" + " = " + "\"" + providerResourceName + "\"";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                return new MapEntryBundle<>(
                        rs.getString("genericResourceName"),
                        rs.getString("providerName")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

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
