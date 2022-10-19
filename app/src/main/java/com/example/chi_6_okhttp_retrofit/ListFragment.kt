package com.example.chi_6_okhttp_retrofit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chi_6_okhttp_retrofit.databinding.FragmentListBinding
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        //setupRecyclerView()
        return binding.root
    }


    private fun setupRecyclerView(animalsList: List<Animal>) {
        binding.animalsList.run {
            adapter = AnimalAdapter(animalsList)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            )
        }

    }
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