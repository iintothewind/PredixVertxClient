package predix.test

import java.util.Base64
import java.util.concurrent.CountDownLatch

import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.oauth2.OAuth2FlowType
import io.vertx.scala.core.{MultiMap, Vertx}
import io.vertx.scala.ext.auth.oauth2.{OAuth2Auth, OAuth2ClientOptions}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class UaaClient extends ClientLike {

  trait Builder {
    val latch = new CountDownLatch(1)
  }

  "An http client" should "be able to fetch token by GET" in new Builder {
    webClient.getAbs("https://d1c3b2e9-4f3a-4776-94ca-f4b3f9394587.predix-uaa.run.aws-jp01-pr.ice.predix.io/oauth/token")
      .putHeader("authorization", "Basic ".concat(Base64.getEncoder.encodeToString("ivar:admin".getBytes())))
      .addQueryParam("grant_type", "client_credentials")
      .sendFuture()
      .onComplete {
        case Success(result) => println(result.getClass); println(s"result: ${result.body}"); latch.countDown()
        case Failure(exception) => println(s"fail: $exception"); latch.countDown()
      }
    latch.await()
  }

  "An http client" should "be able to fetch token by POST" in new Builder {
    webClient.postAbs("https://d1c3b2e9-4f3a-4776-94ca-f4b3f9394587.predix-uaa.run.aws-jp01-pr.ice.predix.io/oauth/token")
      .putHeader("authorization", "Basic ".concat(Base64.getEncoder.encodeToString("ivar:admin".getBytes())))
      .sendFormFuture(MultiMap.caseInsensitiveMultiMap().add("client_id", "ivar").add("grant_type", "client_credentials"))
      .onComplete {
        case Success(result) => println(s"result: ${result.body}"); latch.countDown()
        case Failure(exception) => println(s"fail: $exception"); latch.countDown()
      }
    latch.await()
  }

  "An OAuth client" should "be able to fetch token" in new Builder {
    OAuth2Auth.create(Vertx.vertx(), OAuth2FlowType.CLIENT,
      OAuth2ClientOptions.fromJson(webClientOptions.asJava.toJson).setSite("https://d1c3b2e9-4f3a-4776-94ca-f4b3f9394587.predix-uaa.run.aws-jp01-pr" +
        ".ice.predix.io")
        .setClientID("ivar").setClientSecret("admin")).getTokenFuture(new JsonObject()).onComplete {
      case Success(result) => println(result.opaqueAccessToken()); latch.countDown()
      case Failure(exception) => println(s"fail: $exception"); latch.countDown()
    }
    latch.await()
  }

}
