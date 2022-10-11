package dodo.springframework.reactivebeerclient.model

import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null

data class BeerDto(
    @Null val id: UUID?,
    @NotBlank
    var beerName: String,
    @NotBlank
    var beerStyle: String,
    var upc: String,
    var price: Double,
    var quantityOnHand: Int,
    var createdDate: OffsetDateTime?,
    var lastModifiedDate: OffsetDateTime?
) {

    constructor(beerName: String, beerStyle: String, upc: String, price: Double, quantityOnHand: Int) : this(
        null,
        beerName,
        beerStyle,
        upc,
        price,
        quantityOnHand,
        null,
        null
    )
}
