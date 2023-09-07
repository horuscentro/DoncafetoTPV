package com.doncafeto.doncafetotpv.ui.productos;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.Adaptadores.ProductosAdapter;
import com.doncafeto.doncafetotpv.Clases.productos.Producto;
import com.doncafeto.doncafetotpv.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductosFragment extends Fragment {
    private RecyclerView recyclerView;private ProductosAdapter adapter;private List<Producto> productos;Button btnNuevoProducto;
    private static final String ARG_PARAM1 = "param1";private static final String ARG_PARAM2 = "param2";private String mParam1;
    private String mParam2;private DatabaseReference myRef;private long maxId;EditText editFiltroProductos;

    public ProductosFragment() {
        // Required empty public constructor
    }

    public static ProductosFragment newInstance(String param1, String param2) {
        ProductosFragment fragment = new ProductosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Productos");
        myRef.addValueEventListener(productosListener);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewProductos);
        registerForContextMenu(recyclerView);

        productos = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ProductosAdapter(productos);
        //adapter.filter("");

        recyclerView.setAdapter(adapter);

        btnNuevoProducto = view.findViewById(R.id.btnNuevoProducto);
        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddProductDialog();
            }
        });
        editFiltroProductos=(EditText) view.findViewById(R.id.edtFiltroProductos);

        editFiltroProductos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //adapter.filter(s.toString());
                filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            // ... otros métodos sobrescritos ...
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Establecer el título de la ActionBar
        if (getActivity() instanceof AppCompatActivity) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Productos");
        }
    }

    private void filtrar(String textoaFiltrar) {
        ArrayList<Producto> listaFiltrada=new ArrayList<>();
        for (Producto p : productos){
            if (p.getNombre().toString().toLowerCase().contains(textoaFiltrar.toLowerCase())){
                listaFiltrada.add(p);
            }
        }
        adapter.filter(listaFiltrada);
    }

    private void showAddProductDialog() {
        // Inflate the layout
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.vista_alertdialog_productos, null);

        // Initiate the EditTexts
        EditText editClave = view.findViewById(R.id.edit_clave);
        EditText editNombre = view.findViewById(R.id.edit_nombre);
        EditText editDescripcion = view.findViewById(R.id.edit_descripcion);
        EditText editPrecio = view.findViewById(R.id.edit_precio);
        EditText editCosto = view.findViewById(R.id.edit_costo);
        EditText editStock = view.findViewById(R.id.edit_stock);

        // Build the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Producto")
                .setView(view)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gather the data from the EditTexts
                        long id= maxId;
                        String clave = editClave.getText().toString().trim();
                        String nombre = editNombre.getText().toString().trim();
                        String descripcion = editDescripcion.getText().toString().trim();
                        double precio = Double.parseDouble(editPrecio.getText().toString().trim());
                        double costo = Double.parseDouble(editCosto.getText().toString().trim());
                        int stock = Integer.parseInt(editStock.getText().toString().trim());
                        String proveedor ="Antonio";



                        // Create a new product object
                        Producto producto = new Producto((int) id, null, clave, nombre, descripcion, precio, costo, stock,proveedor);  // Here I put 0 and null for id and Fid because Firebase will handle Fid

                        // Push the new product to Firebase and also get the reference to the pushed item to obtain its unique ID
                        DatabaseReference newProductRef = myRef.push();
                        producto.setId((int) maxId+1); // set the unique ID (Fid) for the product
                        producto.setFid(newProductRef.getKey());

                        newProductRef.setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Producto agregado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(item.getOrder());
        if (viewHolder instanceof ProductosAdapter.ProductoViewHolder) {
            if (item.getItemId() == R.id.item_editar) {
                // Lógica para editar el producto
                int position = viewHolder.getPosition() -1;

                showEditDialog(position); // Una función que muestra un diálogo para editar el producto.

                return true;
            } else if (item.getItemId() == R.id.item_borrar) {
                int position = viewHolder.getAdapterPosition() -1;

                Log.d("DEBUG", "Position: " + position);
                Log.d("DEBUG", "Productos size: " + productos.size());

                if (position < 0 || position > productos.size()) {
                    Log.e(TAG, "Posición inválida");
                    return false;
                }

                Producto producto = productos.get(position);

                myRef.child(producto.getFid()).removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                try {
                                    productos.remove(position);
                                    adapter.notifyItemRemoved(position);
                                } catch (IndexOutOfBoundsException e) {
                                    Log.e(TAG, "Error al eliminar el producto: índice fuera de límites", e);
                                    // Puedes manejar el error aquí o simplemente mostrar un mensaje al usuario
                                }

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error al eliminar el producto", e);
                                Toast.makeText(getContext(), "Error al eliminar el producto.", Toast.LENGTH_SHORT).show();
                            }
                        });
                return true;
            } else {
                return super.onContextItemSelected(item);
            }
        } else {
            return super.onContextItemSelected(item);
        }
    }

    ValueEventListener productosListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            productos.clear();
            for (DataSnapshot productoSnapshot : dataSnapshot.getChildren()) {
                Producto producto = productoSnapshot.getValue(Producto.class);
                productos.add(producto);
            }
            maxId=(dataSnapshot.getChildrenCount());
            adapter.actualizaLista(productos);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "Error al leer los datos.", databaseError.toException());
        }
    };
    private void showEditDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.vista_alertdialog_productos, null);

        // Obtiene el producto a editar
        Producto producto = productos.get(position);

        // Inicializa los campos del diálogo con los datos del producto
        EditText editClave = dialogView.findViewById(R.id.edit_clave);
        EditText editNombre = dialogView.findViewById(R.id.edit_nombre);
        EditText editDescripcion = dialogView.findViewById(R.id.edit_descripcion);
        EditText editPrecio = dialogView.findViewById(R.id.edit_precio);
        EditText editCosto = dialogView.findViewById(R.id.edit_costo);
        EditText editStock = dialogView.findViewById(R.id.edit_stock);

        editClave.setText(producto.getClave());
        editNombre.setText(producto.getNombre());
        editDescripcion.setText(producto.getDescripcion());
        editPrecio.setText(String.valueOf(producto.getPrecio()));
        editCosto.setText(String.valueOf(producto.getCosto()));
        editStock.setText(String.valueOf(producto.getStock()));

        builder.setView(dialogView)
                .setPositiveButton("Guardar", (dialog, id) -> {
                    // Actualiza el producto con los nuevos datos y guarda en la base de datos
                    producto.setClave(editClave.getText().toString());
                    producto.setNombre(editNombre.getText().toString());
                    producto.setDescripcion(editDescripcion.getText().toString());
                    producto.setPrecio(Double.parseDouble(editPrecio.getText().toString()));
                    producto.setCosto(Double.parseDouble(editCosto.getText().toString()));
                    producto.setStock(Integer.parseInt(editStock.getText().toString()));

                    updateProducto(producto, position); // Una función que actualiza el producto en la base de datos y en la lista
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    // Cierra el diálogo sin hacer cambios
                });

        builder.create().show();
    }
    private void updateProducto(Producto producto, int position) {
        // Aquí coloca el código para actualizar el producto en tu base de datos Firebase.
        myRef.child(producto.getFid()).setValue(producto);

        // Actualiza el producto en la lista local y notifica al adaptador
        productos.set(position, producto);
        adapter.notifyItemChanged(position);
    }

}
