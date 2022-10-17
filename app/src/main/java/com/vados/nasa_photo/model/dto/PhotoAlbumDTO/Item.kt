package molchanov.hammertesttask.model.dto

data class Item(
    val `data`: List<Data>,
    val href: String,
    val links: List<Link>
)