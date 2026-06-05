
package Modelo;

public class Venta {
    int codigo;
    String fecha;
    Double total;
    String cliente;
    String usuario;
    Double descuento;
    
    public Venta(){
        
    }

    public Venta(int codigo, String fecha, Double total, String cliente, String usuario, Double descuento) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
        this.usuario = usuario;
        this.descuento = descuento;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }
    
    
}
