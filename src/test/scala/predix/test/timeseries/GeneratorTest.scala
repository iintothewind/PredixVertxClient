package predix.test.timeseries

import org.scalatest.{BeforeAndAfterEach, FlatSpecLike, Matchers}

class GeneratorTest extends FlatSpecLike with Matchers with BeforeAndAfterEach {
  "A Generator" should "be able to generate data" in {
    println(Generator.genMessage("sampleTag", 9, 3D, "sampleAttribute", 4))
  }

  it should "be able to generate more data" in {
    println(Generator.genMessage("pressure", 9, 30D, "sample", 4))
  }
}
