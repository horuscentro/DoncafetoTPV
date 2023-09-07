package com.doncafeto.doncafetotpv.Adaptadores;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.Clases.productos.Producto;

import java.util.ArrayList;

public class VentasAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Producto> listaProductos;

    public VentasAdapter() {
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
