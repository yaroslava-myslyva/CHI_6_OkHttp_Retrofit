package com.example.chi_6_okhttp_retrofit

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chi_6_okhttp_retrofit.databinding.FragmentListBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type
import kotlin.concurrent.thread


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val url = "https://zoo-animal-api.herokuapp.com/animals/rand/10/"

    var list = mutableListOf<Animal>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thread(start = true){
            synchronized(list) {
                try {

                    val client = OkHttpClient()
                    val request: Request = Request.Builder()
                        .url(url)
                        .build()
                    val response: okhttp3.Response = client.newCall(request).execute()
                    val builder = GsonBuilder()
                    val gson = builder.create()
                    val animalListType: Type = object : TypeToken<ArrayList<Animal>?>() {}.type

                    list = gson.fromJson(response.body?.string().toString(), animalListType)
                    Log.d("ttt", "Async result - $list")
                    Handler(Looper.getMainLooper()).post {
                        binding.animalsList.run {
                            //AnimalAdapter().setItems()
                            adapter = AnimalAdapter(list)

                            layoutManager = LinearLayoutManager(context)
                            addItemDecoration(
                                DividerItemDecoration(
                                    context,
                                    LinearLayoutManager(context).orientation
                                )
                            )
                        }
                    }


                } catch (err: Error) {
                    Log.e("ttt", "Async error ${err.localizedMessage}")
                }
            }
        }




    }

//    object OkHttpHandler : AsyncTask<Any?, Any?, Any?>() {
//        private const val url = "https://zoo-animal-api.herokuapp.com/animals/rand/10/"
//        var handlerList = mutableListOf<Animal>()
//        override fun doInBackground(vararg params: Any?): Any? {
//            try {
//                val client = OkHttpClient()
//                val request: Request = Request.Builder()
//                    .url(url)
//                    .build()
//                val response: okhttp3.Response = client.newCall(request).execute()
//                val builder = GsonBuilder()
//                val gson = builder.create()
//                synchronized(handlerList) {
//
//                    val animalListType: Type = object : TypeToken<ArrayList<Animal>?>() {}.type
//
//                    handlerList = gson.fromJson(response.body?.string().toString(), animalListType)
//
//                }
//
//
//                Log.d("ttt", "Async result - $handlerList")
//            } catch (err: Error) {
//                Log.e("ttt", "Async error ${err.localizedMessage}")
//            }
//            return handlerList
//        }
//    }
}


interface AnimalsApi {
    @GET("/animals/rand/10/")
    fun getResponseItem(): Response<List<Animal>>
}

object RetrofitHelper {
    private const val baseUrl = "https://zoo-animal-api.herokuapp.com"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

//class AnimalsListViewModel : ViewModel() {
//    private val animalsApi: AnimalsApi = RetrofitHelper.getInstance().create(AnimalsApi::class.java)
//    private val _networkResponse = MutableLiveData<List<Animal>>().apply {
//        MainScope().launch {
//            value = animalsApi.getResponseItem().body()
//        }
//    }
//    val networkResponse: LiveData<List<Animal>> = _networkResponse
//}