package com.example.test_task_innovecs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.test_task_innovecs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.cities)
        val adapter = ArrayAdapter(baseContext, R.layout.city_list_item, items)
        binding.cityChooserAuto.setAdapter(adapter)

        val viewModel =  ViewModelProvider(this).get(MainViewModel::class.java)
        
    }
}