package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.config.WebClientConfig
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono


internal class BeerClientImplTest {
    private lateinit var beerClient: BeerClient ;

    @BeforeEach
    fun setUp() {
        beerClient = BeerClientImpl(WebClientConfig().webClient())
    }


    @Test
    fun listBeers() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, null)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;

        assertThat(pagedList).isNotNull;
        assertThat(pagedList.content.size).isGreaterThan(0);
        println()
        println(pagedList.toString())
    }

    @Test
    fun findBeerById() {
    }


    @Test
    fun createBeer() {
    }

    @Test
    fun updateBeer() {
    }

    @Test
    fun deleteBeerById() {
    }

    @Test
    fun getBeerByUpc() {
    }
}