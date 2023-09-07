package com.doncafeto.doncafetotpv.ui.ventas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.Clases.Cliente;
import com.doncafeto.doncafetotpv.Clases.NetworkUtil;
import com.doncafeto.doncafetotpv.Clases.productos.Producto;
import com.doncafeto.doncafetotpv.Clases.ventas.AdaptadorBuscarClientesEnVentas;
import com.doncafeto.doncafetotpv.Clases.ventas.AdaptadorDetalleVenta;
import com.doncafeto.doncafetotpv.Clases.ventas.DireccionDeEnvio;
import com.doncafeto.doncafetotpv.Clases.ventas.VentaDetalle;
import com.doncafeto.doncafetotpv.R;
import com.doncafeto.doncafetotpv.databinding.FragmentVentasBinding;
import com.doncafeto.doncafetotpv.interfaces.OnClienteSelectedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class VentasFragment extends Fragment {
    // Variables de instancia...
    private EditText edtNombreDeCliente, edtObservacionesDeVenta, edtFiltrarBuscarClientes;
    private Button btnBuscarCliente, btnAgregarProducto;
    private RecyclerView productosRecyclerView;
    private TextView txvSubTotalVenta, txvIVA, txvEnvio, txvTotal;
    private FragmentVentasBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ArrayList<Cliente> listaDeClientes = new ArrayList<>();

    private AlertDialog alertDialog;
    Spinner spinnerDireccionesEnvio;
    VentaDetalle detalleDeVentas=new VentaDetalle();
    AdaptadorDetalleVenta adaptadorDetalleVenta;
    AdaptadorBuscarClientesEnVentas adaptadorBuscarClientesEnVentas;
    ArrayList<VentaDetalle> listaProductosEnVenta=new ArrayList<>();
    ArrayList <Producto> listaDeProductos=new ArrayList<>();
    ArrayAdapter<String> adaptadorSpinDirecciones;
    ValueEventListener productosEventListener,clientesEventListener,direccionesDeEnvioEventListener;
    View root;
    ArrayList<String> listaDeDirecciones = new ArrayList<>();
    DatabaseReference myRefClientes,myRefProductos,myRefDireccionesDeEnvio;
    TextView txtFibClienteVentas;
    String Fid;
    ArrayList<String> nombresDirecciones;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // (código inicial para ViewModel y Binding)
        root=inflater.inflate(R.layout.fragment_ventas,null);


        // Asociación de elementos de la vista
        associateViewElements(root);
        inicializarFirebase(getView());
        // Agregar listeners
        addListeners(root);

        //fetchDataFromFirebase();

        return root;
    }

    private void inicializarFirebase(View view) {
        //Verificar si tenemos conexion a internet
        if (NetworkUtil.isConnectedToInternet(getContext())){
            //database.setPersistenceEnabled(true);
            myRefClientes= database.getReference("clientes");
            myRefProductos=database.getReference("Productos");
            myRefDireccionesDeEnvio=database.getReference("DireccionesDeEnvio");
            //Snackbar.make(getView(),"hay conexion a internet",Snackbar.LENGTH_SHORT).show();
        }else {
            //Snackbar.make(getView(),"No hay conexion a internet", BaseTransientBottomBar.LENGTH_INDEFINITE).show();
            Log.d("Firebase ", "no hay conexion a internet: ");
            AlertDialog.Builder b= new AlertDialog.Builder(getContext());
            b.setTitle("Conexion a internet.");
            b.setMessage("No hay conexion a internet, se necesita para descargar la base de datos y actualizar los campos.");
            b.setCancelable(false);
            b.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.exit(0);
                }
            });
            b.show();
        }


    }
//Asociamos las vistas y pasamos el View del fragment
    private void associateViewElements(View root) {
        edtNombreDeCliente = root.findViewById(R.id.edtNombreDeCliente);
        btnBuscarCliente = root.findViewById(R.id.btnBuscarCliente);
        btnAgregarProducto = root.findViewById(R.id.btnAgregarProductoFragmentVentas);
        productosRecyclerView = root.findViewById(R.id.recyclerViewVentas);
        edtObservacionesDeVenta = root.findViewById(R.id.edtObservacionesDeVenta);
        txvSubTotalVenta = root.findViewById(R.id.txvSubTotalVenta);
        txvIVA = root.findViewById(R.id.txvIVA);
        txvEnvio = root.findViewById(R.id.txvEnvio);
        txvTotal = root.findViewById(R.id.txvTotalVenta);
        listaProductosEnVenta=new ArrayList<>();
        adaptadorDetalleVenta = new AdaptadorDetalleVenta(getContext(),listaProductosEnVenta);
        RecyclerView recyclerViewVentas = root.findViewById(R.id.recyclerViewVentas);
        recyclerViewVentas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewVentas.setAdapter(adaptadorDetalleVenta);
        adaptadorBuscarClientesEnVentas=new AdaptadorBuscarClientesEnVentas(listaDeClientes);
//        for (int i=0;i<500;i++) {
//            listaProductosEnVenta.add(new VentaDetalle(i, 1, "G"+i, "Gourmet", 62, 0, 62));
//            adaptadorDetalleVenta.notifyDataSetChanged();
//        }
        //spinner de direcciones:
        nombresDirecciones=new ArrayList<>();
        adaptadorSpinDirecciones = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, nombresDirecciones);
        //adaptadorSpinDirecciones.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        View view=View.inflate(getContext(),R.layout.fragment_ventas,null);
        spinnerDireccionesEnvio = view.findViewById(R.id.spinnerDireccionesEnvio);
        spinnerDireccionesEnvio.setAdapter(adaptadorSpinDirecciones);
        txtFibClienteVentas=(view.findViewById(R.id.txtFibClienteVentas));
        Fid=txtFibClienteVentas.getText().toString();
        if (Fid.isEmpty()){
            //btnAgregarProducto.setEnabled(false);
            //spinnerDireccionesEnvio.setEnabled(false);
            btnAgregarProducto.setText("Seleccione un cliente primero para agregar productos");
            Animation blinkAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_animation);
            btnBuscarCliente.clearAnimation();
            btnBuscarCliente.startAnimation(blinkAnimation);

        }
        //myRefDireccionesDeEnvio.child(Fid).addValueEventListener(direccionesDeEnvioEventListener);

        myRefDireccionesDeEnvio = database.getReference("DireccionesDeEnvio/NcpXqvG41o4nO282cTx");

        myRefDireccionesDeEnvio.addValueEventListener(direccionesDeEnvioEventListener);
        nombresDirecciones.add("Agregar Nueva Dirección");
        adaptadorSpinDirecciones.notifyDataSetChanged();
    }

    private void addListeners(View v) {
        //View root= LayoutInflater.from(getContext()).inflate(R.layout.fragment_ventas,null);
        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarCliente();
            }
        });
        btnAgregarProducto.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      agregarProducto(v);
                                                  }
                                              });


        //================================================================
        productosEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeProductos.clear();
                for (DataSnapshot productosSnapshot: snapshot.getChildren()){
                    Producto p =productosSnapshot.getValue(Producto.class);
                    //Log.d("Firebase productos:", p.getNombre());
                    listaDeProductos.add(p);

                }
                adaptadorDetalleVenta.notifyDataSetChanged();
                Log.d("Firebase Productos", "tamaño lista de productos: "+listaDeProductos.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(root,"Error al obtener datos desde Firebase leyendo productos",Snackbar.LENGTH_LONG);
            }
        };
        clientesEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeClientes.clear();
                for (DataSnapshot clientesSnapshot: snapshot.getChildren()){
                    Cliente p =clientesSnapshot.getValue(Cliente.class);
                    //Log.d("Firebase Clientes:", p.getNombre());
                    listaDeClientes.add(p);

                }
                adaptadorDetalleVenta.notifyDataSetChanged();
                Log.d("Firebase Clientes", "tamaño lista de clientes: "+listaDeClientes.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(root,"Error al obtener datos desde Firebase leyendo clientes",Snackbar.LENGTH_LONG);
            }
        };
        direccionesDeEnvioEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeDirecciones.clear();
                try {
                    for (DataSnapshot direccionesSnapshot : snapshot.getChildren()) {
                        String p = direccionesSnapshot.getValue(DireccionDeEnvio.class).getNombre().toString();
                        //Log.d("Firebase Clientes:", p.getNombre());
                        listaDeDirecciones.add(p);

                    }
                    adaptadorSpinDirecciones.notifyDataSetChanged();
                }catch (Exception e){
                    Log.d("listaDeClientes", "onDataChange: "+e.getMessage());
                }
                Log.d("Firebase Direcciones", "tamaño lista de direcciones: "+listaDeClientes.size());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Firebase Direcciones", "Error al descargar la lista de clientes : "+error.getMessage());
                AlertDialog.Builder alerta=new AlertDialog.Builder(getContext());
                alerta.setMessage("Error al descargar la lista de clientes de firebase: "+error.getMessage());
                alerta.setTitle("Error");
                alerta.setPositiveButton("Aceptar.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alerta.show();
            }
        };
        myRefProductos.addValueEventListener(productosEventListener);
        myRefClientes.addValueEventListener(clientesEventListener);

        listaDeDirecciones.add("Nueva Direccion");
        spinnerDireccionesEnvio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = listaDeDirecciones.get(position);
                if (seleccion.equals("Agregar Nueva Dirección")) {
                    // Llama a un método o muestra un diálogo para agregar una nueva dirección
                    agregarNuevaDireccion();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void agregarNuevaDireccion() {
        //  lógica para agregar una nueva dirección
        // Por ejemplo, un AlertDialog con un campo EditText para ingresar la dirección
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Agregar Nueva Dirección");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Aceptar", (dialog, which) -> {
            String nuevaDireccion = input.getText().toString();
            if (!nuevaDireccion.isEmpty()) {
                listaDeDirecciones.add(listaDeDirecciones.size() - 1, nuevaDireccion); // Agrega la dirección antes del ítem "Agregar Nueva Dirección"
                adaptadorSpinDirecciones.notifyDataSetChanged(); // Notifica al adapter sobre el cambio
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void showDialogClientes() {
        View v = View.inflate(getContext(), R.layout.vista_buscar_clientes_lista, null);
        RecyclerView RvBuscarClientes = (RecyclerView) v.findViewById(R.id.RVclientesListView);
        ImageButton btnCerrarDialogClientes = v.findViewById(R.id.btnCerrarDialogoBuscarClientes);

        if (RvBuscarClientes != null) {
            AdaptadorBuscarClientesEnVentas adaptadorBuscarClientesEnVentas1 = new AdaptadorBuscarClientesEnVentas(listaDeClientes);

            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            RvBuscarClientes.setLayoutManager(lm);
            RvBuscarClientes.setAdapter(adaptadorBuscarClientesEnVentas1);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.DialogTheme);
            //builder.setTitle("Buscar Clientes");
            builder.setView(v);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create(); // Crear una instancia del diálogo
            adaptadorBuscarClientesEnVentas1.setOnClienteSelectedListener(new OnClienteSelectedListener() {
                @Override
                public void onClienteSelected(Cliente cliente) {
                    edtNombreDeCliente.setText(cliente.getNombre()+" "+ cliente.getApellido());
                    txtFibClienteVentas.setText(cliente.getFid());
                    btnAgregarProducto.setText("Agregar Producto.");
                    btnBuscarCliente.setEnabled(true);
                    spinnerDireccionesEnvio.setEnabled(true);
                    dialog.dismiss();
                }
            });

            btnCerrarDialogClientes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss(); // Usar esta instancia para cerrar el diálogo
                }
            });

            dialog.show(); // Mostrar el diálogo usando la instancia creada anteriormente
        } else {
            Log.e("VentasFragment", "RvBuscarClientes es nulo!");
        }
    }
//private ArrayList<DireccionDeEnvio> buscarDirecciones(String ClienteFib){
//myRefDireccionesDeEnvio.child(ClienteFib).addValueEventListener(new ValueEventListener() {
//    @Override
//    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError error) {
//
//    }
//});
//        return null;
//
//}


    private void buscarCliente() {
        btnBuscarCliente.clearAnimation();
        showDialogClientes();
    }

    private void agregarProducto(View v) {
        String n = edtNombreDeCliente.getText().toString();
        if (n.isEmpty()){

                Snackbar.make(root,"Seleccione un cliente primero",Snackbar.LENGTH_SHORT).setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE).show();
           // Toast.makeText(getContext(), "Seleccione un cliente primero", Toast.LENGTH_SHORT).show();
        }else {
            showProductDialog();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (clientesEventListener != null && productosEventListener!=null) {
            myRefClientes.removeEventListener(clientesEventListener);
            myRefProductos.removeEventListener(productosEventListener);

        }
        binding = null;
    }

    public void actualizarProdutosDeFirebase() {
        // Usamos el método updateClientNamesList, así que no necesitamos duplicar el código aquí.

    }


    private void addProductToSaleRecyclerView(String product, int quantity, double discount) {
        // ... (tu código para agregar productos al RecyclerView)
    }
    private void showProductDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Seleccionar Producto");

        // Aquí puedes inflar una vista personalizada para el diálogo o usar un layout predeterminado
        // Por ejemplo, podrías tener un RecyclerView con productos, un EditText para la cantidad y otro para el descuento.

        builder.setPositiveButton("Agregar", (dialog, which) -> {
            // Obtener el producto seleccionado, la cantidad y el descuento
            // String selectedProduct = ...;
            // int quantity = ...;
            // double discount = ...;
            detalleDeVentas.setClave("");
            detalleDeVentas=new VentaDetalle();
            detalleDeVentas.setNombre("");
            detalleDeVentas.setCantidad(1);
            detalleDeVentas.setPrecioUnitario(250.00);
            detalleDeVentas.setDescuento(5.00);
            detalleDeVentas.setPrecioConDescuento(225);
            detalleDeVentas.setImporte(225);

            addProductToSaleRecyclerView(detalleDeVentas);
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
    private void addProductToSaleRecyclerView(VentaDetalle d) {
        // Aquí debes agregar el producto a tu lista de datos que está respaldando el RecyclerView
        // También deberías notificar al adaptador que hay un cambio en los datos.
        listaProductosEnVenta.add(d);

        // Actualizar totales
        updateTotals();
    }
    private void updateTotals() {


        double subtotal = 0.0;
        double iva = 0;
        double total = 0;



        for (VentaDetalle detalleDeVenta : listaProductosEnVenta) {
            subtotal += detalleDeVenta.getPrecioConDescuento() * detalleDeVenta.getCantidad();
        }

        iva = subtotal * 0.16; // Esto es un ejemplo, ajusta según tu tasa de IVA
        total = subtotal + iva;

        txvSubTotalVenta.setText(String.format("%.2f", subtotal));
        txvIVA.setText(String.format("%.2f", iva));
        txvTotal.setText(String.format("%.2f", total));
    }



}
