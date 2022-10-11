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
                uriBuilder.path(WebClientProperties().BEER_V1_PATH_GET_BY_ID)
                    .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
                    .build(id)
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
        return webClient.post().uri {
            uriBuilder -> uriBuilder.path(WebClientProperties().BEER_V1_PATH).build()
        }
            .body(Mono.just(beerDto), BeerDto::class.java)
            .retrieve()
            .toEntity(BeerDto::class.java)
    }

    override fun updateBeer(beerDto: BeerDto): Mono<ResponseEntity<BeerDto>> {
        TODO("Not yet implemented")
    }

    override fun updateBeer(id: UUID,beerDto: BeerDto): Mono<ResponseEntity<Void>> {
        return webClient.put().uri {
            uriBuilder -> uriBuilder.path(WebClientProperties().BEER_V1_PATH_GET_BY_ID).build(id)
        }
            .body(Mono.just(beerDto), BeerDto::class.java)
            .retrieve()
            .toBodilessEntity()
    }

    override fun deleteBeerById(id: UUID): Mono<ResponseEntity<Void>> {
        return webClient.delete().uri {
            uriBuilder -> uriBuilder.path(WebClientProperties().BEER_V1_PATH_GET_BY_ID)
                                    .build(id)
        }
            .retrieve()
            .toBodilessEntity()
    }

    override fun getBeerByUpc(upc: String): Mono<BeerDto> {
        return webClient.get().uri {
            it.path(WebClientProperties().BEER_V1_UPC_PATH)
                .build(upc)
        }
            .retrieve()
            .bodyToMono(BeerDto::class.java)
    }
}