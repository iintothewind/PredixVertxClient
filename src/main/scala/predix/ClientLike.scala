package predix

import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}
import com.typesafe.scalalogging.Logger
import io.vertx.core.net.ProxyType
import io.vertx.scala.core.Vertx
import io.vertx.scala.core.http.HttpClient
import io.vertx.scala.core.net.ProxyOptions
import io.vertx.scala.ext.web.client.{WebClient, WebClientOptions}

import scala.util.Try


trait ClientLike {
  implicit lazy val log = Logger("vertx")
  implicit lazy val cfg: Config = Try(ConfigFactory.load("vertx.conf")).getOrElse(ConfigFactory.empty()
    .withValue("vertx.http.ssl", ConfigValueFactory.fromAnyRef(true))
    .withValue("vertx.http.trustAll", ConfigValueFactory.fromAnyRef(true))
    .withValue("vertx.http.followRedirects", ConfigValueFactory.fromAnyRef(true))
    .withValue("vertx.http.tryUseCompression", ConfigValueFactory.fromAnyRef(true))
    .withValue("vertx.http.maxPoolSize", ConfigValueFactory.fromAnyRef(10))
    .withValue("predix.timeseries.origin", ConfigValueFactory.fromAnyRef("https://www.predix.io")))
  implicit lazy val vertx: Vertx = Vertx.vertx()
  implicit lazy val proxyOptions: Option[ProxyOptions] = Try(ProxyOptions().setType(ProxyType.HTTP).setHost(cfg.getString("vertx.http.proxy.host")).setPort(cfg.getInt("vertx.http.proxy.port"))).toOption
  implicit lazy val webClientOptions: WebClientOptions = proxyOptions match {
    case None => WebClientOptions().setSsl(cfg.getBoolean("vertx.http.ssl")).setTrustAll(cfg.getBoolean("vertx.http.trustAll")).setFollowRedirects(cfg.getBoolean("vertx.http.followRedirects")).setTryUseCompression(cfg.getBoolean("vertx.http.tryUseCompression")).setMaxPoolSize(cfg.getInt("vertx.http.maxPoolSize"))
    case Some(proxy) => WebClientOptions().setSsl(cfg.getBoolean("vertx.http.ssl")).setTrustAll(cfg.getBoolean("vertx.http.trustAll")).setFollowRedirects(cfg.getBoolean("vertx.http.followRedirects")).setTryUseCompression(cfg.getBoolean("vertx.http.tryUseCompression")).setMaxPoolSize(cfg.getInt("vertx.http.maxPoolSize")).setProxyOptions(proxy)
  }
  implicit lazy val httpClient: HttpClient = vertx.createHttpClient(webClientOptions)
  implicit lazy val webClient: WebClient = WebClient.wrap(httpClient, webClientOptions)
}
