package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.model.BeerDto
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import java.util.*

interface BeerClient {
    fun findBeerById(id: UUID, showInventoryOnHand: Boolean): Mono<BeerDto>;

    fun listBeers(
        pageNumber: Int?,
        pageSize: Int?,
        beerName: String?,
        beerStyle: String?,
        showInventoryOnHand: Boolean?
    ): Mono<BeerPagedList?>;

    fun createBeer(beerDto: BeerDto): Mono<ResponseEntity<BeerDto>>;

    fun updateBeer(beerDto: BeerDto): Mono<ResponseEntity<BeerDto>>;

    fun deleteBeerById(id: UUID): Mono<ResponseEntity<Void>>;

    fun getBeerByUpc(upc: String): Mono<BeerDto>;
}