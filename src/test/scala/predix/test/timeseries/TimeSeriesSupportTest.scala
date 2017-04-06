package predix.test.timeseries

import java.util.concurrent.CountDownLatch

import io.vertx.scala.ext.auth.oauth2.AccessToken
import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}
import predix.{ClientLike, TimeSeriesSupport, UaaSupport}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}


class TimeSeriesSupportTest extends TimeSeriesSupport with UaaSupport with ClientLike with FlatSpecLike with Matchers with BeforeAndAfterEach {

  trait Builder {
    val latch = new CountDownLatch(1)
    implicit val token: Future[AccessToken] = accessToken
  }

  "A TimeSeriesSupport" should "be able to do tags" in new Builder {
    tags().onComplete {
      case Success(result) => log.info(s"result: ${result.body().toString()}"); latch.countDown()
      case Failure(cause) => log.info(s"fail: $cause"); latch.countDown()
    }
    latch.await()
  }

}
