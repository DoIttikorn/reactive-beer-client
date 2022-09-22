package dodo.springframework.reactivebeerclient.model

import java.time.OffsetDateTime
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Null

data class BeerDto(
    @Null
    private val id: UUID,
    @NotBlank
    private val beerName: String,
    @NotBlank
    private val beerStyle: String,
    private val upc: Long,
    private val price: Double,
    private val quantityOnHand: Int,
    private val createdDate: OffsetDateTime,
    private val lastModifiedDate: OffsetDateTime?
    )
