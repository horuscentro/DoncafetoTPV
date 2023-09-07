package com.doncafeto.doncafetotpv.Clases.productos;

public class Producto {
    int id;
    String Fid,Clave,Nombre,Descripcion;
    Double Precio,Costo;
    int Stock;
    String Proveedor;

    public Producto() {
    }

    public Producto(int id, String Fid, String clave, String nombre, String descripcion, Double precio, Double costo, int stock, String proveedor) {
        this.id = id;
        Clave = clave;
        Nombre = nombre;
        Descripcion = descripcion;
        Precio = precio;
        Costo = costo;
        Stock = stock;
        Proveedor=proveedor;

    }

    public int getId() {
        return id;
    }

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public Double getCosto() {
        return Costo;
    }

    public void setCosto(Double costo) {
        Costo = costo;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int stock) {
        Stock = stock;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", Clave='" + Clave + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Descripcion='" + Descripcion + '\'' +
                ", Precio=" + Precio +
                ", Costo=" + Costo +
                ", Stock=" + Stock +
                '}';
    }
}
