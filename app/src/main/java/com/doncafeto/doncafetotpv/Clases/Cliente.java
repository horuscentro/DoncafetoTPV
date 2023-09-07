package com.doncafeto.doncafetotpv.Clases;

import com.doncafeto.doncafetotpv.Clases.ventas.VentaDetalle;

public class Cliente {
    private long idCliente;
    private String fid;
    private String nombre;
    private String apellido;
    private String direccion;
    private String codigoPostal;
    private String ciudad;
    private String estado;
    private String pais;
    private String telefono;
    private String correoElectronico;
    private VentaDetalle detalleDeVenta;

    public Cliente() {
    }

    public Cliente(long idCliente, String fid, String nombre, String apellido, String direccion, String codigoPostal, String ciudad, String estado, String pais, String telefono, String correoElectronico) {
        this.idCliente = idCliente;
        this.fid = fid;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return

                 nombre + " "+
                 apellido +"\n"+
                 direccion +"\n"+
                 codigoPostal +
                 ciudad +"\n"+
                 estado +"\n"+
                 pais +"\n"+
                 telefono +"\n"+
                 correoElectronico                ;
    }
}

