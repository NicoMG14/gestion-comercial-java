
package Modelo;


public class Rubro {
    //encapsulamos en variables el contenido de la bd
    int codigo;
    String descripcion;
    
    //creamos un constructor vacio
    public Rubro(){
        
    }
    
    //creamos el constructor (click derecho/insert code/constructor)
    public Rubro(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    //generamos el getter and setter (click derecho/insert code/getter and setter)

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
