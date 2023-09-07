package com.doncafeto.doncafetotpv.Clases.ventas;

public class DireccionDeEnvio {
String fib,nombre,calle,numero,colonia,codPostal,ciudad,estado,pais,telefono,contacto;

    public DireccionDeEnvio() {
    }

    public DireccionDeEnvio(String fib, String nombre, String calle, String numero, String colonia, String codPostal, String ciudad, String estado, String pais, String telefono, String contacto) {
        this.fib = fib;
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.colonia = colonia;
        this.codPostal = codPostal;
        this.ciudad = ciudad;
        this.estado = estado;
        this.pais = pais;
        this.telefono = telefono;
        this.contacto = contacto;
    }

    public String getFib() {
        return fib;
    }

    public void setFib(String fib) {
        this.fib = fib;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return "DireccionDeEnvio{" +
                "fib='" + fib + '\'' +
                ", nombre='" + nombre + '\'' +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", colonia='" + colonia + '\'' +
                ", codPostal='" + codPostal + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", estado='" + estado + '\'' +
                ", pais='" + pais + '\'' +
                ", telefono='" + telefono + '\'' +
                ", contacto='" + contacto + '\'' +
                '}';
    }
}
