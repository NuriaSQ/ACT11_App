package cat.itic.myapp

object CollectionsRepository {

    data class GameCollection(
        var name: String,
        var description: String,
        var games: MutableList<Videogame>
    )

    val collections = mutableListOf<GameCollection>()
}