package dodo.springframework.reactivebeerclient.config

import org.springframework.context.annotation.Configuration

@Configuration
data class WebClientProperties(
    public val BASE_URL: String = "http://api.springframework.guru",
    public val BEER_V1_PATH: String = "/api/v1/beer",
)