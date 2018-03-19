package arnold.agura.com.pokedex

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import arnold.agura.com.pokedex.Adapters.PokeEvolAdapter
import arnold.agura.com.pokedex.Adapters.PokeStatAdapter
import arnold.agura.com.pokedex.Adapters.PokeTypeAdapter
import arnold.agura.com.pokedex.Models.*
import arnold.agura.com.pokedex.R.attr.layoutManager
import arnold.agura.com.pokedex.R.id.*
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.poke_details.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Arnold on 18 Mar 2018.
 */
class PokeDetails : AppCompatActivity() {


    private val mTag = "PokeApi"
    private var pokemon = Pokemon(0,"", Sprites(""),0,0,ArrayList(),ArrayList())
    private var pokemonEvolution = ArrayList<EvolPokemon>()
    private var pokemonSprite =Pokemon(0,"", Sprites(""),0,0,ArrayList(),ArrayList())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poke_details)
        var pokename = intent.getStringExtra("key")

        fetchPokemons(pokename)





    }
    private fun fetchPokemons(pokename: String){
        val url =  "https://pokeapi.co/api/v2/pokemon/$pokename/"
        val request = okhttp3.Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e(mTag,"orayt wa ni sod ", e)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful)
                {
                    val json = response.body()?.string()
                    val gson = GsonBuilder().create()
                    pokemon =  gson.fromJson(json, Pokemon::class.java)
                    fetchSpecies(pokemon.name)
                    runOnUiThread{
                        val oray = pokemon.name
                        println(pokemon.toString())
                        txtPokeName.text = pokemon.name.substring(0,1).toUpperCase() + pokemon.name.substring(1)
                        txtHeight.text = pokemon.height.toString()
                        txtWeight.text = pokemon.weight.toString()
                        txtPokeId.text =  String.format("#"+"%03d", pokemon.id)
                        Glide.with(this@PokeDetails).load(pokemon.sprites.front_default).into(pokeImg)

                        val adapter = PokeTypeAdapter(this@PokeDetails, pokemon.types)
                        if(pokemon.types.size ==1) {
                            val layoutManager = GridLayoutManager(this@PokeDetails, 1)

                            recyclerview.adapter = adapter
                            recyclerview.layoutManager = layoutManager
                        }
                        if(pokemon.types.size ==2) {
                            val layoutManager = GridLayoutManager(this@PokeDetails, 2)

                            recyclerview.adapter = adapter
                            recyclerview.layoutManager = layoutManager
                        }
                        if(pokemon.types.size >2) {
                            val layoutManager = GridLayoutManager(this@PokeDetails, 3)

                            recyclerview.adapter = adapter
                            recyclerview.layoutManager = layoutManager
                        }
                        Collections.reverse((pokemon.stats))
                        val adapterStat = PokeStatAdapter(this@PokeDetails, pokemon.stats)
                        val layoutManagerStat = LinearLayoutManager(this@PokeDetails)

                        recyclerviewStat.adapter = adapterStat
                        recyclerviewStat.layoutManager = layoutManagerStat


                    }

                }
            }

        })
    }
    private fun fetchSpecies(pokename: String)
    {
        val urlSpecies =  "https://pokeapi.co/api/v2/pokemon-species/$pokename/"
        val requestSpecies = okhttp3.Request.Builder().url(urlSpecies).build()
        val clientSpecies = OkHttpClient()

        clientSpecies.newCall(requestSpecies).enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful) {
                    val json = response.body()?.string()
                    val gsonSpecies = GsonBuilder().create()
                    val species =  gsonSpecies.fromJson(json, Species::class.java)

                    fetchPokemonsEvolution(species.evolChain.url)
                    runOnUiThread{
                      txtCapture.text = species.captureRate.toString()
                        txtHabitat.text = species.habitat.name
                    }
                }

            }

        })

    }
     fun fetchPokemonsEvolution(url: URL){
        val urlevo =  url.toString()
        val requestevo = okhttp3.Request.Builder().url(urlevo).build()
        val clientevo = OkHttpClient()

        clientevo.newCall(requestevo).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e(mTag,"orayt wa ni sod sud ika duha ", e)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful)
                {
                    val json = response.body()?.string()
                    val gsonevo = GsonBuilder().create()
                    val pokevolution =  gsonevo.fromJson(json, Evolve::class.java)
                    fetchSprite(pokevolution.chain.species.name)
                    var pokemon = EvolPokemon(pokevolution.chain.species.name, Sprites(pokemonSprite.sprites.front_default),EvolDetails(0))
                    pokemonEvolution.add(pokemon)
                        var pokename = pokevolution.chain.evolveTo.get(0).species.name
                          fetchSprite(pokevolution.chain.evolveTo.get(0).species.name)
                        var pokelevel = pokevolution.chain.evolveTo.get(0).evolDetails.get(0).minLevel
                        var pokemonfirst = EvolPokemon(pokename, Sprites(pokemonSprite.sprites.front_default),EvolDetails(pokelevel))
                        pokemonEvolution.add(pokemonfirst)



                    runOnUiThread{
                        val adapter = PokeEvolAdapter(this@PokeDetails, pokemonEvolution)
                        if(pokemonEvolution.size>1) {
                            var layoutManager = GridLayoutManager(this@PokeDetails, 3)
                            println(pokemonSprite.sprites.front_default)
                            println(pokemonEvolution)
                            recyclerviewEvol.adapter = adapter
                            recyclerviewEvol.layoutManager = layoutManager
                        }
                    }
                }
            }

        })

    }
     fun fetchSprite(pokename:String){
        val url =  "https://pokeapi.co/api/v2/pokemon/$pokename/"
        val request = okhttp3.Request.Builder().url(url).build()
        val client = OkHttpClient()
        var pokeSprite = Pokemon(0,"", Sprites(""),0,0,ArrayList(),ArrayList())
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.e(mTag,"orayt wa ni sod ", e)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful)
                {
                    val json = response.body()?.string()
                    val gson = GsonBuilder().create()
                    pokemonSprite =  gson.fromJson(json, Pokemon::class.java)

                    runOnUiThread{
                     println(pokemonSprite)
                    }
                }
            }

        })
    }
}