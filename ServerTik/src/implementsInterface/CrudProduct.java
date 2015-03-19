/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import models.Product;
import org.javalite.activejdbc.Base;
import utils.Utils;

/**
 *
 * @author nico
 */
public class CrudProduct extends UnicastRemoteObject implements interfaces.InterfaceProduct {

    public CrudProduct() throws RemoteException {
        super();
    }

    public boolean create(String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Product product = new Product();
        product.setString("name", name);
        product.setFloat("stock", stock);
        product.setString("measure_unit", measureUnit);
        product.setFloat("unit_price", unitPrice);
        boolean res = product.saveIt();
        Base.commitTransaction();
        Utils.cerrarBase();
        return res;
    }

    public boolean modify(int id, String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Product product = Product.findById(id);
        boolean res = false;
        if (product != null) {
            product.setString("name", name);
            product.setFloat("stock", stock);
            product.setString("measure_unit", measureUnit);
            product.setFloat("unit_price", unitPrice);
            Base.openTransaction();
            res = product.saveIt();
            Base.commitTransaction();
            Utils.cerrarBase();
        }
        return res;
    }

    public boolean delete(int id) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Product product = Product.findById(id);
        boolean res = false;
        if (product != null) {
            Base.openTransaction();
            res = product.delete();
            Base.commitTransaction();
        }
        return res;
    }

     public Map<String,Object> getProduct(int id) throws java.rmi.RemoteException{
         Utils.abrirBase();
         Map<String,Object> ret= Product.findById(id).toMap();
         Utils.cerrarBase();
         return ret;
     }    
    public List<Map> getProducts() throws java.rmi.RemoteException {
        Utils.abrirBase();
        List<Map> ret = Product.findAll().toMaps();
        Utils.cerrarBase();
        return ret;
    }

}
