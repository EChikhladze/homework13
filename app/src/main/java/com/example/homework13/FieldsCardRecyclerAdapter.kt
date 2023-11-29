package com.example.homework13

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.ItemFieldsCardBinding

class FieldsCardRecyclerAdapter(private val cards: List<List<Field>>) :
    RecyclerView.Adapter<FieldsCardRecyclerAdapter.FieldsCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldsCardViewHolder {
        return FieldsCardViewHolder(
            ItemFieldsCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = cards.size

    override fun onBindViewHolder(holder: FieldsCardViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class FieldsCardViewHolder(private val binding: ItemFieldsCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val card = cards[position]
            val fieldAdapter = FieldRecyclerAdapter(card)
            with(binding.recyclerField) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                adapter = fieldAdapter
            }
        }
    }
}