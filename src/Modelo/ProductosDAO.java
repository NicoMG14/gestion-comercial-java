
package Modelo;

//importamos las librerias para la conexion cn la bd
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ProductosDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    // creamos metodo para llenar el combo box de proveedor en la vista productos
    public void ConsultarProveedor(JComboBox proveedor){
        String sql = "select codigo, nombre from proveedor order by nombre asc";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                //pasamos el resultado "nombre" a la variable proveedor
                proveedor.addItem(rs.getString("nombre"));                
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
    
    // creamos metodo para llenar el combo box de rubro en la vista productos
    public void ConsultarRubro(JComboBox rubro){
        String sql = "select descripcion from rubro order by descripcion asc";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                rubro.addItem(rs.getString("descripcion")); //pasamos el resultado "nombre" a la variable proveedor
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
    }
    
    //meotodo registrar productos
    public boolean RegistrarProductos(Productos prod){
        String sql = "insert into producto (fecha, descripcion, precio, cantidad, cantidad_min,	proveedor, rubro) values (?,?,?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getFecha());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio());
            ps.setDouble(4, prod.getCantidad());
            ps.setDouble(5, prod.getCantidad_min());
            ps.setString(6, prod.getProveedor());
            ps.setString(7, prod.getRubro());
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    //meotodo para cargar los prodcutos en la tabla
    public List ListarProductos() { 
        List<Productos> Listaprod = new ArrayList();
        String sql = "select * from producto order by descripcion asc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos prod = new Productos(); //llamamos a la clase productos (ventana)
                prod.setFecha(rs.getString("fecha"));
                prod.setCodigo(rs.getInt("codigo"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCantidad(rs.getDouble("cantidad"));
                prod.setCantidad_min(rs.getDouble("cantidad_min"));
                prod.setProveedor(rs.getString("proveedor"));
                prod.setRubro(rs.getString("rubro"));
                Listaprod.add(prod);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listaprod; // retornamos la lista
    }
    
    //meotodo editar productos
    public boolean ModificarProductos(Productos prod){
        String sql = "update producto set fecha=?, descripcion=?, precio=?, cantidad=?, cantidad_min=?, proveedor=?, rubro=? where codigo=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getFecha());
            ps.setString(2, prod.getDescripcion());
            ps.setDouble(3, prod.getPrecio());
            ps.setDouble(4, prod.getCantidad());
            ps.setDouble(5, prod.getCantidad_min());
            ps.setString(6, prod.getProveedor());
            ps.setString(7, prod.getRubro());
            ps.setInt(8, prod.getCodigo());
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }
    
    //metodo para eliminbar productos
    public boolean EliminarProducto(int cod){//le enviamos la variable id
        String sql = "delete from producto where codigo = ?";
        try{
            ps = con.prepareStatement(sql);
            ps .setInt(1, cod);
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
    
    //metodo para cargar los prodcutos en la tabla pedidos
    public List ListarPedido() { 
        List<Productos> Listaped= new ArrayList();
        String sql = "select * from producto where cantidad_min >= cantidad order by proveedor asc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Productos prod = new Productos(); //llamamos a la clase productos (ventana)
                prod.setFecha(rs.getString("fecha"));
                prod.setCodigo(rs.getInt("codigo"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setCantidad(rs.getDouble("cantidad"));
                prod.setCantidad_min(rs.getDouble("cantidad_min"));
                prod.setProveedor(rs.getString("proveedor"));
                prod.setRubro(rs.getString("rubro"));
                Listaped.add(prod);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listaped; // retornamos la lista
    }
    
    

}
