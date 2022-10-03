package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.config.WebClientConfig
import dodo.springframework.reactivebeerclient.model.BeerDto
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import java.util.*


internal class BeerClientImplTest {
    private lateinit var beerClient: BeerClient;

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
    }

    @Test
    fun `listBeersPageSize 10`() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(1, 10, null, null, null)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;

        assertThat(pagedList).isNotNull;
        assertThat(pagedList.content.size).isEqualTo(10);
    }

    @Test
    fun listBeersNoRecords() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(10, 20, null, null, null)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;

        assertThat(pagedList).isNotNull;
        assertThat(pagedList.content.size).isEqualTo(0);
    }

    @Disabled("API returning inventory when should not be")
    @Test
    fun findBeerById() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, false)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;
        val beerId: UUID = pagedList.content[0]?.id!!;
        val beerFindByIdMono: Mono<BeerDto> = beerClient.findBeerById(beerId, false);

        val beerDto: BeerDto = beerFindByIdMono.block()!!;

        assertThat(beerDto.id).isEqualTo(beerId);
        assertThat(beerDto.quantityOnHand).isNull();
    }

    @Test
    fun findBeerByIdShowInventoryTrue() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, true)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;
        val beerId: UUID = pagedList.content[1]?.id!!;
        val beerFindByIdMono: Mono<BeerDto> = beerClient.findBeerById(beerId, true);

        val beerDto: BeerDto = beerFindByIdMono.block()!!;

        assertThat(beerDto.id).isEqualTo(beerId);
        assertThat(beerDto.quantityOnHand).isNotNull;
    }

    @Test
    fun getBeerByUpc() {
//        find beer all
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, true)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;
//        get id of first beer
        val upc : String = pagedList.content[0]?.upc.toString();
//        use id to get beer
        val beerDtoMono:Mono<BeerDto> = beerClient.getBeerByUpc(upc);
        val beerDto: BeerDto? = beerDtoMono.block();
//        check if beer is not null
        assertNotNull(beerDto);
//        check if beer id is equal to id of first beer
        assertThat(beerDto?.upc.toString()).isEqualTo(upc)

    }
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

