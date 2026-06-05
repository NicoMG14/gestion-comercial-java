/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Modelo.Configuracion;
import Modelo.ConfiguracionDAO;
import Modelo.Detalleventa;
import Modelo.DetalleventaDAO;
import Modelo.Eventos;
import Modelo.NCredito;
import Modelo.NCreditoDAO;
import Modelo.Presupuesto;
import Modelo.PresupuestoDAO;
import Modelo.Productos;
import Modelo.ProductosDAO;
import Modelo.Proveedor;
import Modelo.ProveedorDAO;
import Modelo.Rubro;
import Modelo.RubroDAO;
import Modelo.Usuarios;
import Modelo.UsuariosDAO;
import Modelo.Venta;
import Modelo.VentaDAO;
import Reportes.Excel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class Sistema extends javax.swing.JFrame {

    //importamos eventos para validar tipografia 
    Eventos event = new Eventos();
    //requerimos los metodos y los DAO de la carpeta modelo
    Rubro rub = new Rubro();
    RubroDAO rubdao = new RubroDAO();
    Proveedor prov = new Proveedor();
    ProveedorDAO provdao = new ProveedorDAO();
    Productos prod = new Productos();
    ProductosDAO prodao = new ProductosDAO();
    Presupuesto pres = new Presupuesto();
    PresupuestoDAO presdao = new PresupuestoDAO();
    Configuracion conf = new Configuracion();
    ConfiguracionDAO confdao = new ConfiguracionDAO();
    Usuarios us = new Usuarios();
    UsuariosDAO usdao = new UsuariosDAO();
    NCredito nc = new NCredito();
    NCreditoDAO ncdao = new NCreditoDAO();
    Detalleventa dventa = new Detalleventa();
    DetalleventaDAO dventadao = new DetalleventaDAO();
    Venta v = new Venta();
    VentaDAO vdao = new VentaDAO();

    //sirve para que la tabla tenga estructura y pueda almacenar datos
    DefaultTableModel modelo = new DefaultTableModel();
    // tmp almacena datos en la tabla de manera transitoria
    DefaultTableModel tmp = new DefaultTableModel();
    //declaramos la variable fecha
    Date f = new Date();
    String fecha = new SimpleDateFormat("dd-MM-yyyy").format(f);
    String fechahora = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(f);
    //declaramos una variable entera para la fila en presupeusto
    int itempres;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Sistema.class.getName());

    //constrcutor de las vistas
    public Sistema() {
        initComponents();
    }
    
    //creamos un construcor para recibir al login y dar privilegios de usuarios
    public Sistema(Usuarios u) {
        initComponents();// inicia los paquetes graficos
        // con este codigo se coloca la vista en el centro de la pantalla
        this.setLocationRelativeTo(null);
        // ocultamos el txtCodigo en la vista del sistema
        txtCodRub.setVisible(false);
        txtCodProv.setVisible(false);
        txtIdPres.setVisible(false);
        txtCodConf.setVisible(false);
        txtCodUs.setVisible(false);
        txtIdNC.setVisible(true);
        txtIdNV.setVisible(false);
        txtIdVenta.setVisible(false);
        //desabilita la opcion los tabbed pane
        for (int i = 0; i < jTabbedPane1.getTabCount(); i++) {
            jTabbedPane1.setEnabledAt(i, false);
        }
        //apenas abra el sistema se pulse el boton ventas
        btnVentas.doClick();
        
        //si es igual a asistente me bloquea el boton de config (podes bloquear todos los botones invocando su variable)
        if ("Asistente".equals(u.getRol())) {
            btnNuevoProd.setEnabled(false);
            btnGuardarProd.setEnabled(false);
            btnEditarProd.setEnabled(false);
            btnEliminarProd.setEnabled(false);
            btnNuevoProv.setEnabled(false);
            btnGuardarProv.setEnabled(false);
            btnEditarProv.setEnabled(false);
            btnEliminarProv.setEnabled(false);
            btnNuevoRub.setEnabled(false);
            btnGuardarRub.setEnabled(false);
            btnEditarRub.setEnabled(false);
            btnEliminarRub.setEnabled(false);
            btnNuevoUs.setEnabled(false);
            btnGuardarUs.setEnabled(false);
            btnEditarUs.setEnabled(false);
            btnEliminarUs.setEnabled(false);
            btnEditarConf.setEnabled(false);
            //asignamos el nombre de usuario en el sistema
            labelMenuUsuario.setText(u.getNombre());
        }else{
            labelMenuUsuario.setText(u.getNombre());
        }
    }

    // metodo para limpiar la tabla (este metodo sirve para limpiar cualquier tabla)
    public void LimpiarTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    //metodo para listar rubro en la tabla
    public void ListarRubro() {
        List<Rubro> Listarrub = rubdao.ListarRubro();
        modelo = (DefaultTableModel) tablaRubro.getModel();
        Object[] ob = new Object[2];//cantidad de columnas en la fila
        for (int i = 0; i < Listarrub.size(); i++) {
            ob[0] = Listarrub.get(i).getCodigo();
            ob[1] = Listarrub.get(i).getDescripcion();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaRubro.setModel(modelo);
    }

    //meotodo para listar proveedor en la tabla
    public void ListarProveedor() {
        List<Proveedor> Listarprov = provdao.ListarProveedor();
        modelo = (DefaultTableModel) tablaProveedor.getModel();
        Object[] ob = new Object[5];//cantidad de columnas en la fila
        for (int i = 0; i < Listarprov.size(); i++) {
            ob[0] = Listarprov.get(i).getCodigo();
            ob[1] = Listarprov.get(i).getNombre();
            ob[2] = Listarprov.get(i).getCorreo();
            ob[3] = Listarprov.get(i).getTelefono();
            ob[4] = Listarprov.get(i).getCbu();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaProveedor.setModel(modelo);
    }

    //meotodo para listar proveedor en la tabla
    public void ListarProductos() {
        List<Productos> Listarprod = prodao.ListarProductos();
        modelo = (DefaultTableModel) tablaProductos.getModel();
        Object[] ob = new Object[8];//cantidad de columnas en la fila
        for (int i = 0; i < Listarprod.size(); i++) {
            ob[0] = Listarprod.get(i).getFecha();
            ob[1] = Listarprod.get(i).getCodigo();
            ob[2] = Listarprod.get(i).getDescripcion();
            ob[3] = Listarprod.get(i).getPrecio();
            ob[4] = Listarprod.get(i).getCantidad();
            ob[5] = Listarprod.get(i).getCantidad_min();
            ob[6] = Listarprod.get(i).getProveedor();
            ob[7] = Listarprod.get(i).getRubro();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaProductos.setModel(modelo);
    }

    //meotodo para listar proveedor en la tabla
    public void ListarPedidos() {
        List<Productos> Listarped = prodao.ListarPedido();
        modelo = (DefaultTableModel) tablaPedido.getModel();
        Object[] ob = new Object[8];//cantidad de columnas en la fila
        for (int i = 0; i < Listarped.size(); i++) {
            ob[0] = Listarped.get(i).getCodigo();
            ob[1] = Listarped.get(i).getDescripcion();
            ob[2] = Listarped.get(i).getCantidad();
            ob[3] = Listarped.get(i).getCantidad_min();
            ob[4] = Listarped.get(i).getProveedor();
            ob[5] = Listarped.get(i).getRubro();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaPedido.setModel(modelo);
    }

    //meotodo para listar proveedor en la tabla
    public void ListarPresupuesto() {
        List<Presupuesto> Listarpres = presdao.ListarPresupuesto();
        modelo = (DefaultTableModel) tablaPres.getModel();
        Object[] ob = new Object[6];//cantidad de columnas en la fila
        for (int i = 0; i < Listarpres.size(); i++) {
            ob[0] = Listarpres.get(i).getCodigo();
            ob[1] = Listarpres.get(i).getDescripcion();
            ob[2] = Listarpres.get(i).getPrecio();
            ob[3] = Listarpres.get(i).getCantidad();
            ob[4] = Listarpres.get(i).getSubtotal();
            ob[5] = Listarpres.get(i).getCodigopres();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaPres.setModel(modelo);
    }

    //metodo para listar la configuracion
    public void ListarConfig() {
        confdao.ListarConf(conf);
        txtCodConf.setText("" + conf.getCodigo());
        txtDniConf.setText("" + conf.getDni());
        txtNomConf.setText("" + conf.getNombre());
        txtTelConf.setText("" + conf.getTelefono());
        txtDirConf.setText("" + conf.getDireccion());
        txtRazonConf.setText("" + conf.getRazon());
    }

    //meotodo para listar usuarios en la tabla
    public void ListarUsuarios() {
        List<Usuarios> Listarus = usdao.ListarUsuario();
        modelo = (DefaultTableModel) tablaUs.getModel();
        Object[] ob = new Object[8];//cantidad de columnas en la fila
        for (int i = 0; i < Listarus.size(); i++) {
            ob[0] = Listarus.get(i).getCodigo();
            ob[1] = Listarus.get(i).getNombre();
            ob[2] = Listarus.get(i).getDni();
            ob[3] = Listarus.get(i).getClave();
            ob[4] = Listarus.get(i).getTelefono();
            ob[5] = Listarus.get(i).getCbu();
            ob[6] = Listarus.get(i).getRol();
            ob[7] = Listarus.get(i).getCodigo();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaUs.setModel(modelo);
    }

    //meotodo para listar detalle nota
    public void ListarNota() {
        List<NCredito> Listarnota = ncdao.ListarNota();
        modelo = (DefaultTableModel) tablaNC.getModel();
        Object[] ob = new Object[6];//cantidad de columnas en la fila
        for (int i = 0; i < Listarnota.size(); i++) {
            ob[0] = Listarnota.get(i).getCodigo();
            ob[1] = Listarnota.get(i).getDescripcion();
            ob[2] = Listarnota.get(i).getPrecio();
            ob[3] = Listarnota.get(i).getCantidad();
            ob[4] = Listarnota.get(i).getSubtotal();
            ob[5] = Listarnota.get(i).getCodigonota();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaNC.setModel(modelo);
    }

    //meotodo para listar detalle nueva venta
    public void ListarNV() {
        int v = Integer.parseInt(txtVentaNV.getText());
        List<Detalleventa> ListarDV = dventadao.ListarDV(v);
        modelo = (DefaultTableModel) tablaNV.getModel();
        Object[] ob = new Object[8];//cantidad de columnas en la fila
        for (int i = 0; i < ListarDV.size(); i++) {
            ob[0] = ListarDV.get(i).getCodigo();
            ob[1] = ListarDV.get(i).getDescripcion();
            ob[2] = ListarDV.get(i).getPrecio();
            ob[3] = ListarDV.get(i).getCantidad();
            ob[4] = ListarDV.get(i).getSubtotal();
            ob[5] = ListarDV.get(i).getCodigo_venta();
            ob[6] = ListarDV.get(i).getCodigonv();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaNV.setModel(modelo);
    }

    //meotodo para listar ventas en la tabla
    public void ListarVentas(String fecha1) {
        List<Venta> Listarvent = vdao.ListarVentas(fecha1);
        modelo = (DefaultTableModel) tablaVenta.getModel();
        Object[] ob = new Object[5];//cantidad de columnas en la fila
        for (int i = 0; i < Listarvent.size(); i++) {
            ob[0] = Listarvent.get(i).getFecha();
            ob[1] = Listarvent.get(i).getCodigo();
            ob[2] = Listarvent.get(i).getTotal();
            ob[3] = Listarvent.get(i).getCliente();
            ob[4] = Listarvent.get(i).getUsuario();
            modelo.addRow(ob); // mandamos los resultados al modelo
        }
        tablaVenta.setModel(modelo);
    }

    //metodo para mostrar num de venta
    public void MostrarVenta() {
        dventadao.MostrarVenta(v);
        txtVentaNV.setText("" + v.getCodigo());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        btnPdfVenta = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        btnResumenVenta = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        cbxFechaVenta = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        txtDiaVenta = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodNV = new javax.swing.JTextField();
        txtDescNV = new javax.swing.JTextField();
        txtPrecioNV = new javax.swing.JTextField();
        txtCantDisNV = new javax.swing.JTextField();
        txtCantNV = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaNV = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtClienteNV = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTotalNV = new javax.swing.JTextField();
        btnEliminarNV = new javax.swing.JButton();
        btnGenerarNV = new javax.swing.JButton();
        txtIdNV = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        txtDescuentoNV = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        txtVentaNV = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtCodPres = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaPres = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        txtDescPres = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtPrecioPres = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtCantDisPres = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtCantPres = new javax.swing.JTextField();
        btnEliminarPres = new javax.swing.JButton();
        txtIdPres = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTotalPres = new javax.swing.JTextField();
        btnGenerarPres = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtCodNC = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtDescNC = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtPrecioNC = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        txtCantNC = new javax.swing.JTextField();
        btnEliminarNC = new javax.swing.JButton();
        txtIdNC = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        txtTotalNC = new javax.swing.JTextField();
        btnGenerarNC = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaNC = new javax.swing.JTable();
        jLabel43 = new javax.swing.JLabel();
        txtCantDisNC = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtCodProd = new javax.swing.JTextField();
        txtDescProd = new javax.swing.JTextField();
        txtPrecioProd = new javax.swing.JTextField();
        txtCantProd = new javax.swing.JTextField();
        txtCantminProd = new javax.swing.JTextField();
        cbxProvProd = new javax.swing.JComboBox<>();
        cbxRubroProd = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnNuevoProd = new javax.swing.JButton();
        btnGuardarProd = new javax.swing.JButton();
        btnEditarProd = new javax.swing.JButton();
        btnEliminarProd = new javax.swing.JButton();
        btnControlStock = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tablaPedido = new javax.swing.JTable();
        cbxProvPedido = new javax.swing.JComboBox<>();
        btnGenerarPed = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtNombreProv = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtCorreoProv = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtTelefProv = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCbuProv = new javax.swing.JTextField();
        btnNuevoProv = new javax.swing.JButton();
        btnGuardarProv = new javax.swing.JButton();
        btnEliminarProv = new javax.swing.JButton();
        btnEditarProv = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaProveedor = new javax.swing.JTable();
        txtCodProv = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        txtDescRub = new javax.swing.JTextField();
        btnNuevoRub = new javax.swing.JButton();
        btnGuardarRub = new javax.swing.JButton();
        btnEditarRub = new javax.swing.JButton();
        btnEliminarRub = new javax.swing.JButton();
        txtCodRub = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaRubro = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtNombreUs = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        txtDniUs = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        txtCbuUs = new javax.swing.JTextField();
        btnNuevoUs = new javax.swing.JButton();
        btnGuardarUs = new javax.swing.JButton();
        btnEliminarUs = new javax.swing.JButton();
        btnEditarUs = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tablaUs = new javax.swing.JTable();
        jLabel41 = new javax.swing.JLabel();
        cbxRolUs = new javax.swing.JComboBox<>();
        jLabel42 = new javax.swing.JLabel();
        txtClaveUs = new javax.swing.JPasswordField();
        txtTelefUs = new javax.swing.JTextField();
        txtCodUs = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnEditarConf = new javax.swing.JButton();
        txtDniConf = new javax.swing.JTextField();
        txtNomConf = new javax.swing.JTextField();
        txtTelConf = new javax.swing.JTextField();
        txtDirConf = new javax.swing.JTextField();
        txtRazonConf = new javax.swing.JTextField();
        txtCodConf = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnVentas = new javax.swing.JButton();
        btnNotas = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnProveedorMenu = new javax.swing.JButton();
        btnRubroMenu = new javax.swing.JButton();
        labelMenuUsuario = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btnPdfVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPdfVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/pdf.png"))); // NOI18N
        btnPdfVenta.setText("Ver");
        btnPdfVenta.addActionListener(this::btnPdfVentaActionPerformed);

        btnResumenVenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnResumenVenta.setText("Resumen Diario");
        btnResumenVenta.addActionListener(this::btnResumenVentaActionPerformed);

        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Num. Venta", "Total", "Cliente", "Usuario"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaVenta);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel40.setText("Ventas del dia:");
        jLabel40.setToolTipText("");

        txtDiaVenta.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDiaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPdfVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnResumenVenta))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1008, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResumenVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPdfVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFechaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(txtDiaVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ventas", jPanel4);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Codigo:");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Descripcion:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Precio:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Cantidad Disp.:");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Cantidad:");

        txtCodNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodNVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodNVKeyTyped(evt);
            }
        });

        txtDescNV.setEditable(false);

        txtPrecioNV.setEditable(false);

        txtCantDisNV.setEditable(false);

        txtCantNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantNVKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantNVKeyTyped(evt);
            }
        });

        tablaNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal", "Cdigo_venta", "Codigonv"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaNV);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Cliente:");

        txtClienteNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClienteNVKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/money.png"))); // NOI18N
        jLabel7.setText("Total:");

        txtTotalNV.setEditable(false);

        btnEliminarNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarNV.setText("Eliminar");
        btnEliminarNV.addActionListener(this::btnEliminarNVActionPerformed);

        btnGenerarNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGenerarNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/print.png"))); // NOI18N
        btnGenerarNV.setText("Generar Venta");
        btnGenerarNV.addActionListener(this::btnGenerarNVActionPerformed);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel44.setText("Descuento:");

        txtDescuentoNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoNVKeyTyped(evt);
            }
        });

        jLabel45.setBackground(new java.awt.Color(255, 255, 255));
        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel45.setText("Venta Num:");

        txtVentaNV.setEditable(false);
        txtVentaNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtVentaNVKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVentaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantDisNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnEliminarNV)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdNV, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtClienteNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTotalNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnGenerarNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescuentoNV, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVentaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantDisNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtCantNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarNV)
                            .addComponent(txtIdNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel44)
                            .addComponent(txtDescuentoNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtTotalNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtClienteNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarNV))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Nueva Venta", jPanel3);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Codigo:");

        txtCodPres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodPresKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodPresKeyTyped(evt);
            }
        });

        jScrollPane4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane4MouseClicked(evt);
            }
        });

        tablaPres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal", "CodigoP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaPres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaPresMouseClicked(evt);
            }
        });
        tablaPres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaPresKeyPressed(evt);
            }
        });
        jScrollPane4.setViewportView(tablaPres);
        if (tablaPres.getColumnModel().getColumnCount() > 0) {
            tablaPres.getColumnModel().getColumn(5).setMinWidth(0);
            tablaPres.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaPres.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Descripcion:");

        txtDescPres.setEditable(false);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Precio:");

        txtPrecioPres.setEditable(false);

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setText("Cantidad Disp.:");

        txtCantDisPres.setEditable(false);

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Cantidad:");

        txtCantPres.addActionListener(this::txtCantPresActionPerformed);
        txtCantPres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantPresKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantPresKeyTyped(evt);
            }
        });

        btnEliminarPres.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarPres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarPres.setText("Eliminar");
        btnEliminarPres.addActionListener(this::btnEliminarPresActionPerformed);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/money.png"))); // NOI18N
        jLabel21.setText("Total:");

        txtTotalPres.setEditable(false);

        btnGenerarPres.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGenerarPres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/print.png"))); // NOI18N
        btnGenerarPres.setText("Generar Presupuesto");
        btnGenerarPres.addActionListener(this::btnGenerarPresActionPerformed);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantDisPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnEliminarPres)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdPres, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalPres, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnGenerarPres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantDisPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(txtCantPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarPres)
                            .addComponent(txtIdPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtTotalPres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarPres)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Presupuesto", jPanel6);

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setText("Codigo:");

        txtCodNC.addActionListener(this::txtCodNCActionPerformed);
        txtCodNC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodNCKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodNCKeyTyped(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel27.setText("Descripcion:");

        txtDescNC.setEditable(false);

        jLabel28.setBackground(new java.awt.Color(255, 255, 255));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel28.setText("Precio:");

        txtPrecioNC.setEditable(false);

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel30.setText("Cantidad:");

        txtCantNC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantNCKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantNCKeyTyped(evt);
            }
        });

        btnEliminarNC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarNC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarNC.setText("Eliminar");
        btnEliminarNC.addActionListener(this::btnEliminarNCActionPerformed);

        txtIdNC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdNCKeyTyped(evt);
            }
        });

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/money.png"))); // NOI18N
        jLabel31.setText("Total:");

        txtTotalNC.setEditable(false);

        btnGenerarNC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGenerarNC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/print.png"))); // NOI18N
        btnGenerarNC.setText("Generar Nota de Cred.");
        btnGenerarNC.addActionListener(this::btnGenerarNCActionPerformed);

        tablaNC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Precio", "Cantidad", "Subtotal", "CodigoD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaNC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaNCMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tablaNC);
        if (tablaNC.getColumnModel().getColumnCount() > 0) {
            tablaNC.getColumnModel().getColumn(5).setMinWidth(0);
            tablaNC.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaNC.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel43.setText("Cantidad Disp.:");

        txtCantDisNC.setEditable(false);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodNC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescNC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPrecioNC, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(txtCantDisNC)))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtCantNC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(btnEliminarNC)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtIdNC, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTotalNC, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(btnGenerarNC, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDescNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrecioNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel43)
                            .addComponent(txtCantDisNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel30)
                            .addComponent(txtCantNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarNC)
                            .addComponent(txtIdNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(txtTotalNC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarNC)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Notas de Cred.", jPanel9);

        jPanel5.setForeground(new java.awt.Color(153, 153, 153));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Descripcion:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Codigo:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Precio:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Cantidad:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Cant. Minima:");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Proveedor:");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Rubro:");

        txtCodProd.setEditable(false);

        txtDescProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescProdKeyTyped(evt);
            }
        });

        txtPrecioProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProdKeyTyped(evt);
            }
        });

        txtCantProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantProdKeyTyped(evt);
            }
        });

        txtCantminProd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantminProdKeyTyped(evt);
            }
        });

        cbxProvProd.setEditable(true);
        cbxProvProd.addActionListener(this::cbxProvProdActionPerformed);

        cbxRubroProd.setEditable(true);

        jScrollPane3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane3MouseClicked(evt);
            }
        });

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Fecha", "Codigo", "Descripcion", "Precio", "Cantidad", "Cantidad MIn.", "Proveedor", "Rubro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        tablaProductos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaProductosKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tablaProductos);

        btnNuevoProd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/nuevo.png"))); // NOI18N
        btnNuevoProd.setText("Nuevo");
        btnNuevoProd.addActionListener(this::btnNuevoProdActionPerformed);

        btnGuardarProd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/GuardarTodo.png"))); // NOI18N
        btnGuardarProd.setText("Guardar");
        btnGuardarProd.addActionListener(this::btnGuardarProdActionPerformed);

        btnEditarProd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Actualizar (2).png"))); // NOI18N
        btnEditarProd.setText("Editar");
        btnEditarProd.addActionListener(this::btnEditarProdActionPerformed);

        btnEliminarProd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarProd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarProd.setText("Eliminar");
        btnEliminarProd.addActionListener(this::btnEliminarProdActionPerformed);

        btnControlStock.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnControlStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/excel.png"))); // NOI18N
        btnControlStock.setText("Control Stock");
        btnControlStock.addActionListener(this::btnControlStockActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCantminProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProvProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxRubroProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtDescProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnControlStock)
                        .addGap(0, 647, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtCodProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtDescProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtPrecioProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtCantProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtCantminProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(cbxProvProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cbxRubroProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnControlStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))))
        );

        jTabbedPane1.addTab("Productos", jPanel5);

        tablaPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Cantidad MIn.", "Proveedor", "Rubro"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tablaPedido);

        btnGenerarPed.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGenerarPed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/excel.png"))); // NOI18N
        btnGenerarPed.setText("Generar Pedido");
        btnGenerarPed.addActionListener(this::btnGenerarPedActionPerformed);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 1014, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(cbxProvPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarPed)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxProvPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerarPed))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedidos", jPanel10);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Nombre:");

        txtNombreProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProvKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Correo:");

        txtCorreoProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCorreoProvKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel24.setText("Telefono:");

        txtTelefProv.addActionListener(this::txtTelefProvActionPerformed);
        txtTelefProv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefProvKeyTyped(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel25.setText("CBU/Alias:");

        btnNuevoProv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/nuevo.png"))); // NOI18N
        btnNuevoProv.setText("Nuevo");
        btnNuevoProv.addActionListener(this::btnNuevoProvActionPerformed);

        btnGuardarProv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/GuardarTodo.png"))); // NOI18N
        btnGuardarProv.setText("Guardar");
        btnGuardarProv.addActionListener(this::btnGuardarProvActionPerformed);

        btnEliminarProv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarProv.setText("Eliminar");
        btnEliminarProv.addActionListener(this::btnEliminarProvActionPerformed);

        btnEditarProv.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarProv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Actualizar (2).png"))); // NOI18N
        btnEditarProv.setText("Editar");
        btnEditarProv.addActionListener(this::btnEditarProvActionPerformed);

        tablaProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Correo", "Telefono", "CBU/Alias"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProveedorMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaProveedor);
        if (tablaProveedor.getColumnModel().getColumnCount() > 0) {
            tablaProveedor.getColumnModel().getColumn(0).setMinWidth(0);
            tablaProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefProv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCbuProv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCorreoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodProv, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnGuardarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEliminarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtNombreProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtCorreoProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtTelefProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txtCbuProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarProv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(172, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Proveedor", jPanel7);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setText("Descripcion:");

        txtDescRub.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescRubKeyTyped(evt);
            }
        });

        btnNuevoRub.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoRub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/nuevo.png"))); // NOI18N
        btnNuevoRub.setText("Nuevo");
        btnNuevoRub.addActionListener(this::btnNuevoRubActionPerformed);

        btnGuardarRub.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardarRub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/GuardarTodo.png"))); // NOI18N
        btnGuardarRub.setText("Guardar");
        btnGuardarRub.addActionListener(this::btnGuardarRubActionPerformed);

        btnEditarRub.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarRub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Actualizar (2).png"))); // NOI18N
        btnEditarRub.setText("Editar");
        btnEditarRub.addActionListener(this::btnEditarRubActionPerformed);

        btnEliminarRub.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarRub.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarRub.setText("Eliminar");
        btnEliminarRub.addActionListener(this::btnEliminarRubActionPerformed);

        tablaRubro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripcion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaRubro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaRubroMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaRubro);
        if (tablaRubro.getColumnModel().getColumnCount() > 0) {
            tablaRubro.getColumnModel().getColumn(0).setMinWidth(0);
            tablaRubro.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaRubro.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescRub, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevoRub, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodRub, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnGuardarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnEliminarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(txtDescRub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoRub, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarRub, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodRub, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Rubro", jPanel8);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setText("Nombre:");

        txtNombreUs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreUsKeyTyped(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel32.setText("DNI:");

        txtDniUs.addActionListener(this::txtDniUsActionPerformed);
        txtDniUs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniUsKeyTyped(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel33.setText("Telefono:");

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel34.setText("CBU/Alias:");

        txtCbuUs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCbuUsKeyTyped(evt);
            }
        });

        btnNuevoUs.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/nuevo.png"))); // NOI18N
        btnNuevoUs.setText("Nuevo");
        btnNuevoUs.addActionListener(this::btnNuevoUsActionPerformed);

        btnGuardarUs.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnGuardarUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/GuardarTodo.png"))); // NOI18N
        btnGuardarUs.setText("Guardar");
        btnGuardarUs.addActionListener(this::btnGuardarUsActionPerformed);

        btnEliminarUs.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/eliminar.png"))); // NOI18N
        btnEliminarUs.setText("Eliminar");
        btnEliminarUs.addActionListener(this::btnEliminarUsActionPerformed);

        btnEditarUs.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarUs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Actualizar (2).png"))); // NOI18N
        btnEditarUs.setText("Editar");
        btnEditarUs.addActionListener(this::btnEditarUsActionPerformed);

        tablaUs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "DNI", "Clave", "Telefono", "CBU/Alias", "Rol"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaUs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaUsMouseClicked(evt);
            }
        });
        tablaUs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tablaUsKeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(tablaUs);
        if (tablaUs.getColumnModel().getColumnCount() > 0) {
            tablaUs.getColumnModel().getColumn(0).setMinWidth(0);
            tablaUs.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaUs.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaUs.getColumnModel().getColumn(3).setMinWidth(0);
            tablaUs.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaUs.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel41.setText("Rol:");

        cbxRolUs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Asistente" }));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel42.setText("Clave:");

        txtTelefUs.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefUsKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCbuUs, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                    .addComponent(cbxRolUs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnNuevoUs, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEditarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnGuardarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnEliminarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                                    .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtTelefUs))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNombreUs, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(jPanel11Layout.createSequentialGroup()
                                                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(53, 53, 53)))
                                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDniUs, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                                            .addComponent(txtClaveUs))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(txtCodUs, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)))
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(txtNombreUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(txtDniUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(txtClaveUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel33)
                            .addComponent(txtTelefUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel34)
                            .addComponent(txtCbuUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel41)
                            .addComponent(cbxRolUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevoUs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEditarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarUs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodUs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Usuarios", jPanel11);

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setText("DNI:");

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setText("Nombre:");

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setText("Telefono:");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setText("Direccion:");

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel39.setText("Razon:");

        btnEditarConf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditarConf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Actualizar (2).png"))); // NOI18N
        btnEditarConf.setText("Editar");
        btnEditarConf.addActionListener(this::btnEditarConfActionPerformed);

        txtDniConf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniConfKeyTyped(evt);
            }
        });

        txtNomConf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomConfKeyTyped(evt);
            }
        });

        txtTelConf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelConfKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelConfKeyTyped(evt);
            }
        });

        txtDirConf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtDirConfKeyPressed(evt);
            }
        });

        txtRazonConf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRazonConfKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDniConf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelConf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDirConf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNomConf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRazonConf, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(btnEditarConf, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodConf, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(798, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(txtDniConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(txtNomConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(txtTelConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(txtDirConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(txtRazonConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEditarConf, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodConf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(210, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Configuracion", jPanel12);

        btnVentas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/compras.png"))); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setAlignmentY(0.0F);
        btnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnVentas.setMaximumSize(new java.awt.Dimension(72, 33));
        btnVentas.setMinimumSize(new java.awt.Dimension(72, 33));
        btnVentas.setPreferredSize(new java.awt.Dimension(72, 33));
        btnVentas.addActionListener(this::btnVentasActionPerformed);

        btnNotas.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnNotas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/money.png"))); // NOI18N
        btnNotas.setText("Notas de  Cred.");
        btnNotas.setAlignmentY(0.0F);
        btnNotas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNotas.setMaximumSize(new java.awt.Dimension(72, 33));
        btnNotas.setMinimumSize(new java.awt.Dimension(72, 33));
        btnNotas.setPreferredSize(new java.awt.Dimension(72, 33));
        btnNotas.addActionListener(this::btnNotasActionPerformed);

        btnPedidos.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/compras.png"))); // NOI18N
        btnPedidos.setText("Pedidos");
        btnPedidos.setAlignmentY(0.0F);
        btnPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnPedidos.setMaximumSize(new java.awt.Dimension(72, 33));
        btnPedidos.setMinimumSize(new java.awt.Dimension(72, 33));
        btnPedidos.setPreferredSize(new java.awt.Dimension(72, 33));
        btnPedidos.addActionListener(this::btnPedidosActionPerformed);

        btnConfig.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/config.png"))); // NOI18N
        btnConfig.setText("Configuracion");
        btnConfig.setAlignmentY(0.0F);
        btnConfig.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnConfig.setMaximumSize(new java.awt.Dimension(72, 33));
        btnConfig.setMinimumSize(new java.awt.Dimension(72, 33));
        btnConfig.setPreferredSize(new java.awt.Dimension(72, 33));
        btnConfig.addActionListener(this::btnConfigActionPerformed);

        btnUsuarios.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Clientes.png"))); // NOI18N
        btnUsuarios.setText("Usuarios");
        btnUsuarios.setAlignmentY(0.0F);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnUsuarios.setMaximumSize(new java.awt.Dimension(72, 33));
        btnUsuarios.setMinimumSize(new java.awt.Dimension(72, 33));
        btnUsuarios.setPreferredSize(new java.awt.Dimension(72, 33));
        btnUsuarios.addActionListener(this::btnUsuariosActionPerformed);

        btnSalir.setBackground(new java.awt.Color(204, 0, 0));
        btnSalir.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/salir_1.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.addActionListener(this::btnSalirActionPerformed);

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Carrito-de-compras.png"))); // NOI18N
        jButton2.setText("Presupuesto");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        btnProductos.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/producto.png"))); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setAlignmentY(0.0F);
        btnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnProductos.setMaximumSize(new java.awt.Dimension(72, 33));
        btnProductos.setMinimumSize(new java.awt.Dimension(72, 33));
        btnProductos.setPreferredSize(new java.awt.Dimension(72, 33));
        btnProductos.addActionListener(this::btnProductosActionPerformed);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/Nventa.png"))); // NOI18N
        jButton1.setText("Nueva Venta");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        btnProveedorMenu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnProveedorMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/proveedor.png"))); // NOI18N
        btnProveedorMenu.setText("Proveedor");
        btnProveedorMenu.addActionListener(this::btnProveedorMenuActionPerformed);

        btnRubroMenu.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnRubroMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/producto.png"))); // NOI18N
        btnRubroMenu.setText("Rubro");
        btnRubroMenu.addActionListener(this::btnRubroMenuActionPerformed);

        labelMenuUsuario.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRubroMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProveedorMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelMenuUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVentas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProveedorMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRubroMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelMenuUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //boton eliminar productos de la tabla nueva venta
    private void btnEliminarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNVActionPerformed
        if (!"".equals(txtIdNV.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto de la lista?");
            if (pregunta == 0) {
                int id = Integer.parseInt(txtIdNV.getText());
                int cod = Integer.parseInt(txtCodNV.getText());
                double cant = Double.parseDouble(txtCantNV.getText());
                dventadao.SumarStock(cod, cant);
                dventadao.EliminarProdNV(id);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormNV();
                ListarNV();
                TotalNV();//actualizamos el total
                txtCodNV.requestFocus();
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }
    }//GEN-LAST:event_btnEliminarNVActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        jTabbedPane1.setSelectedIndex(9);
        ListarConfig();
    }//GEN-LAST:event_btnConfigActionPerformed

    private void cbxProvProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxProvProdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxProvProdActionPerformed

    //boton guardar productos
    private void btnGuardarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProdActionPerformed
        if ("".equals(txtDescProd.getText()) || "".equals(txtDescProd.getText()) || "".equals(txtPrecioProd.getText()) || "".equals(txtCantProd.getText()) || "".equals(txtCantminProd.getText()) || "".equals(cbxProvProd.getSelectedItem()) || "".equals(cbxRubroProd.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Complete el formulario");
        } else {
            if ("".equals(txtCodProd.getText())) {
                prod.setFecha(fecha);
                prod.setDescripcion(txtDescProd.getText());
                prod.setPrecio(Double.parseDouble(txtPrecioProd.getText()));
                prod.setCantidad(Double.parseDouble(txtCantProd.getText()));
                prod.setCantidad_min(Double.parseDouble(txtCantminProd.getText()));
                prod.setProveedor(cbxProvProd.getSelectedItem().toString());
                prod.setRubro(cbxRubroProd.getSelectedItem().toString());
                prodao.RegistrarProductos(prod);
                LimpiarTabla();
                ListarProductos();
                LimpiarFormProductos();
                JOptionPane.showMessageDialog(null, "Producto registrado");
                txtDescProd.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
            }
        }
    }//GEN-LAST:event_btnGuardarProdActionPerformed

    //boton para eliminar productos de presupuesto
    private void btnEliminarPresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPresActionPerformed
        if (!"".equals(txtIdPres.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto de la lista?");
            if (pregunta == 0) {
                int cod = Integer.parseInt(txtIdPres.getText());
                presdao.EliminarProdPresupuesto(cod);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormPresupuesto();
                ListarPresupuesto();
                TotalPresupuesto();//actualizamos el total
                txtCodPres.requestFocus();
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }
    }//GEN-LAST:event_btnEliminarPresActionPerformed

    //boton guardar proveedor
    private void btnGuardarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProvActionPerformed
        if ("".equals(txtNombreProv.getText()) || "".equals(txtCorreoProv.getText()) || "".equals(txtTelefProv.getText()) || "".equals(txtCbuProv.getText())) {
            JOptionPane.showMessageDialog(null, "Complete el formulario");
        } else {
            if ("".equals(txtCodProv.getText())) {
                prov.setNombre(txtNombreProv.getText());
                prov.setCorreo(txtCorreoProv.getText());
                prov.setTelefono(Integer.parseInt(txtTelefProv.getText()));
                prov.setCbu(txtCbuProv.getText());
                provdao.RegistrarProveedor(prov);
                LimpiarTabla();
                ListarProveedor();
                LimpiarFormProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor registrado");
                txtNombreProv.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "El proveedor ya esta registrado");
            }
        }
    }//GEN-LAST:event_btnGuardarProvActionPerformed

    //boton guardar rubro
    private void btnGuardarRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarRubActionPerformed
        if (!"".equals(txtDescRub.getText())) {
            if ("".equals(txtCodRub.getText())) {
                rub.setDescripcion(txtDescRub.getText());
                rubdao.RegistrarRubro(rub);
                LimpiarTabla();
                ListarRubro();
                LimpiarFormRubro();
                JOptionPane.showMessageDialog(null, "Rubro registrado");
                txtDescRub.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "El rubro ya esta registrado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Complete el formulario");
        }
    }//GEN-LAST:event_btnGuardarRubActionPerformed

    //boton eliminar productos de la nota de credito
    private void btnEliminarNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarNCActionPerformed
        if (!"".equals(txtIdNC.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el producto de la lista?");
            if (pregunta == 0) {
                int cod = Integer.parseInt(txtIdNC.getText());
                int codp = Integer.parseInt(txtCodNC.getText());
                double cant = Double.parseDouble(txtCantNC.getText());
                ncdao.RestarStock(codp, cant);
                ncdao.EliminarProdNC(cod);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormNC();
                ListarNota();
                TotalNC();//actualizamos el total
                txtCodNC.requestFocus();
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }
    }//GEN-LAST:event_btnEliminarNCActionPerformed

    //boton guardar usuario
    private void btnGuardarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarUsActionPerformed
        if ("".equals(txtNombreUs.getText()) || "".equals(txtDniUs.getText()) || "".equals(txtClaveUs.getText()) || "".equals(txtTelefUs.getText()) || "".equals(txtCbuUs.getText())) {
            JOptionPane.showMessageDialog(null, "Complete el formulario");
        } else {
            if ("".equals(txtCodUs.getText())) {
                us.setNombre(txtNombreUs.getText());
                us.setDni(Integer.parseInt(txtDniUs.getText()));
                us.setClave(txtClaveUs.getText());
                us.setTelefono(txtTelefUs.getText());
                us.setCbu(txtCbuUs.getText());
                us.setRol(cbxRolUs.getSelectedItem().toString());
                usdao.RegistrarUsuarios(us);
                LimpiarTabla();
                ListarUsuarios();
                LimpiarFormUsuario();
                JOptionPane.showMessageDialog(null, "Usuario registrado");
                txtNombreUs.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "El usuario ya esta registrado");
            }
        }
    }//GEN-LAST:event_btnGuardarUsActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        jTabbedPane1.setSelectedIndex(4);
        //llamamos a los metodos
        LimpiarTabla();
        ListarProductos();
        // llamamos al metodo consultar para llenar el combo box
        prodao.ConsultarProveedor(cbxProvProd);
        AutoCompleteDecorator.decorate(cbxProvProd);
        prodao.ConsultarRubro(cbxRubroProd);
        AutoCompleteDecorator.decorate(cbxRubroProd);
    }//GEN-LAST:event_btnProductosActionPerformed

    //boton rubro
    private void btnRubroMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRubroMenuActionPerformed
        jTabbedPane1.setSelectedIndex(7);
        //llamamos a los metodos
        LimpiarTabla();
        ListarRubro();
    }//GEN-LAST:event_btnRubroMenuActionPerformed

    private void btnNuevoRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRubActionPerformed
        //llamamos al metodo limpíar proveedor
        LimpiarFormRubro();
        txtDescRub.requestFocus();
    }//GEN-LAST:event_btnNuevoRubActionPerformed

    //mouse clicked para seleccionar rubro y muestre en el formulario
    private void tablaRubroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaRubroMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaRubro.rowAtPoint(evt.getPoint());
        txtCodRub.setText(tablaRubro.getValueAt(fila, 0).toString());
        txtDescRub.setText(tablaRubro.getValueAt(fila, 1).toString());
    }//GEN-LAST:event_tablaRubroMouseClicked

    //boton para editar rubro
    private void btnEditarRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarRubActionPerformed
        if ("".equals(txtCodRub.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un rubro");
        } else {
            // primero verificamos que los campos no esten vacios
            if (!"".equals(txtDescRub.getText())) {
                rub.setCodigo(Integer.parseInt(txtCodRub.getText()));
                rub.setDescripcion(txtDescRub.getText());
                rubdao.ModificarRubro(rub);
                JOptionPane.showMessageDialog(null, "Rubro actualizado");
                // luego de mandar a clientedao llamamos a los metodos para limpar la tabla
                LimpiarTabla();
                LimpiarFormRubro();
                ListarRubro();
            } else {
                JOptionPane.showMessageDialog(null, "Complete el formulario");
            }
        }
    }//GEN-LAST:event_btnEditarRubActionPerformed

    //boton eliminar rubro
    private void btnEliminarRubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarRubActionPerformed
        if (!"".equals(txtCodRub.getText())) {//verificamos que el codigo no este vacio
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + txtDescRub.getText() + " de la lista?");
            if (pregunta == 0) {
                int codigo = Integer.parseInt(txtCodRub.getText());
                rubdao.EliminarRubro(codigo);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormRubro();
                ListarRubro();
                JOptionPane.showMessageDialog(null, "Rubro eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un rubro");
        }
    }//GEN-LAST:event_btnEliminarRubActionPerformed

    private void btnProveedorMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorMenuActionPerformed
        jTabbedPane1.setSelectedIndex(6);
        //llamamos a los metodos
        LimpiarTabla();
        ListarProveedor();

    }//GEN-LAST:event_btnProveedorMenuActionPerformed

    private void btnNuevoProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProvActionPerformed
        //llamamos al metodo limpíar proveedor
        LimpiarFormProveedor();
        txtNombreProv.requestFocus();
    }//GEN-LAST:event_btnNuevoProvActionPerformed

    //validamos que solo ingresen texto en descripcion
    private void txtDescRubKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescRubKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtDescRubKeyTyped

    //validamos tipografia key typed
    private void txtNombreProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProvKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtNombreProvKeyTyped

    private void txtCorreoProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCorreoProvKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoProvKeyTyped

    //validamos tipografia
    private void txtTelefProvKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefProvKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtTelefProvKeyTyped

    //event/mouseclicked para seleccionar proveedor y mostrar en formulario
    private void tablaProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProveedorMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaProveedor.rowAtPoint(evt.getPoint());
        txtCodProv.setText(tablaProveedor.getValueAt(fila, 0).toString());
        txtNombreProv.setText(tablaProveedor.getValueAt(fila, 1).toString());
        txtCorreoProv.setText(tablaProveedor.getValueAt(fila, 2).toString());
        txtTelefProv.setText(tablaProveedor.getValueAt(fila, 3).toString());
        txtCbuProv.setText(tablaProveedor.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_tablaProveedorMouseClicked

    //boton editar proveedor
    private void btnEditarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProvActionPerformed
        if ("".equals(txtCodProv.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor");
        } else {
            // primero verificamos que los campos no esten vacios
            if ("".equals(txtNombreProv.getText()) || "".equals(txtCorreoProv.getText()) || "".equals(txtTelefProv.getText()) || "".equals(txtCbuProv.getText())) {
                JOptionPane.showMessageDialog(null, "Complete el formulario");
            } else {
                prov.setCodigo(Integer.parseInt(txtCodProv.getText()));
                prov.setNombre(txtNombreProv.getText());
                prov.setCorreo(txtCorreoProv.getText());
                prov.setTelefono(Integer.parseInt(txtTelefProv.getText()));
                prov.setCbu(txtCbuProv.getText());
                provdao.ModificarProveedor(prov);
                JOptionPane.showMessageDialog(null, "Proveedor actualizado");
                // luego de mandar a clientedao llamamos a los metodos para limpar la tabla
                LimpiarTabla();
                LimpiarFormProveedor();
                ListarProveedor();
            }
        }
    }//GEN-LAST:event_btnEditarProvActionPerformed

    //boton eliminar proveedor
    private void btnEliminarProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProvActionPerformed
        if (!"".equals(txtCodProv.getText())) {//verificamos que el codigo no este vacio
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + txtNombreProv.getText() + " de la lista?");
            if (pregunta == 0) {
                int codigo = Integer.parseInt(txtCodProv.getText());
                provdao.EliminarProveedor(codigo);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormProveedor();
                ListarProveedor();
                JOptionPane.showMessageDialog(null, "Proveedor eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor");
        }
    }//GEN-LAST:event_btnEliminarProvActionPerformed

    //boton salir
    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtCodNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodNVKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtCodNVKeyTyped

    private void txtTelefProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefProvActionPerformed

    }//GEN-LAST:event_txtTelefProvActionPerformed

    //para que solo ingresen texto
    private void txtDescProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescProdKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtDescProdKeyTyped

    private void txtPrecioProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProdKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtPrecioProd);
    }//GEN-LAST:event_txtPrecioProdKeyTyped

    private void txtCantProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantProdKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtCantProd);
    }//GEN-LAST:event_txtCantProdKeyTyped

    private void txtCantminProdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantminProdKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtCantminProd);
    }//GEN-LAST:event_txtCantminProdKeyTyped

    private void btnNuevoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProdActionPerformed
        LimpiarFormProductos();
        txtDescProd.requestFocus();
    }//GEN-LAST:event_btnNuevoProdActionPerformed

    private void tablaProductosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaProductosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaProductosKeyPressed

    //mouseclicked para capturar la informacion de una fila
    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaProductos.rowAtPoint(evt.getPoint());

        txtCodProd.setText(tablaProductos.getValueAt(fila, 1).toString());
        txtDescProd.setText(tablaProductos.getValueAt(fila, 2).toString());
        txtPrecioProd.setText(tablaProductos.getValueAt(fila, 3).toString());
        txtCantProd.setText(tablaProductos.getValueAt(fila, 4).toString());
        txtCantminProd.setText(tablaProductos.getValueAt(fila, 5).toString());
        cbxProvProd.setSelectedItem(tablaProductos.getValueAt(fila, 6).toString());
        cbxRubroProd.setSelectedItem(tablaProductos.getValueAt(fila, 7).toString());
    }//GEN-LAST:event_tablaProductosMouseClicked

    //boton editar proveedor
    private void btnEditarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarProdActionPerformed
        if ("".equals(txtCodProd.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        } else {
            // primero verificamos que los campos no esten vacios
            if ("".equals(txtDescProd.getText()) || "".equals(txtPrecioProd.getText()) || "".equals(txtCantProd.getText()) || "".equals(txtCantminProd.getText()) || "".equals(cbxProvProd.getSelectedItem()) || "".equals(cbxRubroProd.getSelectedItem())) {
                JOptionPane.showMessageDialog(null, "Complete el formulario");
            } else {
                prod.setFecha(fecha);
                prod.setCodigo(Integer.parseInt(txtCodProd.getText()));
                prod.setDescripcion(txtDescProd.getText());
                prod.setPrecio(Double.parseDouble(txtPrecioProd.getText()));
                prod.setCantidad(Double.parseDouble(txtCantProd.getText()));
                prod.setCantidad_min(Double.parseDouble(txtCantminProd.getText()));
                prod.setProveedor(cbxProvProd.getSelectedItem().toString());
                prod.setRubro(cbxRubroProd.getSelectedItem().toString());
                prodao.ModificarProductos(prod);
                JOptionPane.showMessageDialog(null, "Producto actualizado");
                // luego de mandar a clientedao llamamos a los metodos para limpar la tabla
                LimpiarTabla();
                LimpiarFormProductos();
                ListarProductos();
            }
        }
    }//GEN-LAST:event_btnEditarProdActionPerformed

    //boton eliminar productos
    private void btnEliminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdActionPerformed
        if (!"".equals(txtCodProd.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + txtDescProd.getText() + " de la lista?");
            if (pregunta == 0) {
                int cod = Integer.parseInt(txtCodProd.getText());
                prodao.EliminarProducto(cod);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormProductos();
                ListarProductos();
                JOptionPane.showMessageDialog(null, "Producto eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
        }
    }//GEN-LAST:event_btnEliminarProdActionPerformed

    //boton control de stock
    private void btnControlStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnControlStockActionPerformed
        Excel.ControlStock();
    }//GEN-LAST:event_btnControlStockActionPerformed

    //boton pedidos
    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        jTabbedPane1.setSelectedIndex(5);
        //llamamos a los metodos
        LimpiarTabla();
        ListarPedidos();
        // llamamos al metodo consultar para llenar el combo box
        prodao.ConsultarProveedor(cbxProvPedido);
        AutoCompleteDecorator.decorate(cbxProvPedido);
    }//GEN-LAST:event_btnPedidosActionPerformed

    //boton para generar pedidos
    private void btnGenerarPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPedActionPerformed
        prod.setProveedor(cbxProvPedido.getSelectedItem().toString());
        Excel.Pedidos(prod);
    }//GEN-LAST:event_btnGenerarPedActionPerformed

    private void jScrollPane3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane3MouseClicked

    //boton presupuesto
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        //llamamos a los metodos
        LimpiarTabla();
        ListarPresupuesto();
        TotalPresupuesto();
        txtCodPres.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    //al presionar enter busca el producto
    private void txtCodPresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPresKeyPressed
        // verificamos que el usuario presione la tecla enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que el campo no este vacio
            if (!"".equals(txtCodPres.getText())) {
                String cod = txtCodPres.getText();
                prod = presdao.BuscarProd(cod);
                //verificamos si el producto existe
                if (prod.getDescripcion() != null) {
                    //mosstramos todos los datos
                    txtDescPres.setText("" + prod.getDescripcion());
                    txtPrecioPres.setText("" + prod.getPrecio());
                    txtCantDisPres.setText("" + prod.getCantidad());
                    //pasamos el cursor a cantidad (requestfocus es para que el cursos se centre en una ventana sin necesidad que el usuario seleccione dicha ventana)
                    txtCantPres.requestFocus();
                } else {
                    //llamamos al metodo para limpiar formulario nueva venta
                    LimpiarFormPresupuesto();
                    //pasamos el cursor, se mantiene en el codigo
                    txtCodPres.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodPres.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodPresKeyPressed

    //key pressed al presionar enter en cantidad
    private void txtCantPresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantPresKeyPressed
        //verificamos que la tecla enter haya sido presionada
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que la cantidad no este vacia
            if (!"".equals(txtCantPres.getText())) {
                double precio = Double.parseDouble(txtPrecioPres.getText());
                double cantd = Double.parseDouble(txtCantDisPres.getText());
                double cant = Double.parseDouble(txtCantPres.getText());
                double subtotal = precio * cant;
                if (cantd < cant) {
                    JOptionPane.showMessageDialog(null, "No hay stock suficiente.");
                } else {
                    pres.setCodigo(Integer.parseInt(txtCodPres.getText()));
                    pres.setDescripcion(txtDescPres.getText());
                    pres.setPrecio(precio);
                    pres.setCantidad(cant);
                    pres.setSubtotal(subtotal);
                    presdao.RegistrarPresupuesto(pres);
                    LimpiarTabla();
                    LimpiarFormPresupuesto();
                    ListarPresupuesto();
                    TotalPresupuesto();
                    txtCodPres.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txtCantPresKeyPressed

    //key typed para codigo de presupuesto
    private void txtCodPresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodPresKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtCantProd);
    }//GEN-LAST:event_txtCodPresKeyTyped

    //key typed para cantidad en presupuesto
    private void txtCantPresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantPresKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtCantProd);
    }//GEN-LAST:event_txtCantPresKeyTyped

    //boton generar presupuesto
    private void btnGenerarPresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPresActionPerformed
        double total = Double.parseDouble(txtTotalPres.getText().toString().trim().replace(",", "."));
        if (total == 0.0) {
            JOptionPane.showMessageDialog(null, "Debes agregar un producto");
        } else {
            GenerarPresupuesto();
            presdao.NuevoPresupuesto();
            txtTotalPres.setText("0.00");
            LimpiarTabla();
            txtCodPres.requestFocus();
        }


    }//GEN-LAST:event_btnGenerarPresActionPerformed

    private void txtCantPresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantPresActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantPresActionPerformed

    //seleccionamos para borrar el producto de la tabla presupuesto
    private void tablaPresKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaPresKeyPressed

    }//GEN-LAST:event_tablaPresKeyPressed

    //seleccionamos la fila para una posible eliminacion
    private void tablaPresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaPresMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaPres.rowAtPoint(evt.getPoint());
        txtIdPres.setText(tablaPres.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tablaPresMouseClicked

    private void jScrollPane4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane4MouseClicked

    //boton editar configuracion
    private void btnEditarConfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarConfActionPerformed
        //validamos que no este vacio
        if ("".equals(txtDniConf.getText()) || "".equals(txtNomConf.getText()) || "".equals(txtTelConf.getText()) || "".equals(txtDirConf.getText()) || "".equals(txtRazonConf.getText())) {
            JOptionPane.showMessageDialog(null, "Complete el formulario");
        } else {
            conf.setDni(Integer.parseInt(txtDniConf.getText()));
            conf.setNombre(txtNomConf.getText());
            conf.setTelefono(txtTelConf.getText());
            conf.setDireccion(txtDirConf.getText());
            conf.setRazon(txtRazonConf.getText());
            conf.setCodigo(Integer.parseInt(txtCodConf.getText()));
            confdao.ModificarDatos(conf);
            JOptionPane.showMessageDialog(null, "Datos de la empresa modificado");
            ListarConfig();
        }
    }//GEN-LAST:event_btnEditarConfActionPerformed

    private void txtDniConfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniConfKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtDniConfKeyTyped

    private void txtNomConfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomConfKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtNomConfKeyTyped

    private void txtTelConfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelConfKeyPressed

    }//GEN-LAST:event_txtTelConfKeyPressed

    private void txtDirConfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDirConfKeyPressed

    }//GEN-LAST:event_txtDirConfKeyPressed

    private void txtRazonConfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRazonConfKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtRazonConfKeyTyped

    private void txtTelConfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelConfKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtTelConfKeyTyped

    private void txtNombreUsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreUsKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtNombreUsKeyTyped

    private void txtDniUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniUsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniUsActionPerformed

    private void txtDniUsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniUsKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtDniUsKeyTyped

    private void txtCbuUsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCbuUsKeyTyped

    }//GEN-LAST:event_txtCbuUsKeyTyped

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        jTabbedPane1.setSelectedIndex(8);
        //llamamos a los metodos
        LimpiarTabla();
        ListarUsuarios();
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnNuevoUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoUsActionPerformed
        //llamamos al metodo limpíar proveedor
        LimpiarFormUsuario();
        txtNombreUs.requestFocus();
    }//GEN-LAST:event_btnNuevoUsActionPerformed

    //boton eliminar usuario
    private void btnEliminarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarUsActionPerformed
        if (!"".equals(txtCodUs.getText())) {//verificamos que el codigo no este vacio
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Desea eliminar " + txtNombreUs.getText() + " de la lista?");
            if (pregunta == 0) {
                int codigo = Integer.parseInt(txtCodUs.getText());
                usdao.EliminarUsuario(codigo);
                //llamamos a los metedos
                LimpiarTabla();
                LimpiarFormUsuario();
                ListarUsuarios();
                JOptionPane.showMessageDialog(null, "Usuario eliminado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario");
        }
    }//GEN-LAST:event_btnEliminarUsActionPerformed

    //selecciona un usuario
    private void tablaUsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablaUsKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablaUsKeyPressed

    private void txtTelefUsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefUsKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtTelefUsKeyTyped

    //seleccion de usuario en la tabla
    private void tablaUsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaUsMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaUs.rowAtPoint(evt.getPoint());
        txtNombreUs.setText(tablaUs.getValueAt(fila, 1).toString());
        txtDniUs.setText(tablaUs.getValueAt(fila, 2).toString());
        txtClaveUs.setText(tablaUs.getValueAt(fila, 3).toString());
        txtTelefUs.setText(tablaUs.getValueAt(fila, 4).toString());
        txtCbuUs.setText(tablaUs.getValueAt(fila, 5).toString());
        cbxRolUs.setSelectedItem(tablaUs.getValueAt(fila, 6).toString());
        txtCodUs.setText(tablaUs.getValueAt(fila, 0).toString());
    }//GEN-LAST:event_tablaUsMouseClicked

    //boton editar usuario
    private void btnEditarUsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarUsActionPerformed
        if ("".equals(txtCodUs.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione un usuario");
        } else {
            // primero verificamos que los campos no esten vacios
            if ("".equals(txtNombreUs.getText()) || "".equals(txtDniUs.getText()) || "".equals(txtClaveUs.getText()) || "".equals(txtTelefUs.getText()) || "".equals(txtCbuUs.getText())) {
                JOptionPane.showMessageDialog(null, "Complete el formulario");
            } else {
                us.setCodigo(Integer.parseInt(txtCodUs.getText()));
                us.setNombre(txtNombreUs.getText());
                us.setDni(Integer.parseInt(txtDniUs.getText()));
                us.setClave(txtClaveUs.getText());
                us.setTelefono(txtTelefUs.getText());
                us.setCbu(txtCbuUs.getText());
                us.setRol(cbxRolUs.getSelectedItem().toString());
                usdao.ModificarUsuario(us);
                JOptionPane.showMessageDialog(null, "Usuario actualizado");
                // luego de mandar a clientedao llamamos a los metodos para limpar la tabla
                LimpiarTabla();
                LimpiarFormUsuario();
                ListarUsuarios();
            }
        }
    }//GEN-LAST:event_btnEditarUsActionPerformed

    //boton de notas de credito
    private void btnNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotasActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        //llamamos a los metodos
        LimpiarTabla();
        ListarNota();
        TotalNC();
        txtCodNC.requestFocus();
    }//GEN-LAST:event_btnNotasActionPerformed

    private void txtCodNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodNCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodNCActionPerformed

    private void txtCodNCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodNCKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtCodNCKeyTyped

    private void txtCantNCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantNCKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtCantNC);
    }//GEN-LAST:event_txtCantNCKeyTyped

    //darle enter a condio NC
    private void txtCodNCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodNCKeyPressed
        // verificamos que el usuario presione la tecla enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que el campo no este vacio
            if (!"".equals(txtCodNC.getText())) {
                String cod = txtCodNC.getText();
                prod = ncdao.BuscarProd(cod);
                //verificamos si el producto existe
                if (prod.getDescripcion() != null) {
                    //mosstramos todos los datos
                    txtDescNC.setText("" + prod.getDescripcion());
                    txtPrecioNC.setText("" + prod.getPrecio());
                    txtCantDisNC.setText("" + prod.getCantidad());
                    //pasamos el cursor a cantidad (requestfocus es para que el cursos se centre en una ventana sin necesidad que el usuario seleccione dicha ventana)
                    txtCantNC.requestFocus();
                } else {
                    //llamamos al metodo para limpiar formulario 
                    LimpiarFormNC();
                    //pasamos el cursor, se mantiene en el codigo
                    txtCodNC.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodPres.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodNCKeyPressed

    //al presionar enter
    private void txtCantNCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantNCKeyPressed
        //verificamos que la tecla enter haya sido presionada
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que la cantidad no este vacia
            if (!"".equals(txtCantNC.getText())) {
                int cod = Integer.parseInt(txtCodNC.getText().toString());
                double precio = Double.parseDouble(txtPrecioNC.getText());
                double cant = Double.parseDouble(txtCantNC.getText());
                double subtotal = precio * cant;
                nc.setCodigo(Integer.parseInt(txtCodNC.getText()));
                nc.setDescripcion(txtDescNC.getText());
                nc.setPrecio(Double.parseDouble(txtPrecioNC.getText()));
                nc.setCantidad(Double.parseDouble(txtCantNC.getText()));
                nc.setSubtotal(subtotal);
                ncdao.SumarStock(cod, cant);
                ncdao.RegistrarNota(nc);
                LimpiarTabla();
                LimpiarFormNC();
                ListarNota();
                TotalNC();
                txtCodNC.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txtCantNCKeyPressed

    //selecciona la fila de la tabla nota de credito
    private void tablaNCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaNCMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaNC.rowAtPoint(evt.getPoint());
        txtCodNC.setText(tablaNC.getValueAt(fila, 0).toString());
        txtDescNC.setText(tablaNC.getValueAt(fila, 1).toString());
        txtPrecioNC.setText(tablaNC.getValueAt(fila, 2).toString());
        txtCantNC.setText(tablaNC.getValueAt(fila, 3).toString());
        txtIdNC.setText(tablaNC.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tablaNCMouseClicked

    //boton generar nota de credito
    private void btnGenerarNCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarNCActionPerformed
        double total = Double.parseDouble(txtTotalNC.getText().toString().trim().replace(",", "."));
        if (total == 0.0) {
            JOptionPane.showMessageDialog(null, "Debes agregar un producto");
        } else {
            GenerarNC();
            ncdao.NuevaNC();
            LimpiarTabla();
            txtTotalNC.setText("0.00");
            txtCodNC.requestFocus();
        }
    }//GEN-LAST:event_btnGenerarNCActionPerformed

    //tipado del ipnota
    private void txtIdNCKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdNCKeyTyped
        event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtIdNCKeyTyped

    private void txtVentaNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVentaNVKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVentaNVKeyTyped

    private void txtCantNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantNVKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtCantNV);
    }//GEN-LAST:event_txtCantNVKeyTyped

    private void txtClienteNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClienteNVKeyTyped
        //event.numberKeyPress(evt);
        event.textKeyPress(evt);
        //event.numberDecimalKeyPress(evt, txtPrecioPro);
    }//GEN-LAST:event_txtClienteNVKeyTyped

    private void txtDescuentoNVKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoNVKeyTyped
        //event.numberKeyPress(evt);
        //event.textKeyPress(evt);
        event.numberDecimalKeyPress(evt, txtDescuentoNV);
    }//GEN-LAST:event_txtDescuentoNVKeyTyped

    //boton nueva venta
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        //llamamos a los metodos
        MostrarVenta();
        LimpiarTabla();
        ListarNV();
        TotalNV();
        txtDescuentoNV.setText("0.00");
        txtCodNV.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    //al presionar enter en el textfiel codigo
    private void txtCodNVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodNVKeyPressed
        // verificamos que el usuario presione la tecla enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que el campo no este vacio
            if (!"".equals(txtCodNV.getText())) {
                String cod = txtCodNV.getText();
                prod = dventadao.BuscarProd(cod);
                //verificamos si el producto existe
                if (prod.getDescripcion() != null) {
                    //mosstramos todos los datos
                    txtDescNV.setText("" + prod.getDescripcion());
                    txtPrecioNV.setText("" + prod.getPrecio());
                    txtCantDisNV.setText("" + prod.getCantidad());
                    //pasamos el cursor a cantidad (requestfocus es para que el cursos se centre en una ventana sin necesidad que el usuario seleccione dicha ventana)
                    txtCantNV.requestFocus();
                } else {
                    //llamamos al metodo para limpiar formulario 
                    LimpiarFormNV();
                    //pasamos el cursor, se mantiene en el codigo
                    txtCodNV.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese el codigo del producto");
                txtCodPres.requestFocus();
            }
        }
    }//GEN-LAST:event_txtCodNVKeyPressed

    //al presionar enter
    private void txtCantNVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantNVKeyPressed
        //verificamos que la tecla enter haya sido presionada
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            //verificamos que la cantidad no este vacia
            if (!"".equals(txtCantNV.getText())) {
                int cod = Integer.parseInt(txtCodNV.getText().toString());
                double precio = Double.parseDouble(txtPrecioNV.getText());
                double cant = Double.parseDouble(txtCantNV.getText());
                double cantd = Double.parseDouble(txtCantDisNV.getText());
                double subtotal = precio * cant;
                if (cantd < cant) {
                    JOptionPane.showMessageDialog(null, "No hay stock suficiente");
                } else {
                    dventa.setCodigo_venta(Integer.parseInt(txtVentaNV.getText()));
                    dventa.setCodigo(Integer.parseInt(txtCodNV.getText()));
                    dventa.setDescripcion(txtDescNV.getText());
                    dventa.setPrecio(Double.parseDouble(txtPrecioNV.getText()));
                    dventa.setCantidad(Double.parseDouble(txtCantNV.getText()));
                    dventa.setSubtotal(subtotal);
                    dventadao.RestarStock(cod, cant);
                    dventadao.RegistrarNV(dventa);
                    LimpiarTabla();
                    LimpiarFormNV();
                    ListarNV();
                    TotalNV();
                    txtCodNV.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ingrese cantidad");
            }
        }
    }//GEN-LAST:event_txtCantNVKeyPressed

    //mouse clicked tabla nueva venta 
    private void tablaNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaNVMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaNV.rowAtPoint(evt.getPoint());
        txtCodNV.setText(tablaNV.getValueAt(fila, 0).toString());
        txtDescNV.setText(tablaNV.getValueAt(fila, 1).toString());
        txtPrecioNV.setText(tablaNV.getValueAt(fila, 2).toString());
        txtCantNV.setText(tablaNV.getValueAt(fila, 3).toString());
        txtIdNV.setText(tablaNV.getValueAt(fila, 6).toString());
    }//GEN-LAST:event_tablaNVMouseClicked

    //boton generar venta
    private void btnGenerarNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarNVActionPerformed
        double total = Double.parseDouble(txtTotalNV.getText().toString().trim().replace(",", "."));
        if (total == 0.0) {
            JOptionPane.showMessageDialog(null, "Debes agregar un producto");
        } else {
            if (txtClienteNV.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Debes agregar el nombre del cliente");
            } else {
                int vent = Integer.parseInt(txtVentaNV.getText().toString());
                double tot = Double.parseDouble(txtTotalNV.getText().trim().replace(",", "."));
                double des = Double.parseDouble(txtDescuentoNV.getText().trim().replace(",", "."));
                double totalv = tot - des;
                v.setCodigo(vent);
                v.setFecha(fechahora);
                v.setTotal(totalv);
                v.setCliente(txtClienteNV.getText());
                v.setUsuario(labelMenuUsuario.getText());
                v.setDescuento(Double.parseDouble(txtDescuentoNV.getText().trim().replace(",", ".")));
                GenerarVenta(vent);//pdf
                vdao.GenerarVenta(v);
                LimpiarTabla();
                vdao.NuevaVenta();
                MostrarVenta();
                LimpiarFormNV();
                txtTotalNV.setText("0.00");
                txtDescuentoNV.setText("0.00");
                txtCodNV.requestFocus();
            }
        }
    }//GEN-LAST:event_btnGenerarNVActionPerformed

    //boton ventas
    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        //llamamos a los metodos
        txtDiaVenta.setText(fecha);
        LimpiarTabla();
        LimpiarFormVenta();
        ListarVentas(fecha);
        // llamamos al metodo consultar para llenar el combo box
        vdao.ConsultarVenta(cbxFechaVenta);
        AutoCompleteDecorator.decorate(cbxFechaVenta);
    }//GEN-LAST:event_btnVentasActionPerformed

    //boton resumen diario
    private void btnResumenVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumenVentaActionPerformed

        //llamamos a los metodos
        String fecha1 = cbxFechaVenta.getSelectedItem().toString();
        txtDiaVenta.setText(fecha1);
        LimpiarTabla();
        LimpiarFormVenta();
        ListarVentas(fecha1);
    }//GEN-LAST:event_btnResumenVentaActionPerformed


    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked

    }//GEN-LAST:event_jScrollPane2MouseClicked

    //seleccion de venta de la tabla
    private void tablaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentaMouseClicked
        //capturamos la fila seleccionada
        int fila = tablaVenta.rowAtPoint(evt.getPoint());
        txtIdVenta.setText(tablaVenta.getValueAt(fila, 1).toString());
    }//GEN-LAST:event_tablaVentaMouseClicked

    //boton ver pdfventa
    private void btnPdfVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfVentaActionPerformed
        if (txtIdVenta.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una venta de la tabla.");
        } else {
            try {

                int vent = Integer.parseInt(txtIdVenta.getText());

                String ruta = "src/pdf/venta" + vent + ".pdf";

                File archivo = new File(ruta);

                if (archivo.exists()) {

                    Desktop.getDesktop().open(archivo);

                } else {

                    System.out.println("El archivo PDF no existe: " + ruta);
                }

            } catch (Exception e) {
                System.out.println("Error al abrir PDF: " + e.toString());
            }
            LimpiarFormVenta();
        }
    }//GEN-LAST:event_btnPdfVentaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Sistema().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnControlStock;
    private javax.swing.JButton btnEditarConf;
    private javax.swing.JButton btnEditarProd;
    private javax.swing.JButton btnEditarProv;
    private javax.swing.JButton btnEditarRub;
    private javax.swing.JButton btnEditarUs;
    private javax.swing.JButton btnEliminarNC;
    private javax.swing.JButton btnEliminarNV;
    private javax.swing.JButton btnEliminarPres;
    private javax.swing.JButton btnEliminarProd;
    private javax.swing.JButton btnEliminarProv;
    private javax.swing.JButton btnEliminarRub;
    private javax.swing.JButton btnEliminarUs;
    private javax.swing.JButton btnGenerarNC;
    private javax.swing.JButton btnGenerarNV;
    private javax.swing.JButton btnGenerarPed;
    private javax.swing.JButton btnGenerarPres;
    private javax.swing.JButton btnGuardarProd;
    private javax.swing.JButton btnGuardarProv;
    private javax.swing.JButton btnGuardarRub;
    private javax.swing.JButton btnGuardarUs;
    private javax.swing.JButton btnNotas;
    private javax.swing.JButton btnNuevoProd;
    private javax.swing.JButton btnNuevoProv;
    private javax.swing.JButton btnNuevoRub;
    private javax.swing.JButton btnNuevoUs;
    private javax.swing.JButton btnPdfVenta;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedorMenu;
    private javax.swing.JButton btnResumenVenta;
    private javax.swing.JButton btnRubroMenu;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JButton btnVentas;
    private javax.swing.JComboBox<String> cbxFechaVenta;
    private javax.swing.JComboBox<String> cbxProvPedido;
    private javax.swing.JComboBox<String> cbxProvProd;
    private javax.swing.JComboBox<String> cbxRolUs;
    private javax.swing.JComboBox<String> cbxRubroProd;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelMenuUsuario;
    private javax.swing.JTable tablaNC;
    private javax.swing.JTable tablaNV;
    private javax.swing.JTable tablaPedido;
    private javax.swing.JTable tablaPres;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tablaProveedor;
    private javax.swing.JTable tablaRubro;
    private javax.swing.JTable tablaUs;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField txtCantDisNC;
    private javax.swing.JTextField txtCantDisNV;
    private javax.swing.JTextField txtCantDisPres;
    private javax.swing.JTextField txtCantNC;
    private javax.swing.JTextField txtCantNV;
    private javax.swing.JTextField txtCantPres;
    private javax.swing.JTextField txtCantProd;
    private javax.swing.JTextField txtCantminProd;
    private javax.swing.JTextField txtCbuProv;
    private javax.swing.JTextField txtCbuUs;
    private javax.swing.JPasswordField txtClaveUs;
    private javax.swing.JTextField txtClienteNV;
    private javax.swing.JTextField txtCodConf;
    private javax.swing.JTextField txtCodNC;
    private javax.swing.JTextField txtCodNV;
    private javax.swing.JTextField txtCodPres;
    private javax.swing.JTextField txtCodProd;
    private javax.swing.JTextField txtCodProv;
    private javax.swing.JTextField txtCodRub;
    private javax.swing.JTextField txtCodUs;
    private javax.swing.JTextField txtCorreoProv;
    private javax.swing.JTextField txtDescNC;
    private javax.swing.JTextField txtDescNV;
    private javax.swing.JTextField txtDescPres;
    private javax.swing.JTextField txtDescProd;
    private javax.swing.JTextField txtDescRub;
    private javax.swing.JTextField txtDescuentoNV;
    private javax.swing.JTextField txtDiaVenta;
    private javax.swing.JTextField txtDirConf;
    private javax.swing.JTextField txtDniConf;
    private javax.swing.JTextField txtDniUs;
    private javax.swing.JTextField txtIdNC;
    private javax.swing.JTextField txtIdNV;
    private javax.swing.JTextField txtIdPres;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtNomConf;
    private javax.swing.JTextField txtNombreProv;
    private javax.swing.JTextField txtNombreUs;
    private javax.swing.JTextField txtPrecioNC;
    private javax.swing.JTextField txtPrecioNV;
    private javax.swing.JTextField txtPrecioPres;
    private javax.swing.JTextField txtPrecioProd;
    private javax.swing.JTextField txtRazonConf;
    private javax.swing.JTextField txtTelConf;
    private javax.swing.JTextField txtTelefProv;
    private javax.swing.JTextField txtTelefUs;
    private javax.swing.JTextField txtTotalNC;
    private javax.swing.JTextField txtTotalNV;
    private javax.swing.JTextField txtTotalPres;
    private javax.swing.JTextField txtVentaNV;
    // End of variables declaration//GEN-END:variables
    //metodo para limpiar el formulario rubro
    private void LimpiarFormRubro() {
        txtCodRub.setText("");
        txtDescRub.setText("");
    }

    //metodo para limpiar el formulario proveedor
    private void LimpiarFormProveedor() {
        txtCodProv.setText("");
        txtNombreProv.setText("");
        txtCorreoProv.setText("");
        txtTelefProv.setText("");
        txtCbuProv.setText("");
    }

    //metodo para limpiar el formulario productos
    private void LimpiarFormProductos() {

        txtCodProd.setText("");
        txtDescProd.setText("");
        txtPrecioProd.setText("");
        txtCantProd.setText("");
        txtCantminProd.setText("");
        cbxProvProd.setSelectedItem(null);
        cbxRubroProd.setSelectedItem(null);
    }

    //metodo para limpiar formulario de mueva venta
    private void LimpiarFormPresupuesto() {
        txtCodPres.setText("");
        txtDescPres.setText("");
        txtPrecioPres.setText("");
        txtCantDisPres.setText("");
        txtCantPres.setText("");
        txtIdPres.setText("");
    }

    //metodo para limpiar formulario usuario
    private void LimpiarFormUsuario() {
        txtCodUs.setText("");
        txtDniUs.setText("");
        txtClaveUs.setText("");
        txtNombreUs.setText("");
        txtTelefUs.setText("");
        txtCbuUs.setText("");
    }

    //metodo para limpiar formulario nota de credito
    private void LimpiarFormNC() {
        txtCodNC.setText("");
        txtDescNC.setText("");
        txtPrecioNC.setText("");
        txtCantDisNC.setText("");
        txtCantNC.setText("");
        txtIdNC.setText("");
    }

    //metodo para limpiar formulario nueva venta
    private void LimpiarFormNV() {
        txtCodNV.setText("");
        txtDescNV.setText("");
        txtPrecioNV.setText("");
        txtCantDisNV.setText("");
        txtCantNV.setText("");
        txtIdNV.setText("");
        txtClienteNV.setText("");
    }
    
    //metodo para limpiar formulario nueva venta
    private void LimpiarFormVenta() {
        txtIdVenta.setText("");
    }

    //metodo para actualizar el total a pagar e presupuesto
    private void TotalPresupuesto() {
        double total = 0.00;
        int numFila = tablaPres.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tablaPres.getModel().getValueAt(i, 4)));
            total = total + cal;
        }
        txtTotalPres.setText(String.format("%.2f", total));
    }

    //metodo para actualizar el total a pagar e presupuesto
    private void TotalNC() {
        double total = 0.00;
        int numFila = tablaNC.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tablaNC.getModel().getValueAt(i, 4)));
            total = total + cal;
        }
        txtTotalNC.setText(String.format("%.2f", total));
    }

    //metodo para actualizar el total a pagar en nueva venta
    private void TotalNV() {
        double total = 0.00;
        int numFila = tablaNV.getRowCount();
        for (int i = 0; i < numFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tablaNV.getModel().getValueAt(i, 4)));
            total = total + cal;
        }
        txtTotalNV.setText(String.format("%.2f", total));
    }

    //metodo para generar reporte de presupuesto
    private void GenerarPresupuesto() {
        try {
            // ruta del PDF
            FileOutputStream archivo;
            File file = new File("src/pdf/presupuesto.pdf");
            archivo = new FileOutputStream(file);

            // ===== CONFIGURACIÓN 48mm =====
            float ancho = 48f * 2.83465f; // mm a puntos
            float alto = 900f; // ajustable según contenido

            Rectangle pageSize = new Rectangle(ancho, alto);

            // márgenes reducidos para ticket
            Document doc = new Document(pageSize, 2, 2, 2, 2);

            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // ===== FUENTE =====
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fontBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            // ===== FECHA =====
            Paragraph fecha = new Paragraph();
            fecha.add(Chunk.NEWLINE);

            Date date = new Date();
            fecha.add("Fecha: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date) + "\n");
            fecha.add("Vendedor: " + labelMenuUsuario.getText().toString());

            // ===== ENCABEZADO =====
            PdfPTable Encabezado = new PdfPTable(1);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);

            confdao.ListarConf(conf);
            String nom = conf.getNombre();
            String tel = conf.getTelefono();
            String dir = conf.getDireccion();
            String ra = conf.getRazon();

            Encabezado.addCell(
                    ra + "\n"
                    + tel + "\n"
                    + dir + "\n"
            );

            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            // ===== TITULO =====
            Paragraph pro = new Paragraph();
            pro.add(Chunk.NEWLINE);
            pro.add("Presupuesto\n\n");
            pro.setAlignment(Element.ALIGN_CENTER);
            doc.add(pro);

            // ===== TABLA PRODUCTOS =====
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);

            float[] Columnapro = new float[]{15f, 45f, 20f, 20f};
            tablapro.setWidths(Columnapro);

            List<Presupuesto> lista = presdao.ListarPresupuesto();

            for (Presupuesto p : lista) {

                // Cantidad + Precio (arriba)
                PdfPCell fila1 = new PdfPCell(new Phrase(
                        p.getDescripcion(),
                        fontBold
                ));
                fila1.setColspan(4);
                fila1.setBorder(0);
                tablapro.addCell(fila1);

                // Descripción (centro)
                PdfPCell fila2 = new PdfPCell(new Phrase(
                        "Cant: " + p.getCantidad() + "    Precio: $" + p.getPrecio(),
                        fontNormal
                ));
                fila2.setColspan(4);
                fila2.setBorder(0);
                tablapro.addCell(fila2);

                // Subtotal (abajo)
                PdfPCell fila3 = new PdfPCell(new Phrase(
                        "Subtotal: $" + p.getSubtotal(),
                        fontNormal
                ));
                fila3.setColspan(4);
                fila3.setBorder(0);
                tablapro.addCell(fila3);
            }

            doc.add(tablapro);

            // ===== TOTAL =====
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            String total = txtTotalPres.getText();
            info.add("Total: $" + total);
            info.setAlignment(Element.ALIGN_LEFT);
            doc.add(info);

            // ===== MENSAJE FINAL =====
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Presupuesto válido por 48 hs.");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            // cerrar documento
            doc.close();
            archivo.close();

            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    //metodo para generar reporte nota de credito
    private void GenerarNC() {
        try {
            // ruta del PDF
            FileOutputStream archivo;
            File file = new File("src/pdf/nota.pdf");
            archivo = new FileOutputStream(file);

            // ===== CONFIGURACIÓN 48mm =====
            float ancho = 48f * 2.83465f; // mm a puntos
            float alto = 900f; // ajustable según contenido

            Rectangle pageSize = new Rectangle(ancho, alto);

            // márgenes reducidos para ticket
            Document doc = new Document(pageSize, 2, 2, 2, 2);

            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // ===== FUENTE =====
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fontBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            // ===== FECHA =====
            Paragraph fecha = new Paragraph();
            fecha.add(Chunk.NEWLINE);

            Date date = new Date();
            fecha.add("Fecha: " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(date) + "\n");
            fecha.add("Vendedor: " + labelMenuUsuario.getText().toString());

            // ===== ENCABEZADO =====
            PdfPTable Encabezado = new PdfPTable(1);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);

            confdao.ListarConf(conf);
            String nom = conf.getNombre();
            String tel = conf.getTelefono();
            String dir = conf.getDireccion();
            String ra = conf.getRazon();

            Encabezado.addCell(
                    ra + "\n"
                    + tel + "\n"
                    + dir + "\n"
            );

            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            // ===== TITULO =====
            Paragraph pro = new Paragraph();
            pro.add(Chunk.NEWLINE);
            pro.add("Nota de Cred.\n");
            pro.setAlignment(Element.ALIGN_CENTER);
            doc.add(pro);

            // ===== TOTAL =====
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            String total = txtTotalNC.getText();
            info.add("Total: $" + total);
            info.setAlignment(Element.ALIGN_LEFT);
            doc.add(info);

            // ===== MENSAJE FINAL =====
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("No tiene vencimiento.");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            // cerrar documento
            doc.close();
            archivo.close();

            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

    //metodo para generar reporte de presupuesto
    private void GenerarVenta(int vent) {
        try {

            // ruta del PDF
            FileOutputStream archivo;
            File file = new File("src/pdf/venta" + vent + ".pdf");
            archivo = new FileOutputStream(file);

            // ===== CONFIGURACIÓN 48mm =====
            float ancho = 48f * 2.83465f; // mm a puntos
            float alto = 900f; // ajustable según contenido

            Rectangle pageSize = new Rectangle(ancho, alto);

            // márgenes reducidos para ticket
            Document doc = new Document(pageSize, 2, 2, 2, 2);

            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // ===== FUENTE =====
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
            Font fontBold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);

            //aca consegimos toda la informacion de la venta
            vdao.ListarVentaR(vent);
            String fecha1 = v.getFecha();
            Double apagar = v.getTotal();
            String cliente = v.getCliente();
            String usuario = v.getUsuario();
            Double descuento = v.getDescuento();

            // ===== FECHA =====
            Paragraph fecha = new Paragraph();
            fecha.add(Chunk.NEWLINE);

            fecha.add("Fecha: " + fecha1 + "\n");
            fecha.add("Venta N.: " + vent + "\n");
            fecha.add("Vendedor: " + usuario + "\n");
            fecha.add("Cliente: " + cliente + "\n");

            // ===== ENCABEZADO =====
            PdfPTable Encabezado = new PdfPTable(1);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);

            confdao.ListarConf(conf);
            String nom = conf.getNombre();
            String tel = conf.getTelefono();
            String dir = conf.getDireccion();
            String ra = conf.getRazon();

            Encabezado.addCell(
                    ra + "\n"
                    + tel + "\n"
                    + dir + "\n"
            );

            Encabezado.addCell(fecha);
            doc.add(Encabezado);

            // ===== TITULO =====
            Paragraph pro = new Paragraph();
            pro.add(Chunk.NEWLINE);
            pro.add("FACTURA\n\n");
            pro.setAlignment(Element.ALIGN_CENTER);
            doc.add(pro);

            // ===== TABLA PRODUCTOS =====
            PdfPTable tablapro = new PdfPTable(4);
            tablapro.setWidthPercentage(100);
            tablapro.getDefaultCell().setBorder(0);

            float[] Columnapro = new float[]{15f, 45f, 20f, 20f};
            tablapro.setWidths(Columnapro);

            List<Detalleventa> lista = dventadao.ListarDV(vent);

            double total = 0;
            for (Detalleventa dv : lista) {

                total += dv.getSubtotal();//acumulador

                // Cantidad + Precio (arriba)
                PdfPCell fila1 = new PdfPCell(new Phrase(
                        dv.getDescripcion(),
                        fontBold
                ));
                fila1.setColspan(4);
                fila1.setBorder(0);
                tablapro.addCell(fila1);

                // Descripción (centro)
                PdfPCell fila2 = new PdfPCell(new Phrase(
                        "Cant: " + dv.getCantidad() + "    Precio: $" + dv.getPrecio(),
                        fontNormal
                ));
                fila2.setColspan(4);
                fila2.setBorder(0);
                tablapro.addCell(fila2);

                // Subtotal (abajo)
                PdfPCell fila3 = new PdfPCell(new Phrase(
                        "Subtotal: $" + dv.getSubtotal(),
                        fontNormal
                ));
                fila3.setColspan(4);
                fila3.setBorder(0);
                tablapro.addCell(fila3);
            }

            doc.add(tablapro);

            // ===== TOTAL =====
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            String a = txtTotalPres.getText();
            info.add("Total: $" + total + "\n");
            info.add("Descuento: $" + descuento + "\n");
            info.add("A pagar: $" + apagar + "\n");
            info.setAlignment(Element.ALIGN_LEFT);
            doc.add(info);

            // ===== MENSAJE FINAL =====
            Paragraph mensaje = new Paragraph();
            mensaje.add(Chunk.NEWLINE);
            mensaje.add("Gracias por su compra.\n");
            mensaje.add("Cambios hasta 24Hs.");
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            // cerrar documento
            doc.close();
            archivo.close();

            Desktop.getDesktop().open(file);

        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
        }
    }

}
