package com.example.test_javafx.models;

import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBModel {
    private static DBModel dbmodel = null;
    Connection con = null;

    //here our queries method
    public DBModel() {
        schemaConnect("project");
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
        source.setDatabaseName("project");
        source.setUser("postgres");
        source.setPassword("123");

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

    //============================================================================================================//
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
        String sql = "select course_id, instructor_name, course_name, course_location"
                + " from courses ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                c.add(new Courses(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4)));
            }
            return c;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<String> getCourseIDs() {
        ArrayList<String> ids = new ArrayList<>();
        String sql = "select course_id from courses;";
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
    public ArrayList<String> getCourseIDsFromAttendance() {
        ArrayList<String> ids = new ArrayList<>();
        String sql = "select distinct course_id from attendance;";
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

    public ArrayList<String> getYears(String course_id) {
        String sql = "select distinct year from section " +
                " WHERE course_id = ? " +
                "GROUP BY year ;";
        ArrayList<String> years = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                years.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return years;
    }

    public ArrayList<String> getTA_id() {
        String sql = "select DISTINCT id from teach_assistant;";
        ArrayList<String> ids = new ArrayList<>();
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


    public ArrayList<String> getSemesters(String course_id, int year) {
        String sql = "select distinct semester from section" +
                " WHERE course_id = ? and year = ? " +
                "GROUP BY semester ;";
        ArrayList<String> semesters = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                semesters.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return semesters;
    }

    public ArrayList<String> getSecIds(String course_id, int year, String semester) {
        String sql = "select distinct sec_id from section" +
                " WHERE course_id = ? and year = ? and semester = ? " +
                "GROUP BY sec_id;";
        ArrayList<String> semesters = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setInt(2, year);
            st.setString(3, semester);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                semesters.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return semesters;
    }
    public ArrayList<String> getLectureIds(String course_id, String year, String semester , String sec_id) {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select distinct lecture_id from lectures" +
                " WHERE course_id = ? and year = CAST(? AS INTEGER) and semester = ? and sec_id = CAST(? AS INTEGER)" +
                "GROUP BY lecture_id;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setString(2, year);
            st.setString(3, semester);
            st.setString(4 , sec_id);
            System.out.println(course_id + " " + year + " " + semester + " " + sec_id );
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return arr;
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

    public boolean creatUser(String name, String email, String password, String userType) {
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

    public void addEnrollment(String course, String year, String semester, String sec, String assistantId) {
        String sql = "INSERT INTO assist (course_id, year, semester, sec_id, assistant_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course);
            st.setInt(2, Integer.parseInt(year));
            st.setString(3, semester);
            st.setInt(4, Integer.parseInt(sec));
            st.setInt(5, Integer.parseInt(assistantId));
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public boolean isPre_Registered(String email) {
        String sql = "select email from users where email = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return true;
            } else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean checkEnrollments(String course, String year, String semester, String sec) {
        String sql = "SELECT COUNT(*) FROM assist WHERE course_id = ? AND year = ? AND semester = ? AND sec_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course);
            st.setInt(2, Integer.parseInt(year));
            st.setString(3, semester);
            st.setInt(4, Integer.parseInt(sec));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }


    public String getAssistantId(String course, String year, String semester, String sec) {
        String id = "";
        String sql = "SELECT assistant_id FROM assist WHERE course_id = ? AND year = ? AND semester = ? AND sec_id = ? LIMIT 1";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course);
            st.setInt(2, Integer.parseInt(year));
            st.setString(3, semester);
            st.setInt(4, Integer.parseInt(sec));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                id = String.valueOf(rs.getInt(1));
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public boolean isValidEmail(String email) {
        String sql = "SELECT check_email_format(?);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            rs.next();
            boolean result = rs.getBoolean(1);
            if (result) {
                return true;
            } else
                return false;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<Lectures> getLectures(String course_id, int year, String semester, int sec_id) {
        ArrayList<Lectures> lects = new ArrayList<>();
        String sql = "select course_id, lecture_id, lecture_title, lecture_time, lecture_date, lecture_location " +
                "from lectures " +
                "where course_id = ? and year = ? and semester = ? and sec_id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setInt(2, year);
            st.setString(3, semester);
            st.setInt(4, sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                lects.add(new Lectures(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getTime(4), rs.getDate(5), rs.getString(6)));
            }
            return lects;
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean deleteCourse(String cid) {
        String sql = "DELETE FROM courses" +
                " WHERE course_id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, cid);
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean deleteLecture(String lid, int year, String semester, String course_id, int sec_id) {
        String sql = "DELETE FROM lectures " +
                " WHERE lecture_id  = ? and  year = ? and semester = ? and course_id  = ? and sec_id = ? ;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, lid);
            st.setInt(2, year);
            st.setString(3, semester);
            st.setString(4, course_id);
            st.setInt(5, sec_id);
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean insertLecture(String title, String date, String time, String location, String course_id, String year, String semester, String sec_id) {
        String sql = "INSERT INTO lectures (lecture_id, lecture_title, lecture_date, lecture_time, lecture_location, " +
                "course_id, year, semester, sec_id) VALUES (generate_next_lecture_id(?, CAST(? AS INTEGER), ?, CAST(? AS INTEGER)), ?, CAST(? AS DATE), CAST(? AS TIME), ?, ?, CAST(? AS INTEGER), ?, CAST(? AS INTEGER));";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setString(2, year);
            st.setString(3, semester);
            st.setString(4, sec_id);
            st.setString(5, title);
            st.setString(6, date);
            st.setString(7, time);
            st.setString(8, location);
            st.setString(9, course_id);
            st.setString(10, year);
            st.setString(11, semester);
            st.setString(12, sec_id);
            if (st.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyLectureDate(String id, String course_id, String year, String semester, String sec_id, String date) {
        String sql = "UPDATE lectures " +
                "SET lecture_date = CAST(? AS DATE) " +
                "WHERE lecture_id = ? " +
                "AND course_id = ? AND year = CAST(? AS INTEGER) AND semester = ? AND sec_id = CAST(? AS INTEGER);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, date);
            st.setString(2, id);
            st.setString(3, course_id);
            st.setString(4, year);
            st.setString(5, semester);
            st.setString(6, sec_id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean addStudent(String studentId, String studentName, String street, String city, String gender, List<String> phoneNumbers) {
        String sql = "INSERT INTO students (student_id, student_name, student_address, gender, student_phone) VALUES (?, ?, ROW(?, ?)::address, ?, ?)";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, studentId);
            st.setString(2, studentName);
            st.setString(3, city);
            st.setString(4, street);
            st.setString(5, gender);
            st.setArray(6, con.createArrayOf("VARCHAR", phoneNumbers.toArray()));

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public ArrayList<AttendanceSheet> attendanceSheet(String lid, String course_id, String year, String semester, String sec_id) {
        ArrayList<AttendanceSheet> arr = new ArrayList<>();
        String sql = "SELECT student_name, student_id, attendance_status " +
                "FROM attendance NATURAL JOIN students " +
                "WHERE lecture_id = ? " +
                "      AND course_id = ? " +
                "      AND year = CAST(? AS INTEGER) " +
                "      AND semester = ? " +
                "      AND sec_id = CAST(? AS INTEGER);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, lid);
            st.setString(2, course_id);
            st.setString(3, year);
            st.setString(4, semester);
            st.setString(5, sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                arr.add(new AttendanceSheet(rs.getString(1) ,rs.getString(2) ,rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr ;
    }
    public String attendance_percentage(String lid, String course_id, String year, String semester, String sec_id){
        String s = "";
        String sql = "SELECT (COUNT(CASE WHEN attendance_status = 'yes' THEN 1 END) * 100 / COUNT(*)) " +
                "FROM attendance " +
                "WHERE lecture_id = ? " +
                "      AND course_id = ? " +
                "      AND year = CAST(? AS INTEGER) " +
                "      AND semester = ? " +
                "      AND sec_id = CAST(? AS INTEGER) " +
                "GROUP BY lecture_id, course_id, year, semester, sec_id; ";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, lid);
            st.setString(2, course_id);
            st.setString(3, year);
            st.setString(4, semester);
            st.setString(5, sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                s = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s ;
    }

    public String attendance_count(String lid, String course_id, String year, String semester, String sec_id){
        String s = "";
        String sql = "SELECT SUM(CASE WHEN attendance_status = 'yes' THEN 1 ELSE 0 END) AS attendance_count " +
                "FROM attendance " +
                "WHERE lecture_id = ? " +
                "      AND course_id = ? " +
                "      AND year = CAST(? AS INTEGER) " +
                "      AND semester = ? " +
                "      AND sec_id = CAST(? AS INTEGER) " +
                "GROUP BY lecture_id, course_id, year, semester, sec_id; ";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, lid);
            st.setString(2, course_id);
            st.setString(3, year);
            st.setString(4, semester);
            st.setString(5, sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                s = rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s ;
    }
    public String student_count(String lid, String course_id, String year, String semester, String sec_id){
        String s = "";
        String sql = "SELECT COUNT(*) " +
                "FROM attendance " +
                "WHERE lecture_id = ? " +
                "      AND course_id = ? " +
                "      AND year = CAST(? AS INTEGER) " +
                "      AND semester = ? " +
                "      AND sec_id = CAST(? AS INTEGER) " +
                "GROUP BY lecture_id, course_id, year, semester, sec_id; ";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, lid);
            st.setString(2, course_id);
            st.setString(3, year);
            st.setString(4, semester);
            st.setString(5, sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                s = rs.getString(1);
            }
        } catch (SQLException ex) {

            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);

        }
        return s ;
    }

    public boolean checkStudentExists(String id) {
        String sql = "SELECT COUNT(*) FROM students WHERE student_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    public List<String> getAllStudentIds() {
        List<String> studentIds = new ArrayList<>();

        String sql = "SELECT DISTINCT student_id FROM students";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("student_id");
                studentIds.add(studentId);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return studentIds;
    }
    public List<String> getStudentPhoneNumbers(String studentId) {
        List<String> phoneNumbers = new ArrayList<>();
        String sql = "SELECT student_phone FROM phone where student_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, studentId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                String phoneNumber = rs.getString("student_phone");
                phoneNumbers.add(phoneNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return phoneNumbers;
    }

    public boolean updateStudentName(String studentId, String newName) {
        String sql = "UPDATE students SET student_name = ? WHERE student_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newName);
            st.setString(2, studentId);

            int rowsAffected = st.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean updateStudentPhoneNumber(String studentId, String oldPhoneNumber, String newPhoneNumber) {
        String sql = "UPDATE phone SET student_phone = ? WHERE student_id = ? AND student_phone = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newPhoneNumber);
            st.setString(2, studentId);
            st.setString(3, oldPhoneNumber);

            int rowsAffected = st.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean updateStudentCity(String studentId, String newCity) {
        String sql = "UPDATE students SET student_address.city = ? WHERE student_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newCity);
            st.setString(2, studentId);

            int rowsAffected = st.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public boolean updateStudentStreet(String studentId, String newStreet) {
        String sql = "UPDATE students SET student_address.street = ? WHERE student_id = ?";

        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, newStreet);
            st.setString(2, studentId);

            int rowsAffected = st.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
        public boolean updateStudentGender(String studentId, String newGender) {
            String sql = "UPDATE students SET gender = ? WHERE student_id = ?";

            try (PreparedStatement st = con.prepareStatement(sql)) {
                st.setString(1, newGender);
                st.setString(2, studentId);

                int rowsAffected = st.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException ex) {
                Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    public boolean modifyLectureTime(String id, String course_id, String year, String semester, String sec_id, String time) {
        String sql = "UPDATE lectures " +
                "SET lecture_time = CAST(? AS TIME) " +
                "WHERE lecture_id = ? " +
                "AND course_id = ? AND year = CAST(? AS INTEGER) AND semester = ? AND sec_id = CAST(? AS INTEGER);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, time);
            st.setString(2, id);
            st.setString(3, course_id);
            st.setString(4, year);
            st.setString(5, semester);
            st.setString(6, sec_id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyLectureTitle(String id, String course_id, String year, String semester, String sec_id, String title) {
        String sql = "UPDATE lectures " +
                "SET lecture_title = ? " +
                "WHERE lecture_id = ? " +
                "AND course_id = ? AND year = CAST(? AS INTEGER) AND semester = ? AND sec_id = CAST(? AS INTEGER);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, title);
            st.setString(2, id);
            st.setString(3, course_id);
            st.setString(4, year);
            st.setString(5, semester);
            st.setString(6, sec_id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean modifyLectureLocation(String id, String course_id, String year, String semester, String sec_id, String location) {
        String sql = "UPDATE lectures " +
                "SET lecture_location = ? " +
                "WHERE lecture_id = ? " +
                "AND course_id = ? AND year = CAST(? AS INTEGER) AND semester = ? AND sec_id = CAST(? AS INTEGER);";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, location);
            st.setString(2, id);
            st.setString(3, course_id);
            st.setString(4, year);
            st.setString(5, semester);
            st.setString(6, sec_id);
            return st.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public ArrayList<String> getLectureId(String course_id, String year, String semester , String sec_id) {
        ArrayList<String> arr = new ArrayList<>();
        String sql = "select distinct lecture_id from lectures " +
                " WHERE course_id = ? and year = CAST(? AS INTEGER) and semester = ? and sec_id = CAST(? AS INTEGER)" +
                "GROUP BY lecture_id;";
        try (PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, course_id);
            st.setString(2, year);
            st.setString(3, semester);
            st.setString(4 , sec_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                arr.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return arr;
    }
}



