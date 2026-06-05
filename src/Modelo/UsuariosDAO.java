
package Modelo;

//importamos las librerias para la conexion cn la bd
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class UsuariosDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    //meotodo registrar usuario
    public boolean RegistrarUsuarios(Usuarios us){
        String sql = "insert into usuario (nombre, dni ,clave, telefono, cbu, rol) values (?,?,?,?,?,?)";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getNombre());
            ps.setInt(2, us.getDni());
            ps.setString(3, us.getClave());
            ps.setString(4, us.getTelefono());
            ps.setString(5, us.getCbu());
            ps.setString(6, us.getRol());
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarUsuario() { 
        List<Usuarios> Listaus = new ArrayList();
        String sql = "select * from usuario";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios us = new Usuarios(); //llamamos a la clase usuarios (ventana)
                us.setCodigo(rs.getInt("codigo"));
                us.setNombre(rs.getString("nombre"));
                us.setDni(rs.getInt("dni"));
                us.setClave(rs.getString("clave"));
                us.setTelefono(rs.getString("telefono"));
                us.setCbu(rs.getString("cbu"));
                us.setRol(rs.getString("rol"));
                Listaus.add(us);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listaus; // retornamos la lista
    }
    
    //metodo para actualizar usuarios
    public boolean ModificarUsuario(Usuarios us){
        String sql = "update usuario set nombre=?, dni=?, clave=?, telefono=?, cbu=?, rol=? where codigo=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, us.getNombre());
            ps.setInt(2, us.getDni());
            ps.setString(3, us.getClave());
            ps.setString(4, us.getTelefono());
            ps.setString(5, us.getCbu());
            ps.setString(6, us.getRol());
            ps.setInt(7, us.getCodigo());
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
    
    //metodo para eliminbar usuario
    public boolean EliminarUsuario(int codigo){//le enviamos la variable codigo
        String sql = "delete from usuario where codigo = ?";
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
    
    // creamos metodo login donde enviamos 2 parametros (correo y contraseña)
    public Usuarios log(int dni, String clave) {
        Usuarios u = null;
        String sql = "select * from usuario where dni = ? and clave = ?"; // creamos una variable para la consulta
        try { //captura las excepciones 
            con = cn.getConnection(); //ejecutamos la conexion
            ps = con.prepareStatement(sql); 
            ps.setInt(1, dni); // enviamos los parametros
            ps.setString(2, clave);
            rs = ps.executeQuery(); //ejecutamos la consulta
            if (rs.next()) { // se valida la consulta
                u = new Usuarios();//iniciamos la clase usuarios
                u.setCodigo(rs.getInt("codigo"));
                u.setNombre(rs.getString("nombre"));
                u.setDni(rs.getInt("dni"));
                u.setRol(rs.getString("rol"));
            }
        } catch (SQLException e){
            System.out.println(e.toString()); // aca se imprime si hay algun error
        }
        return u; //retorna la consulta realizada
    }
    
}
