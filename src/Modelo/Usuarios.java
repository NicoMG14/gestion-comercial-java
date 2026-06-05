
package Modelo;

public class Usuarios {
    int codigo;
    String nombre;
    int dni;
    String clave;
    String telefono;
    String cbu;
    String rol;
    
    public Usuarios(){
    
    }

    public Usuarios(int codigo, String nombre, int dni, String clave, String telefono, String cbu, String rol) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.dni = dni;
        this.clave = clave;
        this.telefono = telefono;
        this.cbu = cbu;
        this.rol = rol;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    
    
}
