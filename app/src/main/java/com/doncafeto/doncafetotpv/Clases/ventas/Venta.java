package com.doncafeto.doncafetotpv.Clases.ventas;

import com.doncafeto.doncafetotpv.Clases.productos.Producto;

import java.util.ArrayList;
import java.util.List;

public class Venta {
    private long idVenta;
    private String idCliente; // asumiendo que los clientes tienen un ID único
    private List<Producto> productos;
    private double totalVenta;
    private double iva;  // impuesto al valor agregado
    private double envio;

    public Venta() {
        productos = new ArrayList<>();
    }

    public Venta(int idVenta, String idCliente) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.productos = new ArrayList<>();
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public double getTotalVenta() {
        double total = 0;
        for (Producto producto : productos) {
            total += producto.getPrecio();
        }
        return total;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getEnvio() {
        return envio;
    }

    public void setEnvio(double envio) {
        this.envio = envio;
    }

    public double calcularTotalConImpuestos() {
        return getTotalVenta() + getIva() + getEnvio();
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", idCliente='" + idCliente + '\'' +
                ", totalVenta=" + getTotalVenta() +
                ", iva=" + iva +
                ", envio=" + envio +
                ", totalConImpuestos=" + calcularTotalConImpuestos() +
                '}';
    }
}

