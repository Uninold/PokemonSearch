package arnold.agura.com.pokedex.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import arnold.agura.com.pokedex.Models.Stats
import arnold.agura.com.pokedex.R
import kotlinx.android.synthetic.main.poke_stat.view.*
import org.jetbrains.anko.doAsync


/**
 * Created by Arnold on 18 Mar 2018.
 */
class PokeStatAdapter(private val mContext: Context, private val pokeStats: ArrayList<Stats>) : RecyclerView.Adapter<PokeStatAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.poke_stat,parent,false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokeStats.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokeStat = pokeStats[position]
        var pokeStatStart = 0
        holder?.itemView?.txtBaseStat?.text = pokeStat.baseStat.toString()
        holder?.itemView?.txtTypeName?.text = pokeStat.stat.name.substring(0,1).toUpperCase() + pokeStat.stat.name.substring(1)

        doAsync {
            while (pokeStatStart <= pokeStat.baseStat){
                holder?.itemView?.progressStat?.progress = pokeStatStart
                pokeStatStart++
                android.os.SystemClock.sleep(50)
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}