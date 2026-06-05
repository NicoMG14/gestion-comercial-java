
package Modelo;

//importamos las librerias para la conexion cn la bd
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    //creamos el metodo registrar proveedor
    public boolean RegistrarProveedor(Proveedor prov){
        String sql = "insert into proveedor (nombre, correo, telefono, cbu) values (?,?,?,?)";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, prov.getNombre());
            ps.setString(2, prov.getCorreo());
            ps.setInt(3, prov.getTelefono());
            ps.setString(4, prov.getCbu());
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    //meotodo para cargar los proveedores en la tabla
    public List ListarProveedor() { 
        List<Proveedor> Listaprov = new ArrayList();
        String sql = "select * from proveedor order by nombre asc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor prov = new Proveedor(); //llamamos a la clase rubro
                prov.setCodigo(rs.getInt("codigo"));
                prov.setNombre(rs.getString("nombre"));
                prov.setCorreo(rs.getString("correo"));
                prov.setTelefono(rs.getInt("telefono"));
                prov.setCbu(rs.getString("cbu"));
                Listaprov.add(prov);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listaprov; // retornamos la lista
    }
    
    //metodo para actualizar proveedor
    public boolean ModificarProveedor(Proveedor prov){
        String sql = "update proveedor set nombre=?, correo=?, telefono=?, cbu=? where codigo=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, prov.getNombre());
            ps.setString(2, prov.getCorreo());
            ps.setInt(3, prov.getTelefono());
            ps.setString(4, prov.getCbu());
            ps.setInt(5, prov.getCodigo());
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
    
    //metodo para eliminbar proveedor
    public boolean EliminarProveedor(int codigo){//le enviamos la variable codigo
        String sql = "delete from proveedor where codigo = ?";
        try{
            ps = con.prepareStatement(sql);
            ps .setInt(1, codigo);
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
}
