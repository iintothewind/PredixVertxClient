package predix.test

import io.vertx.core.net.ProxyType
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.http.HttpClient
import io.vertx.scala.core.net.ProxyOptions
import io.vertx.scala.ext.web.client.{WebClient, WebClientOptions}
import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}


trait ClientLike extends FlatSpecLike with Matchers with BeforeAndAfterEach {
  val vertx: Vertx = Vertx.vertx()
  val proxyOptions: ProxyOptions = ProxyOptions().setType(ProxyType.HTTP).setHost("3.20.128.6").setPort(88)
  //val proxyOptions: ProxyOptions = ProxyOptions().setType(ProxyType.HTTP).setHost("192.168.0.161").setPort(8123)
  val webClientOptions: WebClientOptions = WebClientOptions().setSsl(true).setKeepAlive(true).setTrustAll(true).setFollowRedirects(true)
    .setTryUseCompression(true).setProxyOptions(proxyOptions)
//  val webClientOptions: WebClientOptions = WebClientOptions().setSsl(true).setKeepAlive(true).setTrustAll(true).setFollowRedirects(true)
//    .setTryUseCompression(true)
  val httpClient: HttpClient = vertx.createHttpClient(webClientOptions)
  val webClient: WebClient = WebClient.wrap(httpClient, webClientOptions)
}
