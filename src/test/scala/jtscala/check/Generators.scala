package jtscala.check

import com.vividsolutions.jts.geom._

import org.scalacheck._
import Prop._
import Gen._
import Arbitrary._

// object PointSpecification extends Properties("Point") {
//   import Generators._

//   property("getEnvelope") = forAll { (p: Point) =>
//     p.getEnvelope match {
//       case x:Point => true
//       case _ => false
//     }
//   }

//   property("buffer") = forAll { (p: Point, d: Double) =>
//     p.buffer(d) match {
//       case x:Polygon => true
//       case _ => false
//     }
//   }

// }

// object Generators {
//   lazy val genPoint: Gen[Point] = 
//     for {
//       x <- arbitrary[Double]
//       y <- arbitrary[Double]
//     } yield Point(x, y)

//   implicit lazy val argPoint: Arbitrary[Point] =
//     Arbitrary(genPoint)
// }
