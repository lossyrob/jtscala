package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class PointSet(ps:Set[Point]) extends GeometrySet {
  val geom = factory.createMultiPoint(ps.map(_.geom).toArray)

  def convexHull:Polygon = 
    geom.convexHull.asInstanceOf[jts.Polygon]

  def intersection(l:Line):PointSetIntersectionResult =
    geom.intersection(l.geom) match {
      case p:jts.Point => if(p.isEmpty) NoResult else PointResult(p)
      case mp:jts.MultiPoint => PointSetResult(mp)
      case x => 
        sys.error(s"Unexpected result from multipoint and line intersection: $x")
    }

  def intersection(poly:Polygon):PointSetIntersectionResult =
    geom.intersection(poly.geom) match {
      case p:jts.Point => if(p.isEmpty) NoResult else PointResult(p)
      case mp:jts.MultiPoint => PointSetResult(mp)
      case x => 
        sys.error(s"Unexpected result from multipoint and polygon intersection: $x")
    }

  def intersection(mg:GeometrySet):PointSetIntersectionResult =
    geom.intersection(mg.geom) match {
      case p:jts.Point => if(p.isEmpty) NoResult else PointResult(p)
      case mp:jts.MultiPoint => PointSetResult(mp)
      case x => 
        sys.error(s"Unexpected result from multipoint and multigeom intersection: $x")
    }
}
