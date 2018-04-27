package predix

import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.scala.ext.auth.oauth2.{AccessToken, OAuth2Auth, OAuth2ClientOptions}

import scala.concurrent.Future

trait UaaSupport {
  client: ClientLike =>
  def accessToken(siteUrl: String, clientId: String, clientSecret: String): Future[AccessToken] = {
    OAuth2Auth
      .create(
        vertx,
        OAuth2FlowType.CLIENT,
        OAuth2ClientOptions
          .fromJson(webClientOptions.asJava.toJson)
          .setSite(siteUrl)
          .setClientID(clientId)
          .setClientSecret(clientSecret))
      .getTokenFuture(new JsonObject())
  }

  def accessToken: Future[AccessToken] = {
    OAuth2Auth
      .create(
        vertx,
        OAuth2FlowType.CLIENT,
        OAuth2ClientOptions
          .fromJson(webClientOptions.asJava.toJson)
          .setSite(cfg.getString("predix.uaa.url"))
          .setClientID(cfg.getString("predix.uaa.client.id"))
          .setClientSecret(cfg.getString("predix.uaa.client.secret")))
      .getTokenFuture(new JsonObject())
  }
}
