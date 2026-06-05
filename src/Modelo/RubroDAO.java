
package Modelo;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class RubroDAO {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    //creamos el metodo registrar rubro
    public boolean RegistrarRubro(Rubro ru){
        String sql = "insert into rubro (descripcion) values (?)";
        try{
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, ru.getDescripcion());
            ps.execute();
            return true;
        }catch (SQLException e){
            System.out.println(e.toString());
            return false;
        }
    }
    
    //meotodo para cargar los rubro en la tabla
    public List ListarRubro() { 
        List<Rubro> Listarub = new ArrayList();
        String sql = "select * from rubro order by descripcion asc";
        try{
            con = cn .getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Rubro rub = new Rubro(); //llamamos a la clase rubro
                rub.setCodigo(rs.getInt("codigo"));
                rub.setDescripcion(rs.getString("descripcion"));
                Listarub.add(rub);//aca pasamos los resultados a la lista
            }
        }catch (SQLException e){ //capturamos los errores
            System.out.println(e.toString());
        }
        return Listarub; // retornamos la lista
    }
    
    //metodo para actualizar cliente
    public boolean ModificarRubro(Rubro ru){
        String sql = "update rubro set descripcion=? where codigo=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, ru.getDescripcion());
            ps.setInt(2, ru.getCodigo());
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
    
    //metodo para eliminbar rubro
    public boolean EliminarRubro(int codigo){//le enviamos la variable codigo
        String sql = "delete from rubro where codigo = ?";
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
