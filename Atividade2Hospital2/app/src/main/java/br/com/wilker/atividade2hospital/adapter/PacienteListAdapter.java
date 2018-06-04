package br.com.wilker.atividade2hospital.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.wilker.atividade2hospital.R;
import br.com.wilker.atividade2hospital.entity.Paciente;

/**
 * Created by Wilker on 03/06/2018.
 */

public class PacienteListAdapter extends BaseAdapter {
    private final List<Paciente> pacienteList;
    private final Activity activity;


    public PacienteListAdapter(List<Paciente> pacientes, Activity activity){
        this.pacienteList = pacientes;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return pacienteList.size();
    }

    @Override
    public Object getItem(int position) {
        return pacienteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pacienteList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.pacientes_list_item, parent, false);
        Paciente paciente = pacienteList.get(position);
        TextView nome = view.findViewById(R.id.paciente_list_item_nome);
        TextView numero = view.findViewById(R.id.paciente_list_item_numero_leito);
        nome.setText(paciente.getNome());
        numero.setText(paciente.getNumeroLeito().toString());
        return view;
    }
}
