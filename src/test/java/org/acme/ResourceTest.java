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
class ResourceTest {
    @ConfigProperty(name = "port")
    int port;
    UrlPattern url = WireMock.urlEqualTo("/notFound");

    WireMockServer wireMockServer;

    @BeforeEach
    void beforeEach() {
        wireMockServer = new WireMockServer(port);
        wireMockServer.start();
        wireMockServer.stubFor(WireMock.get(url)
                .willReturn(WireMock.aResponse().withStatus(HttpURLConnection.HTTP_NOT_FOUND)));
    }

    @AfterEach
    void afterEach() {
        wireMockServer.resetAll();
        wireMockServer.stop();
    }

    @Test
    void async() {
        given()
            .when().get("/async")
            .then().statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);

        wireMockServer.verify(1, WireMock.getRequestedFor(url));
    }

    @Test
    void sync() {
        given()
                .when().get("/sync")
                .then().statusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);

        wireMockServer.verify(1, WireMock.getRequestedFor(url));
    }
}