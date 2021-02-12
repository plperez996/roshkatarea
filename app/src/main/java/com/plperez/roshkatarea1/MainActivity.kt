package com.plperez.roshkatarea1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val asuncion = findViewById<TextView>(R.id.tvAsu)
        val ciudadeste = findViewById<TextView>(R.id.tvCde)
        val villarrica = findViewById<TextView>(R.id.tvVill)
        val lomaplata = findViewById<TextView>(R.id.tvLoma)
        val encarnacion = findViewById<TextView>(R.id.tvEncar)
        val intent = Intent(this, SecondActivity::class.java)
        asuncion.setOnClickListener {
            intent.putExtra("CITY",1)
            startActivity(intent)
        }
        ciudadeste.setOnClickListener {
            intent.putExtra("CITY",2)
            startActivity(intent)
        }
        villarrica.setOnClickListener {
            intent.putExtra("CITY",3)
            startActivity(intent)
        }
        lomaplata.setOnClickListener {
            intent.putExtra("CITY",4)
            startActivity(intent)
        }
        encarnacion.setOnClickListener {
            intent.putExtra("CITY",5)
            startActivity(intent)
        }

    }
}