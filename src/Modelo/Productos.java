
package Modelo;

public class Productos {
    int codigo;
    String fecha;
    String descripcion;
    double precio;
    double cantidad;
    double cantidad_min;
    String proveedor;
    String rubro;
    
    public Productos(){
        
    }
    
    //generamos el constreuctor
    public Productos(int codigo, String fecha, String descripcion, double precio, double cantidad, double cantidad_min, String proveedor, String rubro) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.cantidad_min = cantidad_min;
        this.proveedor = proveedor;
        this.rubro = rubro;
    }
    
    //generamos el getter and setter
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

    public double getCantidad_min() {
        return cantidad_min;
    }

    public void setCantidad_min(double cantidad_min) {
        this.cantidad_min = cantidad_min;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }
    
    
}
