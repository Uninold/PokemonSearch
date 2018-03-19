package arnold.agura.com.pokedex.Adapters

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import arnold.agura.com.pokedex.MainActivity
import arnold.agura.com.pokedex.Models.EvolPokemon
import arnold.agura.com.pokedex.Models.Pokemonize
import arnold.agura.com.pokedex.R
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.poke_evolution.view.*
import kotlinx.android.synthetic.main.poke_stat.view.*
import kotlinx.android.synthetic.main.pokemon_main.view.*

/**
 * Created by Arnold on 19 Mar 2018.
 */
class PokeEvolAdapter(private val mContext: Context, var pokemonEvolList: ArrayList<EvolPokemon>): RecyclerView.Adapter<PokeEvolAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonEvolList[position]
        var pokeStatStart = 0
        holder?.itemView?.txtPokeName?.text = pokemon.name
        val pokemonImage = holder?.itemView.pokemonImage
        if(pokemon.sprites.front_default != null) {
//            Picasso.with(holder?.itemView?.context).load(pokemon.sprites.front_default).into(pokemonImage)
        }
        if(position > 0)
        {

            holder?.itemView?.txtlevel?.text = pokemon.evolDetails.minLevel.toString()
            holder?.itemView?.linearViewLevel?.visibility = VISIBLE

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.poke_evolution,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
            return pokemonEvolList.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokeImage = itemView.findViewById<ImageView>(R.id.pokemon_image)
        var cardView = itemView.findViewById<CardView>(R.id.cardViewPoke)
        val mlinear = itemView.findViewById<LinearLayout>(R.id.linearView)
    }
}