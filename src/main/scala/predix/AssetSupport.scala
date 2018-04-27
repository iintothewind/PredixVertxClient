package predix

import io.vertx.core.buffer.Buffer
import io.vertx.scala.ext.auth.oauth2.AccessToken
import io.vertx.scala.ext.web.client.HttpResponse

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait AssetSupport {
  client: ClientLike =>
  implicit lazy val host: String = cfg.getString("predix.asset.host")
  implicit lazy val port: Int = cfg.getInt("predix.asset.port")
  implicit lazy val zoneId: String = cfg.getString("predix.asset.zoneId")

  def collections()(implicit token: Future[AccessToken]): Future[HttpResponse[Buffer]] =
    token.flatMap(t =>
      webClient
        .get(port, host, "/")
        .putHeader("Authorization", s"Bearer ${t.principal().getString("access_token")}")
        .putHeader("predix-zone-id", zoneId)
        .sendFuture())
}
