package arnold.agura.com.pokedex.Models

import com.google.gson.annotations.SerializedName
import java.net.URL
import java.net.URLDecoder

/**
 * Created by Arnold on 18 Mar 2018.
 */
data class Pokemonize (
        val id: Int,
        val name: String,
        val sprites: Sprites
)
data class Pokemon (
        var id: Int,
        var name: String,
        var sprites: Sprites,
        var weight: Int,
        var height: Int,
        var types : ArrayList<Types>,
        var stats : ArrayList<Stats>
)
data class Stats(
        var stat: Stat,
        @SerializedName("base_stat")
        var baseStat: Int
)
data class  Stat(
        var name: String
)
data class Types(
        var type: Type
)
data class Type(
        var name: String
)
data class Sprites(
        var front_default: String
)
data class Species(
        @SerializedName("capture_rate")
        var captureRate: Int,
        var habitat : Habitat,
        @SerializedName("evolution_chain")
        var evolChain: EvolChain
)
data class EvolChain(
        var url : URL
)
data class Habitat(
        var name : String
)
data class Evolve(
        var chain: Chain
)
data class Chain(@SerializedName("evolves_to")
                 var evolveTo: ArrayList<EvolveTo>,
                 var species: SpeciesEvo
)
data class EvolveTo (
        var species: SpeciesEvo,@SerializedName("evolves_to")
        var evolveTo: ArrayList<EvolveTo>,
        @SerializedName("evolution_details")
        var evolDetails : ArrayList<EvolDetails>
)
data class EvolDetails(
        @SerializedName("min_level")
        var minLevel: Int
)
data class SpeciesEvo(
        var name: String
)
data class EvolPokemon(
        var name: String,
        var sprites: Sprites,
        var evolDetails: EvolDetails
)