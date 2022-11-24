package com.example.splitthebill.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.splitthebill.R
import com.example.splitthebill.adapter.PessoaAdapter
import com.example.splitthebill.databinding.ActivityMainBinding
import com.example.splitthebill.model.Constant
import com.example.splitthebill.model.Constant.DELETE_PESSOA
import com.example.splitthebill.model.Constant.EXTRA_PESSOA
import com.example.splitthebill.model.Pessoa

class MainActivity : AppCompatActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val pessoaList: MutableList<Pessoa> = mutableListOf()

    private lateinit var pessoaAdapter: PessoaAdapter

    private lateinit var carl: ActivityResultLauncher<Intent>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)
//        fillPessoaList()
        pessoaAdapter = PessoaAdapter(pessoaList,this )
        amb.recycleViewPessoas.adapter = pessoaAdapter
        amb.recycleViewPessoas.layoutManager = LinearLayoutManager(applicationContext)
//        percorreECalculaValorAReceber()
//        pessoaAdapter.notifyDataSetChanged()

        carl = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK ) {
                val pessoa = result.data?.getParcelableExtra<Pessoa>(EXTRA_PESSOA)
                val delPessoa = result.data?.getParcelableExtra<Pessoa>(DELETE_PESSOA)

                pessoa?.let { _pessoa->
                    val position = pessoaList.indexOfFirst { it.id == _pessoa.id }
                    if (position != -1) {
                        // Alterar na posição
                        pessoaList[position] = _pessoa
                    }
                    else {
                        pessoaList.add(_pessoa)
                    }
                    pessoaAdapter.notifyDataSetChanged()
                }

                delPessoa?.let{
                    _pessoa ->
                    pessoaList.remove(_pessoa);
                    pessoaAdapter.notifyDataSetChanged()
                }

                percorreECalculaValorAReceber()

            }
        }

        pessoaAdapter.mOnItemClickListener = object : PessoaAdapter.OnItemClickListener {
            override fun onItemClick(index: Int, pessoa: Pessoa) {
                navigateTelaEdicaoPessoa(this@MainActivity, pessoa )
            }

        }

        registerForContextMenu(amb.recycleViewPessoas)


        }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.addContactMi -> {
               carl.launch(Intent(this, PessoaActivity::class.java))
                true
            }
            else -> { false }
        }
    }

    fun navigateTelaEdicaoPessoa(ctx: Context, pessoa: Pessoa) {
        val pessoaIntent = Intent(ctx ,PessoaActivity::class.java )
        pessoaIntent.putExtra(EXTRA_PESSOA,pessoa )
        pessoaIntent.putExtra(Constant.VIEW_PESSOA, true)
        carl.launch(pessoaIntent)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }


    private fun percorreECalculaValorAReceber(){
        var valorTotal = 0.0;
        pessoaList.forEachIndexed{
                index, it ->valorTotal += pessoaList[index].valorPago
        }
        val valorTotalDividoPorTodos = valorTotal/pessoaAdapter.getItemCount()

        pessoaList.forEachIndexed{
            index, it -> pessoaList[index].valorAReceber =  valorTotalDividoPorTodos -  it.valorPago
        }

        pessoaAdapter.notifyDataSetChanged()
    }




}