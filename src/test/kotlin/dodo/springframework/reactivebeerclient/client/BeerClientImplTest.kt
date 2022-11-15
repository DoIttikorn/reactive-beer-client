package dodo.springframework.reactivebeerclient.client

import dodo.springframework.reactivebeerclient.config.WebClientConfig
import dodo.springframework.reactivebeerclient.model.BeerDto
import dodo.springframework.reactivebeerclient.model.BeerPagedList
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.validator.internal.util.Contracts.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicReference


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

    //    learn reactive threading
    @Test
    fun functionalTestGetBeerById() {
        val beerName: AtomicReference<String> = AtomicReference<String>();
        val countDownLatch: CountDownLatch = CountDownLatch(1);
        beerClient
            .listBeers(null, null, null, null, false)
            .map { beerPageList -> beerPageList?.content?.get(0)?.id }
            .map { beerId -> beerClient.findBeerById(beerId!!, false) }
            .flatMap { mono -> mono }
            .subscribe { beerDto ->
                beerName.set(beerDto.beerName)
                countDownLatch.countDown()
            }

        countDownLatch.await();
        assertThat(beerName.get()).isEqualTo("Mango Bobs");
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
        val upc: String = pagedList.content[0]?.upc.toString();
//        use id to get beer
        val beerDtoMono: Mono<BeerDto> = beerClient.getBeerByUpc(upc);
        val beerDto: BeerDto? = beerDtoMono.block();
//        check if beer is not null
        assertNotNull(beerDto);
//        check if beer id is equal to id of first beer
        assertThat(beerDto?.upc.toString()).isEqualTo(upc)

    }

    @Test
    fun createBeer() {
        val beerDto: BeerDto = BeerDto(
            beerName = "BeerName",
            beerStyle = "Ale",
            upc = "123456789012",
            quantityOnHand = 100,
            price = 12.99
        );
        val responseEntityMono: Mono<ResponseEntity<BeerDto>> = beerClient.createBeer(beerDto);
        val responseEntity: ResponseEntity<BeerDto>? = responseEntityMono.block();
        assertThat(responseEntity?.statusCode).isEqualTo(HttpStatus.CREATED);
    }

    @Disabled("Disabled until we figure out localhost issue with WebClient")
    @Test
    fun updateBeer() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, true)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;
        val beerId: UUID = pagedList.content[0]?.id!!;
        val beerFindByIdMono: Mono<BeerDto> = beerClient.findBeerById(beerId, true);
        val beerDto: BeerDto = beerFindByIdMono.block()!!;


//        beerDto.name = "New Beer Name";
//        beerDto.price = 23234.99;
        val responseEntityMono: Mono<ResponseEntity<Void>> = beerClient.updateBeer(beerDto.id!!, beerDto);
        val responseEntity: ResponseEntity<Void>? = responseEntityMono.block();
        assertThat(responseEntity?.statusCode).isEqualTo(HttpStatus.NO_CONTENT);
    }

    // learn how to test exceptions
    @Test
    fun testDeleteBeerHandleException() {
        val responseEntityMono: Mono<ResponseEntity<Void>> = beerClient.deleteBeerById(UUID.randomUUID());
        val responseEntity: ResponseEntity<Void>? = responseEntityMono.onErrorResume {
            if (it is WebClientResponseException) {
                Mono.just(ResponseEntity.status(it.statusCode).build())
            } else {
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
            }
        }.block();
        assertThat(responseEntity?.statusCode).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // learn how to test exceptions
    @Test
    fun deleteBeerByIdNotFound() {
        val responseEntityMono: Mono<ResponseEntity<Void>> = beerClient.deleteBeerById(UUID.randomUUID());
        assertThrows<WebClientResponseException> {
            val responseEntity: ResponseEntity<Void>? = responseEntityMono.block();
            assertThat(responseEntity?.statusCode).isEqualTo(HttpStatus.NOT_FOUND);

        }
    }

    //learn delete beer
    @Test
    fun deleteBeerById() {
        val beerPagedListMono: Mono<BeerPagedList?> = beerClient.listBeers(null, null, null, null, true)
        val pagedList: BeerPagedList = beerPagedListMono.block()!!;
        val beerId: UUID = pagedList.content[0]?.id!!;
        val responseEntityMono: Mono<ResponseEntity<Void>> = beerClient.deleteBeerById(beerId);
        val responseEntity: ResponseEntity<Void>? = responseEntityMono.block();
        assertThat(responseEntity?.statusCode).isEqualTo(HttpStatus.NO_CONTENT);
    }
}



