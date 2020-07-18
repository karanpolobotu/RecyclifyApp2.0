package com.example.recyclifyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recyclifyapp.model.State
import com.example.recyclifyapp.network.ApiHelper
import com.example.recyclifyapp.network.RetrofitBuilder
import com.example.recyclifyapp.utils.ViewModelFactory
import com.example.recyclifyapp.viewmodel.ProductViewModel
import com.google.zxing.integration.android.IntentIntegrator

//implementing onclicklistener
class MainActivity : AppCompatActivity(), View.OnClickListener {
    //View Objects
    private lateinit var buttonScan: Button
    private lateinit var textViewScannedText: TextView
    private lateinit var textViewAddress: TextView
    var abc = 0

    //qr code scanner object
    private var qrScan: IntentIntegrator? = null
    lateinit var productViewModel: ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //View objects
        buttonScan = findViewById(R.id.buttonScan)
        textViewScannedText = findViewById(R.id.textViewScannedText)

        //intializing scan object
        qrScan = IntentIntegrator(this)
        abc = 10
        //attaching onclick listener
        buttonScan.setOnClickListener(this)
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {

        productViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ProductViewModel::class.java)
        //productViewModel.getProduct("016229008611")
    }

    private fun setupObservers() {
        productViewModel.productLiveData.observe(this, Observer {
            it?.let { state ->
                when (state) {
                    is State.Loading -> {
                        Log.e("response", "loading")
                    }
                    is State.Success -> {
                        Log.e("response", state.data.toString())

                        textViewScannedText.text = state.data.product.categories
                    }
                    is State.Error -> {
                        Log.e("response", state.message)
                    }
                }
            }
        })
    }

    //Getting the scan results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            //if qrcode has nothing in it
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show()
            } else {
                //if qr contains data
//                try {
                //converting the data to json
//                    JSONObject obj = new JSONObject(result.getContents());
                //setting values to textviews
                textViewScannedText!!.text = "Scanned Barcode: " + result.contents

                productViewModel.getProduct(result.contents)
                /* } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }*/
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onClick(view: View) {
        //initiating the qr code scan
        qrScan!!.initiateScan()
    }
}