/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class Proveedor {
    //encapsulamos las variables de la bd
    int codigo;
    String nombre;
    String correo;
    int telefono;
    String cbu;
    
    //creamos un contrcutor vacio
    public Proveedor(){
        
    }
    
    //creamos el constructor - insert code
    public Proveedor(int codigo, String nombre, String correo, int telefono, String cbu) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.cbu = cbu;
    }
    
    //creamos el getter and setter - insert code
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }
    
    
}
