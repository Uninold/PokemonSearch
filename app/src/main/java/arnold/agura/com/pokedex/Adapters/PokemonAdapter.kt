package arnold.agura.com.pokedex.Adapters

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.ContextCompat.startForegroundService
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import arnold.agura.com.pokedex.MainActivity
import arnold.agura.com.pokedex.Models.Pokemonize
import arnold.agura.com.pokedex.PokeDetails
import arnold.agura.com.pokedex.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.pokemon_main.view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by Arnold on 18 Mar 2018.
 */
class PokemonAdapter(private val mContext: Context, var pokemonList: ArrayList<Pokemonize>,val ma: MainActivity): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.pokemon_main, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon: Pokemonize = pokemonList[position]
        holder?.itemView?.pokemon_name?.text = pokemon.name.substring(0,1).toUpperCase() + pokemon.name.substring(1)
        val pokemonImage = holder?.itemView.pokemon_image
        Glide.with(holder?.itemView.context).load(pokemon.sprites.front_default).into(pokemonImage)

        holder?.itemView?.cardViewPoke?.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                        ma.showPokeDetail(pokemon.name.toString())
                Toast.makeText(mContext,"atay",Toast.LENGTH_LONG).show()
            }

        })


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokeImage = itemView.findViewById<ImageView>(R.id.pokemon_image)
        var cardView = itemView.findViewById<CardView>(R.id.cardViewPoke)
        val mlinear = itemView.findViewById<LinearLayout>(R.id.linearView)
    }


    override fun getItemCount(): Int {
        return pokemonList.size
    }

    public fun filterList(filterdNames: ArrayList<Pokemonize>) {
        pokemonList = filterdNames
        notifyDataSetChanged()
    }
}