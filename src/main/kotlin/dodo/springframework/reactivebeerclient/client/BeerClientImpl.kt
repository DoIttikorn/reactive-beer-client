package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.config.WebClientProperties
import dodo.springframework.reactivebeerclient.model.BeerDto
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.util.*

@RequiredArgsConstructor
@Service
class BeerClientImpl(val webClient: WebClient) : BeerClient {

    override fun findBeerById(id: UUID, showInventoryOnHand: Boolean): Mono<BeerDto> {
        TODO("Not yet implemented")
    }

    override fun listBeers(
        pageNumber: Int?,
        pageSize: Int?,
        beerName: String?,
        beerStyle: String?,
        showInventoryOnHand: Boolean?
    ): Mono<BeerPagedList?> {
        return webClient.get().uri(WebClientProperties().BEER_V1_PATH)
            .retrieve()
            .bodyToMono(BeerPagedList::class.java)
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