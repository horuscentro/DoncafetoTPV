package com.doncafeto.doncafetotpv;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doncafeto.doncafetotpv.Clases.ventas.DireccionDeEnvio;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link nav_Pruebas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class nav_Pruebas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Spinner spinnerDirecciones;
    Button btnGuardar;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myrefDireccciones=database.getReference("DireccionesDeEnvio");
    ArrayList<DireccionDeEnvio> listaDeDirecciones;
    EditText edtFib,edtcalle ,edtnombre,edtnumero,edtcolonia,edtcodPostal,edtciudad ,edtestado ,edtpais ,edttelefono,edtcontacto ;
    ArrayList<String> nombresDirecciones;


    public nav_Pruebas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_Pruebas.
     */
    // TODO: Rename and change types and number of parameters
    public static nav_Pruebas newInstance(String param1, String param2) {
        nav_Pruebas fragment = new nav_Pruebas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav__pruebas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerDirecciones=(Spinner) view.findViewById(R.id.spinnerDirecciones);

        iniciar(view);
    }
    private  void iniciar(View v){
        nombresDirecciones=new ArrayList<>();
        nombresDirecciones.add("Agregar Nueva Dirección");
        listaDeDirecciones=new ArrayList<>();
        btnGuardar=(Button)  v.findViewById(R.id.btnGuardarPruebas);

        edtFib=v.findViewById(R.id.fibEditText);
        edtcalle=v.findViewById(R.id.calleEditText);
        edtnombre=v.findViewById(R.id.nombreEditText);
        edtnumero=v.findViewById(R.id.numeroEditText);
        edtcolonia=v.findViewById(R.id.coloniaEditText);
        edtcodPostal=v.findViewById(R.id.codPostalEditText);
        edtciudad=v.findViewById(R.id.ciudadEditText);
        edtestado=v.findViewById(R.id.estadoEditText);
        edtpais=v.findViewById(R.id.paisEditText);
        edttelefono=v.findViewById(R.id.telefonoEditText);
        edtcontacto=v.findViewById(R.id.contactoEditText);

        spinnerDirecciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = nombresDirecciones.get(position);
                if (seleccion.equals("Agregar Nueva Dirección")) {
                    // Llama a un método o muestra un diálogo para agregar una nueva dirección
                    limpiarCampos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ValueEventListener escuchador=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeDirecciones.clear(); // Limpia la lista antes de agregar nuevos datos
                nombresDirecciones = new ArrayList<>();

                for (DataSnapshot direccionesSnapshot : snapshot.getChildren()) {
                    DireccionDeEnvio p = direccionesSnapshot.getValue(DireccionDeEnvio.class);
                    //Log.d("Firebase Clientes:", p.getNombre());
                    listaDeDirecciones.add(p);
                    nombresDirecciones.add(p.getNombre()); // Asumiendo que quieres mostrar el nombre en el Spinner. Puedes cambiar esto por cualquier otro campo si lo deseas.

                    Log.d("Direcciones:", "onDataChange: " + p.toString());

                }
                nombresDirecciones.add("Agregar Nueva Dirección");
                // Crear ArrayAdapter usando la lista de nombres
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, nombresDirecciones);
                spinnerDirecciones.setAdapter(adapter); // Establece el ArrayAdapter en el Spinner

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    myrefDireccciones.addValueEventListener(escuchador);


    btnGuardar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            guardarEnFirebase(view);
        }
    });
    }

    private void limpiarCampos() {
          edtnombre.setText("");
          edtcalle.setText("");
         edtnumero.setText("");
         edtcolonia.setText("");
         edtcodPostal.setText("");
         edtciudad.setText("");
         edtestado.setText("");
         edtpais.setText("");
        edttelefono.setText("");
        edtcontacto.setText("");
    }

    private void guardarEnFirebase(View v) {
        // Obtener los datos de los EditText
        DatabaseReference fib = myrefDireccciones.push();
        edtFib.setText(fib.getKey());
        edtFib.setFocusable(false);

        String nombre = edtnombre.getText().toString();
        String calle = edtcalle.getText().toString();
        String numero = edtnumero.getText().toString();
        String colonia = edtcolonia.getText().toString();
        String codPostal = edtcodPostal.getText().toString();
        String ciudad = edtciudad.getText().toString();
        String estado = edtestado.getText().toString();
        String pais = edtpais.getText().toString();
        String telefono =edttelefono.getText().toString();
        String contacto =edtcontacto.getText().toString();
        // ... repetir para cada EditText

        // Crear un objeto DireccionDeEnvio con esos datos
        DireccionDeEnvio direccion = new DireccionDeEnvio(edtFib.getText().toString(), nombre, calle,numero,colonia,codPostal,ciudad,estado,pais,telefono,contacto /*...otros campos*/);

        // Guardar ese objeto en Firebase
        // Aquí estoy usando el valor de 'fib' como clave única para cada dirección.
        // Puedes elegir usar otro campo o dejar que Firebase genere una clave única con push().getKey().
        myrefDireccciones.child(fib.getKey()).setValue(direccion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                limpiarCampos();
            }});
    }

}