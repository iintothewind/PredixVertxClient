package predix.test

import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}

import scala.util.Try


class ConfigTest extends FlatSpecLike with Matchers with BeforeAndAfterEach {
  "Config" should "be able to load" in {
    println(Try(ConfigFactory.load("vertx.conf")).getOrElse(ConfigFactory.empty()
      .withValue("vertx.http.ssl", ConfigValueFactory.fromAnyRef(true))
      .withValue("vertx.http.trustAll", ConfigValueFactory.fromAnyRef(true))
      .withValue("vertx.http.followingRediects", ConfigValueFactory.fromAnyRef(true))
      .withValue("vertx.http.tryUseCompression", ConfigValueFactory.fromAnyRef(true))
      .withValue("predix.timeseries.origin", ConfigValueFactory.fromAnyRef("https://www.predix.io")))
      .getString("vertx.http.proxy.host"))
  }
}
