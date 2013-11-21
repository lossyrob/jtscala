package jtscala.check.jts

import com.vividsolutions.jts.geom._

import org.scalacheck._
import Prop._

object LineCheck extends Properties("Line") {
  import Generators._

  // SLOW!
  // property("buffer => Polygon") =
  //   forAll { (l:LineString,d:Double) =>
  //     l.buffer(d) match {
  //       case _:Polygon => true
  //       case x =>
  //         println(s"FAILED WITH $x")
  //         false
  //     }
  //   }

  property("difference[self]") = forAll { (l:LineString) =>
    l.difference(l).isEmpty
  }

  property("difference[self] => Empty") = 
    forAll { (l:LineString) =>
      // l.normalize
      // // val nl = factory.createLineString(
      // //   l.getCoordinateSequence.toCoordinateArray.drop(1)
      // // )

      // val nl = factory.createPoint(l.getCoordinateSequence.toCoordinateArray()(0))

      // println(l)
      // println(nl)
      l.difference(l).isEmpty//  match {
                             //   case _:MultiLineString => true
                             //   case _ => false
                             // }
    }

  property("difference[point] => (MultiLineString,LineString)") = 
    forAll { (l:LineString,p:Point) =>
      l.difference(p)  match {
        case _:MultiLineString => true
        case _:LineString => true
        case x =>
          println(s"FAILED WITH $x")
          false
      }
    }

  property("difference[other line] => (MultiLineString,LineString)") = 
    forAll { (l:LineString,l2:LineString) =>
      l.difference(l2)  match {
        case _:MultiLineString => true
        case _:LineString => true
        case x =>
          println(s"FAILED WITH $x")
          false
      }
    }

  property("difference[polygon] => (MultiLineString,LineString,Empty)") = 
    forAll { (l:LineString,p:Polygon) =>
      l.difference(p)  match {
        case _:MultiLineString => true
        case _:LineString => true
        case x =>
          if(x.isEmpty) {
            p.contains(l)
          } else {
            println(s"FAILED WITH $x")
            false
          }
      }
    }

  property("intersection[point] => Point") =
    forAll { (l:LineString,p:Point) =>
      l.intersection(p)  match {
        case _:Point => true
        case x =>
          if(x.isEmpty) {
            l.disjoint(p)
          } else {
            println(s"FAILED WITH $x")
            false
          }
      }
    }

  property("intersection[line] => (Point,LineString)") =
    forAll { (l:LineString,p:Point) =>
      l.intersection(p)  match {
        case _:Point => true
        case _:LineString => true
        case x =>
          if(x.isEmpty) {
            l.disjoint(p)
          } else {
            println(s"FAILED WITH $x")
            false
          }
      }
    }

  // property("getEnvelope") = forAll { (p: Point) =>
  //   p.getEnvelope match {
  //     case x:Point => true
  //     case _ => false
  //   }
  // }

  // property("buffer") = forAll { (p: Point, d: Double) =>
  //   p.buffer(d) match {
  //     case x:Polygon => true
  //     case _ => false
  //   }
  // }

  // property("convexHull") = forAll { (p: Point) =>
  //   p.convexHull match {
  //     case x:Point => true
  //     case _ => false
  //   }
  // }

  // property("covers[itself]") = forAll { (p: Point) =>
  //   p.covers(p)
  // }

  // property("covers[others]") = forAll { (p1: Point,p2: Point) =>
  //   !p1.covers(p2) || (p1 == p2)
  // }

  // property("getInteriorPoint") = forAll { (p:Point) =>
  //   p.getInteriorPoint == p
  // }

  // property("getDimension") = forAll { (p:Point) =>
  //   p.getDimension == 0
  // }

}
