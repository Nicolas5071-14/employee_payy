package com.cbozan.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import java.util.List;

import com.cbozan.entity.Employer;
import com.cbozan.entity.Employer.EmployerBuilder;
import com.cbozan.exception.EntityException;
import java.util.Date;

public class EmployerDAO {

    private final HashMap<Integer, Employer> cache = new HashMap<>();
    private boolean usingCache = true;

    private EmployerDAO() {
        list();
    }

    public Employer findById(int employer_id) {

        if (usingCache == false) {
            list();
        }

        if (cache.containsKey(employer_id)) {
            return cache.get(employer_id);
        }
        return null;
    }

    public List<Employer> list() {

        List<Employer> list = new ArrayList<>();

        if (cache.size() != 0 && usingCache) {
            for (Entry<Integer, Employer> employer : cache.entrySet()) {
                list.add(employer.getValue());
            }

            return list;
        }

        cache.clear();

        Connection conn;
        Statement st;
        ResultSet rs;
        String query = "SELECT * FROM employer;";

        try {
            conn = DB.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            EmployerBuilder builder;
            Employer employer;

            while (rs.next()) {

                builder = new EmployerBuilder();
                builder.setEmployer_id(rs.getInt("employer_id"));
                builder.setName(rs.getString("name"));
                builder.setSurname(rs.getString("surname"));
                builder.setPhonenumber(rs.getString("phonenumber"));

                builder.setBusiness(rs.getString("business"));
                builder.setDate(rs.getTimestamp("date"));

                try {

                    employer = builder.build();
                    list.add(employer);
                    cache.put(employer.getEmployer_id(), employer);

                } catch (EntityException e) {
                    showEntityException(e, rs.getString("name") + " " + rs.getString("surname"));
                }

            }

        } catch (SQLException e) {
            showSQLException(e);
        }

        return list;
    }

    public boolean create(Employer employer) {

        if (createControl(employer) == false) {
            return false;
        }

        Connection conn;
        PreparedStatement pst;
        int result = 0;
        String query = "INSERT INTO employer (name, surname, phonenumber, business, date) VALUES (?,?,?,?, ?);";
        //String query = "INSERT INTO employer (fname,lname,tel,description) VALUES (?,?,?,?);";
        String query2 = "SELECT * FROM employer ORDER BY employer_id DESC LIMIT 1;";

        try {
            conn = DB.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employer.getName());
            pst.setString(2, employer.getSurname());
            pst.setString(3, employer.getPhonenumber());

            pst.setString(4, employer.getBusiness());
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            pst.setDate(5, date);
            result = pst.executeUpdate();

            // adding cache
            if (result != 0) {

                ResultSet rs = conn.createStatement().executeQuery(query2);
                while (rs.next()) {

                    EmployerBuilder builder = new EmployerBuilder();
                    builder.setEmployer_id(rs.getInt("employer_id"));
                    builder.setName(rs.getString("name"));
                    builder.setSurname(rs.getString("surname"));
                    builder.setPhonenumber(rs.getString("phonenumber"));
                    builder.setBusiness(rs.getString("business"));
                    builder.setDate(rs.getTimestamp("date"));

                    try {

                        Employer emp = builder.build();
                        cache.put(emp.getEmployer_id(), emp);

                    } catch (EntityException e) {
                        showEntityException(e, rs.getString("name") + " " + rs.getString("surname"));
                    }
                }

            }

        } catch (SQLException e) {
            showSQLException(e);
        }

        return result == 0 ? false : true;
    }

    private boolean createControl(Employer employer) {

        for (Entry<Integer, Employer> obj : cache.entrySet()) {
            if (obj.getValue().getName().equals(employer.getName())
                    && obj.getValue().getSurname().equals(employer.getSurname())) {

                DB.ERROR_MESSAGE = obj.getValue().getName() + " " + obj.getValue().getSurname() + " Vous êtes deja inscrit.";
                return false;
            }
        }

        return true;
    }

    public boolean update(Employer employer) {

        if (updateControl(employer) == false) {
            return false;
        }

        Connection conn;
        PreparedStatement pst;
        int result = 0;
        String query = "UPDATE employer SET name=?,"
                + "surname=?, phonenumber=?, business=?, date=? WHERE employer_id=?;";

        try {
            conn = DB.getConnection();
            pst = conn.prepareStatement(query);
            pst.setString(1, employer.getName());
            pst.setString(2, employer.getSurname());
            pst.setString(3, employer.getPhonenumber());
            pst.setString(4, employer.getBusiness());
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            pst.setDate(5, date);
            pst.setInt(6, employer.getEmployer_id());

            result = pst.executeUpdate();

            // update cache
            if (result != 0) {
                cache.put(employer.getEmployer_id(), employer);
            }

        } catch (SQLException e) {
            showSQLException(e);
        }

        return result == 0 ? false : true;
    }

    private boolean updateControl(Employer employer) {
        for (Entry<Integer, Employer> obj : cache.entrySet()) {
            if (obj.getValue().getName().equals(employer.getName())
                    && obj.getValue().getSurname().equals(employer.getSurname())
                    && obj.getValue().getEmployer_id() != employer.getEmployer_id()) {
                DB.ERROR_MESSAGE = obj.getValue().getName() + " " + obj.getValue().getSurname() + " Vous êtes deja inscrit ";
                return false;
            }
        }
        return true;
    }

    public boolean delete(Employer employer) {

        Connection conn;
        PreparedStatement ps;
        int result = 0;
        String query = "DELETE FROM employer WHERE employer_id=?;";

        try {

            conn = DB.getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, employer.getEmployer_id());
            result = ps.executeUpdate();

            if (result != 0) {
                cache.remove(employer.getEmployer_id());
            }

        } catch (SQLException e) {
            showSQLException(e);
        }

        return result == 0 ? false : true;

    }

    public boolean isUsingCache() {
        return this.usingCache;
    }

    public void setUsingCache(boolean usingCache) {
        this.usingCache = usingCache;
    }

    private static class EmployerDAOHelper {

        private static final EmployerDAO instance = new EmployerDAO();
    }

    public static EmployerDAO getInstance() {
        return EmployerDAOHelper.instance;
    }

    private void showEntityException(EntityException e, String msg) {
        String message = msg + " Echec d'ajout"
                + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage() + e.getCause();
        JOptionPane.showMessageDialog(null, message);
    }

    private void showSQLException(SQLException e) {
        String message = e.getErrorCode() + "\n" + e.getMessage() + "\n" + e.getLocalizedMessage() + "\n" + e.getCause();
        JOptionPane.showMessageDialog(null, message);
    }

}
