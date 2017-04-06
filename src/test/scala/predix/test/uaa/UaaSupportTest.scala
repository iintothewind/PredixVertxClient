package predix.test.uaa

import java.util.concurrent.CountDownLatch

import io.vertx.scala.ext.auth.oauth2.AccessToken
import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}
import predix.{ClientLike, UaaSupport}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class UaaSupportTest extends UaaSupport with ClientLike with FlatSpecLike with Matchers with BeforeAndAfterEach {

  trait Builder {
    val latch = new CountDownLatch(1)
    val token: Future[AccessToken] = accessToken
  }

  "An UaaSupport Client" should "be able to fetch token" in new Builder {
    token.onComplete {
      case Success(result) => println(result.principal()); latch.countDown()
      case Failure(exception) => println(s"fail: $exception"); latch.countDown()
    }
    latch.await()
  }

}
