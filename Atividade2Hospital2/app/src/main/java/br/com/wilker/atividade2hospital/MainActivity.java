package br.com.wilker.atividade2hospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.wilker.atividade2hospital.adapter.PacienteListAdapter;
import br.com.wilker.atividade2hospital.dao.PacienteDao;
import br.com.wilker.atividade2hospital.entity.Paciente;

public class MainActivity extends AppCompatActivity {
    PacienteDao pacienteDao;
    List<Paciente> pacienteList = new ArrayList<>();
    PacienteListAdapter pacienteListAdapter;
    ListView pacientesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = findViewById(R.id.pacientes_list_toolbar);
        setSupportActionBar(myToolbar);

        pacienteDao = DataBaseService.getDatabase(this).pacienteDao();
        pacientesListView = findViewById(R.id.pacientes_list_view);
        pacienteListAdapter = new PacienteListAdapter(pacienteList, MainActivity.this);
        pacientesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Paciente paciente = (Paciente) pacientesListView.getItemAtPosition(position);
                Intent editarPacienteIntent = new Intent(MainActivity.this, ManterPacienteActivity.class);
                editarPacienteIntent.putExtra("idPaciente", paciente.getId());
                editarPacienteIntent.putExtra("editar", true);
                startActivity(editarPacienteIntent);
            }
        });
        pacientesListView.setAdapter(pacienteListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                pacienteList.clear();
                pacienteList.addAll(pacienteDao.getAll());
                runOnUiThread(new Runnable() {
                    public void run() {
                        pacienteListAdapter.notifyDataSetChanged();
                    };
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pacientes_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_adicionar_paciente:
                Intent irParaAdicionarPacienteIntent = new Intent(this, ManterPacienteActivity.class);
                startActivity(irParaAdicionarPacienteIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
