package com.example.splitthebill.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.splitthebill.R
import com.example.splitthebill.activity.PessoaActivity
import com.example.splitthebill.model.Constant.EXTRA_PESSOA
import com.example.splitthebill.model.Constant.VIEW_PESSOA
import com.example.splitthebill.model.Pessoa

class PessoaAdapter( private val listPessoa: MutableList<Pessoa>,  internal var ctx: Context) :


    RecyclerView.Adapter<PessoaAdapter.ViewHolder>() {
    var mOnItemClickListener: OnItemClickListener? = null
    var valorTotal = 0.0;
    interface  OnItemClickListener {
        fun onItemClick(index: Int, pessoa: Pessoa)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomePessoaTv: TextView
        val valorPagoTv: TextView
        val valorAReceberTv: TextView

        init {
            nomePessoaTv = view.findViewById(R.id.nomeTv)
            valorPagoTv = view.findViewById(R.id.valorPagoTv)
            valorAReceberTv = view.findViewById(R.id.valorAReceberTv)

        }
    }



    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(ctx)
            .inflate(R.layout.tile_pessoa, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = listPessoa.get(position)
        // Get element from your dataset at this position and replace the
        // contents of the view with that element]
        viewHolder.itemView.setOnClickListener{
            mOnItemClickListener?.onItemClick(position, item)

        }




//            listPessoa
//            val contactIntent = Intent(this@HomeActivity, ContactActivity::class.java)
//            contactIntent.putExtra(EXTRA_PESSOA, pessoa)
//            contactIntent.putExtra(VIEW_CONTACT, true)
//            startActivity(contactIntent)
        viewHolder.nomePessoaTv.text = listPessoa[position].nome
        viewHolder.valorPagoTv.text = listPessoa[position].valorPago.toString()
        viewHolder.valorAReceberTv.text = listPessoa[position].valorAReceber.toString()

    }



    fun navigateTelaEdicaoPessoa(ctx: Context, pessoa: Pessoa) {
        val pessoaIntent = Intent(ctx ,PessoaActivity::class.java )
        pessoaIntent.putExtra(EXTRA_PESSOA,pessoa )
        pessoaIntent.putExtra(VIEW_PESSOA, true)
        ctx.startActivity((pessoaIntent))



    }



    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = listPessoa.size }