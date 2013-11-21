package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class Polygon(geom:jts.Polygon) extends Geometry {
  def intersection(p:Point) = p.intersection(this)
  def intersection(l:Line) = l.intersection(this)

  def intersection(p:Polygon):PolygonPolygonIntersectionResult =
    geom.intersection(p.geom) match {
      case p:jts.Point => PointResult(p)
      case ps:jts.MultiPoint => PointSeqResult(ps)
      case l:jts.LineString => LineResult(l)
      case ls:jts.MultiLineString => LineSeqResult(ls)
      case p:jts.Polygon => PolygonResult(p)
      case ps:jts.MultiPolygon => PolygonSeqResult(ps)
      case gc:jts.GeometryCollection => GeometryCollectionResult(gc)
      case _ => NoResult
    }
}

object Polygon {
  implicit def jtsToPolygon(geom:jts.Polygon):Polygon = Polygon(geom)
}
