package edu.uw.ischool.wtom002.tipcalc

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var serviceChargeEditText: EditText
    private lateinit var tipButton: Button
    private lateinit var tipPercentageSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceChargeEditText = findViewById(R.id.serviceChargeEditText)
        tipButton = findViewById(R.id.tipButton)
        tipPercentageSpinner = findViewById(R.id.tipPercentageSpinner)

        val adapter = ArrayAdapter.createFromResource(this, R.array.tip_percentages, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tipPercentageSpinner.adapter = adapter

        serviceChargeEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tipButton.isEnabled = isValidAmount(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        tipPercentageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                calculateTip()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        tipButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun isValidAmount(input: String): Boolean {
        return input.matches(Regex("^\\d+(\\.\\d{2})?$"))
    }

    private fun calculateTip() {
        val serviceCharge = serviceChargeEditText.text.toString()
        val amount = serviceCharge.toDoubleOrNull() ?: 0.0

        val tipPercentage = tipPercentageSpinner.selectedItem.toString().toInt()
        val tip = (amount * tipPercentage) / 100.0

        val formattedTip = DecimalFormat("$0.00").format(tip)
        Toast.makeText(this, "Tip: $formattedTip", Toast.LENGTH_SHORT).show()
    }
}

