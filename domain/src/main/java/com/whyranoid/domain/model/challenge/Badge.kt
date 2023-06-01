package com.whyranoid.domain.model.challenge

data class Badge(
    val id: Long,
    val name: String,
    val imageUrl: String,
) {
    companion object {
        val DUMMY = Badge(
            id = 0L,
            name = "badgeName",
            imageUrl = "https://picsum.photos/250/250",
        )

        val DUMMY_LIST = listOf(
            Badge(
                id = 0L,
                name = "badgeName1",
                imageUrl = "https://picsum.photos/250/250",
            ),
            Badge(
                id = 1L,
                name = "badgeName2",
                imageUrl = "https://picsum.photos/250/250",
            ),
            Badge(
                id = 2L,
                name = "badgeName3",
                imageUrl = "https://picsum.photos/250/250",
            ),
        )
    }
}
