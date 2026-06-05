package Modelo;

public class Presupuesto {

    int codigo;
    String descripcion;
    Double precio;
    Double cantidad;
    Double subtotal;
    int Codigopres;

    //creamos el constructor vacio
    public Presupuesto() {

    }

    public Presupuesto(int codigo, String descripcion, Double precio, Double cantidad, Double subtotal, int Codigopres) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.Codigopres = Codigopres;
    }

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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCodigopres() {
        return Codigopres;
    }

    public void setCodigopres(int Codigopres) {
        this.Codigopres = Codigopres;
    }

    
    
}
