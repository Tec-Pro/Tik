##Producyos y Categorias

##Server

#Modelos
* Caregory
* Subcategory
* Pproduct (producto primario)
* Eproduct (producto elaborado)
* Fproduct (producto final)
* EproductsPproducts (relacion n n elaborados y primarios)
* FproductsEproducts (relacion n n finales y elaborados)
* FproductsPproducts (relacion n n finales y primarios)

#Interfaces
* InterfaceCategory
* InterfaceEproduct
* InterfaceFproduct
* InterfacePproduct

#Implementacion de Interfaces
* CRUDPproduct implementa InterfacePproduct
* CRUDEproduct implementa InterfaceEproduct
* CRUDFproduct implementa InterfaceFproduct


