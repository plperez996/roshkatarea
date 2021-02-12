package com.plperez.roshkatarea1


import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.URL

class SecondActivity : AppCompatActivity() {

    var torigin: Int = 0
    var tmaximo: Int = 0
    var tminimo: Int = 0
    var sensterm: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        weatherTask().execute()
        val celsius = findViewById<Button>(R.id.btmC)
        val kelvin = findViewById<Button>(R.id.btmK)
        val farenh = findViewById<Button>(R.id.btmF)
        celsius.setOnClickListener {
            var numero = torigin -273
            findViewById<TextView>(R.id.temp).text = "$numero°C"
            numero = tmaximo -273
            findViewById<TextView>(R.id.temp_max).text = "Max Temp: $numero°C"
            numero = tminimo -273
            findViewById<TextView>(R.id.temp_min).text = "Min Temp: $numero°C"
            numero = sensterm -273
            findViewById<TextView>(R.id.feels).text = "Sens Term: $numero°C"
        }
        kelvin.setOnClickListener {
            findViewById<TextView>(R.id.temp).text = "${torigin} K"
            findViewById<TextView>(R.id.temp_max).text = "Max Temp: ${tmaximo} K"
            findViewById<TextView>(R.id.temp_min).text = "Min Temp: ${tminimo} K"
            findViewById<TextView>(R.id.feels).text = "Sens Term: ${sensterm} K"
        }
        farenh.setOnClickListener {
            var numero = ((torigin -273) * 9/5) + 32
            findViewById<TextView>(R.id.temp).text = "$numero°F"
            numero = ((tmaximo -273) * 9/5) + 32
            findViewById<TextView>(R.id.temp_max).text = "Max Temp: $numero°F"
            numero = ((tminimo -273) * 9/5) + 32
            findViewById<TextView>(R.id.temp_min).text = "Min Temp: $numero°F"
            numero = ((sensterm -273) * 9/5) + 32
            findViewById<TextView>(R.id.feels).text = "Sens Term: $numero°F"
        }
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            lateinit var CITY: String
            val API: String = "c07a6223744db62173f902eb93af13e3" // Use API key
            val bundle = intent.extras
            val valor = bundle?.get("CITY").toString()
            if(valor == "1"){
                CITY =  "asuncion"
            }else if(valor == "2"){
                CITY =  "ciudad+del+este"
            }else if(valor == "3"){
                CITY =  "villarrica"
            }else if(valor == "4"){
                CITY =  "loma+plata"
            }else if(valor == "5"){
                CITY =  "encarnacion"
            }
            var response:String?
            try{
                response = URL("http://api.openweathermap.org/data/2.5/weather?q=${CITY}&appid=${API}").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")

                val temp1 = main.getInt("temp")
                val temp = "$temp1 K"
                val tempMin1 =  main.getInt("temp_min")
                val tempMin = "Min Temp: $tempMin1 K"
                val tempMax1 = main.getInt("temp_max")
                val tempMax = "Max Temp: $tempMax1 K"
                val feelslike1 = main.getInt("feels_like")
                val feelslike = "Sens Term: $feelslike1 K"

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.feels).text = feelslike
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                torigin = temp1
                tmaximo = tempMax1
                tminimo = tempMin1
                sensterm = feelslike1

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}