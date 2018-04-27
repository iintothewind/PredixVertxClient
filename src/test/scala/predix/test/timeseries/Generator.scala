package predix.test.timeseries


import io.vertx.core.json.{JsonArray, JsonObject}

import scala.compat.Platform.currentTime
import scala.util.Random

object Generator {
  def genDataPts(maxMeasurement: Double): Stream[JsonArray] = {
    require(maxMeasurement > 1, "maxMeasurement must be bigger than 1")
    Stream.iterate(
      new JsonArray()
        .add(currentTime)
        .add(Random.nextInt(maxMeasurement.toInt).toDouble + Random.nextDouble())
        .add(Random.nextInt(4)))(ja =>
      new JsonArray()
        .add(ja.getLong(0) + 1000)
        .add(Random.nextInt(maxMeasurement.toInt).toDouble + Random.nextDouble())
        .add(Random.nextInt(4)))
  }

  def genAttributes(attributeName: String, maxAttributeValue: Int): JsonObject = {
    require(Option(attributeName).isDefined, "attributeName must not be empty")
    require(maxAttributeValue > 1, "maxAttributeValue must be bigger than 1")
    new JsonObject().put(attributeName, Random.nextInt(maxAttributeValue).toString)
  }

  def genMessage(tag: String,
                 numOfDpts: Int,
                 maxMeasurement: Double,
                 attributeName: String,
                 maxAttributeValue: Int): JsonObject = {
    require(Option(tag).isDefined, "tag must not be empty")
    require(numOfDpts > 0, "number of data points must be greater than 0")
    new JsonObject()
      .put("messageId", currentTime)
      .put("body",
        new JsonArray()
          .add(
            new JsonObject()
              .put("name", tag)
              .put("datapoints",
                genDataPts(maxMeasurement)
                  .take(numOfDpts)
                  .toList
                  .foldRight(new JsonArray())((point, array) => array.add(point)))
              .put("attributes", genAttributes(attributeName, maxAttributeValue))))
  }

}
