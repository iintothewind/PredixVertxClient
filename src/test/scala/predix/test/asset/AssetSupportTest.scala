package predix.test.asset

import java.util.concurrent.CountDownLatch

import io.vertx.scala.ext.auth.oauth2.AccessToken
import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}
import predix.{AssetSupport, ClientLike, UaaSupport}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class AssetSupportTest extends AssetSupport with UaaSupport with ClientLike with FlatSpecLike with Matchers with BeforeAndAfterEach {

  trait Builder {
    val latch = new CountDownLatch(1)
    implicit val token: Future[AccessToken] = accessToken
  }

  "An AssetSupport" should "be able to do collections" in new Builder {
    collections().onComplete {
      case Success(result) => log.info(s"result: ${result.body()}"); latch.countDown()
      case Failure(cause) => log.info(s"fail: $cause"); latch.countDown()
    }
    latch.await()
  }
}
