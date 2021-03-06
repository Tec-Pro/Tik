/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.cashbox;

import controllers.deposits.ControllerGUINewDeposit;
import controllers.ControllerMain;
import controllers.providers.ControllerGuiTicketsPaid;
import controllers.statistics.ControllerGuiProductList;
import controllers.withdrawals.ControllerGUINewWithdrawal;
import gui.cashbox.GUICashbox;
import gui.cashbox.GuiPayProvider;
import gui.deposit.GUINewDeposit;
import gui.providers.GuiTicketDetails;
import gui.providers.purchases.GuiPayPurchase;
import gui.providers.purchases.GuiPurchase;
import gui.withdrawal.GUINewWithdrawal;
import interfaces.InterfaceAdmin;
import interfaces.InterfaceOrder;
import interfaces.InterfacePproduct;
import interfaces.InterfaceTurn;
import interfaces.InterfaceUser;
import interfaces.cashbox.InterfaceCashbox;
import interfaces.cashbox.expenses.InterfaceExpenses;
import interfaces.deposits.InterfaceDeposit;
import interfaces.providers.InterfaceProvider;
import interfaces.providers.payments.InterfacePayments;
import interfaces.providers.purchases.InterfacePurchase;
import interfaces.withdrawals.InterfaceWithdrawal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import reports.cashboxReport.Detalle;
import reports.cashboxReport.EntregaMozo;
import reports.cashboxReport.EntregaRetiroPersona;
import reports.userReport.UserData;
import utils.Dates;
import utils.InterfaceName;
import utils.Pair;
import utils.ParserFloat;

/**
 *
 * @author joako
 */
public class ControllerGUICashbox implements ActionListener {

    public static GUICashbox gui; //DEJAR EN PUBLIC
    private static InterfaceWithdrawal withdrawal;
    private static InterfaceDeposit deposit;
    private static InterfaceAdmin admin;
    private static InterfaceUser user;
    private static InterfaceCashbox cashbox;
    private static InterfaceProvider interfaceProvider;
    private final InterfacePayments interfacePayments;
    private final InterfaceExpenses interfaceExpenses;
    private final InterfaceAdmin interfaceAdmin;
    private final InterfaceTurn interfaceTurn;
    private static InterfaceTurn turn;
    private static InterfaceExpenses expenses;
    private static InterfaceOrder crudOrder;
    private static GuiPurchase guiPurchase;
    private final InterfacePproduct interfacePProduct;
    private final InterfacePurchase interfacePurchase;

    public ControllerGUICashbox(GUICashbox guiCashbox, GuiPurchase guiPurchase) throws RemoteException, NotBoundException {
        gui = guiCashbox;
        this.guiPurchase = guiPurchase;
        withdrawal = (InterfaceWithdrawal) InterfaceName.registry.lookup(InterfaceName.CRUDWithdrawal);
        deposit = (InterfaceDeposit) InterfaceName.registry.lookup(InterfaceName.CRUDDeposit);
        admin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        user = (InterfaceUser) InterfaceName.registry.lookup(InterfaceName.CRUDUser);
        cashbox = (InterfaceCashbox) InterfaceName.registry.lookup(InterfaceName.CRUDCashbox);
        turn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        expenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        crudOrder = (InterfaceOrder) InterfaceName.registry.lookup(InterfaceName.CRUDOrder);
        this.interfaceProvider = (InterfaceProvider) InterfaceName.registry.lookup(InterfaceName.CRUDProvider);
        this.interfacePayments = (InterfacePayments) InterfaceName.registry.lookup(InterfaceName.CRUDpayments);
        this.interfaceExpenses = (InterfaceExpenses) InterfaceName.registry.lookup(InterfaceName.CRUDExpenses);
        this.interfaceAdmin = (InterfaceAdmin) InterfaceName.registry.lookup(InterfaceName.CRUDAdmin);
        this.interfaceTurn = (InterfaceTurn) InterfaceName.registry.lookup(InterfaceName.CRUDTurn);
        this.interfacePProduct = (InterfacePproduct) InterfaceName.registry.lookup(InterfaceName.CRUDPproduct);
        this.interfacePurchase = (InterfacePurchase) InterfaceName.registry.lookup(InterfaceName.CRUDPurchase);
        gui.setActionListener(this);
        gui.getCashboxIncomeLabel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                    guiNewDeposit.getBtnOk().setActionCommand("OK ENTRADA");
                    try {
                        ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                        controller.loadComboBoxAdmins();
                        guiNewDeposit.setVisible(true);
                        guiNewDeposit.toFront();
                    } catch (RemoteException | NotBoundException ex) {
                        Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        loadWithdrawals();
        loadWaiterDeposits();
        loadAdminDeposits();
        loadExpenses();
        loadExistantCashbox();
        reloadDialyCashbox();
        gui.getExpensesDetailTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2  && e.getButton() == MouseEvent.BUTTON1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (row != -1) {
                        //Muestro los detalles de la compra en un JDialog.
                        GuiTicketDetails guiDetails = new GuiTicketDetails(ControllerMain.guiMain, true);
                        try {
                            //Cargo los detalles de la compra.
                            //JOAKOOOOOOOO ACÁ VA TU PARTE
                            //REEMPLAZAR EL 7 POR LA COLUMNA DONDE ESTÁ EL ID DE LA COMPRA, ASEGURATE QUE SEA STRING LA COLUMNA
                            // Y PONELE "" SI NO ES UN PAGO A UNA FACTURA
                            String idPurchase =  target.getValueAt(row, 1).toString();
                            if (!idPurchase.isEmpty()) {
                                loadTicketDetails(Integer.valueOf(idPurchase), guiDetails);
                            }
                        } catch (RemoteException ex) {
                            Logger.getLogger(ControllerGuiTicketsPaid.class.getName()).log(Level.SEVERE, null, ex);
                        }
                          //Muestro la GUI.
                        guiDetails.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            //Si quiere cargar un nuevo retiro:
            case "NUEVO RETIRO":
                GUINewWithdrawal guiNewWithdrawal = new GUINewWithdrawal(ControllerMain.guiMain, true);
                try {
                    ControllerGUINewWithdrawal controller = new ControllerGUINewWithdrawal(guiNewWithdrawal);
                    guiNewWithdrawal.setVisible(true);
                    guiNewWithdrawal.toFront();
                    ECLoadWithdrawals();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            //Si quiere cargar una entrega de mozo.
            case "ENTREGA MOZO": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                //Cambio el action command para que el otro controlador sepa que hacer.
                guiNewDeposit.getBtnOk().setActionCommand("OK MOZO");
                try {
                    ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                    controller.loadComboBoxWaiters();
                    guiNewDeposit.setVisible(true);
                    guiNewDeposit.toFront();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            //Si es una entrega de admin.
            case "ENTREGA ADMIN": {
                GUINewDeposit guiNewDeposit = new GUINewDeposit(ControllerMain.guiMain, true);
                //Cambio el action command así el nuevo controlador sabe que hacer.
                guiNewDeposit.getBtnOk().setActionCommand("OK ADMIN");
                try {
                    ControllerGUINewDeposit controller = new ControllerGUINewDeposit(guiNewDeposit);
                    controller.loadComboBoxAdmins();
                    guiNewDeposit.setVisible(true);
                    guiNewDeposit.toFront();
                } catch (RemoteException | NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        /*Botón de nuevo gasto*/
        if (e.getSource() == gui.getBtnNewExpense()) {
            try {
                List<Map> providers;
                List<Map> admins;
                providers = interfaceProvider.getProviders();
                admins = interfaceAdmin.getAdmins();
                try {
                    GuiPayProvider guiPay = new GuiPayProvider(ControllerMain.guiMain, true, admins, providers);
                    guiPay.setLocationRelativeTo(gui);
                    guiPay.setVisible(true);
                    if (guiPay.getReturnStatus() == GuiPayPurchase.RET_OK) {
                        String detail = guiPay.getDetail();
                        String nameAdmin = guiPay.getNameAdmin();
                        float payAdmin = guiPay.getPayAdmin();
                        float payBox = guiPay.getPayBox();
                        int idProvider = guiPay.getProvider();
                        String datePaid = Dates.dateToMySQLDate(Calendar.getInstance().getTime(), false);
                        interfacePayments.createPayment(idProvider, detail, payAdmin + payBox, -1, datePaid, nameAdmin);
                        int auxType; //Indica el tipo de gasto que fue cargado. Si no tiene proveedor, es otro gasto.
                        if (idProvider == -1) {
                            auxType = 3;
                        } else {
                            auxType = 1;
                        }
                        interfaceExpenses.createExpense(auxType, detail, payAdmin + payBox, -1, idProvider, interfaceTurn.getTurn());
                        if (idProvider >= 0) {
                            interfaceProvider.payPurchases(idProvider, payAdmin + payBox);
                        }
                        if (guiPay.getPayAdmin() > 0) {

                            /**
                             * ACA DEBO CREAR UNA ENTREGA DEL MOZO SIEMPRE Y
                             * CUANDO LO QUE ENTREGA MOZO ES MAYOR A 0
                             */
                            int idAdmin = -1;
                            Iterator<Map> it = admins.iterator();
                            while (it.hasNext() && idAdmin == -1) {
                                Map a = it.next();
                                if (((String) a.get("name")).equals(nameAdmin)) {
                                    idAdmin = (Integer) a.get("id");
                                }
                            }
                            deposit.createAdminDeposit(idAdmin, guiPay.getPayAdmin());
                            controllers.cashbox.ControllerGUICashbox.reloadAdminDeposits();
                        }

                    }
                } catch (NotBoundException ex) {
                    Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
                }
                loadExpenses();
                ECLoadExpenses();
            } catch (RemoteException ex) {
                Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == gui.getBtnNewPurchase()) {
            try {
                guiPurchase.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            guiPurchase.setVisible(true);
            guiPurchase.toFront();
        }
        try {
            ECLoadBalance();
            reloadDialyCashbox();
        } catch (RemoteException ex) {
            Logger.getLogger(ControllerGUICashbox.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (e.getSource() == gui.getBtnPrintReport()) {
            try {
                JasperReport reporte = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/cashboxReport/cashboxReport.jasper"));//cargo el reporte
                JasperPrint jasperPrint;
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("saldoInicial", gui.getDCInitialBalanceField().getText());
                parametros.put("cajaEntrada", gui.getDCCashboxIncomeField().getText());
                parametros.put("recM", gui.getDCEarningsFieldMorn().getText());
                parametros.put("recT", gui.getDCEarningsFieldAft().getText());
                parametros.put("gastos", gui.getDCExpensesField().getText());
                parametros.put("sig", gui.getDCNextTurnField().getText());
                parametros.put("saldo", gui.getDCBalanceField().getText());

                parametros.put("esaldoInicial", gui.getECInitialBalanceField().getText());
                parametros.put("eentrada", gui.getECCashboxIncomeField().getText());
                parametros.put("eentregaMozo", gui.getECWaiterDepositsField().getText());
                parametros.put("eentregaCaja", gui.getECAdminDepositsField().getText());
                parametros.put("eretiros", gui.getECWithdrawalsField().getText());
                parametros.put("egastos", gui.getECCashboxExpensesField().getText());
                parametros.put("esig", gui.getECNextTurnField().getText());
                parametros.put("esaldo", gui.getECBalanceField().getText());
                List<EntregaMozo> entregas = new ArrayList();

                for (int i = 0; i < gui.getWaiterDepositsTable().getRowCount(); i++) {
                    EntregaMozo em = new EntregaMozo(gui.getWaiterDepositsTable().getValueAt(i, 0),
                            gui.getWaiterDepositsTable().getValueAt(i, 1),
                            gui.getWaiterDepositsTable().getValueAt(i, 2),
                            gui.getWaiterDepositsTable().getValueAt(i, 3));
                    entregas.add(em);
                }
                EntregaMozo em = new EntregaMozo(" ", " ", "TOTAL: ", gui.getWaiterDepositsTotalField().getText());
                entregas.add(em);

                jasperPrint = JasperFillManager.fillReport(reporte, parametros, new JRBeanCollectionDataSource(entregas));
                ////////////////
                List<EntregaRetiroPersona> entregasPersona = new ArrayList();
                if (gui.getAdminDepositsTable().getRowCount() > 0) {
                    for (int i = 0; i < gui.getAdminDepositsTable().getRowCount(); i++) {
                        EntregaRetiroPersona ep = new EntregaRetiroPersona(gui.getAdminDepositsTable().getValueAt(i, 0),
                                gui.getAdminDepositsTable().getValueAt(i, 1),
                                gui.getAdminDepositsTable().getValueAt(i, 2),
                                gui.getAdminDepositsTable().getValueAt(i, 3));
                        entregasPersona.add(ep);
                    }
                    EntregaRetiroPersona ep = new EntregaRetiroPersona(null, null, "TOTAL: ", gui.getAdminDepositsTotalField().getText());
                    entregasPersona.add(ep);
                    JasperReport reportePersona = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/cashboxReport/entregasCajaReport.jasper"));//cargo el reporte
                    JasperPrint jasperPrint2;
                    jasperPrint2 = JasperFillManager.fillReport(reportePersona, null, new JRBeanCollectionDataSource(entregasPersona));

                    List pages = jasperPrint2.getPages();
                    for (int j = 0; j < pages.size(); j++) {
                        JRPrintPage object = (JRPrintPage) pages.get(j);
                        jasperPrint.addPage(object);
                    }
                }

                List<EntregaRetiroPersona> retirosPersona = new ArrayList();
                if (gui.getWithdrawalsTable().getRowCount() > 0) {
                    for (int i = 0; i < gui.getWithdrawalsTable().getRowCount(); i++) {
                        EntregaRetiroPersona ep = new EntregaRetiroPersona(gui.getWithdrawalsTable().getValueAt(i, 0),
                                gui.getWithdrawalsTable().getValueAt(i, 1),
                                gui.getWithdrawalsTable().getValueAt(i, 2),
                                gui.getWithdrawalsTable().getValueAt(i, 3));
                        retirosPersona.add(ep);
                    }
                    EntregaRetiroPersona ep = new EntregaRetiroPersona(null, null, "TOTAL: ", gui.getWithdrawalTotalField().getText());
                    retirosPersona.add(ep);
                    JasperReport reporteRetiroPersona = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/cashboxReport/retirosReport.jasper"));//cargo el reporte
                    JasperPrint jasperPrint3;
                    jasperPrint3 = JasperFillManager.fillReport(reporteRetiroPersona, null, new JRBeanCollectionDataSource(retirosPersona));

                    List pages = jasperPrint3.getPages();
                    for (int j = 0; j < pages.size(); j++) {
                        JRPrintPage object = (JRPrintPage) pages.get(j);
                        jasperPrint.addPage(object);
                    }
                }

                List<Detalle> detalles = new ArrayList();
                if (gui.getExpensesDetailTable().getRowCount() > 0) {
                    for (int i = 0; i < gui.getExpensesDetailTable().getRowCount(); i++) {
                        Detalle ep = new Detalle(gui.getExpensesDetailTable().getValueAt(i, 0),
                                gui.getExpensesDetailTable().getValueAt(i, 1),
                                gui.getExpensesDetailTable().getValueAt(i, 2),
                                gui.getExpensesDetailTable().getValueAt(i, 3));
                        detalles.add(ep);
                    }
                    Detalle ep = new Detalle(null, null, "TOTAL: ", gui.getExpensesTotalField().getText());
                    detalles.add(ep);
                    JasperReport reporteDetalle = (JasperReport) JRLoader.loadObject(getClass().getResource("/reports/cashboxReport/detalleReport.jasper"));//cargo el reporte
                    JasperPrint jasperPrint4;
                    jasperPrint4 = JasperFillManager.fillReport(reporteDetalle, null, new JRBeanCollectionDataSource(detalles));

                    List pages = jasperPrint4.getPages();
                    for (int j = 0; j < pages.size(); j++) {
                        JRPrintPage object = (JRPrintPage) pages.get(j);
                        jasperPrint.addPage(object);
                    }
                }

                ////////////
                JasperViewer.viewReport(jasperPrint, false);

            } catch (JRException ex) {
                Logger.getLogger(ControllerGuiProductList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Método que actualiza los retiros en la tabla y en los campos
     * correspondientes de caja existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void reloadWithdrawals() throws RemoteException {
        loadWithdrawals();
    }

    //Método que carga la tabla con los retiros y modifica los campos de caja existente.
    private static void loadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> withdrawalList = withdrawal.getWithdrawals();
        gui.getWithdrawalsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map w : withdrawalList) {
            o[0] = admin.getAdmin((int) w.get("admin_id")).get("name");
            o[1] = w.get("turn");
            hour = w.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) w.get("amount"));
            gui.getWithdrawalsTableModel().addRow(o);
            total = total + (Float) w.get("amount");
        }
        gui.getWithdrawalTotalField().setText(ParserFloat.floatToString(total));
        gui.getECWithdrawalsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de entregas de mozo y los campos
     * correspondientes de la caja existente
     *
     * @throws RemoteException error de conexión
     */
    public static void reloadWaiterDeposits() throws RemoteException {
        loadWaiterDeposits();
    }

    /*
     Método que se encarga de cargar la lista de entregas de mozo.
     */
    private static void loadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getWaitersDeposits();
        gui.getWaiterDepositsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map d : depositsList) {
            o[0] = user.getUser((int) d.get("waiter_id")).get("name");
            o[1] = d.get("turn");
            hour = d.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) d.get("amount"));
            gui.getWaiterDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
        }
        gui.getWaiterDepositsTotalField().setText(ParserFloat.floatToString(total));
        gui.getECWaiterDepositsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de depósitos de administradores y los
     * campos correspondientes de caja existente.
     *
     * @throws RemoteException
     */
    public static void reloadAdminDeposits() throws RemoteException {
        loadAdminDeposits();
    }
    /*
     Método que se encarga de cargar la lista de depósitos de administradores
     y actualiza los campos de la caja existente.
     */

    private static void loadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        List<Map> depositsList = deposit.getAdminsDeposits();
        gui.getAdminDepositsTableModel().setRowCount(0);
        Object[] o = new Object[4];
        Float total = 0.0f;
        String hour;
        for (Map d : depositsList) {
            o[0] = admin.getAdmin((int) d.get("admin_id")).get("name");
            o[1] = d.get("turn");
            hour = d.get("created_at").toString();
            o[2] = (hour.split(" "))[1].subSequence(0, 5);
            o[3] = ParserFloat.floatToString((Float) d.get("amount"));
            gui.getAdminDepositsTableModel().addRow(o);
            total = total + (Float) d.get("amount");
        }
        gui.getAdminDepositsTotalField().setText(ParserFloat.floatToString(total));
        gui.getECAdminDepositsField().setText(ParserFloat.floatToString(total));
    }

    /**
     * Método que actualiza la lista de gastos y los campos de la caja
     * existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void reloadExpenses() throws RemoteException {
        loadExpenses();
    }

    /*
     Método que carga los gastos pagados con dinero de la caja.
     */
    private static void loadExpenses() throws RemoteException {
        List<Map> exp = expenses.getExpenses("N");
        gui.getExpensesTableModel().setRowCount(0);
        Object[] o = new Object[5];
        Float total = 0.0f;
        for (Map e : exp) {
            o[0] = e.get("id");
            o[1] = "";
            o[2] = e.get("type");
            Object provider_id = e.get("provider_id");
            Object purchase_id = e.get("purchase_id");
            String detail = (String) e.get("detail");
            if (provider_id != null) {
                Map<String, Object> provider = interfaceProvider.getProvider((int) provider_id);
                if (purchase_id != null) {
                    o[1] = purchase_id;
                    detail += "- Compra realizada a: " + provider.get("name");
                } else {
                    detail += "- Pago realizado a: " + provider.get("name");
                }
            }
            o[3] = detail;
            o[4] = ParserFloat.floatToString((Float) e.get("amount"));
            total = total + (Float) e.get("amount");
            gui.getExpensesTableModel().addRow(o);
        }
        gui.getExpensesTotalField().setText(ParserFloat.floatToString(total));
        gui.getECCashboxExpensesField().setText(ParserFloat.floatToString(total));
    }

    /*
     Método que carga el saldo inicial de la caja.
     */
    private static Float ECLoadInitialBalance() throws RemoteException {
        Map lastTurn = cashbox.getLast();
        Map mm;
        if (lastTurn.get("turn").equals("T")) {
            mm = cashbox.getLast("TT"); // si el ultimo turno es tarde, necesito el otro turno tarde
        } else {
            mm = cashbox.getLast("T");
        }
        //con esto busco el ultimo turno mañana para tener cual es el saldo inicial
        return ((float) mm.get("balance")) + ((float) mm.get("spend")) - ((float) mm.get("entry_cash")) - ((float) mm.get("collect"));
    }

    /*
     Método que carga los depósitos de admin en la caja existente.
     */
    private static Float ECLoadAdminDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float adminDeposits = deposit.getAdminsDepositsTotal();
        gui.getECAdminDepositsField().setText(ParserFloat.floatToString(adminDeposits));
        return adminDeposits;
    }

    /*
     Método que carga las entregas de mozo en la caja existente.
     */
    private static Float ECLoadWaiterDeposits() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float waiterDeposits = deposit.getWaitersDepositsTotal();
        gui.getECWaiterDepositsField().setText(ParserFloat.floatToString(waiterDeposits));
        return waiterDeposits;
    }
    /*
     Método que carga los retiros en la caja existente.
     */

    private static Float ECLoadWithdrawals() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float withdrawals = withdrawal.getWithdrawalsTotal();
        gui.getECWithdrawalsField().setText(ParserFloat.floatToString(withdrawals));
        return withdrawals;
    }

    private static Float ECLoadCashboxIncome() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();
        Float incomes = deposit.getIncomesTotal(date, "N"); // con N retorno todos
        gui.getECCashboxIncomeField().setText(ParserFloat.floatToString(incomes));
        return incomes;
    }

    /*
     Método que carga los gastos en la caja existente.
     */
    private static Float ECLoadExpenses() throws RemoteException {
        Float exp = expenses.getSumExpenses("N");
        gui.getECCashboxExpensesField().setText(ParserFloat.floatToString(exp));
        return exp;
    }

    /**
     * Método que actualiza el saldo de la caja existente.
     *
     * @throws RemoteException error de conexión.
     */
    public static void ECReloadBalance() throws RemoteException {
        ECLoadBalance();
    }
    /*
     Método que carga el balance en la caja existente.
     */

    public static Float ECLoadBalance() throws RemoteException {
        Float initialBalance = ParserFloat.stringToFloat(gui.getECInitialBalanceField().getText());
        Float adminDeposits = ParserFloat.stringToFloat(gui.getECAdminDepositsField().getText());
        Float waiterDeposits = ParserFloat.stringToFloat(gui.getECWaiterDepositsField().getText());
        Float withdrawals = ParserFloat.stringToFloat(gui.getECWithdrawalsField().getText());
        Float exp = ParserFloat.stringToFloat(gui.getECCashboxExpensesField().getText());
        Float cashboxIncome = ParserFloat.stringToFloat(gui.getECCashboxIncomeField().getText());
        Float balance = initialBalance + adminDeposits + waiterDeposits + cashboxIncome - withdrawals - exp;
        gui.getECBalanceField().setText(ParserFloat.floatToString(balance));
        return balance;
    }

    /*
     Método que carga todos los valores de la caja existente.
     */
    private static void loadExistantCashbox() throws RemoteException {
        String date = new java.sql.Date(System.currentTimeMillis()).toString();

        Float initialBalance = ECLoadInitialBalance();

        Float adminDeposits = ECLoadAdminDeposits();

        Float cashboxIncome = ECLoadCashboxIncome();

        Float waiterDeposits = ECLoadWaiterDeposits();

        Float withdrawals = ECLoadWithdrawals();

        Float exp = ECLoadExpenses();

        Float balance = initialBalance + adminDeposits + waiterDeposits + cashboxIncome - withdrawals - exp;
        gui.getECBalanceField().setText(ParserFloat.floatToString(balance));

    }

    /**
     * Método que actualiza todos los valores de la caja existente.
     *
     * @throws RemoteException
     */
    public static void reloadExistantCashbox() throws RemoteException {
        loadExistantCashbox();
    }

    public static void reloadIncome() throws RemoteException {
        ECLoadCashboxIncome();
    }

    /**
     * Método que actualiza todos los valores de la caja diaria.
     *
     * @throws RemoteException
     */
    public static void reloadDialyCashbox() throws RemoteException {
        //cargo siempre el saldo inicial
        Map lastTurn = cashbox.getLast();
        Map mm;
        if (lastTurn.get("turn").equals("T") && !turn.isTurnOpen()) {
            mm = cashbox.getLast("TT"); // si el ultimo turno es tarde, necesito el otro turno tarde
        } else {
            mm = cashbox.getLast("T");
        }
        //con esto busco el ultimo turno mañana para tener cual es el saldo inicial
        float initialBalance = ((float) mm.get("balance"));
        gui.getDCInitialBalanceField().setText(ParserFloat.floatToString(initialBalance));
        gui.getECInitialBalanceField().setText(ParserFloat.floatToString(initialBalance));
        if (turn.isTurnOpen()) {
            if (turn.getTurn().equals("M")) {
                gui.getDCCashboxIncomeField().setText(gui.getECCashboxIncomeField().getText());
                gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString(crudOrder.totalEarn() + crudOrder.getAllExceptions()));
                gui.getDCEarningsFieldAft().setText(ParserFloat.floatToString((float) 0));
                gui.getDCExpensesField().setText(gui.getECCashboxExpensesField().getText());
                gui.getDCNextTurnField().setText(ParserFloat.floatToString((float) 0));
                gui.getLblCurrentTurn().setText("TURNO MAÑANA ABIERTO");
            } else {
                Map m = cashbox.getLast();
                if (m.get("turn").equals("M")) {
                    gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString((float) m.get("delivery_waiter")));
                } else {
                    gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString((float) 0));
                }
                Map mt = cashbox.getLast("T");
                gui.getDCInitialBalanceField().setText(ParserFloat.floatToString((float) mt.get("balance")));
                gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString(ParserFloat.stringToFloat(gui.getECCashboxIncomeField().getText())));
                //float currentEarnes = crudOrder.totalEarn() + crudOrder.getAllExceptions();
                gui.getDCEarningsFieldAft().setText(ParserFloat.floatToString(crudOrder.totalEarn() + crudOrder.getAllExceptions()));
                float currentExpenses = ParserFloat.stringToFloat(gui.getECCashboxExpensesField().getText());
                gui.getDCExpensesField().setText(ParserFloat.floatToString(currentExpenses));
                gui.getDCNextTurnField().setText("0");
                gui.getLblCurrentTurn().setText("TURNO TARDE ABIERTO");
            }

        } else {
            gui.getLblCurrentTurn().setText("NO HAY TURNO ABIERTO");
            lastTurn = cashbox.getLast();
            if (lastTurn.get("turn").equals("T")) {
                /**
                 * ahora esta cerrado todos los turnos, si el turno anterior es
                 * T entonces tengo que setear el turno siguiente sino queda
                 * vacio con 0
                 *
                 */
                gui.getDCNextTurnField().setText(ParserFloat.floatToString((float) lastTurn.get("balance")));
            } else// lo dejo en 0, todavía no se definio cuanto deja para el día de mañana
            {
                gui.getDCNextTurnField().setText(ParserFloat.floatToString((float) 0));
            }
            Map m = cashbox.getLast();
            if (m.get("turn").equals("M")) {
                gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString(((float) m.get("entry_cash"))));
                gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString((float) m.get("collect")));
                gui.getDCEarningsFieldAft().setText(ParserFloat.floatToString((float) 0));
                gui.getDCExpensesField().setText(ParserFloat.floatToString(((float) m.get("spend"))));
            }
            if (m.get("turn").equals("T")) {
                if (cashbox.getPenultimoTurno().get("turn").equals("M")) {
                    mm = cashbox.getLast("M");
                    gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString((float) m.get("entry_cash") + (float) mm.get("entry_cash")));
                    gui.getDCEarningsFieldAft().setText(ParserFloat.floatToString((float) m.get("collect")));
                    gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString((float) mm.get("collect")));

                    gui.getDCExpensesField().setText(ParserFloat.floatToString(((float) m.get("spend") + (float) mm.get("spend"))));
                } else {
                    gui.getDCCashboxIncomeField().setText(ParserFloat.floatToString((float) m.get("entry_cash")));
                    gui.getDCEarningsFieldAft().setText(ParserFloat.floatToString((float) m.get("collect")));
                    gui.getDCEarningsFieldMorn().setText(ParserFloat.floatToString((float) mm.get("collect")));
                    gui.getDCExpensesField().setText(ParserFloat.floatToString(((float) m.get("spend"))));
                }
            }
        }

        float incomes = ParserFloat.stringToFloat(gui.getDCCashboxIncomeField().getText());
        float earningMorn = ParserFloat.stringToFloat(gui.getDCEarningsFieldMorn().getText());
        float earningAft = ParserFloat.stringToFloat(gui.getDCEarningsFieldAft().getText());

        float expenses = ParserFloat.stringToFloat(gui.getDCExpensesField().getText());
        gui.getDCBalanceField().setText(ParserFloat.floatToString(incomes + earningMorn + earningAft - expenses - initialBalance));
        gui.getECNextTurnField().setText(gui.getDCNextTurnField().getText());
    }

    public static void blockButtons() throws RemoteException {
        if (turn.isTurnOpen()) {
            gui.getBtnNewExpense().setEnabled(true);
            gui.getNewWithdrawalButton().setEnabled(true);
            gui.getNewWaiterDepositButton().setEnabled(true);
            gui.getNewAdminDepositButton().setEnabled(true);
        } else {
            gui.getBtnNewExpense().setEnabled(false);
            gui.getNewWithdrawalButton().setEnabled(false);
            gui.getNewWaiterDepositButton().setEnabled(false);
            gui.getNewAdminDepositButton().setEnabled(false);
        }
    }

    /**
     * Función que carga los detalles de una compra en un JDialog nuevo.
     *
     * @param ticket_id id de la compra de la que se quiere saber los detalles.
     * @param gui JDialog que contiene los detalles de la compra.
     * @throws RemoteException
     */
    private void loadTicketDetails(int ticket_id, GuiTicketDetails gui) throws RemoteException {
        Pair<Map<String, Object>, List<Map>> purchase = interfacePurchase.getPurchase(ticket_id);
        gui.getTxtDate().setText(purchase.first().get("date").toString());
        gui.getTxtTotal().setText(purchase.first().get("cost").toString());
        gui.getTicketId().setText(purchase.first().get("id").toString());
        DefaultTableModel dtmTableDetails = (DefaultTableModel) gui.getTableTicketDetails().getModel();
        dtmTableDetails.setRowCount(0);
        Object[] row = new Object[6];
        for (Map<String, Object> product : purchase.second()) {
            Map<String, Object> p = interfacePProduct.getPproduct((Integer.parseInt(product.get("pproduct_id").toString())));
            row[0] = product.get("pproduct_id");
            row[1] = p.get("name");
            row[2] = product.get("amount");
            if (p.get("measure_unit").equals("gr")) {
                row[3] = "Kg";
            } else if (p.get("measure_unit").equals("ml")) {
                row[3] = "L";
            } else {
                 row[3] = "unitario";
            }
            row[4] = product.get("final_price");
            //row[5] = product.get("iva");
            //float addIva = ((float) product.get("final_price") * (float) product.get("iva")) / 100;
            row[5] = (float) product.get("final_price");
            dtmTableDetails.addRow(row);
        }
    }

}
