/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import implementsInterface.statistics.CRUDStatistics;
import interfaces.resume.InterfaceResume;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.resume.Adminresume;
import models.resume.Resume;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import utils.Pair;
import utils.Triple;
import utils.Utils;

/**
 *
 * @author Alan
 */
public class CRUDResume extends UnicastRemoteObject implements InterfaceResume{
    
    private Connection conn;

        /**
     *
     * @throws RemoteException
     */
    public CRUDResume() throws RemoteException {
        super();
    }
    
    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDStatistics.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Override
    public Map<String, Object> create(Float income, Float earning, Float expenses, Float final_balance, Date resume_date, List<Triple> listAdmin) throws RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Resume r = Resume.createIt("income", income, "earning", earning, "expenses", expenses, "final_balance", final_balance,"resume_date", resume_date);
        r.saveIt();
        for(Triple t : listAdmin){
            Adminresume ar = Adminresume.createIt("resume_id",r.getId(),"admin",t.getName(),"deposit",t.getDeposit(),"withdrawal",t.getWithdrawal());
            ar.saveIt();
        }
        Base.commitTransaction();
        return r.toMap();
    }
    @Override
    public List<Map> getResumeDaily(String since, String until) throws RemoteException{
        Utils.abrirBase();
        LazyList<Resume> list  = Resume.where("resume_date >= ? and resume_date <= ? ", since, until);
        return list.toMaps();
    }
    
    @Override
    public List<Map> getResumeMonthly(String since, String until) throws RemoteException{
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, SUM(income) AS income, SUM(earning) AS earning, SUM(expenses) AS expenses, "
                    + "SUM(final_balance) AS final_balance, resume_date  FROM resumes "
                    + "WHERE resume_date >= '" + since + "' and resume_date <= '" + until + "' "
                    + "GROUP BY year(resume_date), month(resume_date)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("income", rs.getObject("income"));
                    m.put("earning", rs.getObject("earning"));
                    m.put("expenses", rs.getObject("expenses"));
                    m.put("final_balance", rs.getObject("final_balance"));
                    m.put("resume_date", rs.getObject("resume_date"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDResume.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    @Override
    public List<Map> getResumeAnnual(String since, String until) throws RemoteException{
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, SUM(income) AS income, SUM(earning) AS earning, SUM(expenses) AS expenses, "
                    + "SUM(final_balance) AS final_balance, resume_date  FROM resumes "
                    + "WHERE resume_date >= '" + since + "' and resume_date <= '" + until + "' "
                    + "GROUP BY year(resume_date)";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("income", rs.getObject("income"));
                    m.put("earning", rs.getObject("earning"));
                    m.put("expenses", rs.getObject("expenses"));
                    m.put("final_balance", rs.getObject("final_balance"));
                    m.put("resume_date", rs.getObject("resume_date"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDResume.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    @Override
    public List<Map> getResumeAll(String since, String until) throws RemoteException{
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            String sql = "SELECT DISTINCT id, SUM(income) AS income, SUM(earning) AS earning, SUM(expenses) AS expenses, "
                    + "SUM(final_balance) AS final_balance, resume_date  FROM resumes "
                    + "WHERE resume_date >= '" + since + "' and resume_date <= '" + until + "'";
            try (Statement stmt = conn.createStatement();
                    java.sql.ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Map m = new HashMap();
                    m.put("id", rs.getObject("id"));
                    m.put("income", rs.getObject("income"));
                    m.put("earning", rs.getObject("earning"));
                    m.put("expenses", rs.getObject("expenses"));
                    m.put("final_balance", rs.getObject("final_balance"));
                    m.put("resume_date", rs.getObject("resume_date"));
                    ret.add(m);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDResume.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    @Override
    public List<Map> getAdminResume(int idResume) throws RemoteException{
        Utils.abrirBase();
        LazyList<Adminresume> list = Adminresume.where("resume_id = ?", idResume);
        return list.toMaps();
    }
    
}
