/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package implementsInterface;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import utils.Pair;

/**
 *
 * @author jacinto
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws RemoteException {
        CRUDPproduct crudp = new CRUDPproduct();
        CRUDEproduct crude = new CRUDEproduct();
        CRUDFproduct crudf = new CRUDFproduct();
        CRUDCategory cat = new CRUDCategory();
        
 //         System.out.println("----------------TEST PRIMARIOS----------------");
//        crudp.create("primario1", 15, "Kg", 5,1);
 //       crudp.create("primario2", 30, "Kg", 5,1);
 //       crudp.delete(1);
  //      crudp.modify(1, "primario1Modificado", 15, "gramos", 5,1);
   //    System.out.println(crudp.getPproducts().toString());
//        System.out.println(crudp.getPproduct(1).toString());
//        
//        System.out.println("----------------TEST ELABORADOS----------------");
  //      List<Pair> pProducts = new LinkedList<Pair>();
 //       Float f = new Float(1);
//        Pair p = new Pair((Integer) 1, f);
//        pProducts.add(p);
//        crude.create("elaborado1", 5, "mg", 1, pProducts);
//        crude.create("elaborado3", 5, "mg", 1, pProducts);
     //   crude.delete(2);
       // crude.modify(1,"elaborado1MODIFICADO", 5, "mg", 1, pProducts);
 //     System.out.println(crude.getEproducts().toString());
//        System.out.println(crude.getPproducts(3).toString());
//        System.out.println(crude.getEproduct(3).toString());
   
//         List<Pair> eProducts = new LinkedList<Pair>();
//        Float f2 = new Float(1);
//        Pair p2 = new Pair((Integer) 1, f2);
//        eProducts.add(p2);
        //crudf.create("productoFinalAPROBAR", 1, pProducts, eProducts);
        //crudf.create("productoFinal2", 1, pProducts, eProducts);
//        crudf.modify(2, "productoFinal1MODIFICADO", 1, pProducts, eProducts);
        //crudf.delete(2);
    //    System.out.println(crudf.getEproducts(2));
    //   System.out.println(crudf.getFproduct(2));
       // System.out.println(crudf.getFproducts());
    //    System.out.println(crudf.getPproducts(2));
       // System.out.println(crude.getEproducts());
        //System.out.println(crudp.getPproducts());

    }
}
