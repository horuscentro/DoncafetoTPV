package com.doncafeto.doncafetotpv.Clases.ventas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.R;

import java.util.List;

public class AdaptadorDetalleVenta extends RecyclerView.Adapter<AdaptadorDetalleVenta.ViewHolder> {

    private List<VentaDetalle> ventaDetalles;
    private Context context;

    public AdaptadorDetalleVenta(Context context, List<VentaDetalle> ventaDetalles) {
        this.context = context;
        this.ventaDetalles = ventaDetalles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_venta_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VentaDetalle ventaDetalle = ventaDetalles.get(position);

        holder.tvClave.setText(ventaDetalle.getClave());
        holder.tvNombre.setText(ventaDetalle.getNombre());
        holder.tvPrecio.setText(String.valueOf(ventaDetalle.getPrecioUnitario()));
        //... configura los demás campos de la manera similar
    }

    @Override
    public int getItemCount() {
        return ventaDetalles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvClave, tvNombre, tvPrecio;
        //... declara los demás TextViews que se corresponden con los campos de la vista item

        public ViewHolder(View view) {
            super(view);

            tvClave = view.findViewById(R.id.tvClave);
            tvNombre = view.findViewById(R.id.tvNombre);
            tvPrecio = view.findViewById(R.id.tvPrecioUnitario);
            //... inicializa los demás TextViews de la manera similar
        }
    }
}

