package jtscala.check.jts

import com.vividsolutions.jts.geom._

import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._

object Generators {
  val factory = new GeometryFactory()

  lazy val genCoordinate: Gen[Coordinate] = 
    for {
      x <- choose(-99999999999999999999.0,99999999999999999999.0)
      y <- choose(-99999999999999999999.0,99999999999999999999.0)
    } yield new Coordinate(x, y)

  lazy val genPoint:Gen[Point] = 
    genCoordinate.map(factory.createPoint(_))

  lazy val genLineString:Gen[LineString] =
    for {
      size <-Gen.choose(2,40)
      s <- Gen.containerOfN[Set,Coordinate](size,genCoordinate)
    } yield { factory.createLineString(s.toArray) }

  // Doesn't yet deal with interior rings
  lazy val genPolygon:Gen[Polygon] =
    for {
      size <-Gen.choose(6,50)
      shareSize <- Gen.choose(3,size)
      subSize1 <- Gen.choose(3,size)
      subSize2 <- Gen.choose(3,size)
      fullSet <- Gen.containerOfN[Set,Coordinate](size,genCoordinate)
      sharedSet <- Gen.pick(shareSize,fullSet)
      subSet1 <- Gen.pick(subSize1,fullSet)
      subSet2 <- Gen.pick(subSize2,fullSet)
    } yield {
      val polyOne = 
        factory.createMultiPoint((subSet1 ++ sharedSet).toArray).convexHull.asInstanceOf[Polygon]
      val polyTwo =
        factory.createMultiPoint((subSet2 ++ sharedSet).toArray).convexHull.asInstanceOf[Polygon]
      polyOne.intersection(polyTwo).asInstanceOf[Polygon]
    }

  implicit lazy val arbCoordinate: Arbitrary[Coordinate] = 
    Arbitrary(genCoordinate)

  implicit lazy val arbPoint: Arbitrary[Point] =
    Arbitrary(genPoint)

  implicit lazy val arbLineString: Arbitrary[LineString] =
    Arbitrary(genLineString)

  implicit lazy val arbPolygon: Arbitrary[Polygon] =
    Arbitrary(genPolygon)
}
