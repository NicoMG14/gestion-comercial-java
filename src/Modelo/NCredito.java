
package Modelo;

public class NCredito {
    int codigo;
    String descripcion;
    double precio;
    double cantidad;
    double subtotal;
    int codigonota;
    
    public NCredito(){
        
    }

    public NCredito(int codigo, String descripcion, double precio, double cantidad, double subtotal, int codigonota) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.codigonota = codigonota;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getCodigonota() {
        return codigonota;
    }

    public void setCodigonota(int codigonota) {
        this.codigonota = codigonota;
    }

    
   
}
