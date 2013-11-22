package jtscala

import com.vividsolutions.jts.{geom=>jts}
import GeomFactory._

case class PolygonSet(ps:Set[Polygon]) extends GeometrySet {
  val geom = factory.createMultiPolygon(ps.map(_.geom).toArray)

  def &(l:Line) = intersection(l)
  def intersection(l:Line):LineSetIntersectionResult = 
    geom.intersection(l.geom)

  def intersection(ls:LineSet):LineSetIntersectionResult = ls.intersection(this)
  def &(ls:LineSet):LineSetIntersectionResult = intersection(ls)

  def &(p:Polygon) = intersection(p)
  def intersection(p:Polygon):PolygonSetIntersectionResult = 
    geom.intersection(p.geom)

  def &(ps:PolygonSet) = intersection(ps)
  def intersection(ps:PolygonSet):PolygonSetIntersectionResult =
    geom.intersection(ps.geom)
}
