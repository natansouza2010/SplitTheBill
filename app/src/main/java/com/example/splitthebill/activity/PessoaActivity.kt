package com.example.splitthebill.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.splitthebill.databinding.ActivityMainBinding
import com.example.splitthebill.databinding.ActivityPessoaBinding
import com.example.splitthebill.model.Constant.DELETE_PESSOA
import com.example.splitthebill.model.Constant.EXTRA_PESSOA
import com.example.splitthebill.model.Pessoa
import java.util.*

class PessoaActivity : AppCompatActivity() {

    private val acb: ActivityPessoaBinding by lazy {
        ActivityPessoaBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)


        val receivedPessoa = intent.getParcelableExtra<Pessoa>(EXTRA_PESSOA)
        receivedPessoa?.let{ _receivedPessoa ->
            with(acb) {
                with(_receivedPessoa) {
                    nameEt.setText(nome)
                    valorPagoEt.setText(valorPago.toString())
                }
            }
        }


        acb.saveBt.setOnClickListener {
            val pessoa = Pessoa (
                id = receivedPessoa?.id?: Random(System.currentTimeMillis()).nextInt(),
                nome = acb.nameEt.text.toString(),
                valorPago = acb.valorPagoEt.text.toString().toDouble(),
                valorAReceber = acb.valorPagoEt.text.toString().toDouble()
                    )
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_PESSOA, pessoa)
            setResult(RESULT_OK, resultIntent)
            finish()
        }

        acb.deleteBt.setOnClickListener {
            if(receivedPessoa != null) {
                val resultIntent = Intent()
                resultIntent.putExtra(DELETE_PESSOA, receivedPessoa)
                setResult(RESULT_OK, resultIntent)
                finish()

            }


        }
    }
}