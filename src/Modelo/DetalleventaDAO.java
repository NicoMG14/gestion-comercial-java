
package Modelo;

import java.sql.*;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DetalleventaDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    //metodo para buscar num de venta
    public Venta MostrarVenta(Venta v){
        String sql = "SELECT codigo FROM venta ORDER BY codigo DESC LIMIT 1";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                v.setCodigo(rs.getInt("codigo"));
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return v;
    } 
    
    //metodo para buscar un producto en la vista nueva venta
    public Productos BuscarProd(String cod) {
        Productos prod = new Productos();
        String sql = "select * from producto where codigo=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                prod.setCodigo(rs.getInt("codigo"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return prod;
    }
    
    // metodo editar stock
    public boolean RestarStock(int cod, double cant) {

        String sql = "UPDATE producto SET cantidad = cantidad - ? WHERE codigo = ?";

        try {
            ps = con.prepareStatement(sql);

            ps.setDouble(1, cant);
            ps.setInt(2, cod);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    // metodo editar stock
    public boolean SumarStock(int cod, double cant) {

        String sql = "UPDATE producto SET cantidad = cantidad + ? WHERE codigo = ?";

        try {
            ps = con.prepareStatement(sql);

            ps.setDouble(1, cant);
            ps.setInt(2, cod);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    //meotodo registrar detalle nota
    public boolean RegistrarNV(Detalleventa dv) {
        String sql = "insert into detalleventa (codigo_venta, codigo, descripcion, precio,cantidad, subtotal) values (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dv.getCodigo_venta());
            ps.setInt(2, dv.getCodigo());
            ps.setString(3, dv.getDescripcion());
            ps.setDouble(4, dv.getPrecio());
            ps.setDouble(5, dv.getCantidad());
            ps.setDouble(6, dv.getSubtotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    //metodo listar nota
    public List ListarDV(int v) {
        List<Detalleventa> ListaDV = new ArrayList();
        String sql = "select * from detalleventa where codigo_venta = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v);
            rs = ps.executeQuery();
            while (rs.next()) {
                Detalleventa dv = new Detalleventa(); //llamamos a la clase 
                dv.setCodigo(rs.getInt("codigo"));
                dv.setDescripcion(rs.getString("descripcion"));
                dv.setPrecio(rs.getDouble("precio"));
                dv.setCantidad(rs.getDouble("cantidad"));
                dv.setSubtotal(rs.getDouble("subtotal"));
                dv.setCodigo_venta(rs.getInt("codigo_venta"));
                dv.setCodigonv(rs.getInt("codigonv"));
                ListaDV.add(dv);//aca pasamos los resultados a la lista
            }
        } catch (SQLException e) { //capturamos los errores
            System.out.println(e.toString());
        }
        return ListaDV; // retornamos la lista
    }
    
    //metodo para eliminbar productos del presupuesto
    public boolean EliminarProdNV(int id) {//le enviamos la variable id
        String sql = "delete from detalleventa where codigonv = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
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
}
