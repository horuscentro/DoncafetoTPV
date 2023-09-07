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

import com.doncafeto.doncafetotpv.Clases.Cliente;
import com.doncafeto.doncafetotpv.R;

import java.util.ArrayList;
import java.util.List;

public class ClientesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<Cliente> clientes;
    private ArrayList<Cliente> clientesCompleto;  // Lista con todos los clientes


    public ClientesAdapter(List<Cliente> clientes) {
        if (this.clientes == null) {
            this.clientes = new ArrayList<>();
            this.clientesCompleto = new ArrayList<>();

        } else {
            this.clientes = (ArrayList<Cliente>) clientes;
            this.clientesCompleto = new ArrayList<>(this.clientes);
        }
        Log.d("ADAPTER", "Tamaño de los clientes: " + this.clientes.size());
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
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_item_lista_clientes, parent, false);
            return new ClienteViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_encabezado_lista_clientes, parent, false);
            return new HeaderViewHolder(itemView);
        }

        throw new RuntimeException("No hay ningún tipo que coincida con " + viewType + ". Deberías implementar un caso para ello.");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClienteViewHolder) {
            Cliente cliente = getCliente(position);
            ((ClienteViewHolder) holder).bindCliente(cliente);
        } else if (holder instanceof HeaderViewHolder) {
            // Aquí puedes asignar valores a las vistas en tu encabezado si es necesario
        }
    }

    private Cliente getCliente(int position) {
        return clientes.get(position - 1);  // restamos 1 a la posición para tener en cuenta el encabezado
    }

    @Override
    public int getItemCount() {
        return clientes.size() + 1;  // sumamos 1 al tamaño de la lista para tener en cuenta el encabezado
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView etNombre,txApellidos ;
        TextView etid;

        public ClienteViewHolder(View view) {
            super(view);

            etNombre = view.findViewById(R.id.txvNombreListaClientes);
             etid = view.findViewById(R.id.txvIdListaClientes);
             txApellidos=(TextView) view.findViewById(R.id.txvApellidoListaClientes);

        }

        public void bindCliente(Cliente cliente) {
            etNombre.setText(cliente.getNombre());
            etid.setText(String.valueOf(cliente.getIdCliente()));
            txApellidos.setText(cliente.getApellido());

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
        clientes.remove(position - 1);  // Se resta 1 a la posición para tener en cuenta el encabezado
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clientes.size());
    }
    public void filter(ArrayList<Cliente> listaFiltrada) {
        clientes =listaFiltrada;
        notifyDataSetChanged();

    }
    public void actualizaLista(List <Cliente> clientes){
        this.clientes = (ArrayList<Cliente>) clientes;
        clientesCompleto= (ArrayList<Cliente>) clientes;
    }


}

