
package Modelo;

public class Detalleventa {
    int codigonv;
    int codigo;
    int codigo_venta;
    String descripcion;
    Double precio;
    Double cantidad;
    Double subtotal;
    
    public Detalleventa(){
        
    }

    public Detalleventa(int codigonv, int codigo, int codigo_venta, String descripcion, Double precio, Double cantidad, Double subtotal) {
        this.codigonv = codigonv;
        this.codigo = codigo;
        this.codigo_venta = codigo_venta;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getCodigonv() {
        return codigonv;
    }

    public void setCodigonv(int codigonv) {
        this.codigonv = codigonv;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo_venta() {
        return codigo_venta;
    }

    public void setCodigo_venta(int codigo_venta) {
        this.codigo_venta = codigo_venta;
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

    
    
}
