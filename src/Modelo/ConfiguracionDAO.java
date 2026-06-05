
package Modelo;

import java.sql.SQLException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;


public class ConfiguracionDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    //metodo para listar configuracion
    public Configuracion ListarConf(Configuracion conf){
        String sql = "select * from configuracion";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                conf.setCodigo(rs.getInt("codigo"));
                conf.setDni(rs.getInt("dni"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setRazon(rs.getString("razon"));
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return conf;
    } 
    
    //metodo para actualizar configuracion
    public boolean ModificarDatos(Configuracion conf){
        String sql = "update configuracion set dni=?, nombre=?, telefono=?, direccion=?, razon=? where codigo=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, conf.getDni());
            ps.setString(2, conf.getNombre());
            ps.setString(3, conf.getTelefono());
            ps.setString(4, conf.getDireccion());
            ps.setString(5, conf.getRazon());
            ps.setInt(6, conf.getCodigo());
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
}
