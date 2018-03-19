package arnold.agura.com.pokedex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.Toast
import arnold.agura.com.pokedex.Adapters.PokeStatAdapter
import arnold.agura.com.pokedex.Adapters.PokeTypeAdapter
import arnold.agura.com.pokedex.Adapters.PokemonAdapter
import arnold.agura.com.pokedex.Models.Pokemon
import arnold.agura.com.pokedex.Models.Pokemonize
import arnold.agura.com.pokedex.Models.Sprites
import arnold.agura.com.pokedex.R.id.progressBar
import arnold.agura.com.pokedex.R.id.recyclerView_pokemon
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.poke_details.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {
    private val EXTRA_MESSAGE = 123
    private var pokemonList = ArrayList<Pokemonize>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



            var url =  "https://pokeapi.co/api/v2/pokemon/"
        for (i in 1 ..10)
        {
            var request = okhttp3.Request.Builder().url(url+i.toString()).build()
            var client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }

                override fun onResponse(call: Call?, response: Response?) {
                    if(response !=null && response.isSuccessful)
                    {
                        val json = response.body()?.string()
                        val gson = GsonBuilder().create()
                        var pokemon =  gson.fromJson(json, Pokemonize::class.java)
                        runOnUiThread{
                            pokemonList.add(Pokemonize(pokemon.id, pokemon.name, Sprites(pokemon.sprites.front_default)))
                            recyclerView_pokemon.layoutManager = LinearLayoutManager(this@MainActivity)

                            recyclerView_pokemon.adapter = PokemonAdapter(this@MainActivity,pokemonList, this@MainActivity)


                            if(pokemonList.size > 3){
                                progressBar.visibility = View.GONE
                                txtprogress.visibility = View.GONE
                            }
                        }

                    }
                }

            })
        }
        search.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
               filter(newText.toString())
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                showPokeDetail(query)
                return false
            }

        })

    }
    private fun filter(text: String) {
        val filterdNames = ArrayList<Pokemonize>()

        for (s in pokemonList) {
            if (s.name.toLowerCase().contains(text.toLowerCase())) {
                filterdNames.add(s)
            }

            recyclerView_pokemon.adapter = PokemonAdapter(this@MainActivity, filterdNames,this@MainActivity)
        }

    }
   public fun showPokeDetail(pokename: String){

       val intent = Intent(this, PokeDetails::class.java)
       intent.putExtra("key", pokename)
        startActivityForResult(intent, EXTRA_MESSAGE)

    }

    private fun fetchPokemons(pokeid: String){

    }
}
