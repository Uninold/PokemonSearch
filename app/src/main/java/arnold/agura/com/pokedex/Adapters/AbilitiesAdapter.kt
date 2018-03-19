package arnold.agura.com.pokedex.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import arnold.agura.com.pokedex.Models.Abilities
import arnold.agura.com.pokedex.Models.EvolPokemon
import arnold.agura.com.pokedex.Models.PokeAbility
import arnold.agura.com.pokedex.R
import kotlinx.android.synthetic.main.poke_abilities.view.*
import kotlinx.android.synthetic.main.poke_evolution.view.*

/**
 * Created by Arnold on 19 Mar 2018.
 */
class AbilitiesAdapter(private val mContext: Context, var pokemonAbilityList: ArrayList<PokeAbility>): RecyclerView.Adapter<PokeEvolAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeEvolAdapter.ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.poke_abilities,parent,false)
        return PokeEvolAdapter.ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonAbilityList.size
    }

    override fun onBindViewHolder(holder: PokeEvolAdapter.ViewHolder, position: Int) {
        val pokemon = pokemonAbilityList[position]
        var pokeStatStart = 0
        holder?.itemView?.txtability_name?.text = pokemon.name.substring(0,1).toUpperCase() + pokemon.name.substring(1)
        holder?.itemView?.txtability_desc?.text = pokemon.effect.get(0).effect

        val pokemonImage = holder?.itemView.pokemonImage
    }


}