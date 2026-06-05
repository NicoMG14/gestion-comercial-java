package Modelo;

//importamos las librerias para la conexion cn la bd
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class PresupuestoDAO {

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

    //meotodo registrar presupuesto
    public boolean RegistrarPresupuesto(Presupuesto pres) {
        String sql = "insert into presupuesto (codigo, descripcion, precio, cantidad, subtotal) values (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, pres.getCodigo());
            ps.setString(2, pres.getDescripcion());
            ps.setDouble(3, pres.getPrecio());
            ps.setDouble(4, pres.getCantidad());
            ps.setDouble(5, pres.getSubtotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    //metodo listar presupuesto
    public List ListarPresupuesto() {
        List<Presupuesto> Listapres = new ArrayList();
        String sql = "select * from presupuesto";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Presupuesto pres = new Presupuesto(); //llamamos a la clase productos (ventana)
                pres.setCodigo(rs.getInt("codigo"));
                pres.setDescripcion(rs.getString("descripcion"));
                pres.setPrecio(rs.getDouble("precio"));
                pres.setCantidad(rs.getDouble("cantidad"));
                pres.setSubtotal(rs.getDouble("subtotal"));
                pres.setCodigopres(rs.getInt("Codigopres"));
                Listapres.add(pres);//aca pasamos los resultados a la lista
            }
        } catch (SQLException e) { //capturamos los errores
            System.out.println(e.toString());
        }
        return Listapres; // retornamos la lista
    }

    //metodo para eliminbar productos del presupuesto
    public boolean EliminarProdPresupuesto(int cod) {//le enviamos la variable id
        String sql = "delete from presupuesto where codigopres = ?";
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
    public boolean NuevoPresupuesto() {
        String sql = "delete from presupuesto";
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

    }
