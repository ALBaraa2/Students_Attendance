package com.example.test_javafx.models;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBModel {
    private static DBModel dbmodel = null;
    Connection con = null;

    //here our queries method
    public DBModel() {
        schemaConnect("attendance");
    }

    public static DBModel getModel() {
        if (dbmodel == null) {
            dbmodel = new DBModel();
        }
        return dbmodel;
    }

    public void connect() {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setServerName("localhost");
        source.setDatabaseName("project_database");
        source.setUser("postgres");
        source.setPassword("feraskhaled30");

        try {
            con = source.getConnection();
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public void schemaConnect(String schema) {
        String sql = "set search_path to '" + schema + "'";
        Statement s1 = null;
        try {
            connect();
            s1 = con.createStatement();
            s1.execute(sql);
            System.out.println("Connected to schema " + schema);
        } catch (SQLException ex) {
        } finally {
            try {
                s1.close();
            } catch (SQLException ex) {
            }

        }

    }

    private void closeEverything() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exit() {
        closeEverything();
        System.out.println("Exiting... \nBye!");
        System.exit(0);
    }

    public ArrayList<String> getBuildings() {
        String sql = "select building from classroom;";
        ArrayList<String> buildings = new ArrayList<>();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                buildings.add(rs.getString(1));
            }
            return buildings;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public ArrayList<String> getABuildingRooms(String building) {
        String sql = "select room_number from classroom where building = ? ;";
        ArrayList<String> rooms = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, building);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                rooms.add(rs.getString(1));
            }
            return rooms;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public boolean isClassCross(String b, String r, String s, int y, String t) {
        String sql = "select count(sec_id) from section " +
                "where building=? and room_number =? and semester=? and "
                + "year=? and time_slot_id= ? "
                + " group by building, room_number,semester,year,time_slot_id;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, b);
            st.setString(2, r);
            st.setString(3, s);
            st.setInt(4, y);
            st.setString(5, t);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt(1));
                return rs.getInt(1) > 0;
            }
            return false;

        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int maxSecID(String c, String s, int y) {
        String sql = "select max(sec_id) from section " +
                "where course_id=? and semester=? and "
                + "year=? ;";
        int max;
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, c);
            st.setString(2, s);
            st.setInt(3, y);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                max = rs.getInt(1);
                if (max > 0) return max;
                else return 0;
            } else return 0;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }


    public String insertSection(String c, String b, String r, String s, int y, String t) {
        String sql = "insert into section (course_id,sec_id,building,room_number,semester,"
                + "year,time_slot_id) values (?,?,?,?,?,?,?);";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, c);
            st.setString(2, String.valueOf(maxSecID(c, s, y) + 1));
            st.setString(3, b);
            st.setString(4, r);
            st.setString(5, s);
            st.setInt(6, y);
            st.setString(7, t);


            if (st.executeUpdate() > 0) {
                System.out.println("\tsection added successfully\n\tsec_id = " + maxSecID(c, s, y) + 1);
                return " section added successfully\n sec_id = " + maxSecID(c, s, y) + 1;

            } else return "";

        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public String getStdName(String id) {
        String sql = "select name from student where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String getStdDept(String id) {
        String sql = "select dept_name from student where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String getInstructorName(String id) {
        String sql = "select name from instructor where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public String getInstructorDept(String id) {
        String sql = "select dept_name from instructor where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1));
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public ArrayList<String> getStdYears(String id) {
        ArrayList<String> years = new ArrayList<>();
        String sql = "select distinct year from takes where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                years.add(rs.getInt(1) + "");
            }
            return years;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<String> getStdSems(String id, int year) {
        ArrayList<String> sems = new ArrayList<>();
        String sql = "select distinct semester from takes where id = ? and year = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                sems.add(rs.getString(1) + "");
            }
            return sems;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<String> getInstructorYears(String id) {
        ArrayList<String> years = new ArrayList<>();
        String sql = "select distinct year from teaches where id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                years.add(rs.getInt(1) + "");
            }
            return years;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<String> getInstructorSems(String id, int year) {
        ArrayList<String> sems = new ArrayList<>();
        String sql = "select distinct semester from teaches where id = ? and year = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                sems.add(rs.getString(1) + "");
            }
            return sems;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String switchDay(String day) {
        switch (day) {
            case "M":
                return "Monday";
            case "F":
                return "Friday";
            case "T":
                return "Tuesday";
            case "R":
                return "Thursday";
            case "W":
                return "Wednesday";
        }
        return "";
    }

    public ArrayList<LectureTime> getStdLectures(String id, String sem, int year) {
        ArrayList<LectureTime> lects = new ArrayList<>();
        String time = "";
        String sql = "select course_id, title, building, room_number, start_hr"
                + ", start_min, end_hr,end_min ,string_agg(day ,' , ' ) as days"
                + " from takes natural join section natural join course natural join time_slot"
                + " where id = ? and year = ? and semester = ?"
                + " group by 1,2,3,4,5,6,7,8 ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            st.setInt(2, year);
            st.setString(3, sem);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                time = "";
                System.out.println(rs.getArray(9).toString());
                String arr = rs.getArray(9).toString();
                String[] days = arr.split(" , ");
                //  rs.getArray(9).toString();
                /*Array arr =  rs.getArray(9);
                String [] days = (String [])(arr.getArray());*/
                for (String day : days) {
                    time += switchDay(day) + " ";
                    System.out.println(day);
                }
                time += "\n";
                time += "From " + rs.getInt(5) + " : " + rs.getInt(6) + " To " + rs.getInt(7) + " : " + rs.getInt(8);
                lects.add(new LectureTime(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), time));
            }
            return lects;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<LectureTime> getInstructorLectures(String id, String sem, int year) {
        ArrayList<LectureTime> lects = new ArrayList<>();
        String time = "";
        String sql = "select course_id, title, building, room_number, start_hr"
                + ", start_min, end_hr,end_min ,string_agg(day ,' , ' ) as days"
                + " from teaches natural join section natural join course natural join time_slot"
                + " where id = ? and year = ? and semester = ?"
                + " group by 1,2,3,4,5,6,7,8 ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            st.setInt(2, year);
            st.setString(3, sem);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                time = "";
                System.out.println(rs.getArray(9).toString());
                String arr = rs.getArray(9).toString();
                String[] days = arr.split(" , ");
                for (String day : days) {
                    time += switchDay(day) + " ";
                    System.out.println(day);
                }
                time += "\n";
                time += "From " + rs.getInt(5) + " : " + rs.getInt(6) + " To " + rs.getInt(7) + " : " + rs.getInt(8);
                lects.add(new LectureTime(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), time));
            }
            return lects;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
//-------------------------------------------------------------------------------------------------//
    public boolean getEmailPassword(String e, String p) {
        String sql = "select email, password" +
                " from users" +
                " where email = ? and password = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, e);
            st.setString(2, p);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String email = rs.getString(1);
                String password = rs.getString(2);
                if (email.equals(e) && password.equals(p)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean isAdmin(String e) {
        String sql = "select user_type" +
                " from users" +
                " where email = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, e);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String user_type = rs.getString(1);
                if (user_type.equals("admin")) {
                    return true;
                } else
                    return false;
            } else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyCourseName(String cId, String cName) {
        String sql = "UPDATE courses " +
                "SET course_name = ? " +
                "WHERE course_id = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, cName);
                st.setString(2, cId);
            if (st.executeUpdate() > 0) {
                return true;
            } else return false;

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyCourseLocation(String cId, String cLocation) {
        String sql = "UPDATE courses " +
                "SET course_location = ? " +
                "WHERE course_id = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, cLocation);
            st.setString(2, cId);
            if (st.executeUpdate() > 0) {
                return true;
            } else return false;

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyInstructorName(String cId, String iName) {
        String sql = "UPDATE courses " +
                "SET instructor_name = ? " +
                "WHERE course_id = ?;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, iName);
            st.setString(2, cId);
            if (st.executeUpdate() > 0) {
                return true;
            } else return false;

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean insertCourse(String cId, String iName, String cName, String cLocation) {
        String sql = "insert into courses (course_id, instructor_name, course_name, course_location) values (?,?,?,?);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, cId);
            st.setString(2, iName);
            st.setString(3, cName);
            st.setString(4, cLocation);
            if (st.executeUpdate() > 0) {
                return true;
            } else return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Courses> getCourses() {
        ArrayList<Courses> c = new ArrayList<>();
        String sql = "select course_id, instructor_name, course_name, course_location, year"
                + " from courses natural join assist ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                c.add(new Courses(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<String> getCourseIDs() {
        String sql = "select course_id from courses;";
        List<String> ids = new ArrayList<>();
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)
        ) {
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
            return ids;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String getcourseName(String id) {
        String sql = "select course_name from courses where course_id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean creatUser(String name, String email, String password, String userType){
        String sql = "insert into users (username, email, password, user_type) values (?,?,?,?);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, password);
            st.setString(4, userType);
            if (st.executeUpdate() > 0) {
                return true;
            } else return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean isPre_Registered(String email){
        String sql = "select email from users where email = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            }else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean isValidEmail(String email){
        String sql = "SELECT check_email_format(?);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            rs.next();
            boolean result = rs.getBoolean(1);
            if (result) {
                return true;
            }else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}