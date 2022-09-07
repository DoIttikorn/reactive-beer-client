package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.model.BeerDto
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import java.util.*

class BeerClientImpl : BeerClient {
    override fun findBeerById(id: UUID, showInventoryOnHand: Boolean): Mono<BeerDto> {
        TODO("Not yet implemented")
    }

    override fun listBeers(
        pageNumber: Int,
        pageSize: Int,
        beerName: String,
        beerStyle: String,
        showInventoryOnHand: Boolean
    ): Mono<BeerPagedList> {
        TODO("Not yet implemented")
    }

    override fun createBeer(beerDto: BeerDto): Mono<ResponseEntity<BeerDto>> {
        TODO("Not yet implemented")
    }

    override fun updateBeer(beerDto: BeerDto): Mono<ResponseEntity<BeerDto>> {
        TODO("Not yet implemented")
    }

    override fun deleteBeerById(id: UUID): Mono<ResponseEntity<Void>> {
        TODO("Not yet implemented")
    }

    override fun getBeerByUpc(upc: String): Mono<BeerDto> {
        TODO("Not yet implemented")
    }
}