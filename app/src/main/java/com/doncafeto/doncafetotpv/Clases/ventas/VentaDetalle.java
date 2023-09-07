package com.doncafeto.doncafetotpv.Clases.ventas;

public class VentaDetalle {
    private long idVenta;
    private int cantidad;
    private String clave;
    private String nombre;
    private double precioUnitario;
    private double descuento;
    private double precioConDescuento;
    private double importe;
    // ... getters, setters, etc.

    public VentaDetalle() {
    }

    public VentaDetalle(long idVenta, int cantidad, String clave,String nombre, double precioUnitario, double descuento, double importe) {
        this.idVenta = idVenta;
        this.cantidad = cantidad;
        this.clave=clave;
        this.nombre=nombre;
        this.precioUnitario = precioUnitario;
        this.descuento = descuento;
        this.importe = importe;
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getPrecioConDescuento() {
        return precioConDescuento;
    }

    public void setPrecioConDescuento(double precioConDescuento) {
        this.precioConDescuento = precioConDescuento;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }


}

