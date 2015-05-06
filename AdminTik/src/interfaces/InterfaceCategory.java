/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nico
 */
public interface InterfaceCategory extends Remote {
    
     /**
     * Crea una nueva categoria. 
     * @param name de la categoria a crear.
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> create(String name) throws java.rmi.RemoteException;
     
    /**
     *Modifica una categoria existente.
     * @param id de la categoria a modificar
     * @param name : nuevo nombre de la categoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> modify(int id,String name) throws java.rmi.RemoteException;
     
    /**
     *Elimina la categoria y todas sus subcategorias.
     * @param id de la categoria a eliminar
     * @return boolean
     * @throws RemoteException
     */
    public boolean delete(int id) throws java.rmi.RemoteException;
     
    /**
     * Retorna true si el nombre de una categoria ya existe.
     * @param name de la categoria que se decea saber si existe
     * @return boolean
     * @throws RemoteException
     */
    public boolean categoryExists(String name) throws java.rmi.RemoteException;
      
    /**
     * Retorna true si el nombre de una subcategoria ya existe.
     * @param name de la subcategoria que se desea saber si existe.
     * @return boolean
     * @throws RemoteException
     */
    public boolean subCategoryExists(String name) throws java.rmi.RemoteException;

    /**
     *Retorna la categoria cuyo id es el pasado como parametro.
     * @param id de la categoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> getCategory(int id) throws java.rmi.RemoteException;
     
    /**
     * Retorna la categoria cuyo nombre es el pasado como parametro.
     * @param name de la categoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String, Object> getCategoryByName(String name) throws java.rmi.RemoteException;
     
    /**
     * Retorna todas las categorias.
     * @return List<Map>
     * @throws RemoteException
     */
    public  List<Map> getCategories() throws java.rmi.RemoteException;    
     
    /**
     * Agrega una nueva subcategoria a una categoria dada.
     * @param id de la categoria a la cual se le va a agregar la subcategoria
     * @param name de la nueva subcategoria
     * @return  Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> addSubcategory(int id,String name)throws java.rmi.RemoteException;
     
    /**
     * Elimina una sabcategoria dada.
     * @param id de la subcategoria a eliminar
     * @return boolean
     * @throws RemoteException
     */
    public boolean deleteSubcategory(int id) throws java.rmi.RemoteException;
     
    /**
     *  Modifica una subcategoria dada.
     * @param id de la subcategoria a modificar
     * @param number nuevo nombre de la subcategoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> modifySubcategory(int id, String number)throws java.rmi.RemoteException;
     
    /**
     * Retorna todas las subcategorias de una categoria dada.
     * @param id de la categoria de la cual se quieren obtener sus subcategorias
     * @return List<Map>
     * @throws RemoteException
     */
    public List<Map> getSubcategoriesCategory(int id)throws java.rmi.RemoteException;
     
    /**
     *Retorna todas las subcategorias.
     * @return List<Map>
     * @throws RemoteException
     */
    public List<Map> getSubcategoriesCategory()throws java.rmi.RemoteException;
             
    /**
     * Retorna una subcategoria cuyo id es el pasado como parametro.
     * @param id de la subcategoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> getSubcategory(int id)throws java.rmi.RemoteException;
     
    /**
     * Retorna la subcategoria cuyo nombre es el pasado como parametro.
     * @param name de la subcategoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> getSubcategory(String name)throws java.rmi.RemoteException;
    
    /**
     * Retorna los FProducts pertenecientes a la subcategoria cuyo id es pasado como parametro.
     * @param id de la subcategoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public List<Map> getFProductsSubcategory(int id)throws java.rmi.RemoteException;

	/**
     * Retorna la categoria padre de la subcategoria
     * @param name de la subcategoria
     * @return Map<String,Object>
     * @throws RemoteException
     */
    public Map<String,Object> getCategoryOfSubcategory(int id)throws java.rmi.RemoteException;

    public List<Map> searchCategories(String txt) throws java.rmi.RemoteException;
    
    public List<Map> searchSubcategories(String txt) throws java.rmi.RemoteException;
}
