package Modelo;

import java.sql.*;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class VentaDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    //metodo para actualizar proveedor
    public boolean GenerarVenta(Venta v) {

        String sql = "UPDATE venta SET fecha=?, total=?, cliente=?, usuario=?, descuento=? WHERE codigo=?";

        try {

            con = cn.getConnection(); // SIEMPRE abrir aquí

            ps = con.prepareStatement(sql);

            ps.setString(1, v.getFecha());
            ps.setDouble(2, v.getTotal());
            ps.setString(3, v.getCliente());
            ps.setString(4, v.getUsuario());
            ps.setDouble(5, v.getDescuento());
            ps.setInt(6, v.getCodigo());

            ps.execute();

            return true;

        } catch (SQLException e) {

            System.out.println(e.toString());
            return false;

        } finally {

            try {
                if (con != null) {
                    con.close(); // SOLO si existe
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    //metodo para eliminbar productos del presupuesto
    public boolean NuevaVenta() {
        String sql = "INSERT INTO `venta` (`codigo`, `fecha`, `total`, `cliente`, `usuario`, `descuento`) VALUES (NULL, NULL, NULL, '', NULL, NULL);";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    //metodo para mostrar la venta en pdf
    public Venta ListarVentaR(int vent) {

        Venta v = new Venta(); // ✔ objeto local

        String sql = "SELECT * FROM venta WHERE codigo = ?";

        try {

            con = cn.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, vent); // ✔ AQUÍ USAS LA VARIABLE

            rs = ps.executeQuery();

            if (rs.next()) {

                v.setCodigo(rs.getInt("codigo"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setCliente(rs.getString("cliente"));
                v.setUsuario(rs.getString("usuario"));
                v.setDescuento(rs.getDouble("descuento"));
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return v;
    }
    
    //meotodo para cargar las ventas en la tabla
    public List ListarVentas(String fecha1) { 
        List<Venta> Listavent = new ArrayList();
        String sql = "select * from venta  WHERE LEFT(fecha, 10) = ? order by codigo desc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha1);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta(); //llamamos a la clase rubro
                v.setCodigo(rs.getInt("codigo"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setCliente(rs.getString("cliente"));
                v.setUsuario(rs.getString("usuario"));
                Listavent.add(v);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listavent; // retornamos la lista
    }
    
    // creamos metodo para llenar el combo box de las ventas
    public void ConsultarVenta(JComboBox venta){
        String sql = "SELECT DISTINCT LEFT(fecha, 10) AS fecha FROM venta ORDER BY STR_TO_DATE(LEFT(fecha, 10), '%d-%m-%Y') DESC";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                //pasamos el resultado "fecha" a la variable venta
                venta.addItem(rs.getString("fecha"));                
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
    
    //meotodo para cargar las ventas en la tabla
    public List ListarVentaSelect(String fecha1) { 
        List<Venta> Listavent = new ArrayList();
        String sql = "select * from venta  WHERE LEFT(fecha, 10) = '" + fecha1 + "' order by codigo desc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta(); //llamamos a la clase rubro
                v.setCodigo(rs.getInt("codigo"));
                v.setFecha(rs.getString("fecha"));
                v.setTotal(rs.getDouble("total"));
                v.setCliente(rs.getString("cliente"));
                v.setUsuario(rs.getString("usuario"));
                Listavent.add(v);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listavent; // retornamos la lista
    }
    
}
