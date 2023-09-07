package com.doncafeto.doncafetotpv.ui.Clientes;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doncafeto.doncafetotpv.Adaptadores.ClientesAdapter;
import com.doncafeto.doncafetotpv.Clases.Cliente;
import com.doncafeto.doncafetotpv.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ClientesFragment extends Fragment {

    // ... (toda la parte inicial y declaraciones se mantienen igual)
    Button btnNuevoCliente;
    View dialogView;
    EditText etFiltroClientes, etNombre ,etApellido,etDireccion,etCodigoPostal, etCiudad,etEstado, etPais,etTelefono,etCorreoElectronico;
    ClientesAdapter clienteAdapter;
    ArrayList<Cliente> listaDeClientes;
    RecyclerView rvClientes;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clientes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("clientes");
         myRef.addValueEventListener(clientesListener);
        listaDeClientes=new ArrayList<>();
        btnNuevoCliente = view.findViewById(R.id.btnAddCliente);
        clienteAdapter=new ClientesAdapter(listaDeClientes);
        rvClientes=(RecyclerView) view.findViewById(R.id.recyclerViewClientes);
        etFiltroClientes=(EditText) view.findViewById(R.id.etFilter);
        etFiltroClientes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filtraClientes(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        LinearLayoutManager lm=new LinearLayoutManager(getContext());
        rvClientes.setLayoutManager(lm);
        rvClientes.setAdapter(clienteAdapter);
        btnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNuevoClienteDialogo();
                Toast.makeText(getContext(), "btn", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void filtraClientes(String valueOf) {
        ArrayList<Cliente> listaFiltrada=new ArrayList<>();
        for (Cliente c : listaDeClientes){
            if (c.getNombre().toString().toLowerCase().contains(valueOf.toLowerCase())){
                listaFiltrada.add(c);
            }
        }
        clienteAdapter.filter(listaFiltrada);
    }

    private void showNuevoClienteDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // Inflate y set up view
        LayoutInflater inflater = getLayoutInflater();
        // Usa dialogView aquí

        // Declara la vista del diálogo aquí
        dialogView = inflater.inflate(R.layout.vista_dialogo_clientes, null);
         etNombre = dialogView.findViewById(R.id.etNombre);
         etApellido = dialogView.findViewById(R.id.etApellido);
         etDireccion = dialogView.findViewById(R.id.etDireccion);
         etCodigoPostal= dialogView.findViewById(R.id.etCodigoPostal);
         etCiudad = dialogView.findViewById(R.id.etCiudad);
         etEstado = dialogView.findViewById(R.id.etEstado);
         etPais = dialogView.findViewById(R.id.etPais);
         etTelefono = dialogView.findViewById(R.id.etTelefono);
         etCorreoElectronico = dialogView.findViewById(R.id.etCorreoElectronico);
         etNombre.setText("Humberto");
        etApellido.setText("Valle Saucedo");
        etDireccion.setText("Real de guadalupe 105a, barrio de guadalupe");
        etCodigoPostal.setText("29230");
        etCiudad.setText("San Cristobal de las Casas");
        etEstado.setText("Chiapas");
         etPais.setText("Mexico");
        etTelefono.setText("961359851");
        etCorreoElectronico.setText("horus.central3@gmail.com");
        builder.setView(dialogView);
        builder.setTitle("Agregar Cliente.");

        AlertDialog dialog = builder.create();

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Este callback ahora está vacío. Luego asignaremos la acción real.
            }
        });

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // ... (El resto de tu código de validación)
                        String nombre = etNombre.getText().toString();
                        String apellido = etApellido.getText().toString();
                        String direccion = etDireccion.getText().toString();
                        String cogigoPostal=etCodigoPostal.getText().toString();
                        String ciudad = etCiudad.getText().toString();
                        String estado = etEstado.getText().toString();
                        String pais = etPais.getText().toString();
                        String telefono = etTelefono.getText().toString();
                        String correoElectronico = etCorreoElectronico.getText().toString();
                        if (validarDatos(nombre,apellido,direccion,ciudad,estado,pais,telefono,correoElectronico)){
                        Cliente nuevoCliente = new Cliente();
                        nuevoCliente.setNombre(nombre);
                        nuevoCliente.setApellido(apellido);
                        nuevoCliente.setDireccion(direccion);
                        nuevoCliente.setCodigoPostal(cogigoPostal);
                        nuevoCliente.setCiudad(ciudad);
                        nuevoCliente.setEstado(estado);
                        nuevoCliente.setPais(pais);
                        nuevoCliente.setTelefono(telefono);
                        nuevoCliente.setCorreoElectronico(correoElectronico);

                        if (guardarNuevoCliente(nuevoCliente)==0){
                            //listaDeClientes.add(nuevoCliente); // clienteAdapter es la instancia de tu Adapter
                            //clienteAdapter.notifyDataSetChanged();
                            Log.d("Firebase: ","Cliente agregado con exito");
                        }else {
                            Log.e("Firebase: ", "Error al guardar el cliente en firebase");
                        }

                        dialog.dismiss();
                    }


                        //dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }
    private int guardarNuevoCliente(Cliente c){
        final int[] resultado = {0};

        DatabaseReference nuevoClienteFid =myRef.push();
        c.setFid(nuevoClienteFid.getKey());
        myRef.child(nuevoClienteFid.getKey()).setValue(c, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error != null){
                    Log.e("Firebase","Error al guardar en firebase, "+error.toException());
                    Toast.makeText(getContext(), "Error al guardar el cliente: ", Toast.LENGTH_SHORT).show();
                    resultado[0] = 1;
                }else {
                    Toast.makeText(getContext(), "Cliente guardado con exito.", Toast.LENGTH_SHORT).show();
                    //listaDeClientes.add(c);
                    clienteAdapter.notifyDataSetChanged();
                }
            }
        });
        return resultado[0];
    }
    private boolean validarDatos(String nombre,String apellido,String direccion,String ciudad,String estado,String pais,String telefono,String correoElectronico){
        // Validación
        boolean validacion=true;
        if (!isValidString(nombre)) {
            etNombre.setError("Introduce un nombre válido.");
            validacion=false;
        }
        if (!isValidString(apellido)) {
            etApellido.setError("Introduce un apellido válido.");
            validacion=false;
        }
        if (!isValidString(direccion)) {
            etDireccion.setError("Introduce una dirección válida.");
            validacion=false;
        }
        if (!isValidString(ciudad)) {
            etCiudad.setError("Introduce una ciudad válida.");
            validacion=false;
        }
        if (!isValidString(estado)) {
            etEstado.setError("Introduce un estado válido.");
            validacion=false;
        }
        if (!isValidString(pais)) {
            etPais.setError("Introduce un país válido.");
            validacion=false;
        }
        if (!isValidPhone(telefono)) {
            etTelefono.setError("Introduce un teléfono válido.");
            validacion=false;
        }
        if (!isValidEmail(correoElectronico)) {
            etCorreoElectronico.setError("Introduce un correo electrónico válido.");
            validacion=false;
        }
        return validacion;
    }

    // ... (métodos de validación y cualquier otro código adicional)
    private boolean isValidString(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email != null && email.matches(emailPattern);
    }

    private boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\+?[0-9]{10,15}");
    }
    ValueEventListener clientesListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            listaDeClientes.clear();
            for (DataSnapshot clienteSnapshot : dataSnapshot.getChildren()) {
                Cliente cliente = clienteSnapshot.getValue(Cliente.class);
                listaDeClientes.add(cliente);
            }
            //maxId=(dataSnapshot.getChildrenCount());
            clienteAdapter.actualizaLista(listaDeClientes);
            clienteAdapter.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.w(TAG, "Error al leer los datos.", databaseError.toException());
        }
    };


}
