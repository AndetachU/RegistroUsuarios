package co.edu.uniminuto.registrousuarios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

import co.edu.uniminuto.registrousuarios.entity.User;
import co.edu.uniminuto.registrousuarios.model.UserDao;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Validate";
    private Context context;
    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContra;
    private ListView listUsers;
    private int documento;
    String usuario;
    String nombres;
    String apellidos;
    String Contra;
    SQLiteDatabase baseDatos;
    private Button btnSave;
    private Button btnListUsers;
    private Button btnLimpiar;
    private Button btnBuscar;
    private Button btnDelete;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        context = this;
        initObject();
        btnSave.setOnClickListener(this::createUser);
        btnListUsers.setOnClickListener(this::listUserShow);
        btnLimpiar.setOnClickListener(this::clearUserList);
        btnBuscar.setOnClickListener(this::searchUser);
        btnDelete.setOnClickListener(this::deleteUser);
        btnUpdate.setOnClickListener(this::updateUser);
    }



    //buscar un usuario
    private void searchUser(View view) {
        getData();
        UserDao dao = new UserDao(context, findViewById(R.id.lvList));
        User user = dao.getUserByDocument(documento);
        if (user != null) {
            etDocumento.setText(String.valueOf(user.getDocument()));
            etUsuario.setText(user.getUser());
            etNombres.setText(user.getNames());
            etApellidos.setText(user.getLastNames());
            etContra.setText(user.getPassword());
        } else {
            Toast.makeText(this, "No se encontró el usuario con el documento especificado", Toast.LENGTH_SHORT).show();
        }
    }

    //Mostrar lista de usuarios
    private void listUserShow(View view) {
        UserDao dao = new UserDao(context,findViewById(R.id.lvList));
        ArrayList<User> users = dao.getUserList();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
        listUsers.setAdapter(adapter);
    }
    //Limpiar lista de usuarios
    private void clearUserList(View view) {
        UserDao dao = new UserDao(context, findViewById(R.id.lvList));
        ArrayList<User> emptyList = new ArrayList<>();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emptyList);
        listUsers.setAdapter(adapter);
    }
//Metodo que llama la creación de usuarios
    private void createUser(View view) {
        getData();
        User user = new User(documento,nombres,apellidos,usuario,Contra);
        UserDao dao = new UserDao(context,view);
        dao.insertUser(user);
        listUserShow(view);
    }

    private void getData(){
        documento = Integer.parseInt(etDocumento.getText().toString());
        usuario = etUsuario.getText().toString();
        nombres = etNombres.getText().toString();
        apellidos = etApellidos.getText().toString();
        Contra = etContra.getText().toString();

        //Validación de datos con expresiones regulares
        String documentoPattern = "\\d+";
        if(!documentoPattern.matches(etDocumento.getText().toString())) {
              Toast.makeText(this, "El documento debe ser un número entero", Toast.LENGTH_SHORT).show();
        }
        String usuarioPattern = "^[a-zA-Z0-9_-]+$";
        if (!usuarioPattern.matches(usuario)) {

             Toast.makeText(this, "El usuario solo puede contener letras, números y guiones bajos.", Toast.LENGTH_SHORT).show();
        }
        String nombresApellidosPattern = "^[a-zA-Z\\s]+$";
        if (!nombresApellidosPattern.matches(nombres)) {

            Toast.makeText(this, "Los nombres solo pueden contener letras y espacios.", Toast.LENGTH_SHORT).show();
        }
        if (!nombresApellidosPattern.matches(apellidos)) {

            Toast.makeText(this, "Los apellidos solo pueden contener letras y espacios.", Toast.LENGTH_SHORT).show();
        }
        String contraPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        if (!contraPattern.matches(Contra)) {
            Toast.makeText(this, "La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un número y 8 caracteres.", Toast.LENGTH_SHORT).show();
        }
    }
    //Actualizar usuario
    private void updateUser(View view) {
        getData();
        User user = new User(documento, nombres, apellidos, usuario, Contra);
        UserDao dao = new UserDao(context, view);
        dao.updateUser(user);
        listUserShow(view);
    }

    //Dar de baja a un usuario
    private void deleteUser(View view) {
        getData();
        UserDao dao = new UserDao(context, view);
        dao.deleteUser(documento);
        listUserShow(view);
    }
    private void initObject(){
        btnSave = findViewById(R.id.btnRegistrar);
        btnListUsers = findViewById(R.id.btnListar);
        etNombres = findViewById(R.id.etnombres);
        etApellidos = findViewById(R.id.etapellidos);
        etDocumento = findViewById(R.id.etdocumento);
        etContra = findViewById(R.id.etContra);
        etUsuario = findViewById(R.id.etusuario);
        listUsers = findViewById(R.id.lvList);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }
}