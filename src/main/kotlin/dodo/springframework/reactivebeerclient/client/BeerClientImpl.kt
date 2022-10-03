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
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(WebClientProperties().BEER_V1_PATH + "/" + id.toString())
                    .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                    .build()
            }.retrieve().bodyToMono(BeerDto::class.java);

    }

    override fun listBeers(
        pageNumber: Int?, pageSize: Int?, beerName: String?, beerStyle: String?, showInventoryOnHand: Boolean?
    ): Mono<BeerPagedList?> {
        return webClient.get().uri { uriBuilder ->
            uriBuilder.path(WebClientProperties().BEER_V1_PATH)
                .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                .queryParamIfPresent("showInventoryOnhand", Optional.ofNullable(showInventoryOnHand)).build()
        }.retrieve().bodyToMono(BeerPagedList::class.java)
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
        return webClient.get().uri {
            it.path(WebClientProperties().BEER_V1_UPC_PATH + "/" + upc)
                .build()
        }
            .retrieve()
            .bodyToMono(BeerDto::class.java)
    }
}