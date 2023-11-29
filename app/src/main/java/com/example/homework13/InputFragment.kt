package com.example.homework13

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework13.databinding.FragmentInputBinding
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class InputFragment : Fragment() {
    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!
    private val fileName = "fields.json"
    private lateinit var cardAdapter: FieldsCardRecyclerAdapter
    private val allFields = mutableListOf<Field>()
    private val userInput = mutableMapOf<Int, String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
    }

    private fun setUp() {
        createFields()
        listeners()
    }

    private fun createFields() {
        val fieldLists = jsonToData()
        setUpRecycler(fieldLists)
    }

    private fun jsonToData(): List<List<Field>> {
        val jsonString =
            context?.assets?.open(fileName)?.bufferedReader().use { it?.readText() ?: "" }
        val fieldListType = object : TypeToken<List<List<Field>>>() {}.type
        val cards: List<List<Field>> = Gson().fromJson(jsonString, fieldListType)

        for (card in cards) {
            allFields.addAll(card)
        }

        Log.d("jsonparsed", cards.toString())
        return cards
    }

    private fun setUpRecycler(fieldLists: List<List<Field>>) {
        cardAdapter = FieldsCardRecyclerAdapter(fieldLists)
        with(binding.recyclerFieldsCard) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
            adapter = cardAdapter
        }
    }

    private fun listeners() {
        binding.fabRegister.setOnClickListener {
            if (requiredFieldsAreFilled()) {
                userInput.clear()

                for (field in allFields) {
                    userInput[field.id] = field.input ?: ""
                }

            }
            Log.d("userInput", "userInput: $userInput")
        }
    }

    private fun requiredFieldsAreFilled(): Boolean {
        for (field in allFields) {
            if (field.required && field.input == null || field.input == "") {
                Snackbar.make(binding.root, "${field.hint} is required", Snackbar.LENGTH_SHORT)
                    .show()
                return false
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}