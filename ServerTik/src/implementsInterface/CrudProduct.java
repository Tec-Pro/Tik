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

    public Map<String,Object> create(String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Base.openTransaction();
        Map<String,Object>  ret= Product.createIt("name", name,"stock", stock,"measure_unit", measureUnit,"unit_price", unitPrice);
        Base.commitTransaction();
        Utils.cerrarBase();
        return ret;
    }

    public Map<String,Object> modify(int id, String name, float stock, String measureUnit, float unitPrice) throws java.rmi.RemoteException {
        Utils.abrirBase();
        Product product = Product.findById(id);
        Map<String,Object> res = null;
        if (product != null) {
            product.setString("name", name);
            product.setFloat("stock", stock);
            product.setString("measure_unit", measureUnit);
            product.setFloat("unit_price", unitPrice);
            Base.openTransaction();
            product.saveIt();
            res= product.toMap();
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
