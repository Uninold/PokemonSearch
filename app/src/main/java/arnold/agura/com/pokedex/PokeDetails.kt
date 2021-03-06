package arnold.agura.com.pokedex

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
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
import android.support.v7.widget.DividerItemDecoration
import arnold.agura.com.pokedex.Adapters.AbilitiesAdapter


/**
 * Created by Arnold on 18 Mar 2018.
 */
class PokeDetails : AppCompatActivity() {


    private val mTag = "PokeApi"
    private var pokemon = Pokemon(0,"", Sprites(""),0,0,ArrayList(),ArrayList(), ArrayList())

    private var pokemonAbility = ArrayList<PokeAbility>()
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
                fetchPokemons(pokename)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful)
                {
                    val json = response.body()?.string()
                    val gson = GsonBuilder().create()
                    pokemon =  gson.fromJson(json, Pokemon::class.java)
                    fetchSpecies(pokemon)
                    fetchAbilities(pokemon.abilities)
                    runOnUiThread{
                        progresstxt.visibility = View.GONE
                        progress2.visibility = View.GONE
                        relativeDetail.visibility = View.VISIBLE
                        relative1.visibility = View.VISIBLE
                        relative2.visibility = View.VISIBLE
                        val oray = pokemon.name
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
                else{

                }
            }
        })
    }
    private fun fetchSpecies(pokemon: Pokemon)
    {
        val urlSpecies =  "https://pokeapi.co/api/v2/pokemon-species/${pokemon.name}/"
        val requestSpecies = okhttp3.Request.Builder().url(urlSpecies).build()
        val clientSpecies = OkHttpClient()

        clientSpecies.newCall(requestSpecies).enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {
                fetchSpecies(pokemon)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful) {
                    val json = response.body()?.string()
                    val gsonSpecies = GsonBuilder().create()
                    val species =  gsonSpecies.fromJson(json, Species::class.java)

                    fetchPokemonsEvolution(species.evolChain.url, pokemon)
                    runOnUiThread{
                      if(species.captureRate != null)
                      {
                          txtCapture.text = species.captureRate.toString()
                      }
                      if(species.habitat.name != null)
                      {
                          txtHabitat.text = species.habitat.name.substring(0,1).toUpperCase() + species.habitat.name.substring(1)
                      }
                    }
                }

            }

        })

    }
    private fun fetchAbilities(pokeAbilities: ArrayList<Abilities>)
    {
        for(i in pokeAbilities)
        {
        val urlAbility =  i.ability.url
        val requestAbility = okhttp3.Request.Builder().url(urlAbility).build()
        val clientAbility = OkHttpClient()

        clientAbility.newCall(requestAbility).enqueue(object :Callback{
            override fun onFailure(call: Call?, e: IOException?) {
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful) {
                    val json = response.body()?.string()
                    val gsonSpecies = GsonBuilder().create()
                    val abilities =  gsonSpecies.fromJson(json, PokeAbility::class.java)
                        pokemonAbility.add(abilities)
                    runOnUiThread{
                        lblAbility.visibility = View.VISIBLE
                        val adapterAbil = AbilitiesAdapter(this@PokeDetails, pokemonAbility)
                        val layoutManagerAbil = LinearLayoutManager(this@PokeDetails)

                        recyclerView_abilities.adapter = adapterAbil
                        recyclerView_abilities.layoutManager = layoutManagerAbil
                    }
                }

            }

        })
        }

    }
     fun fetchPokemonsEvolution(url: URL, pokemon: Pokemon){
         var pokemonEvolution = ArrayList<EvolPokemon>()
        val urlevo =  url.toString()
        val requestevo = okhttp3.Request.Builder().url(urlevo).build()
        val clientevo = OkHttpClient()

        clientevo.newCall(requestevo).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
               fetchPokemonsEvolution(url, pokemon)
            }

            override fun onResponse(call: Call?, response: Response?) {
                if(response !=null && response.isSuccessful)
                {
                    val json = response.body()?.string()
                    val gsonevo = GsonBuilder().create()
                    val pokevolution =  gsonevo.fromJson(json, Evolve::class.java)
                    var pokemonEvo = EvolPokemon(pokevolution.chain.species.name, EvolDetails(0))
                    pokemonEvolution.add(pokemonEvo)
                    for( i in 0.. pokevolution.chain.evolveTo.size-1){
                        if(pokevolution.chain.evolveTo.get(i)!=null){
                            var pokename = pokevolution.chain.evolveTo.get(i).species.name
                            var pokelevel = pokevolution.chain.evolveTo.get(i).evolDetails.get(0).minLevel
                            var pokemonfirst = EvolPokemon(pokename,EvolDetails(pokelevel))
                            pokemonEvolution.add(pokemonfirst)
                            for( j in 0.. pokevolution.chain.evolveTo.get(i).evolveTo.size-1){
                                if(pokevolution.chain.evolveTo.get(i).evolveTo.get(j)!=null){
                                    var pokename = pokevolution.chain.evolveTo.get(i).evolveTo.get(j).species.name
                                    var pokelevel = pokevolution.chain.evolveTo.get(i).evolveTo.get(j).evolDetails.get(0).minLevel
                                    var pokemonsecond = EvolPokemon(pokename,EvolDetails(pokelevel))
                                    pokemonEvolution.add(pokemonsecond)
                                }
                            }
                        }
                    }


                    fetchSprite(pokemonEvolution)
                    runOnUiThread{

                        }
                    }
                }
        })

    }
     fun fetchSprite(pokemonEvol : ArrayList<EvolPokemon>) {
         var pokemonEvolSprite = ArrayList<EvolPokemonSprite>()
         for (i in pokemonEvol) {

             val url = "https://pokeapi.co/api/v2/pokemon/${i.name}/"
             val request = okhttp3.Request.Builder().url(url).build()
             val client = OkHttpClient()
             client.newCall(request).enqueue(object : Callback {
                 override fun onFailure(call: Call?, e: IOException?) {
                     fetchSprite(pokemonEvol)
                 }

                 override fun onResponse(call: Call?, response: Response?) {
                     if (response != null && response.isSuccessful) {
                         val json = response.body()?.string()
                         val gson = GsonBuilder().create()
                         val pokemonSprite = gson.fromJson(json, Pokemon::class.java)
                         pokemonEvolSprite.add(EvolPokemonSprite(i.name, Sprites(pokemonSprite.sprites.front_default), i.evolDetails))
                         val sortedList = ArrayList(pokemonEvolSprite.sortedWith(compareBy({ it.evolDetails.minLevel })))
                         runOnUiThread {
                             println(pokemonEvolSprite + " orayt")
                                 val adapter = PokeEvolAdapter(this@PokeDetails, sortedList)
                                 var layoutManager = GridLayoutManager(this@PokeDetails, 3)
                                 recyclerviewEvol.adapter = adapter
                                 recyclerviewEvol.layoutManager = layoutManager

                         }
                     }
                 }
             })
         }
     }
}