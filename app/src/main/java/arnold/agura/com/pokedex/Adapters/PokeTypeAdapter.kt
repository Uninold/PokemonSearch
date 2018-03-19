package arnold.agura.com.pokedex.Adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import arnold.agura.com.pokedex.Models.Types
import arnold.agura.com.pokedex.R
import kotlinx.android.synthetic.main.poke_type.view.*

/**
 * Created by Arnold on 18 Mar 2018.
 */
class PokeTypeAdapter (private val mContext: Context, private val types : ArrayList<Types>) : RecyclerView.Adapter<PokeTypeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.poke_type, parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return types.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokeType = types[position]
        var color = pokeType.type.name.toString()

        when(pokeType.type.name.toString()) {
            "normal" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.normal))
            "fighting" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.fighting))
            "flying" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.flying))
            "poison" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.poison))
            "ground" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.ground))
            "rock" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.rock))
            "bug" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.bug))
            "ghost" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.ghost))
            "steel" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.steel))
            "fire" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.fire))
            "water" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.water))
            "grass" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.grass))
            "electric" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.electric))
            "psychic" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.psychic))
            "ice" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.ice))
            "dragon" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.dragon))
            "dark" -> holder?.itemView?.txtType?.setTextColor(ContextCompat.getColor(mContext, R.color.dark))


        }


        holder?.itemView?.txtType?.text = pokeType.type.name
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

}