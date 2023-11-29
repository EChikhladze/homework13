package com.example.homework13

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.ItemFieldBinding
import com.squareup.picasso.Picasso

class FieldRecyclerAdapter(private val fields: List<Field>) :
    RecyclerView.Adapter<FieldRecyclerAdapter.FieldViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        return FieldViewHolder(
            ItemFieldBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = fields.size

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class FieldViewHolder(private val binding: ItemFieldBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val field = fields[position]
            if (field.isActive) {
                with(binding) {
                    edField.hint = field.hint
                    edField.inputType = when (field.keyboard) {
                        "number" -> InputType.TYPE_CLASS_NUMBER
                        else -> InputType.TYPE_CLASS_TEXT
                    }
                    Picasso.get().load(field.icon).into(imgIcon)
                }
                listeners(field)
            }

        }

        private fun listeners(field: Field) {
            binding.edField.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    field.input = s.toString()
                }

            })
        }
    }
}