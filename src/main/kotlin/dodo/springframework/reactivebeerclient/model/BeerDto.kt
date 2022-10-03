package dodo.springframework.reactivebeerclient.model

import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null

data class BeerDto(
    @Null val id: UUID,
    @NotBlank
    val beerName: String,
    @NotBlank
    val beerStyle: String,
    val upc: String,
    val price: Double,
    val quantityOnHand: Int,
    val createdDate: OffsetDateTime,
    val lastModifiedDate: OffsetDateTime?
)
