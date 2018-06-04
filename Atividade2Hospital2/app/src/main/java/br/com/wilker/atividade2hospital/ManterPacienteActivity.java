package br.com.wilker.atividade2hospital;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import br.com.wilker.atividade2hospital.dao.PacienteDao;
import br.com.wilker.atividade2hospital.entity.Paciente;

/**
 * Created by Wilker on 03/06/2018.
 */

public class ManterPacienteActivity extends AppCompatActivity {

    boolean isVisualizar;
    boolean isEditar;
    Integer idPaciente;
    Paciente pacienteEditando;

    private final String REQUIRED_MSG = "Campo obrigatório";
    TextInputEditText pacienteNomeInput;
    TextInputEditText pacienteNumeroLeitoInput;
    TextInputEditText pacientePressaoSistolicaInput;
    TextInputEditText pacientePressaoDisatolicaInput;
    TextInputEditText pacienteBatimentosInput;
    TextInputEditText pacienteTemperaturaInput;
    Button botaoSalvar;
    Button botaoRemover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manter_paciente_activity);

        Toolbar myToolbar = findViewById(R.id.manter_paciente_toolbar);
        setSupportActionBar(myToolbar);

        pacienteNomeInput = findViewById(R.id.paciente_nome_input);
        pacienteNumeroLeitoInput = findViewById(R.id.paciente_numero_leito_input);
        pacientePressaoSistolicaInput = findViewById(R.id.paciente_pressao_sistolica_input);
        pacientePressaoDisatolicaInput = findViewById(R.id.paciente_pressao_diastolica_input);
        pacienteBatimentosInput = findViewById(R.id.paciente_batimentos_input);
        pacienteTemperaturaInput = findViewById(R.id.paciente_temperatura_input);
        botaoSalvar = findViewById(R.id.btn_paciente_salvar);
        botaoRemover = findViewById(R.id.btn_paciente_remover);

        carregarTela();

    }

    private void buscarPacienteEPreencherCampos(final int idPaciente) {
        this.idPaciente = idPaciente;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pacienteEditando = getPacienteDao().findById(idPaciente);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pacienteNomeInput.setText(String.valueOf(pacienteEditando.getNome()));
                                pacienteNumeroLeitoInput.setText(String.valueOf(pacienteEditando.getNumeroLeito()));
                                pacientePressaoSistolicaInput.setText(String.valueOf(pacienteEditando.getPressaoArterialSistolica()));
                                pacientePressaoDisatolicaInput.setText(String.valueOf(pacienteEditando.getPressaoArterialDiastolica()));
                                pacienteBatimentosInput.setText(String.valueOf(pacienteEditando.getBatimentosCardiacos()));
                                pacienteTemperaturaInput.setText(String.valueOf(pacienteEditando.getTemperatura()));
                            }
                        });
                    }
                }).start();
            }
        }, 100);


    }

    private void desabilitarCampos(TextInputEditText... editTexts) {
        for (TextInputEditText textInputEditText : editTexts) {
            textInputEditText.setEnabled(false);
        }
    }

    private void adicionarVaidacaoRequired(TextInputEditText... editTexts){
        for (TextInputEditText textInputEditText : editTexts) {
            textInputEditText.addTextChangedListener(getValidarRequiredTextWatcher(textInputEditText));
        }

    }

    private void carregarTela() {
        Intent intent = getIntent();
        if (intent.getBooleanExtra("visualizar", false)) {
            isVisualizar = true;
            buscarPacienteEPreencherCampos(intent.getIntExtra("idPaciente", -1));
            botaoSalvar.setVisibility(View.INVISIBLE);
            botaoRemover.setVisibility(View.INVISIBLE);
            desabilitarCampos(pacienteNomeInput, pacienteNumeroLeitoInput, pacientePressaoDisatolicaInput, pacientePressaoSistolicaInput, pacienteBatimentosInput, pacienteTemperaturaInput);
        } else if (intent.getBooleanExtra("editar", false)) {
            isEditar = true;
            buscarPacienteEPreencherCampos(intent.getIntExtra("idPaciente", -1));
        } else {
            botaoRemover.setVisibility(View.INVISIBLE);
        }
        adicionarVaidacaoRequired(pacienteNomeInput, pacienteNumeroLeitoInput, pacientePressaoSistolicaInput, pacientePressaoDisatolicaInput, pacientePressaoSistolicaInput, pacienteBatimentosInput, pacienteTemperaturaInput);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposPreenchidos(pacienteNomeInput, pacienteNumeroLeitoInput, pacientePressaoDisatolicaInput, pacientePressaoSistolicaInput, pacienteBatimentosInput, pacienteTemperaturaInput)) {
                    final Paciente paciente = getPacienteFromForm();
                    if (isEditar) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getPacienteDao().updatePaciente(paciente);
                                finish();
                            }
                        }).start();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getPacienteDao().insertAll(paciente);
                                finish();
                            }
                        }).start();
                    }
                }
            }
        });

        botaoRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getPacienteDao().delete(pacienteEditando);
                        finish();
                    }
                }).start();
            }
        });

        return;

    }

    private TextWatcher getValidarRequiredTextWatcher(final TextInputEditText editText) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!camposPreenchidos(editText)) {
                    editText.setError(REQUIRED_MSG);
                } else {
                    editText.setError(null);
                }
            }
        };
    }

    private boolean camposPreenchidos(TextInputEditText... editTexts) {
        for (TextInputEditText editText : editTexts) {
            String text = editText.getText().toString().trim();
            if (text.length() == 0) {
                return false;
            }
        }
        return true;
    }

    private PacienteDao getPacienteDao() {
        return DataBaseService.getDatabase(this).pacienteDao();
    }

    private Paciente getPacienteFromForm() {
        Paciente paciente = new Paciente();
        if(idPaciente != null){
            paciente.setId(idPaciente);
        }
        paciente.setNome(pacienteNomeInput.getText().toString());
        paciente.setNumeroLeito(Integer.parseInt(pacienteNumeroLeitoInput.getText().toString()));
        paciente.setPressaoArterialSistolica(Integer.parseInt(pacientePressaoSistolicaInput.getText().toString()));
        paciente.setPressaoArterialDiastolica(Integer.parseInt(pacientePressaoDisatolicaInput.getText().toString()));
        paciente.setBatimentosCardiacos(Integer.parseInt(pacienteBatimentosInput.getText().toString()));
        paciente.setTemperatura(Integer.parseInt(pacienteTemperaturaInput.getText().toString()));
        return paciente;
    }

}
