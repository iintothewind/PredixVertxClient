package predix.test

import java.util.concurrent.CountDownLatch

import com.typesafe.scalalogging.Logger
import io.vertx.core.json.{JsonArray, JsonObject}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class Sample extends ClientLike {
  val log = Logger("test")

  trait Builder {
    val latch = new CountDownLatch(1)
  }

  "A Vertx WebClient" should "be able to do GET" in new Builder {
    webClient.getAbs("https://ivar-spider.run.aws-jp01-pr.ice.predix.io/api/hello").sendFuture().onComplete {
      case Success(result) => log.info(s"result: ${result.body().toString()}"); latch.countDown()
      case Failure(cause) => log.info(s"fail: $cause"); latch.countDown()
    }
    latch.await()
  }

  "A JsonObject" should "be able to serialized to Array" in {
    new JsonArray().add(new JsonObject())
  }
}
