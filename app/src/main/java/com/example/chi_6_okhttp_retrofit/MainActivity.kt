package com.example.chi_6_okhttp_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

//ДЗ
//baseUrl - "https://zoo-animal-api.herokuapp.com/animals/rand/"
//
//1) Создать свой проект с экраном (активность+фрагмент), на котором будет recyclerView
//2) Сделать реквест через чистый OkHttp на один эндпоинт (1-10)
//3) Сделать реквест через retrofit на другой эндпоинт (1-10) с пробросом параметра через аннотацию
//5) Соединить результаты (списки) в один
//6) Вывести в ресайклер эти два списка в виде карточек с данными
//
//Запускать реквесты сразу, как открывается фрагмент и запускать их последовательно в одном потоке

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}