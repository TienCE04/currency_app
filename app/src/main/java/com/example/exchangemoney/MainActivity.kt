package com.example.exchangemoney

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.example.exchangemoney.databinding.ActivityMainBinding
import kotlin.toString


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val hintVND = HtmlCompat.fromHtml("<u><small>đ</small></u> <big><b>0</b></big>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val hintUSD = HtmlCompat.fromHtml("<small>$</small> <big><b>0</b></big>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val hintJPY = HtmlCompat.fromHtml("<small>¥</small> <big><b>0</b></big>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val hintCNY = HtmlCompat.fromHtml("<small>NTD</small> <big><b>0</b></big>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val hintGBP = HtmlCompat.fromHtml("<small>£</small> <big><b>0</b></big>", HtmlCompat.FROM_HTML_MODE_LEGACY)
    private val exchangeRates = mapOf(
        "Vietnam - Dong" to 1.0,
        "United States - Dollar" to 24000.0,
        "Japan - Yen" to 180.0,
        "China - Yuan" to 3500.0,
        "United Kingdom - Pound" to 30000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moneyList=resources.getStringArray(R.array.money1)
        val adapter= ArrayAdapter(
            this,
            R.layout.custom_spinner_item,
            moneyList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spiner1.adapter=adapter
        binding.spiner2.adapter=adapter
        binding.money1.hint = hintVND
        binding.money2.hint = hintVND
        setupSpinnerListeners()
        val menhgia="1 ¥ = 180 VND\n"+
                "1 NDT = 3500 VND\n"+
                "1 USD = 24000 VND\n" +
                "1 £ = 30000 VND\n"+
                "1 NDT = 19.4444 ¥\n"+
                "1 USD = 133.3333 ¥\n"+
                "1 £ = 166.6667 ¥\n"+
                "1 USD = 6.8571 NDT\n"+
                "1 £ = 8.5714 NDT\n"+
                "1 £ = 1.25 USD"
        binding.menhgia.text=menhgia
        binding.doitien.setOnClickListener(this)

    }
    private fun setupSpinnerListeners(){
        binding.spiner1.onItemSelectedListener=createOnItemSelectedListener("Spinner 1")
        binding.spiner2.onItemSelectedListener=createOnItemSelectedListener("Spinner 2")
    }

    private fun createOnItemSelectedListener(spinnerName: String): AdapterView.OnItemSelectedListener{
        return object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectCurrent=parent?.getItemAtPosition(position).toString()
                if(spinnerName=="Spinner 1"){
                    when(selectCurrent){
                        "Vietnam - Dong" ->{
                            val tien1=binding.money1.text.toString()
                            if(tien1.isEmpty()){
                                binding.money1.hint=hintVND
                            }
                        }
                        "United States - Dollar" ->{
                            val tien1=binding.money1.text.toString()
                            if(tien1.isEmpty()){
                                binding.money1.hint=hintUSD
                            }
                        }
                        "Japan - Yen" ->{
                            val tien1=binding.money1.text.toString()
                            if(tien1.isEmpty()){
                                binding.money1.hint=hintJPY
                            }
                        }
                        "China - Yuan" ->{
                            val tien1=binding.money1.text.toString()
                            if(tien1.isEmpty()){
                                binding.money1.hint=hintCNY
                            }
                        }
                        "United Kingdom - Pound" ->{
                            val tien1=binding.money1.text.toString()
                            if(tien1.isEmpty()){
                                binding.money1.hint=hintGBP
                            }
                        }
                    }
                }
                else{
                    when(selectCurrent){
                        "Vietnam - Dong" ->{
                            val tien2=binding.money2.text.toString()
                            if(tien2.isEmpty()){
                                binding.money2.hint=hintVND
                            }
                        }
                        "United States - Dollar" ->{
                            val tien2=binding.money2.text.toString()
                            if(tien2.isEmpty()){
                                binding.money2.hint=hintUSD
                            }
                        }
                        "Japan - Yen" ->{
                            val tien2=binding.money2.text.toString()
                            if(tien2.isEmpty()){
                                binding.money2.hint=hintJPY
                            }
                        }
                        "China - Yuan" ->{
                            val tien2=binding.money2.text.toString()
                            if(tien2.isEmpty()){
                                binding.money2.hint=hintCNY
                            }
                        }
                        "United Kingdom - Pound" ->{
                            val tien2=binding.money2.text.toString()
                            if(tien2.isEmpty()){
                                binding.money2.hint=hintGBP
                            }
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.doitien -> {
                val item1 = binding.spiner1.selectedItem.toString()
                val item2 = binding.spiner2.selectedItem.toString()
                val tien1 = binding.money1.text.toString()
                val tien2 = binding.money2.text.toString()

                if (tien1.isEmpty() && tien2.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show()
                    binding.money1.requestFocus()
                    return
                }

                val rate1 = exchangeRates[item1] ?: 1.0
                val rate2 = exchangeRates[item2] ?: 1.0

                if (binding.money1.hasFocus()) {  // Nếu con trỏ chuột đang ở money1
                    if (tien1.isNotEmpty()) {
                        val kq = (tien1.toDouble() * rate1) / rate2
                        binding.money2.setText(formatResult(kq))
                    } else {
                        Toast.makeText(this, "Vui lòng nhập dữ liệu vào tiền 1", Toast.LENGTH_SHORT).show()
                    }
                }
                else if (binding.money2.hasFocus()) {  // Nếu con trỏ chuột đang ở money2
                    if (tien2.isNotEmpty()) {
                        val kq = (tien2.toDouble() * rate2) / rate1
                        binding.money1.setText(formatResult(kq))
                    } else {
                        Toast.makeText(this, "Vui lòng nhập dữ liệu vào tiền 2", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // Hàm định dạng kết quả
    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) { // Nếu là số nguyên
            value.toInt().toString()  // Chuyển thành Int và trả về chuỗi
        } else {
            String.format("%.4f", value)
        }
    }
}