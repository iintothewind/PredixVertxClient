package predix.test

import java.util.concurrent.CountDownLatch

import com.typesafe.scalalogging.Logger
import io.vertx.core.buffer.Buffer
import io.vertx.scala.core.MultiMap
import io.vertx.scala.core.http.{RequestOptions, WebSocket}

import scala.util.{Failure, Success, Try}

class TimeSeriesClient extends ClientLike {
  val log = Logger("test")

  trait Builder {
    val latch = new CountDownLatch(1)
  }

  "A TimeSeriesClient" should "be able to ingest data" in new Builder {
    httpClient.websocket(RequestOptions().setPort(443)
      .setHost("gateway-predix-data-services.run.aws-jp01-pr.ice.predix.io")
      .setURI("/v1/stream/messages"),
      MultiMap.caseInsensitiveMultiMap().add("content-type", "application/json")
        .add("predix-zone-id", "1ab6aec0-2a97-4cbf-a951-bdb092ec6e0d")
        .add("Authorization", "Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImxlZ2FjeS10b2tlbi1rZXkiLCJ0eXAiOiJKV1QifQ.eyJqdGkiOiI3MzRlNDRmMDVhOWU0OTc3YmFlODE0MDQ4MTFhMzgzZiIsInN1YiI6Iml2YXIiLCJzY29wZSI6WyJjbGllbnRzLnJlYWQiLCJ0aW1lc2VyaWVzLnpvbmVzLjFhYjZhZWMwLTJhOTctNGNiZi1hOTUxLWJkYjA5MmVjNmUwZC51c2VyIiwidGltZXNlcmllcy56b25lcy4xYWI2YWVjMC0yYTk3LTRjYmYtYTk1MS1iZGIwOTJlYzZlMGQuaW5nZXN0IiwidGltZXNlcmllcy56b25lcy4xYWI2YWVjMC0yYTk3LTRjYmYtYTk1MS1iZGIwOTJlYzZlMGQucXVlcnkiLCJjbGllbnRzLndyaXRlIiwic2NpbS53cml0ZSIsInNjaW0ucmVhZCIsInByZWRpeC1hc3NldC56b25lcy41ZTEzMjNiNy1mMDE1LTRkMWYtODA3Ni04MTBhZDFlYzI2NGYudXNlciJdLCJjbGllbnRfaWQiOiJpdmFyIiwiY2lkIjoiaXZhciIsImF6cCI6Iml2YXIiLCJncmFudF90eXBlIjoiY2xpZW50X2NyZWRlbnRpYWxzIiwicmV2X3NpZyI6IjMyNjc4ZmI1IiwiaWF0IjoxNDkwNzUyMjQ1LCJleHAiOjE0OTA4Mzg2NDUsImlzcyI6Imh0dHBzOi8vZDFjM2IyZTktNGYzYS00Nzc2LTk0Y2EtZjRiM2Y5Mzk0NTg3LnByZWRpeC11YWEucnVuLmF3cy1qcDAxLXByLmljZS5wcmVkaXguaW8vb2F1dGgvdG9rZW4iLCJ6aWQiOiJkMWMzYjJlOS00ZjNhLTQ3NzYtOTRjYS1mNGIzZjkzOTQ1ODciLCJhdWQiOlsic2NpbSIsImNsaWVudHMiLCJ0aW1lc2VyaWVzLnpvbmVzLjFhYjZhZWMwLTJhOTctNGNiZi1hOTUxLWJkYjA5MmVjNmUwZCIsInByZWRpeC1hc3NldC56b25lcy41ZTEzMjNiNy1mMDE1LTRkMWYtODA3Ni04MTBhZDFlYzI2NGYiLCJpdmFyIl19.bFn15hjmfiknJOjEQJJkGAeGNLDsff0NDitxpv3hKGC3YE2Ap72BogwNUfie1OxpseNIevR4l9ygiO5TN4qackWvyLX6V2jOlMRzWU9fhiCixyRRkDb3w61WX_0ysW6gj3Tlrvs_BVxqbHW8GMO4yAmwkbg_fX9cua9oyDveVZBz0RQJGZIiadNNE2OWWug2kyp5_y-CxMhqy6tnn08-l_PCJn6PQLcZ9K3d845qd-QwQWMp6NlqcxG8uOxmjp42rqr02JRlWSJaCKOEKWffrnbfKZsrKPHw5O7f56zWV_wDS5_by7i1C77gjsj6ViaPiRB8PVyqm2xL06IbPu7pig")
        .add("Origin", "http://www.predix.io/"),
      (ws: WebSocket) => {
        Try {
          ws.write(Buffer.buffer("{\"messageId\":\"1453338376222\",\"body\":[{\"name\":\"Compressor-2015:CompressionRatio\",\"datapoints\":[[1453338376222,10,3],[1453338377222,10,1]],\"attributes\":{\"host\":\"server1\",\"customer\":\"Acme\"}}]}"))
        } match {
          case Success(_) => ws.close(); latch.countDown()
          case Failure(e) => e.printStackTrace(); latch.countDown()
        }
      },
      (e: Throwable) => {
        e.printStackTrace()
        latch.countDown()
      }
    )
    latch.await()
  }


}
