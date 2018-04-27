package predix

import io.vertx.core.buffer.Buffer
import io.vertx.scala.core.MultiMap
import io.vertx.scala.core.http.RequestOptions
import io.vertx.scala.ext.auth.oauth2.AccessToken
import io.vertx.scala.ext.web.client.HttpResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

trait TimeSeriesSupport {
  client: ClientLike =>
  implicit lazy val host: String = cfg.getString("predix.timeseries.host")
  implicit lazy val port: Int = cfg.getInt("predix.timeseries.port")
  implicit lazy val zoneId: String = cfg.getString("predix.timeseries.zoneId")
  implicit lazy val ingestUri: String = cfg.getString("predix.timeseries.ingestUri")
  implicit lazy val origin: String = cfg.getString("predix.timeseries.origin")

  def ingest(buffer: Buffer)(implicit token: Future[AccessToken]): Unit = {
    token.foreach { t =>
      httpClient.websocket(
        RequestOptions().setPort(port).setHost(host).setURI(ingestUri),
        MultiMap
          .caseInsensitiveMultiMap()
          .add("content-type", "application/json")
          .add("Authorization", s"Bearer ${t.principal().getString("access_token")}")
          .add("predix-zone-id", zoneId)
          .add("Origin", origin),
        ws =>
          Try {
            ws.write(buffer)
          } match {
            case Success(_) => ws.close()
            case Failure(e) => log.warn(s"webSocket.write() error, message = $buffer")
          },
        throwable => log.warn(s"webSocket.write() error $throwable")
      )
    }
  }

  def tags()(implicit token: Future[AccessToken]): Future[HttpResponse[Buffer]] = {
    token.flatMap(t =>
      webClient
        .get(port, host, "/v1/tags")
        .putHeader("Authorization", s"Bearer ${t.principal().getString("access_token")}")
        .putHeader("predix-zone-id", zoneId)
        .sendFuture())
  }
}
