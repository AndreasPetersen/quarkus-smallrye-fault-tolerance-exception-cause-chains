package org.acme;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import io.quarkus.test.junit.QuarkusTest;
import java.net.HttpURLConnection;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class GreetingResourceTest {
    @ConfigProperty(name = "port")
    int port;

    WireMockServer wireMockServer;

    @BeforeEach
    void beforeEach() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
    }

    @AfterEach
    void afterEach() {
        wireMockServer.stop();
    }

    @Test
    void testHelloEndpoint() {
        UrlPattern url = WireMock.urlEqualTo("/notFound");
        wireMockServer.stubFor(WireMock.get(url)
                .willReturn(WireMock.aResponse().withStatus(HttpURLConnection.HTTP_NOT_FOUND)));

        given()
            .when().get("/hello")
            .then().statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);

        wireMockServer.verify(1, WireMock.getRequestedFor(url));
    }
}