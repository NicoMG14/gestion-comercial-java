package Modelo;

import java.sql.*;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NCreditoDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;

    //metodo para buscar un producto en la vista presupuesto
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

    //meotodo registrar detalle nota
    public boolean RegistrarNota(NCredito nc) {
        String sql = "insert into detallenota (codigo, descripcion, precio, cantidad, subtotal) values (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, nc.getCodigo());
            ps.setString(2, nc.getDescripcion());
            ps.setDouble(3, nc.getPrecio());
            ps.setDouble(4, nc.getCantidad());
            ps.setDouble(5, nc.getSubtotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    //metodo listar nota
    public List ListarNota() {
        List<NCredito> Listanota = new ArrayList();
        String sql = "select * from detallenota";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                NCredito nc = new NCredito(); //llamamos a la clase 
                nc.setCodigo(rs.getInt("codigo"));
                nc.setDescripcion(rs.getString("descripcion"));
                nc.setPrecio(rs.getDouble("precio"));
                nc.setCantidad(rs.getDouble("cantidad"));
                nc.setSubtotal(rs.getDouble("subtotal"));
                nc.setCodigonota(rs.getInt("Codigonota"));
                Listanota.add(nc);//aca pasamos los resultados a la lista
            }
        } catch (SQLException e) { //capturamos los errores
            System.out.println(e.toString());
        }
        return Listanota; // retornamos la lista
    }

    //metodo para eliminbar productos del presupuesto
    public boolean EliminarProdNC(int cod) {//le enviamos la variable id
        String sql = "delete from detallenota where codigonota = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cod);
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

    //metodo para eliminbar productos del presupuesto
    public boolean NuevaNC() {
        String sql = "delete from detallenota";
        try {
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

    //meotodo editar stock 
    public boolean SumarStock(int cod, double cant) {
        String sqlUpdate = "UPDATE producto SET cantidad = cantidaD + ? WHERE codigo = ?";
        try {
            ps = con.prepareStatement(sqlUpdate);
            ps.setDouble(1, cant);
            ps.setInt(2, cod);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
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
}
