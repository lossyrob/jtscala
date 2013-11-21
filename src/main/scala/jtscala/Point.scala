package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class Point(geom:jts.Point) extends Geometry {
  val x = geom.getX
  val y = geom.getY

  def intersection(other:Geometry):Option[Point] =
    geom.intersection(other.geom) match {
      case p:jts.Point => Some(Point(p))
      case _ => None
    }

  // def buffer(d:Double):Polygon =
  //   Polygon(geom.buffer(d).asInstanceOf[jts.Polygon])
}

object Point {
  def apply(x:Double,y:Double):Point =
    Point(factory.createPoint(new jts.Coordinate(x,y)))

  implicit def jts2Point(geom:jts.Point) = apply(geom)
}
