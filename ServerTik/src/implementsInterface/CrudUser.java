/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.awt.image.BufferedImage;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import models.Admin;
import models.User;
import org.javalite.activejdbc.Base;
import utils.Encryption;
import utils.SerializableBufferedImage;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudUser extends UnicastRemoteObject implements interfaces.InterfaceUser {

    private Connection conn;
    private String sql = "";

    public CrudUser() throws RemoteException {
        super();

    }

    public Map<String, Object> create(
            String name,
            String surname,
            String pass,
            Date hiredDate,
            Date dischargedDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String address,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position,
            SerializableBufferedImage photo
    ) throws java.rmi.RemoteException {

        Utils.abrirBase();
        Base.openTransaction();
        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        Map<String, Object> res = User.createIt(
                "name", name,
                "surname", surname,
                "pass", passEncrypted,
                "date_hired", hiredDate,
                "date_discharged", dischargedDate,
                "turn", turn,
                "date_of_birth", dateOfBirth,
                "place_of_birth", placeOfBirth,
                "id_type", idType,
                "id_number", idNumber,
                "address", address,
                "home_phone", homePhone,
                "emergency_phone", emergencyPhone,
                "mobile_phone", mobilePhone,
                "marital_status", maritalStatus,
                "blood_type", bloodType,
                "position", position
        ).toMap();
        Base.commitTransaction();
        try {
            savePhoto(photo, res.get("id").toString(), "jpg");
        } catch (IOException ex) {
            Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public Map<String, Object> modify(
            int id,
            String name,
            String surname,
            String pass,
            Date hiredDate,
            Date dischargedDate,
            String turn,
            Date dateOfBirth,
            String placeOfBirth,
            String idType,
            String idNumber,
            String address,
            String homePhone,
            String emergencyPhone,
            String mobilePhone,
            String maritalStatus,
            String bloodType,
            String position,
            SerializableBufferedImage photo
    ) throws java.rmi.RemoteException {
        Utils.abrirBase();
        User user = User.findById(id);
        Map<String, Object> res = null;
        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (user != null) {
            user.setString("name", name);
            user.setString("surname", surname);
            user.set("pass", passEncrypted);
            user.setDate("date_hired", hiredDate);
            user.setDate("date_discharged", dischargedDate);
            user.setString("turn", turn);
            user.setDate("date_of_birth", dateOfBirth);
            user.setString("place_of_birth", placeOfBirth);
            user.setString("id_type", idType);
            user.setString("id_number", idNumber);
            user.setString("address", address);
            user.setString("home_phone", homePhone);
            user.setString("emergency_phone", emergencyPhone);
            user.setString("mobile_phone", mobilePhone);
            user.setString("marital_status", maritalStatus);
            user.setString("blood_type", bloodType);
            user.setString("position", position);
            res = user.toMap();
            Base.openTransaction();
            user.saveIt();
            Base.commitTransaction();
            try {
                savePhoto(photo, res.get("id").toString(), "jpg");
            } catch (IOException ex) {
                Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return res;
    }

    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        User user = User.findById(id);
        boolean res = false;
        if (user != null) {
            Base.openTransaction();
            res = user.delete();
            Base.commitTransaction();
        }
        return res;
    }

    @Override
    public Map<String, Object> getUser(int id) throws java.rmi.RemoteException {
        openBase();
                Map m = new HashMap();
        try {
            sql = "SELECT * from users where id ='" + id + "';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                m.put("id", rs.getObject("id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                rs.close();
                stmt.close();
                conn.close();
                return m;
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return m;
    }

    @Override
    public List<Map> getUsers() throws java.rmi.RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * from users ";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();

                m.put("id", rs.getObject("id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    public List<Map> getWaiters() throws java.rmi.RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * from users WHERE position = 'Mozo';";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();

                m.put("id", rs.getObject("id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    public List<Map> getCooks() throws java.rmi.RemoteException {
        openBase();
        List<Map> ret = new LinkedList<>();
        try {
            sql = "SELECT * from users WHERE position = 'Cocinero'; ";
            Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map m = new HashMap();

                m.put("id", rs.getObject("id"));
                m.put("name", rs.getObject("name"));
                m.put("surname", rs.getObject("surname"));
                m.put("pass", rs.getObject("pass"));
                m.put("date_hired", rs.getObject("date_hired"));
                m.put("date_discharged", rs.getObject("date_discharged"));
                m.put("turn", rs.getObject("turn"));
                m.put("date_of_birth", rs.getObject("date_of_birth"));
                m.put("place_of_birth", rs.getObject("place_of_birth"));
                m.put("id_type", rs.getObject("id_type"));
                m.put("id_number", rs.getObject("id_number"));
                m.put("address", rs.getObject("address"));
                m.put("home_phone", rs.getObject("home_phone"));
                m.put("emergency_phone", rs.getObject("emergency_phone"));
                m.put("mobile_phone", rs.getObject("mobile_phone"));
                m.put("marital_status", rs.getObject("marital_status"));
                m.put("blood_type", rs.getObject("blood_type"));
                m.put("position", rs.getObject("position"));
                m.put("order_count", rs.getObject("order_count"));
                ret.add(m);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

    public boolean validatePass(int id, String pass) throws java.rmi.RemoteException {
        Utils.abrirBase();

        byte[] passEncrypted = {0};
        try {
            passEncrypted = Encryption.encrypt(pass);
        } catch (Exception ex) {
            Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return User.first("id = ? and pass = ?", id, passEncrypted) != null;
    }

    public SerializableBufferedImage getPhoto(int idUser) throws java.rmi.RemoteException {
        BufferedImage img = null;
        SerializableBufferedImage ret = null;
        File f = new File(System.getProperty("user.dir") + "/user_images/" + idUser + ".jpg");
        if (f.exists() && !f.isDirectory()) { /* do something */

            try {
                img = ImageIO.read(f);
                ret = new SerializableBufferedImage(img);

            } catch (IOException e) {
            }
        }
        return ret;
    }

    //metodo que guarda la imagen en disco en formato JPG
    private void savePhoto(final SerializableBufferedImage imageser, final String idUser, final String extension) throws IOException {
        Thread thread = new Thread() {
            public void run() {
                if (imageser == null) {
                    File f = new File(System.getProperty("user.dir") + "/user_images/" + idUser + ".jpg");
                    if (f.exists() && !f.isDirectory()) { /* do something */

                        f.delete();
                    }
                } else {
                    try {
                        //se escribe en disco
                        ImageIO.write(imageser.getBImage(), extension, new File(System.getProperty("user.dir") + "/user_images/" + idUser + "." + extension));
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ImageIO.write((RenderedImage) imageser.getBImage(), extension, out);
                        InputStream in = new ByteArrayInputStream(out.toByteArray());
                    } catch (IOException ex) {
                        Logger.getLogger(CrudUser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        thread.start();
    }

    private void openBase() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection("jdbc:mysql://localhost/tik", "root", "root");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDOrder.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
