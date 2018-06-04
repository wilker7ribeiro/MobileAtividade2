package br.com.wilker.atividade1balaiodelenha;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button calcularButton;
    EditText inputConsumo;
    EditText inputCouvertArtistico;
    EditText inputDividirPorPessoa;

    EditText inputTaxaServico;
    EditText inputContaTotal;
    EditText inputValorPorPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.toolbar_action_bar);
        setSupportActionBar(myToolbar);

        calcularButton = findViewById(R.id.bt_calcular);
        inputConsumo = findViewById(R.id.edt_consumo);
        inputCouvertArtistico = findViewById(R.id.edt_couvert_artistico);
        inputDividirPorPessoa = findViewById(R.id.edt_dividir);
        inputTaxaServico = findViewById(R.id.edt_servico);
        inputContaTotal = findViewById(R.id.edt_conta_total);
        inputValorPorPessoa = findViewById(R.id.edt_valor_pessoa);

        inputConsumo.addTextChangedListener(habilitarOuDesabilitarBotaoCalcularTextWather);
        inputCouvertArtistico.addTextChangedListener(habilitarOuDesabilitarBotaoCalcularTextWather);
        inputDividirPorPessoa.addTextChangedListener(habilitarOuDesabilitarBotaoCalcularTextWather);

        calcularButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double consumo = Double.parseDouble(inputConsumo.getText().toString());
                double couvertArtistico = Double.parseDouble(inputCouvertArtistico.getText().toString());
                int quantidadeDePessoas = Integer.parseInt(inputCouvertArtistico.getText().toString());

                Double taxaServico = consumo * 0.1;
                Double contaTotal = consumo + taxaServico + couvertArtistico;
                Double valorPorPessoa = contaTotal / quantidadeDePessoas;

                inputContaTotal.setText(contaTotal.toString());
                inputTaxaServico.setText(taxaServico.toString());
                inputValorPorPessoa.setText(valorPorPessoa.toString());
            }
        });


    }

    private TextWatcher habilitarOuDesabilitarBotaoCalcularTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            calcularButton.setEnabled(!botaoCalcularDeveEstarDesabilitado());
        }
    };

    private boolean botaoCalcularDeveEstarDesabilitado(){
        return isEmpty(inputConsumo) || isEmpty(inputCouvertArtistico) ||  isEmpty(inputDividirPorPessoa);
    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }
}
