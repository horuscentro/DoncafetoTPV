package com.doncafeto.doncafetotpv.Adaptadores;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.Clases.productos.Producto;
import com.doncafeto.doncafetotpv.R;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Producto> productos;
    private List<Producto> productosCompleto;  // Lista con todos los productos


    public ProductosAdapter(List<Producto> productos) {
        if (productos == null) {
            this.productos = new ArrayList<>();
            this.productosCompleto = new ArrayList<>();

        } else {
            this.productos = productos;
            this.productosCompleto = new ArrayList<>(productos);
        }
        Log.d("ADAPTER", "Tamaño de los productos: " + this.productos.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_lista_productos, parent, false);
            return new ProductoViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_encabezado, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("No hay ningún tipo que coincida con " + viewType + ". Deberías implementar un caso para ello.");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductoViewHolder) {
            Producto producto = getProduct(position);
            ((ProductoViewHolder) holder).bindProducto(producto);
        } else if (holder instanceof HeaderViewHolder) {
            // Aquí puedes asignar valores a las vistas en tu encabezado si es necesario
        }
    }

    private Producto getProduct(int position) {
        return productos.get(position - 1);  // restamos 1 a la posición para tener en cuenta el encabezado
    }

    @Override
    public int getItemCount() {
        return productos.size() + 1;  // sumamos 1 al tamaño de la lista para tener en cuenta el encabezado
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView txtId;
        private TextView txtDescripcion;
        private TextView txtPrecio;
        private TextView txtStock;

        public ProductoViewHolder(View view) {
            super(view);
            txtId = view.findViewById(R.id.vistaProductosTxtId);
            txtDescripcion = view.findViewById(R.id.vistaProductosTxtDescripcion);
            txtPrecio = view.findViewById(R.id.vistaProductosTxtPrecio);
            txtStock = view.findViewById(R.id.vistaProductosTxtStock);
            view.setOnCreateContextMenuListener(this);
        }

        public void bindProducto(Producto producto) {
            txtId.setText(String.valueOf(producto.getId()));
            txtDescripcion.setText(producto.getDescripcion());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtStock.setText(String.valueOf(producto.getStock()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuItem Edit = contextMenu.add(Menu.NONE, R.id.item_editar, getAdapterPosition(), "Editar");
            MenuItem Delete = contextMenu.add(Menu.NONE, R.id.item_borrar, getAdapterPosition(), "Borrar");
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        // Aquí puedes definir las vistas en tu encabezado

        public HeaderViewHolder(View view) {
            super(view);
            // Aquí puedes inicializar las vistas en tu encabezado
        }
    }
    public void removeAt(int position) {
        productos.remove(position - 1);  // Se resta 1 a la posición para tener en cuenta el encabezado
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productos.size());
    }
    public void filter(ArrayList<Producto> listaFiltrada) {
        productos=listaFiltrada;
        notifyDataSetChanged();

    }
public void actualizaLista(List <Producto> Productos){
        this.productos=Productos;
        productosCompleto=Productos;
}


}
